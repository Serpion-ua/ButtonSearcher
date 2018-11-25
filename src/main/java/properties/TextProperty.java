package properties;

public class TextProperty implements Property
{
  public static final String TEXT_PROPERTY = "textProperty";

  private final String text;

  public TextProperty(String text)
  {
    this.text = text;
  }

  @Override
  public String getName()
  {
    return TEXT_PROPERTY;
  }

  @Override
  public String getValue()
  {
    return text;
  }
}
