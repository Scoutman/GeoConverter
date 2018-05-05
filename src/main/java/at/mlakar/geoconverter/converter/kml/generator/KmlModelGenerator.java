package at.mlakar.geoconverter.converter.kml.generator;

import at.mlakar.geoconverter.converter.generator.XmlModel;
import at.mlakar.geoconverter.converter.generator.XmlModelGenerator;

/**
 * Generiert aus XML String, im KML Format, ein Java Datenmodell der Klasse <code>MKml</code>.
 *
 */
public class KmlModelGenerator<T extends XmlModel> extends XmlModelGenerator<T>
{
	/**
	 * Instanziiert KML Modell Generator Klasse.
	 * 	
	 * @param type <code>XmlModel</code> Klasse
	 */
	public KmlModelGenerator(Class<T> type)
	{
		super(type);
	}
}