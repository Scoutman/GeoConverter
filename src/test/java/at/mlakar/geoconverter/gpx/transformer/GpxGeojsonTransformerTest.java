package at.mlakar.geoconverter.gpx.transformer;

import org.junit.Test;

import at.mlakar.geoconverter.generator.ModelGenerator;
import at.mlakar.geoconverter.geojson.generator.GeojsonGenerator;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.gpx.generator.GpxModelGenerator;
import at.mlakar.geoconverter.gpx.model.MGpx;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.GpxResources;

public class GpxGeojsonTransformerTest
{

	@Test
	public void test()
	{
		String gpxString = FileHelper.readFile(GpxResources.MIXED_ELEMENTS);
		
		ModelGenerator<MGpx> gpxModelGenerator = new GpxModelGenerator<>(MGpx.class);
		MGpx mGpx = gpxModelGenerator.getModel(gpxString);
		
		GpxGeojsonTransformer gpxGeojsonTransformer = new GpxGeojsonTransformer();
		MGeojson mGeojson = gpxGeojsonTransformer.getGeojsonModel(mGpx);
		
		GeojsonGenerator geojsonGenerator = new GeojsonGenerator();
		String geojsonString = geojsonGenerator.getJson(mGeojson);
		
		System.out.println(geojsonString);
	}

}
