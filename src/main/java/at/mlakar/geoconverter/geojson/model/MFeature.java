package at.mlakar.geoconverter.geojson.model;

import java.util.ArrayList;
import java.util.List;

public class MFeature
{
	private List<MProperty> mProperties = new ArrayList<>(); 
	private MGeometry mGeometry;
	
	public List<MProperty> getProperties()
	{
		return mProperties;
	}

	public void setProperties(List<MProperty> mProperties)
	{
		this.mProperties = mProperties;
	}

	public MGeometry getGeometry()
	{
		return mGeometry;
	}

	public void setGeometry(MGeometry mGeometry)
	{
		this.mGeometry = mGeometry;
	}	
	
}
