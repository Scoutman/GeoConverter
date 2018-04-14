package at.mlakar.geoconverter.kml.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class MDocument extends MFeature
{
	@XmlElement(name = "Placemark")
	private List<MPlacemark> placemarkList = new ArrayList<>();
	@XmlElement(name = "Folder")
	private List<MFolder> folderList = new ArrayList<>();
	
	public List<MPlacemark> getPlacemarkList()
	{
		return placemarkList;
	}

	public void addPlacemark(MPlacemark placemark)
	{
		this.placemarkList.add(placemark);
	}

	public List<MFolder> getFolderList()
	{
		return folderList;
	}

	public void addFolder(MFolder folder)
	{
		this.folderList.add(folder);
	}

	
}
