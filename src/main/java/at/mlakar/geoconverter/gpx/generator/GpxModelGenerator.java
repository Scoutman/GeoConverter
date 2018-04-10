package at.mlakar.geoconverter.gpx.generator;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
			JAXBContext context = JAXBContext.newInstance(MGpx.class);
			Unmarshaller um = context.createUnmarshaller();
			mGpx = (MGpx) um.unmarshal(new FileReader(gpxFile));
		}
		catch (JAXBException | FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return mGpx;
	}
}
