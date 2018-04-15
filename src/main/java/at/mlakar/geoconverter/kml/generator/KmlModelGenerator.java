package at.mlakar.geoconverter.kml.generator;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import at.mlakar.geoconverter.kml.model.MKml;

public class KmlModelGenerator
{
	
	
	public MKml getModel(String kmlFile)
	{
		MKml mKml = new MKml();

		
		try
		{
			/*
			JAXBContext context = JAXBContext.newInstance(MKml.class);
			Unmarshaller um = context.createUnmarshaller();
			um.setEventHandler(new CustomValidationEventHandler());
			mKml = (MKml) um.unmarshal(new FileReader(kmlFile));
			*/
			
			
			JAXBContext jc = JAXBContext.newInstance(MKml.class);
			XMLInputFactory xif = XMLInputFactory.newFactory();
			xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false); // this is the magic line
			StreamSource source = new StreamSource(new FileReader(kmlFile));
			XMLStreamReader xsr = xif.createXMLStreamReader(source);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			mKml = (MKml) unmarshaller.unmarshal(xsr);
			
		}
		catch (JAXBException | FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (javax.xml.stream.XMLStreamException e)
		{
			e.printStackTrace();
		}
		
		return mKml;
	}


}