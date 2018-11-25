package properties;

import properties.comparators.PropertyComparator;
import properties.comparators.PropertyComparatorFabric;

import java.util.Optional;

public class PropertiesSimilarityChecker
{
  private final PropertiesSet templateProperties;
  private final PropertyComparatorFabric propertyComparatorFabric;

  public PropertiesSimilarityChecker(PropertiesSet templateProperties, PropertyComparatorFabric propertyComparatorFabric)
  {
    this.templateProperties = templateProperties;
    this.propertyComparatorFabric = propertyComparatorFabric;
  }

  public double getSimilarityWithProperties(PropertiesSet otherProperties)
  {
    return otherProperties.getAllProperties()
            .stream()
            .mapToDouble(this::getSimilarity)
            .sum();
  }

  private double getSimilarity(Property otherProperty)
  {
    Optional<Property> templateProperty = templateProperties.getPropertyByKey(otherProperty.getName());
    if (templateProperty.isPresent())
    {
      PropertyComparator comparator = propertyComparatorFabric.getPropertyComparatorByName(templateProperty.get().getName());
      return comparator.checkSimilarity(templateProperty.get(), otherProperty);
    }

    return 0;
  }
}
