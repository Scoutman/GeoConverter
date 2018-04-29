package at.mlakar.geoconverter.geojson.transformer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import at.mlakar.geoconverter.geojson.generator.GeojsonModelGenerator;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.JsonResources;

public class GeojsonKmlTransformerTest
{
	private static MKml mKml;
	
	@BeforeClass
	public static void before()
	{
		String jsonString = FileHelper.readFile(JsonResources.MIXED_MULTI_ELEMENTS);

		GeojsonModelGenerator geojsonModelGenerator = new GeojsonModelGenerator();
		MGeojson mGeojson = geojsonModelGenerator.getModel(jsonString);

		GeojsonKmlTransformer geojsonKmlTransformer = new GeojsonKmlTransformer();
		mKml = geojsonKmlTransformer.getKmlModel(mGeojson);
	}

	@Test
	public void pointTest1()
	{
		Assert.assertEquals(3, mKml.getDocument().getPlacemarkList().get(0).getMultiGeometry().getGeometry().size());
	}
	
	@Test
	public void pointTest2()
	{
		Assert.assertEquals("multi point", mKml.getDocument().getPlacemarkList().get(0).getName());
	}
	
	@Test
	public void lineStringTest1()
	{
		Assert.assertEquals(2, mKml.getDocument().getPlacemarkList().get(1).getMultiGeometry().getGeometry().size());
	}
	
	@Test
	public void lineStringTest2()
	{
		Assert.assertEquals("multi linestring", mKml.getDocument().getPlacemarkList().get(1).getName());
	}	

	@Test
	public void multiGeometryTest1()
	{
		Assert.assertEquals(2, mKml.getDocument().getPlacemarkList().get(2).getMultiGeometry().getGeometry().size());
	}
	
	@Test
	public void multiGeometryTest2()
	{
		Assert.assertEquals("multi polygon", mKml.getDocument().getPlacemarkList().get(2).getName());
	}
	
}