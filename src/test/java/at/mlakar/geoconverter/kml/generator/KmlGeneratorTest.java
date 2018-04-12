package at.mlakar.geoconverter.kml.generator;

import org.junit.Test;

import at.mlakar.geoconverter.kml.model.MKml;

public class KmlGeneratorTest
{

	@Test
	public void test()
	{
		MKml mKml = new MKml();
		
		
		KmlGenerator kmlGenerator = new KmlGenerator();
		String kml = kmlGenerator.getKml(mKml);
		
		System.out.println(kml);
	}

}
