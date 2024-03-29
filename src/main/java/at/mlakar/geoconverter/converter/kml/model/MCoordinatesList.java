package at.mlakar.geoconverter.converter.kml.model;

import java.util.ArrayList;
import java.util.List;

public class MCoordinatesList
{
	private List<MCoordinate> coordinateList = new ArrayList<>();

	
	public void addCoordinatesString(String coordinateString)
	{
		coordinateString = coordinateString.trim().replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "")
				.replaceAll("\t", " ").replaceAll("[ ]+", " ");

		String[] splitCoordinate = coordinateString.split(" ");

		for (String stringCoordinate : splitCoordinate)
		{
			String c[] = stringCoordinate.trim().split(",");

			Double lon = Double.valueOf(c[0]);
			Double lat = Double.valueOf(c[1]);
			int altitude;

			if (c.length == 3)
			{
				altitude = Integer.parseInt(c[2]);
			}
			else
			{
				altitude = 0;
			}
			
			this.coordinateList.add(new MCoordinate(lat, lon, altitude));
		}
	}

	public void addCoordinate(MCoordinate coordinate)
	{
		this.coordinateList.add(coordinate);
	}
	
	public void removeCoordinate(int index)
	{
		this.coordinateList.remove(index);
	}

	public List<MCoordinate> getCoordinateList()
	{
		return coordinateList;
	}

	public String getCoordinatesListString()
	{
		StringBuilder coordinatesString = new StringBuilder();
		
		for (MCoordinate coordinate : coordinateList)
		{
			coordinatesString.append(coordinate.getLon()).append(",");
			coordinatesString.append(coordinate.getLat()).append(",");
			coordinatesString.append(coordinate.getAltitude()).append(" ");
		}
		
		return coordinatesString.toString();
	}
}
