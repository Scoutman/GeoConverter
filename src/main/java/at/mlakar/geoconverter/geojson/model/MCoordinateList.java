package at.mlakar.geoconverter.geojson.model;

import java.util.ArrayList;
import java.util.List;

public class MCoordinateList extends MCoordinate
{
	private List<MCoordinate> coordinateList = new ArrayList<>();

	@Override
	public List<MCoordinate> getCoordinateList()
	{
		return coordinateList;
	}

	@Override
	public void addCoordinateList(MCoordinate coordinate)
	{
		coordinateList.add(coordinate);
	}
	

}
