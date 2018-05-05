package at.mlakar.geoconverter.converter.kml.generator;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import at.mlakar.geoconverter.converter.kml.model.MKml;

/**
 * Generiert aus Java Datenmodell ein KML String. 
 *
 */
public class KmlGenerator
{

	public String getKml(MKml mKml)
	{
		StringWriter kmlXml = new StringWriter();

		try
		{
			JAXBContext context = JAXBContext.newInstance(MKml.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			m.marshal(mKml, kmlXml);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		return kmlXml.toString();
	}
}
