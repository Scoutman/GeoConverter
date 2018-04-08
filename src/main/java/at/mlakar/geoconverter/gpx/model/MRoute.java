package at.mlakar.geoconverter.gpx.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class MRoute extends MNamedElement
{
	//@XmlElementWrapper(name = "rte")
	@XmlElement(name = "wpt")
	private ArrayList<MWaypoint> waypointList;

	
	public ArrayList<MWaypoint> getWaypointsList()
	{
		return waypointList;
	}

	public void setWaypointList(ArrayList<MWaypoint> waypointList)
	{
		this.waypointList = waypointList;
	}
	
	
}
