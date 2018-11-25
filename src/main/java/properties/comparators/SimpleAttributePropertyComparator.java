package properties.comparators;
import org.apache.commons.lang3.StringUtils;
import properties.Property;
import properties.comparators.PropertyComparator;

public class SimpleAttributePropertyComparator extends PropertyComparator
{
  SimpleAttributePropertyComparator(String propertyName)
  {
    super(propertyName);
  }

  @Override
  protected double similarity(Property left, Property right)
  {
    if ((StringUtils.equals(left.getValue(), right.getValue())))
    {
      return 1.0;
    }

    return 0.0;
  }
}
