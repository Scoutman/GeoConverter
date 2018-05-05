package at.mlakar.geoconverter.converter.gpx.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wpt")
public class MWaypoint extends MNamedElement
{
	private Double lat;
	private Double lon;
	
	public MWaypoint()
	{
	}
	
	public MWaypoint(Double lat, Double lon)
	{
		this.lat = lat;
		this.lon = lon;
	}

	@XmlAttribute(name = "lat")
	public Double getLat()
	{
		return lat;
	}

	public void setLat(Double lat)
	{
		this.lat = lat;
	}
	
	@XmlAttribute(name = "lon")
	public Double getLon()
	{
		return lon;
	}

	public void setLon(Double lon)
	{
		this.lon = lon;
	}

	@Override
	public String toString()
	{ 
		return "Lat: " + this.lat + ", Lon: " + this.lon;
	}
}
