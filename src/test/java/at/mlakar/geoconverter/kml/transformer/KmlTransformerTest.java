package at.mlakar.geoconverter.kml.transformer;

import static org.junit.Assert.*;

import org.junit.Test;

import at.mlakar.geoconverter.generator.ModelGenerator;
import at.mlakar.geoconverter.geojson.generator.GeojsonGenerator;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.kml.generator.KmlModelGenerator;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.testhelper.KmlResources;

public class KmlTransformerTest
{

	@Test
	public void test()
	{
		ModelGenerator<MKml> kmlModelGenerator = new KmlModelGenerator<>(MKml.class);
		MKml mKml = kmlModelGenerator.getModel(KmlResources.TESTFILE_POINT_KML);
		
		KmlTransformer kmlTransformer = new KmlTransformer();
		MGeojson mGeojson = kmlTransformer.getGeojsonModel(mKml);
		
		//System.out.println(mGeojson.getFeaturesList().get(0).getGeometry().getCoordinates().getCoordinateList());
		
		GeojsonGenerator geojsonGenerator = new GeojsonGenerator();
		String geojsonString = geojsonGenerator.getJson(mGeojson);
		
		System.out.println(geojsonString);
		
	}

}
