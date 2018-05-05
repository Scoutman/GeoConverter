package at.mlakar.geoconverter.converter.geojson.model;

public class MPolygon extends MType
{
	@Override
	public boolean isSingleGeometry()
	{
		return true;
	}

	@Override
	public boolean isMultiGeometry()
	{
		return false;
	}
}
