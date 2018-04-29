package at.mlakar.geoconverter.geojson.transformer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import at.mlakar.geoconverter.geojson.generator.GeojsonModelGenerator;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.gpx.model.MGpx;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.JsonResources;

public class GeojsonGpxTransformerTest
{
	private static MGpx mGpx;
	
	@BeforeClass
	public static void before()
	{
		String jsonString = FileHelper.readFile(JsonResources.MIXED_MULTI_ELEMENTS);

		GeojsonModelGenerator geojsonModelGenerator = new GeojsonModelGenerator();
		MGeojson mGeojson = geojsonModelGenerator.getModel(jsonString);

		GeojsonGpxTransformer geojsonGpxTransformer = new GeojsonGpxTransformer();
		mGpx = geojsonGpxTransformer.getGpxModel(mGeojson);
	}

	@Test
	public void pointTest1()
	{
		Assert.assertEquals(3, mGpx.getWaypointsList().size());
	}
	
	@Test
	public void pointTest2()
	{
		Assert.assertEquals("multi point", mGpx.getWaypointsList().get(0).getName());
	}
	
	@Test
	public void pointTest3()
	{
		Assert.assertEquals((Double)47.31706481870458, mGpx.getWaypointsList().get(0).getLat());
		Assert.assertEquals((Double)15.37450790405273, mGpx.getWaypointsList().get(0).getLon());
	}
	
	@Test
	public void trackTest1()
	{
		Assert.assertEquals(2, mGpx.getTracksList().size());
	}
	
	@Test
	public void trackTest2()
	{
		Assert.assertEquals("multi linestring", mGpx.getTracksList().get(0).getName());
	}

	@Test
	public void trackTest3()
	{
		Assert.assertEquals(2, mGpx.getTracksList().get(0).getSegmentsList().size());
	}
	
	@Test
	public void trackTest4()
	{
		Assert.assertEquals(3, mGpx.getTracksList().get(0).getSegmentsList().get(0).getWaypointsList().size());
	}
	
	@Test
	public void trackTest5()
	{
		Assert.assertEquals((Double)47.31706481870458, mGpx.getTracksList().get(0).getSegmentsList().get(0).getWaypointsList().get(0).getLat());
		Assert.assertEquals((Double)15.37450790405273, mGpx.getTracksList().get(0).getSegmentsList().get(0).getWaypointsList().get(0).getLon());
	}
	
	@Test
	public void trackPolygonTest1()
	{
		Assert.assertEquals(2, mGpx.getTracksList().get(1).getSegmentsList().size());
		Assert.assertEquals(4, mGpx.getTracksList().get(1).getSegmentsList().get(0).getWaypointsList().size());
	}	
}