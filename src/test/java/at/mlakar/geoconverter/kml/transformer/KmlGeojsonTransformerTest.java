package at.mlakar.geoconverter.kml.transformer;

import org.junit.Test;

import at.mlakar.geoconverter.generator.ModelGenerator;
import at.mlakar.geoconverter.geojson.generator.GeojsonGenerator;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.kml.generator.KmlModelGenerator;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.testhelper.FileHelper;
import at.mlakar.geoconverter.testhelper.KmlResources;

public class KmlGeojsonTransformerTest
{

	@Test
	public void test()
	{
		String xmlString = FileHelper.readFile(KmlResources.TESTFILE_KML);
		
		ModelGenerator<MKml> kmlModelGenerator = new KmlModelGenerator<>(MKml.class);
		MKml mKml = kmlModelGenerator.getModel(xmlString);
		
		KmlGeojsonTransformer kmlTransformer = new KmlGeojsonTransformer();
		MGeojson mGeojson = kmlTransformer.getGeojsonModel(mKml);
		
	
		GeojsonGenerator geojsonGenerator = new GeojsonGenerator();
		String geojsonString = geojsonGenerator.getJson(mGeojson);
		
		System.out.println(geojsonString);
		
	}

}
