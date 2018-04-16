package at.mlakar.geoconverter.generator;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

public abstract class ModelGenerator<T extends XmlModel>
{
	private Class<T> type;

	public ModelGenerator(Class<T> type)
	{
		this.type = type;
	}

	public final Class<T> getModelType()
	{
		return this.type;
	}

	/**
	 * Gibt erstelltes Datenmodell zurück.
	 * 
	 * @param xmlFile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <E extends XmlModel> T getModel(String xmlFile)
	{
		T xmlModel = null;

		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(this.type);
			XMLInputFactory inputFactory = XMLInputFactory.newFactory();
			inputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
			StreamSource streamSource = new StreamSource(new FileReader(xmlFile));
			XMLStreamReader streamReader = inputFactory.createXMLStreamReader(streamSource);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			xmlModel = (T) unmarshaller.unmarshal(streamReader);

		}
		catch (JAXBException | FileNotFoundException | XMLStreamException e)
		{
			e.printStackTrace();
		}

		return xmlModel;
	}
}
