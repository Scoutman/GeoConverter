package at.mlakar.geoconverter.converter.gpx.generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.mlakar.geoconverter.converter.generator.XmlModelGenerator;
import at.mlakar.geoconverter.converter.gpx.generator.GpxModelGenerator;
import at.mlakar.geoconverter.converter.gpx.model.MGpx;
import at.mlakar.geoconverter.converter.testhelper.FileHelper;
import at.mlakar.geoconverter.converter.testhelper.GpxResources;

public class GpxModelGeneratorTest
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
	public void getModelNotNullTest()
	{
		Assert.assertNotNull(mGpx);
	}

	@Test
	public void versionTest()
	{
		Assert.assertEquals("1.1", mGpx.getVersion());
	}
	
	@Test
	public void waypointTest()
	{
		Assert.assertEquals(new Double(47.32923199357016), mGpx.getWaypointsList().get(0).getLat());
		Assert.assertEquals(new Double(15.362652534697418), mGpx.getWaypointsList().get(0).getLon());
		Assert.assertEquals("Mixnitz", mGpx.getWaypointsList().get(0).getName());
	}
	
	@Test
	public void waypointsSizeTest()
	{
		Assert.assertEquals(2, mGpx.getWaypointsList().size());
	}
	
	@Test
	public void routesSizeTest()
	{
		Assert.assertEquals(1, mGpx.getRoutesList().size());
	}
	
	@Test
	public void routeTest()
	{
		Assert.assertEquals("Tolle erste Route", mGpx.getRoutesList().get(0).getName());
		Assert.assertEquals(new Double(47.32923199357016), mGpx.getRoutesList().get(0).getWaypointsList().get(0).getLat());
		Assert.assertEquals(new Double(15.362652534697418), mGpx.getRoutesList().get(0).getWaypointsList().get(0).getLon());
	}	
	
	@Test
	public void tracksSizeTest()
	{
		Assert.assertEquals(1, mGpx.getTracksList().size());
	}

	@Test
	public void trackSegmentsSizeTest()
	{
		Assert.assertEquals(2, mGpx.getTracksList().get(0).getSegmentsList().size());
	}

	@Test
	public void tracksNameTest()
	{
		Assert.assertEquals("Ein aufgezeichneter Track", mGpx.getTracksList().get(0).getName());
	}	
	
	@Test
	public void trackSegmentTest()
	{
		Assert.assertEquals(new Double(47.36261372836142), mGpx.getTracksList().get(0).getSegmentsList().get(1).getWaypointsList().get(1).getLat());
		Assert.assertEquals(new Double(15.42582392141616), mGpx.getTracksList().get(0).getSegmentsList().get(1).getWaypointsList().get(1).getLon());
		Assert.assertEquals("Hochlantsch", mGpx.getTracksList().get(0).getSegmentsList().get(1).getWaypointsList().get(1).getName());
	}	
}

