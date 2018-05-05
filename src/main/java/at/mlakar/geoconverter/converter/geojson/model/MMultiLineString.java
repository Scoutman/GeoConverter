package at.mlakar.geoconverter.converter.geojson.model;

public class MMultiLineString extends MType
{
	@Override
	public boolean isSingleGeometry()
	{
		return false;
	}

	@Override
	public boolean isMultiGeometry()
	{
		return true;
	}
}
