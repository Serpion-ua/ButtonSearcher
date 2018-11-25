package properties;

import org.jsoup.nodes.Attribute;

public class AttributeProperty implements Property
{
  private final Attribute attribute;

  AttributeProperty(Attribute attribute)
  {
    this.attribute = attribute.clone();
  }

  @Override
  public String getName()
  {
    return attribute.getKey();
  }

  @Override
  public String getValue()
  {
    return attribute.getValue();
  }
}
