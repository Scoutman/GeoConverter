package at.mlakar.geoconverter.kml.generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.mlakar.geoconverter.generator.ModelGenerator;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.KmlResources;

public class KmlGeneratorTest
{
	private MKml mKmlFromModel;
	
	
	@Before
	public void before()
	{
		String xmlString = FileHelper.readFile(KmlResources.TESTFILE_KML);
		
		ModelGenerator<MKml> kmlModelGenerator = new KmlModelGenerator<>(MKml.class);
		mKmlFromModel = kmlModelGenerator.getModel(xmlString);
	}

	@Test
	public void kmlGeneratorTest()
	{
		KmlGenerator kmlGenerator = new KmlGenerator();
		
		String kmlFromModel = kmlGenerator.getKml(this.mKmlFromModel);
		String kmlFromFile = FileHelper.readFile(KmlResources.TESTFILE_KML);

		Assert.assertEquals(FileHelper.cleanString(kmlFromFile), FileHelper.cleanString(kmlFromModel));
	}	
		
}
