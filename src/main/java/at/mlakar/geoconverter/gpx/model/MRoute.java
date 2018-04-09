package at.mlakar.geoconverter.gpx.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class MRoute extends MNamedElement
{
	@XmlElement(name = "rtept")
	private ArrayList<MWaypoint> waypointList = new ArrayList<>();

	
	public ArrayList<MWaypoint> getWaypointsList()
	{
		return waypointList;
	}

	public void addWaypoint(MWaypoint waypoint)
	{
		this.waypointList.add(waypoint);
	}
	
	
}
