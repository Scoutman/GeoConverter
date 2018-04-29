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
		fileTestHelper(JsonResources.POINT);
		fileTestHelper(JsonResources.LINE_STRING);
		fileTestHelper(JsonResources.POLYGON);
		fileTestHelper(JsonResources.MIXED_ELEMENTS);	
		fileTestHelper(JsonResources.MULTI_LINE_STRING);
	}
	
	private void fileTestHelper(String JsonResources)
	{
		String jsonString = FileHelper.readFile(JsonResources);
		
		GeojsonModelGenerator modelGenerator = new GeojsonModelGenerator();
		MGeojson geojsonModel = modelGenerator.getModel(jsonString);	
		GeojsonGenerator geojsonGenerator = new GeojsonGenerator();
		
		String geojsonFromModel = geojsonGenerator.getGeojson(geojsonModel);
		String geojsonFromFile = FileHelper.readFile(JsonResources);
		
		Assert.assertEquals(FileHelper.cleanString(geojsonFromFile), FileHelper.cleanString(geojsonFromModel));
	}
	
}
