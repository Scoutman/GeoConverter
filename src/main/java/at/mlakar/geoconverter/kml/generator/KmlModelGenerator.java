package at.mlakar.geoconverter.kml.generator;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import at.mlakar.geoconverter.kml.model.MKml;

/**
 * Generiert aus XML String, im KML Format, ein Java Datenmodell der Klasse <code>MKml</code>.
 *
 */
public class KmlModelGenerator
{

	public MKml getModel(String kmlFile)
	{
		MKml mKml = new MKml();

		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(MKml.class);
			XMLInputFactory inputFactory = XMLInputFactory.newFactory();
			inputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
			StreamSource streamSource = new StreamSource(new FileReader(kmlFile));
			XMLStreamReader streamReader = inputFactory.createXMLStreamReader(streamSource);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			mKml = (MKml) unmarshaller.unmarshal(streamReader);
		}
		catch (JAXBException | FileNotFoundException | XMLStreamException e)
		{
			e.printStackTrace();
		}

		return mKml;
	}
}