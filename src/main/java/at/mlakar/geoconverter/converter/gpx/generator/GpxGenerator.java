package at.mlakar.geoconverter.converter.gpx.generator;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import at.mlakar.geoconverter.converter.gpx.model.MGpx;

/**
 * Generiert aus Java Datenmodell ein GPX String. 
 *
 */
public class GpxGenerator
{

	public String getGpx(MGpx mGpx)
	{
		StringWriter gpxXml = new StringWriter();

		try
		{
			JAXBContext context = JAXBContext.newInstance(MGpx.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			m.marshal(mGpx, gpxXml);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		return gpxXml.toString();
	}
}
