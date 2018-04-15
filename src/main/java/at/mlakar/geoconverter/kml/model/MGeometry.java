package at.mlakar.geoconverter.kml.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ MPoint.class, MLineString.class, MLinearRing.class})
public class MGeometry
{
	MCoordinatesList mCoordinatesList = new MCoordinatesList();

	
	@XmlElement(name = "coordinates")
	public String getCoordinateList()
	{
		return mCoordinatesList.getCoordinatesListString();
	}
	
	public void setCoordinateList(String coordinateString)
	{
		mCoordinatesList.addCoordinatesString(coordinateString);
	}
	
	public MCoordinatesList getCoordinates()
	{
		return this.mCoordinatesList;
	}
	
	public void addCoordinate(MCoordinate coordinate)
	{
		this.mCoordinatesList.addCoordinate(coordinate);
	}

}
