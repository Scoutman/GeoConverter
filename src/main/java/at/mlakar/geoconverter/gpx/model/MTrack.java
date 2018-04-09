package at.mlakar.geoconverter.gpx.model;

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
		this.segmentList.add(trackSegment);
	}
	
	
}
