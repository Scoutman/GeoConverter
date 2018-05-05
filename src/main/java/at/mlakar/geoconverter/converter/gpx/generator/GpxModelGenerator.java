package at.mlakar.geoconverter.converter.gpx.generator;

import at.mlakar.geoconverter.converter.generator.XmlModel;
import at.mlakar.geoconverter.converter.generator.XmlModelGenerator;

/**
 * Generiert aus XML String, im GPX Format, ein Java Datenmodell der Klasse <code>MGpx</code>.
 *
 */
public class GpxModelGenerator<T extends XmlModel> extends XmlModelGenerator<T>
{
	/**
	 * Instanziiert GPX Modell Generator Klasse.
	 * 
	 * @param type <code>XmlModel</code> Klasse
	 */
	public GpxModelGenerator(Class<T> type)
	{
		super(type);
	}
}
