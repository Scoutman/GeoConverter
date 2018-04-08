package at.mlakar.geoconverter.gpx.model;

import java.util.ArrayList;
import java.util.List;

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
	private List<MWaypoint> waypointList;
	@XmlElement(name = "rte")
	private List<MRoute> routeList = new ArrayList<>();
	@XmlElement(name = "trk")
	private List<MTrack> trackList;
		
	
	
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

	public List<MWaypoint> getWaypointsList()
	{
		return waypointList;
	}

	public List<MRoute> getRoutesList()
	{
		return routeList;
	}

	public void addRouteList(MRoute routeList)
	{
		this.routeList.add(routeList);
	}


}
