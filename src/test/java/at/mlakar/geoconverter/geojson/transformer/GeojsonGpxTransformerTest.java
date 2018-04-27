package at.mlakar.geoconverter.geojson.transformer;

import org.junit.Test;

import at.mlakar.geoconverter.geojson.generator.GeojsonModelGenerator;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.gpx.generator.GpxGenerator;
import at.mlakar.geoconverter.gpx.model.MGpx;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.JsonResources;

public class GeojsonGpxTransformerTest
{

	@Test
	public void test()
	{
		String jsonString = FileHelper.readFile(JsonResources.MIXED_MULTI_ELEMENTS);
		
		GeojsonModelGenerator geojsonModelGeneratorGenerator = new GeojsonModelGenerator();
		MGeojson mGeojson = geojsonModelGeneratorGenerator.getModel(jsonString);
		
		GeojsonGpxTransformer geojsonGpxTransformer = new GeojsonGpxTransformer();
		MGpx mGpx = geojsonGpxTransformer.getModel(mGeojson);
		
		GpxGenerator gpxGenerator = new GpxGenerator();
		String gpxString = gpxGenerator.getGpx(mGpx);
		
		System.out.println(gpxString);
	}

}