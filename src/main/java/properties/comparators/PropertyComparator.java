package properties.comparators;

import org.apache.commons.lang3.StringUtils;
import properties.Property;

public abstract class PropertyComparator
{
  private final String propertyName;

  PropertyComparator(String propertyName)
  {
    this.propertyName = propertyName;
  }

  public final String getPropertyName()
  {
    return propertyName;
  }

  public final double checkSimilarity(Property left, Property right)
  {
    if (StringUtils.equals(left.getName(), right.getName()))
    {
      return similarity(left, right);
    }

    throw new IllegalArgumentException("Try to check inconsistent properties");
  }

  protected abstract double similarity(Property left, Property right);
}
