package at.mlakar.geoconverter.kml.generator;

import at.mlakar.geoconverter.generator.XmlModelGenerator;
import at.mlakar.geoconverter.generator.XmlModel;

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