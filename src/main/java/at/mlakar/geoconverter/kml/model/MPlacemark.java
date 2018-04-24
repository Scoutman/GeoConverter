package at.mlakar.geoconverter.kml.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;

public class MPlacemark extends MFeature
{
	
	private MGeometry mGeometry;
	private MMultiGeometry mMultiGeometry;
	private boolean isSetGeometry = false;
	private boolean isSetMultiGeometry = false;

	@XmlElementRef
	public MGeometry getGeometry()
	{
		return mGeometry;
	}

	public void setGeometry(MGeometry mGeometry)
	{
		if (this.isSetMultiGeometry == true)
		{
			throw new IllegalArgumentException("MultiGeometry object is already set.");
		}
		
		this.mGeometry = mGeometry;
		this.isSetGeometry = true;
	}

	@XmlElement(name = "MultiGeometry")
	public MMultiGeometry getMultiGeometry()
	{
		return mMultiGeometry;
	}

	public void setMultiGeometry(MMultiGeometry mMultiGeometry)
	{
		if (this.isSetGeometry == true)
		{
			throw new IllegalArgumentException("Geometry object is already set.");
		}		
		
		this.mMultiGeometry = mMultiGeometry;
		this.isSetMultiGeometry = true;
	}

	public boolean isSetGeometry()
	{
		return isSetGeometry;
	}

	public boolean isSetMultiGeometry()
	{
		return isSetMultiGeometry;
	}	
	
}
