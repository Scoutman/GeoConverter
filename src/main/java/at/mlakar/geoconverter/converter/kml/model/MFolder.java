package at.mlakar.geoconverter.converter.kml.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class MFolder extends MFeature
{
	@XmlElement(name = "Placemark")
	private List<MPlacemark> placemarkList = new ArrayList<>();
	
	
	public List<MPlacemark> getPlacemarkList()
	{
		return placemarkList;
	}

	public void addPlacemark(MPlacemark placemark)
	{
		this.placemarkList.add(placemark);
	}
	
}
