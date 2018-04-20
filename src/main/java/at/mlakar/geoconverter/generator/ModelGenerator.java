package at.mlakar.geoconverter.generator;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;

/**
 * Abstrakter Modell Generator für XML Strukturen. 
 *
 * @param <T>
 */
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
	 * @param xmlString
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <E extends XmlModel> T getModel(String xmlString)
	{
		T xmlModel = null;

		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(this.type);
			XMLInputFactory inputFactory = XMLInputFactory.newFactory();
			inputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);		
			StringReader stringReader = new StringReader(xmlString);		
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			xmlModel = (T) unmarshaller.unmarshal(stringReader);

		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		return xmlModel;
	}
}
