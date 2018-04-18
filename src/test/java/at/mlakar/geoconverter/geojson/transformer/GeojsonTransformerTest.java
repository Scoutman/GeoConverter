package at.mlakar.geoconverter.geojson.transformer;

import org.junit.Test;

import at.mlakar.geoconverter.geojson.generator.GeojsonModelGenerator;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.kml.generator.KmlGenerator;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.testhelper.JsonResources;

public class GeojsonTransformerTest
{

	@Test
	public void test()
	{
		GeojsonModelGenerator geojsonModelGeneratorGenerator = new GeojsonModelGenerator();
		MGeojson mGeojson = geojsonModelGeneratorGenerator.getModel(JsonResources.MIXED_JSON);

		GeojsonTransformer geojsonTransformer = new GeojsonTransformer();
		MKml mKml = geojsonTransformer.getKmlModel(mGeojson);
		
		KmlGenerator kmlGenerator = new KmlGenerator();
		String kmlString = kmlGenerator.getKml(mKml);
		
		System.out.println(kmlString);

	}

}