package at.mlakar.geoconverter.converter.gpx.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.mlakar.geoconverter.converter.generator.XmlModel;


@XmlRootElement(name="gpx")
public class MGpx implements XmlModel
{
	private String version = "1.1";
	
	@XmlElement(name = "wpt")
	private List<MWaypoint> waypointList = new ArrayList<>();
	@XmlElement(name = "rte")
	private List<MRoute> routeList = new ArrayList<>();
	@XmlElement(name = "trk")
	private List<MTrack> trackList = new ArrayList<>();


	@XmlAttribute(name = "version")
	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public List<MWaypoint> getWaypointsList()
	{
		return waypointList;
	}	
	
	public void addWaypoint(MWaypoint waypoint)
	{
		if (waypoint.getLat() == null || waypoint.getLon() == null)
		{
			throw new IllegalArgumentException("MWaypoint object is empty.");
		}		
		this.waypointList.add(waypoint);
	}
	
	public List<MRoute> getRoutesList()
	{
		return routeList;
	}	
	
	public void addRoute(MRoute route)
	{
		if (route.getWaypointsList().size() == 0)
		{
			throw new IllegalArgumentException("MRoute object is empty.");
		}		
		this.routeList.add(route);
	}

	public List<MTrack> getTracksList()
	{
		return trackList;
	}

	public void addTrack(MTrack track)
	{
		if (track.getSegmentsList().size() == 0)
		{
			throw new IllegalArgumentException("MTrack object is empty.");
		}
		
		this.trackList.add(track);
	}

	
}
