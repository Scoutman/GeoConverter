package at.mlakar.geoconverter.converter.gpx.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class MTrack extends MNamedElement
{
	@XmlElement(name = "trkseg")
	private ArrayList<MTrackSegment> segmentList = new ArrayList<>();

	
	public ArrayList<MTrackSegment> getSegmentsList()
	{
		return segmentList;
	}

	public void addSegment(MTrackSegment trackSegment)
	{
		if (trackSegment.getWaypointsList().size() == 0)
		{
			throw new IllegalArgumentException("MTrackSegment object is empty.");
		}
		
		this.segmentList.add(trackSegment);
	}
	
	
}
