package at.mlakar.geoconverter.gpx.transformer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import at.mlakar.geoconverter.generator.XmlModelGenerator;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MLineString;
import at.mlakar.geoconverter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.gpx.generator.GpxModelGenerator;
import at.mlakar.geoconverter.gpx.model.MGpx;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.GpxResources;

public class GpxGeojsonTransformerTest
{
	private static MGeojson mGeojson;
	
	@BeforeClass
	public static void test()
	{
		String gpxString = FileHelper.readFile(GpxResources.MIXED_ELEMENTS);

		XmlModelGenerator<MGpx> gpxModelGenerator = new GpxModelGenerator<>(MGpx.class);
		MGpx mGpx = gpxModelGenerator.getModel(gpxString);

		GpxGeojsonTransformer gpxGeojsonTransformer = new GpxGeojsonTransformer();
		mGeojson = gpxGeojsonTransformer.getGeojsonModel(mGpx);
	}

	@Test
	public void waypointTest1()
	{
		Assert.assertTrue(mGeojson.getFeaturesList().get(0).getGeometry().getType() instanceof MPoint);
		Assert.assertTrue(mGeojson.getFeaturesList().get(1).getGeometry().getType() instanceof MPoint);
	}

	@Test
	public void waypointTest2()
	{
		Assert.assertEquals((Double)47.32923199357016, ((MCoordinatePosition)mGeojson.getFeaturesList().get(0).getGeometry().getCoordinates().getCoordinateList().get(0)).getLat());
		Assert.assertEquals((Double)15.362652534697418, ((MCoordinatePosition)mGeojson.getFeaturesList().get(0).getGeometry().getCoordinates().getCoordinateList().get(0)).getLon());
	}	

	@Test
	public void waypointTest3()
	{
		Assert.assertEquals("Hochlantsch", mGeojson.getFeaturesList().get(1).getProperties().get(0).getValue());

	}
	
	@Test
	public void lineStringTest1()
	{
		Assert.assertTrue(mGeojson.getFeaturesList().get(2).getGeometry().getType() instanceof MLineString);
	}	

	@Test
	public void lineStringTest2()
	{
		Assert.assertEquals(2, mGeojson.getFeaturesList().get(2).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().size());
	}	

	@Test
	public void lineStringTest3()
	{
		Assert.assertEquals((Double)47.32923199357016, ((MCoordinatePosition)mGeojson.getFeaturesList().get(2).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().get(0)).getLat());
		Assert.assertEquals((Double)15.362652534697418, ((MCoordinatePosition)mGeojson.getFeaturesList().get(2).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().get(0)).getLon());
	}

	@Test
	public void lineStringTest4()
	{
		Assert.assertEquals("Tolle erste Route", mGeojson.getFeaturesList().get(2).getProperties().get(0).getValue());
	}
	
	@Test
	public void multiLineStringTest1()
	{
		Assert.assertTrue(mGeojson.getFeaturesList().get(3).getGeometry().getType() instanceof MMultiLineString);
	}
	
	@Test
	public void multiLineStringTest2()
	{
		Assert.assertEquals("Ein aufgezeichneter Track", mGeojson.getFeaturesList().get(3).getProperties().get(0).getValue());
	}
	
	@Test
	public void multiLineStringTest3()
	{
		Assert.assertEquals(2, mGeojson.getFeaturesList().get(3).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().size());
		Assert.assertEquals(2, mGeojson.getFeaturesList().get(3).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().get(0).getCoordinateList().size());
	}	
}
