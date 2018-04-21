package at.mlakar.geoconverter.geojson.transformer;

import org.junit.Test;

import at.mlakar.geoconverter.geojson.generator.GeojsonModelGenerator;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.kml.generator.KmlGenerator;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.JsonResources;

public class GeojsonKmlTransformerTest
{

	@Test
	public void test()
	{
		String jsonString = FileHelper.readFile(JsonResources.MIXED_JSON);
		
		GeojsonModelGenerator geojsonModelGeneratorGenerator = new GeojsonModelGenerator();
		MGeojson mGeojson = geojsonModelGeneratorGenerator.getModel(jsonString);

		GeojsonKmlTransformer geojsonTransformer = new GeojsonKmlTransformer();
		MKml mKml = geojsonTransformer.getKmlModel(mGeojson);
		
		KmlGenerator kmlGenerator = new KmlGenerator();
		String kmlString = kmlGenerator.getKml(mKml);
		
		System.out.println(kmlString);

	}

}