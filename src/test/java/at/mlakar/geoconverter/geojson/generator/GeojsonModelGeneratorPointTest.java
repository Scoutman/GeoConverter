package at.mlakar.geoconverter.geojson.generator;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.JsonResources;

public class GeojsonModelGeneratorPointTest
{
	private MGeojson geojsonModel;
	private MGeojson geojsonModelNoProperties;
	
	@Before
	public void before()
	{
		String jsonPoint = FileHelper.readFile(JsonResources.POINT_JSON);
		String jsonNoPropertiesPoint = FileHelper.readFile(JsonResources.POINT_NO_PROPERTIES_JSON);
		
		GeojsonModelGenerator modelGenerator = new GeojsonModelGenerator();
		geojsonModel = modelGenerator.getModel(jsonPoint);
		
		GeojsonModelGenerator modelGenerator2 = new GeojsonModelGenerator();
		geojsonModelNoProperties = modelGenerator2.getModel(jsonNoPropertiesPoint);
	}
	
	@Test
	public void propertyTest()
	{
		List<MProperty> propertyList = geojsonModel.getFeaturesList().get(0).getProperties();
		String propertyName = geojsonModel.getFeaturesList().get(0).getProperties().get(0).getName();
		String propertyValue = geojsonModel.getFeaturesList().get(0).getProperties().get(0).getValue();
		
		Assert.assertEquals(1, propertyList.size());
		Assert.assertEquals("name", propertyName);
		Assert.assertEquals("rote wand", propertyValue);
	}

	@Test
	public void noPropertyTest()
	{
		List<MProperty> propertyList = geojsonModelNoProperties.getFeaturesList().get(0).getProperties();
		
		Assert.assertNull(propertyList);
	}
	
	@Test
	public void typeTest() 
	{
		MType type = geojsonModel.getFeaturesList().get(0).getGeometry().getType();
		
		Assert.assertTrue(type instanceof MPoint);		
	}
	
	@Test
	public void coordinateTest() 
	{
		List<MCoordinate> coordinates = geojsonModel.getFeaturesList().get(0).getGeometry().getCoordinates().getCoordinateList();
		MCoordinatePosition position = (MCoordinatePosition)coordinates.get(0);
		
		Assert.assertEquals((Double)47.32969013244894, position.getLat());
		Assert.assertEquals((Double)15.398197174072264, position.getLon());		
	}	
	
}
