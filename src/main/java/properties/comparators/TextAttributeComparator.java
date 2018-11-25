package properties.comparators;

import org.apache.commons.lang3.StringUtils;
import properties.Property;
import properties.comparators.PropertyComparator;

public class TextAttributeComparator extends PropertyComparator
{
  TextAttributeComparator(String propertyName)
  {
    super(propertyName);
  }

  @Override
  protected double similarity(Property left, Property right)
  {
    if (StringUtils.equalsIgnoreCase(left.getValue(), right.getValue()))
    {
      return 1.5;
    }

    return 0;
  }
}
