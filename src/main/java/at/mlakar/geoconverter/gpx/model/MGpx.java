package at.mlakar.geoconverter.gpx.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// This statement means that class "Bookstore.java" is the root-element of our example
@XmlRootElement(name="gpx")
public class MGpx
{

	// XmLElementWrapper generates a wrapper element around XML representation
	//@XmlElementWrapper(name = "bookList")
	// XmlElement sets the name of the entities
	
	private String version;
	@XmlElement(name = "wpt")
	private ArrayList<MWaypoint> waypointList;
	@XmlElement(name = "rte")
	private ArrayList<MRoute> routeList;
	@XmlElement(name = "trk")
	private ArrayList<MTrack> trackList;
		
	
	
	@XmlAttribute(name = "version")
	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public void setWaypointList(ArrayList<MWaypoint> waypointList)
	{
		this.waypointList = waypointList;
	}

	public ArrayList<MWaypoint> getWaypointsList()
	{
		return waypointList;
	}


}
