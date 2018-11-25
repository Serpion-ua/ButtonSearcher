package properties;

import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;


public class PropertiesBuilder
{
  public PropertiesSet buildProperties(Element sourceElement)
  {
    Map<String, Property> buildedProperties = new HashMap<>();

    buildAttributeProperties(buildedProperties, sourceElement);
    buildTextProperty(buildedProperties, sourceElement);

    return new PropertiesSet(sourceElement, buildedProperties);
  }


  private void buildAttributeProperties(Map<String, Property> buildedProperties, Element sourceElement)
  {
    sourceElement.attributes().asList()
        .stream()
        .map(AttributeProperty::new)
        .forEach(ap -> buildedProperties.put(ap.getName(), ap));
  }

  private void buildTextProperty(Map<String, Property> buildedProperties, Element sourceElement)
  {
    if (sourceElement.hasText())
    {
      Property textProperty = new TextProperty(sourceElement.text());
      buildedProperties.put(textProperty.getName(), textProperty);
    }
  }
}


