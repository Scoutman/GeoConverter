package at.mlakar.geoconverter.converter.kml.generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.mlakar.geoconverter.converter.generator.XmlModelGenerator;
import at.mlakar.geoconverter.converter.kml.generator.KmlGenerator;
import at.mlakar.geoconverter.converter.kml.generator.KmlModelGenerator;
import at.mlakar.geoconverter.converter.kml.model.MKml;
import at.mlakar.geoconverter.converter.testhelper.FileHelper;
import at.mlakar.geoconverter.converter.testhelper.KmlResources;

public class KmlGeneratorTest
{
	private MKml mKmlFromModel;
	
	
	@Before
	public void before()
	{
		String xmlString = FileHelper.readFile(KmlResources.MIXED_ELEMENTS);
		
		XmlModelGenerator<MKml> kmlModelGenerator = new KmlModelGenerator<>(MKml.class);
		mKmlFromModel = kmlModelGenerator.getModel(xmlString);
	}

	@Test
	public void kmlGeneratorTest()
	{
		KmlGenerator kmlGenerator = new KmlGenerator();
		
		String kmlFromModel = kmlGenerator.getKml(this.mKmlFromModel);
		String kmlFromFile = FileHelper.readFile(KmlResources.MIXED_ELEMENTS);

		Assert.assertEquals(FileHelper.cleanString(kmlFromFile), FileHelper.cleanString(kmlFromModel));
	}	
		
}
