package at.mlakar.geoconverter.gpx.generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.mlakar.geoconverter.gpx.model.MGpx;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.GpxResources;

public class GpxGeneratorTest
{
	private MGpx mGpx;

	@Before
	public void before()
	{
		GpxModelGenerator gpxModelGenerator = new GpxModelGenerator();
		mGpx = gpxModelGenerator.getModel(GpxResources.TESTFILE_GPX);
	}

	@Test
	public void gpxGeneratorTest()
	{
		GpxGenerator gpxGenerator = new GpxGenerator();
		
		String gpxFromModel = gpxGenerator.getGpx(this.mGpx);
		String gpxFromFile = FileHelper.readFile(GpxResources.TESTFILE_GPX);

		Assert.assertEquals(FileHelper.cleanString(gpxFromFile), FileHelper.cleanString(gpxFromModel));
	}
	
}
