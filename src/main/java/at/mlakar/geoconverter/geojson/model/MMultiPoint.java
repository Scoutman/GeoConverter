package at.mlakar.geoconverter.geojson.model;

public class MMultiPoint extends MType
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
