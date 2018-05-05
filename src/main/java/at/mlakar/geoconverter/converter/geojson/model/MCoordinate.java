package at.mlakar.geoconverter.converter.geojson.model;

import java.util.List;

public abstract class MCoordinate
{

	public List<MCoordinate> getCoordinateList()
	{
		throw new UnsupportedOperationException();
	}

	public void addCoordinateList(MCoordinate coordinate)
	{
		throw new UnsupportedOperationException();
	}
	

}
