package at.mlakar.geoconverter.kml.transformer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import at.mlakar.geoconverter.generator.XmlModelGenerator;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MMultiPoint;
import at.mlakar.geoconverter.kml.generator.KmlModelGenerator;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.KmlResources;

public class KmlGeojsonTransformerTest
{
	private static MGeojson mGeojson;

	@BeforeClass
	public static void before()
	{
		String xmlString = FileHelper.readFile(KmlResources.MIXED_MULTI_ELEMENTS);

		XmlModelGenerator<MKml> kmlModelGenerator = new KmlModelGenerator<>(MKml.class);
		MKml mKml = kmlModelGenerator.getModel(xmlString);

		KmlGeojsonTransformer kmlGeojsonTransformer = new KmlGeojsonTransformer();
		mGeojson = kmlGeojsonTransformer.getGeojsonModel(mKml);
	}

	@Test
	public void pointTest1()
	{
		Assert.assertEquals(3,
				mGeojson.getFeaturesList().get(0).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().size());
	}

	@Test
	public void pointTest2()
	{
		Assert.assertEquals("multi point", mGeojson.getFeaturesList().get(0).getProperties().get(0).getValue());
	}

	@Test
	public void pointTest3()
	{
		Assert.assertEquals((Double) 47.31706481870458, ((MCoordinatePosition) mGeojson.getFeaturesList().get(0).getGeometry().getCoordinates()
				.getCoordinateList().get(0).getCoordinateList().get(0)).getLat());
		Assert.assertEquals((Double) 15.37450790405273, ((MCoordinatePosition) mGeojson.getFeaturesList().get(0).getGeometry().getCoordinates()
				.getCoordinateList().get(0).getCoordinateList().get(0)).getLon());

	}
	
	@Test
	public void pointTest4()
	{
		Assert.assertTrue(mGeojson.getFeaturesList().get(0).getGeometry().getType() instanceof MMultiPoint);
	}	
	
	@Test
	public void lineStringTest1()
	{
		Assert.assertEquals(2, mGeojson.getFeaturesList().get(1).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().size());
		Assert.assertEquals(3, mGeojson.getFeaturesList().get(1).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().get(0).getCoordinateList().size());
	}
	
	@Test
	public void linePolygonTest1()
	{
		Assert.assertEquals(2, mGeojson.getFeaturesList().get(2).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().size());
		Assert.assertEquals(4, mGeojson.getFeaturesList().get(2).getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().get(0).getCoordinateList().get(0).getCoordinateList().size());	
	}		
}

