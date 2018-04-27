package at.mlakar.geoconverter.geojson.model;

public class MPoint extends MType
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
