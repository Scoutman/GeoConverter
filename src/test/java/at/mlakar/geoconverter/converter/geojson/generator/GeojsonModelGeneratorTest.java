package at.mlakar.geoconverter.converter.geojson.generator;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.mlakar.geoconverter.converter.geojson.generator.GeojsonModelGenerator;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.converter.geojson.model.MGeojson;
import at.mlakar.geoconverter.converter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.converter.geojson.model.MPoint;
import at.mlakar.geoconverter.converter.geojson.model.MProperty;
import at.mlakar.geoconverter.converter.geojson.model.MType;
import at.mlakar.geoconverter.converter.testhelper.FileHelper;
import at.mlakar.geoconverter.converter.testhelper.JsonResources;

public class GeojsonModelGeneratorTest
{
	private MGeojson geojsonModelPoint;
	private MGeojson geojsonModelNoProperties;
	private MGeojson geojsonModelLine;
	private MGeojson geojsonModelMultiLine;
	
	@Before
	public void before()
	{
		String jsonPoint = FileHelper.readFile(JsonResources.POINT);
		String jsonNoPropertiesPoint = FileHelper.readFile(JsonResources.POINT_NO_PROPERTIES);
		String jsonLineString = FileHelper.readFile(JsonResources.LINE_STRING);
		String jsonMultiLineString = FileHelper.readFile(JsonResources.MULTI_LINE_STRING);
		
		GeojsonModelGenerator modelGenerator = new GeojsonModelGenerator();
		geojsonModelPoint = modelGenerator.getModel(jsonPoint);
		
		GeojsonModelGenerator modelGenerator2 = new GeojsonModelGenerator();
		geojsonModelNoProperties = modelGenerator2.getModel(jsonNoPropertiesPoint);
		
		GeojsonModelGenerator modelGenerator3 = new GeojsonModelGenerator();
		geojsonModelMultiLine = modelGenerator3.getModel(jsonMultiLineString);
		
		GeojsonModelGenerator modelGenerator4 = new GeojsonModelGenerator();
		geojsonModelLine = modelGenerator4.getModel(jsonLineString);
	}
	
	@Test
	public void propertyTest()
	{
		List<MProperty> propertyList = geojsonModelPoint.getFeaturesList().get(0).getProperties();
		String propertyName = geojsonModelPoint.getFeaturesList().get(0).getProperties().get(0).getName();
		String propertyValue = geojsonModelPoint.getFeaturesList().get(0).getProperties().get(0).getValue();
		
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
		MType type = geojsonModelPoint.getFeaturesList().get(0).getGeometry().getType();
		
		Assert.assertTrue(type instanceof MPoint);		
	}
	
	@Test
	public void coordinatePointTest() 
	{
		List<MCoordinate> coordinates = geojsonModelPoint.getFeaturesList().get(0).getGeometry().getCoordinates().getCoordinateList();
		MCoordinatePosition position = (MCoordinatePosition)coordinates.get(0);
		
		Assert.assertEquals((Double)47.32969013244894, position.getLat());
		Assert.assertEquals((Double)15.398197174072264, position.getLon());		
	}	
	
	@Test
	public void coordinateLineStringTest()
	{
		List<MCoordinate> coordinates = geojsonModelLine.getFeaturesList().get(0).getGeometry().getCoordinates().getCoordinateList();
		
		Assert.assertEquals((Double) 47.31706481870457, ((MCoordinatePosition)coordinates.get(0).getCoordinateList().get(0)).getLat());
		Assert.assertEquals((Double) 15.374507904052734, ((MCoordinatePosition)coordinates.get(0).getCoordinateList().get(0)).getLon());
		
		Assert.assertEquals((Double) 47.32369784820449, ((MCoordinatePosition)coordinates.get(0).getCoordinateList().get(1)).getLat());
		Assert.assertEquals((Double) 15.394506454467772, ((MCoordinatePosition)coordinates.get(0).getCoordinateList().get(1)).getLon());
		
		Assert.assertEquals((Double) 47.33271510510286, ((MCoordinatePosition)coordinates.get(0).getCoordinateList().get(2)).getLat());
		Assert.assertEquals((Double) 15.403175354003906, ((MCoordinatePosition)coordinates.get(0).getCoordinateList().get(2)).getLon());		
	}
	
	@Test
	public void MultiLineStringTest()
	{
		Assert.assertEquals(MMultiLineString.class, geojsonModelMultiLine.getFeaturesList().get(0).getGeometry().getType().getClass());	
	}

	@Test
	public void MultiLineStringCoordinateTest()
	{
		List<MCoordinate> coordinateList = geojsonModelMultiLine.getFeaturesList().get(0).getGeometry().getCoordinates().getCoordinateList();

		Double lat1 = ((MCoordinatePosition)coordinateList.get(0).getCoordinateList().get(0).getCoordinateList().get(0)).getLat();
		Double lon1 = ((MCoordinatePosition)coordinateList.get(0).getCoordinateList().get(0).getCoordinateList().get(0)).getLon(); 

		Double lat2 = ((MCoordinatePosition)coordinateList.get(0).getCoordinateList().get(0).getCoordinateList().get(1)).getLat();
		Double lon2 = ((MCoordinatePosition)coordinateList.get(0).getCoordinateList().get(0).getCoordinateList().get(1)).getLon(); 

		Double lat3 = ((MCoordinatePosition)coordinateList.get(0).getCoordinateList().get(0).getCoordinateList().get(2)).getLat();
		Double lon3 = ((MCoordinatePosition)coordinateList.get(0).getCoordinateList().get(0).getCoordinateList().get(2)).getLon(); 	
		
		Assert.assertEquals((Double) 47.31706481870458, lat1);
		Assert.assertEquals((Double) 15.37450790405273, lon1);
		
		Assert.assertEquals((Double) 47.32369784820451, lat2);
		Assert.assertEquals((Double) 15.39450645446777, lon2);
		
		Assert.assertEquals((Double) 47.33271510510287, lat3);
		Assert.assertEquals((Double) 15.40317535400392, lon3);
	}	
}
