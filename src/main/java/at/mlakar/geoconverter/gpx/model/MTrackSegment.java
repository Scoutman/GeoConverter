package at.mlakar.geoconverter.gpx.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class MTrackSegment
{
	@XmlElement(name = "trkpt")
	private ArrayList<MWaypoint> waypointList = new ArrayList<>();

	
	public ArrayList<MWaypoint> getWaypointsList()
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
	
	
}
