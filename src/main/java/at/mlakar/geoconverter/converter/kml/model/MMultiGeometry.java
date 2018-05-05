package at.mlakar.geoconverter.converter.kml.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;


public class MMultiGeometry
{
	@XmlElementRef
	private List<MGeometry> geometryList = new ArrayList<>();

	public List<MGeometry> getGeometry()
	{
		return geometryList;
	}

	public void addGeometry(MGeometry geometry)
	{
		this.geometryList.add(geometry);
	}

}
