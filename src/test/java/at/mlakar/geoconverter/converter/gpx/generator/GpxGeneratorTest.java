package at.mlakar.geoconverter.converter.gpx.generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.mlakar.geoconverter.converter.generator.XmlModelGenerator;
import at.mlakar.geoconverter.converter.gpx.generator.GpxGenerator;
import at.mlakar.geoconverter.converter.gpx.generator.GpxModelGenerator;
import at.mlakar.geoconverter.converter.gpx.model.MGpx;
import at.mlakar.geoconverter.converter.testhelper.FileHelper;
import at.mlakar.geoconverter.converter.testhelper.GpxResources;

public class GpxGeneratorTest
{
	private MGpx mGpx;

	@Before
	public void before()
	{
		String xmlString = FileHelper.readFile(GpxResources.MIXED_ELEMENTS);
		
		XmlModelGenerator<MGpx> gpxModelGenerator = new GpxModelGenerator<>(MGpx.class);
		mGpx = gpxModelGenerator.getModel(xmlString);
	}

	@Test
	public void gpxGeneratorTest()
	{
		GpxGenerator gpxGenerator = new GpxGenerator();
		
		String gpxFromModel = gpxGenerator.getGpx(this.mGpx);
		String gpxFromFile = FileHelper.readFile(GpxResources.MIXED_ELEMENTS);

		Assert.assertEquals(FileHelper.cleanString(gpxFromFile), FileHelper.cleanString(gpxFromModel));
	}
	
}
