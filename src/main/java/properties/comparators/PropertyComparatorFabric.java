package properties.comparators;

import java.util.HashMap;
import java.util.Map;

import static properties.TextProperty.TEXT_PROPERTY;

public class PropertyComparatorFabric
{
  private final Map<String, PropertyComparator> comparators = new HashMap<>();

  public PropertyComparator getPropertyComparatorByName(String propertyName)
  {
    switch (propertyName)
    {
      case TEXT_PROPERTY:
      {
        return comparators.computeIfAbsent(propertyName, name -> new TextAttributeComparator(propertyName));
      }

      default:
        return comparators.computeIfAbsent(propertyName, name -> new SimpleAttributePropertyComparator(propertyName));
    }
  }
}
