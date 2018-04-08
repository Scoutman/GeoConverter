package at.mlakar.geoconverter.geojson.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import at.mlakar.geoconverter.geojson.model.MGeojson;
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
		String geojsonFromFile = readJsonFile(JsonResources);
		
		Assert.assertEquals(cleanJsonString(geojsonFromFile), cleanJsonString(geojsonFromModel));
	}
	
	private String readJsonFile(String geojsonFile)
	{
		String json = "";
		
		try
		{
			json = new String(Files.readAllBytes(Paths.get(geojsonFile)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return json;
	}
	
	private String cleanJsonString(String json)
	{
		json = json.trim();
		json = json.replace(" ", "");
		json = json.replace("\r\n", "");
		json = json.replace("\n", "");
		json = json.replace("\t", "");
		
		return json;
	}
}
