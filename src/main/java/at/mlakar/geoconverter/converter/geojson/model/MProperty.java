package at.mlakar.geoconverter.converter.geojson.model;


public class MProperty extends MNamedElement
{
	private String value;

	public MProperty(String name, String value)
	{
		setName(name);
		setValue(value);
	}
	
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return "Key: " + getName() + ", Value: " + getValue(); 
	}
}
