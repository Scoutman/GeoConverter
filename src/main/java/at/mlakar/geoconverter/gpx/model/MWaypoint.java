package at.mlakar.geoconverter.gpx.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wpt")
public class MWaypoint extends MNamedElement
{
	private Double lat;
	private Double lon;

	
	@XmlAttribute(name = "lat")
	public Double getLat()
	{
		return lat;
	}

	public void setLat(Double lat)
	{
		this.lat = lat;
	}

	public Double getLon()
	{
		return lon;
	}

	public void setLon(Double lon)
	{
		this.lon = lon;
	}

}
