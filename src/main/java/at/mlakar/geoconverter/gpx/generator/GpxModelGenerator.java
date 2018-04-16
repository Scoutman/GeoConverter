package at.mlakar.geoconverter.gpx.generator;

import at.mlakar.geoconverter.generator.ModelGenerator;
import at.mlakar.geoconverter.generator.XmlModel;

/**
 * Generiert aus XML String, im GPX Format, ein Java Datenmodell der Klasse <code>MGpx</code>.
 *
 */
public class GpxModelGenerator<T extends XmlModel> extends ModelGenerator<T>
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
