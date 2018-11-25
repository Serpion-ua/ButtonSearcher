import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import properties.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import properties.comparators.PropertyComparatorFabric;

public class ButtonSearcher
{
  private static final Logger LOGGER = LoggerFactory.getLogger(ButtonSearcher.class);
  private static final Double SIMILARITY_MIN_THRESHOLD = 3.0;
  private static final String HTML_FILE_CHARSET_NAME = "utf8";
  private static final String SEARCHED_ELEMENT_ID = "make-everything-ok-button";
  private static final PropertiesBuilder PROPERTIES_BUILDER = new PropertiesBuilder();

  public static void main(String[] args)
  {
    if (args.length != 2)
    {
      LOGGER.error("Two parameters are required: <input_origin_file_path> <input_other_sample_file_path>");
      return;
    }

    String changedHtml = args[1];

    Optional<PropertiesSet> propertiesSet = getPropertiesOfElementInFile(new File(args[0]), SEARCHED_ELEMENT_ID);
    if (!propertiesSet.isPresent())
    {
      return;
    }

    PropertiesSimilarityChecker propertiesSimilarityChecker =
        new PropertiesSimilarityChecker(propertiesSet.get(), new PropertyComparatorFabric());

    Elements otherElements = getAllElementsInDocument(new File(changedHtml));

    Map.Entry<PropertiesSet, Double> maxSimilarElement = getMaxSimilarityValue(propertiesSimilarityChecker, otherElements);

    if (maxSimilarElement.getValue() > SIMILARITY_MIN_THRESHOLD)
    {
      LOGGER.info("Looks like next element is new element in source html:");
      LOGGER.info(maxSimilarElement.getKey().getSourceElement().toString());

      LOGGER.info("XPath to the new element is:");
      LOGGER.info(buildXPath(maxSimilarElement.getKey().getSourceElement()));

    }
  }

  private static Map.Entry<PropertiesSet, Double> getMaxSimilarityValue(
      PropertiesSimilarityChecker propertiesSimilarityChecker, Elements otherElements)
  {
    Map<PropertiesSet, Double> similarityResult =
        otherElements
            .stream()
            .map(PROPERTIES_BUILDER::buildProperties)
            .collect(Collectors.toMap(Function.identity(), propertiesSimilarityChecker::getSimilarityWithProperties));

    return Collections.max(similarityResult.entrySet(), Map.Entry.comparingByValue());
  }

  private static Optional<PropertiesSet> getPropertiesOfElementInFile(File htmlFile, String searchedElementId)
  {
    Optional<Element> searchedElement = findElementByIdInFile(htmlFile, searchedElementId);
    if (!searchedElement.isPresent())
    {
      LOGGER.error("Searched element with id " + searchedElementId + "are not present in " + htmlFile);
      return Optional.empty();
    }

    return Optional.of(PROPERTIES_BUILDER.buildProperties(searchedElement.get()));
  }


  private static Optional<Element> findElementByIdInFile(File htmlFile, String targetElementId)
  {
    try
    {
      Document doc = Jsoup.parse(
          htmlFile,
          HTML_FILE_CHARSET_NAME,
          htmlFile.getAbsolutePath());
      return Optional.of(doc.getElementById(targetElementId));

    }
    catch (IOException e)
    {
      LOGGER.error("Failed to open file" + htmlFile.getAbsolutePath() + e);
      return Optional.empty();
    }
  }

  private static Elements getAllElementsInDocument(File htmlFile)
  {
    try
    {
      Document doc = Jsoup.parse(
          htmlFile,
          HTML_FILE_CHARSET_NAME,
          htmlFile.getAbsolutePath());

      return doc.body().children().select("*");

    } catch (IOException e)
    {
      LOGGER.error("Failed to open file" + htmlFile.getAbsolutePath() + e);
      return new Elements();
    }
  }

  private static String buildXPath(Element sourceElement)
  {
    StringBuilder absPath = new StringBuilder();
    Elements parents = sourceElement.parents();

    for (int j = parents.size() - 1; j >= 0; j--)
    {
      Element element = parents.get(j);
      absPath.append("/");
      absPath.append(element.tagName());
      absPath.append("[");
      absPath.append(element.elementSiblingIndex());
      absPath.append("]");
    }

    absPath.append("/");
    absPath.append(sourceElement.tagName());
    absPath.append("[");
    absPath.append(sourceElement.elementSiblingIndex());
    absPath.append("]");

    return absPath.toString();
  }
}
