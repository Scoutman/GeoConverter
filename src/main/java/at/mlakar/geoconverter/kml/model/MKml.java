package at.mlakar.geoconverter.kml.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="kml")
public class MKml
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
