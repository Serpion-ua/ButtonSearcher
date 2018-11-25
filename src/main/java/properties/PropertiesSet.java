package properties;

import org.jsoup.nodes.Element;

import java.util.*;

public class PropertiesSet
{
  private final Element sourceElement;
  private final Map<String, Property> properties;

  public PropertiesSet(Element sourceElement, Map<String, Property> properties)
  {
    this.sourceElement = sourceElement;
    this.properties = properties;
  }

  public Set<String> getPropertiesNames()
  {
    return properties.keySet();
  }

  public Optional<Property> getPropertyByKey(String propertyName)
  {
    return Optional.ofNullable(properties.get(propertyName));
  }

  public Collection<Property> getAllProperties()
  {
    return properties.values();
  }

  public Element getSourceElement()
  {
    return sourceElement;
  }
}
