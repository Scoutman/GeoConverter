package at.mlakar.geoconverter.geojson.generator;

import org.junit.Assert;
import org.junit.Test;

import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.JsonResources;

public class GeojsonGeneratorTest
{

	@Test
	public void generatorTest()
	{
		fileTestHelper(JsonResources.POINT_JSON);
		fileTestHelper(JsonResources.LINE_STRING_JSON);
		fileTestHelper(JsonResources.POLYGON_JSON);
		fileTestHelper(JsonResources.MIXED_JSON);
	}
	
	private void fileTestHelper(String JsonResources)
	{
		GeojsonModelGenerator modelGenerator = new GeojsonModelGenerator();
		MGeojson geojsonModel = modelGenerator.getModel(JsonResources);	
		GeojsonGenerator geojsonGenerator = new GeojsonGenerator();
		
		String geojsonFromModel = geojsonGenerator.getJson(geojsonModel);
		String geojsonFromFile = FileHelper.readFile(JsonResources);
		
		Assert.assertEquals(FileHelper.cleanString(geojsonFromFile), FileHelper.cleanString(geojsonFromModel));
	}
	
}
