package at.mlakar.geoconverter.kml.model;

import javax.xml.bind.annotation.XmlElementRef;

public class MPlacemark extends MFeature
{
	
	private MGeometry mGeometry;

	@XmlElementRef
	public MGeometry getGeometry()
	{
		return mGeometry;
	}

	public void setGeometry(MGeometry mGeometry)
	{
		this.mGeometry = mGeometry;
	}
	
	
}
