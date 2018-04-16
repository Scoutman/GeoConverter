package at.mlakar.geoconverter.kml.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.mlakar.geoconverter.generator.XmlModel;

@XmlRootElement(name="kml")
public class MKml implements XmlModel
{
	
	private MDocument mDocument = new MDocument();
	
	
	public void setDocument(MDocument mDocument)
	{
		this.mDocument = mDocument;
	}
	
	@XmlElement(name = "Document")
	public MDocument getDocument()
	{
		return this.mDocument;
	}
}
