package at.mlakar.geoconverter.gpx.generator;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import at.mlakar.geoconverter.gpx.model.MGpx;

/**
 * Generiert aus XML String, im GPX Format, ein Java Datenmodell der Klasse <code>MGpx</code>.
 *
 */
public class GpxModelGenerator
{

	public MGpx getModel(String gpxFile)
	{
		MGpx mGpx = new MGpx();

		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(MGpx.class);
			XMLInputFactory inputFactory = XMLInputFactory.newFactory();
			inputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
			StreamSource streamSource = new StreamSource(new FileReader(gpxFile));
			XMLStreamReader streamReader = inputFactory.createXMLStreamReader(streamSource);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			mGpx = (MGpx) unmarshaller.unmarshal(streamReader);
		}
		catch (JAXBException | FileNotFoundException | XMLStreamException e)
		{
			e.printStackTrace();
		}

		return mGpx;
	}
}
