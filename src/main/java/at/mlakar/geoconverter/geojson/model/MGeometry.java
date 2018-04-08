package at.mlakar.geoconverter.geojson.model;

public class MGeometry
{
	private MType mType;
	private MCoordinate coordinatesList;

	public MGeometry(MType type)
	{
		setType(type);
	}

	public MType getType()
	{
		return mType;
	}

	public void setType(MType mType)
	{
		this.mType = mType;
	}
	
	public MCoordinate getCoordinates()
	{
		return coordinatesList;
	}

	public void setCoordinates(MCoordinate coordinates)
	{
		this.coordinatesList = coordinates;
	}

}
