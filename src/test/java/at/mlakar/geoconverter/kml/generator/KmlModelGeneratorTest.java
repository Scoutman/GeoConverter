package at.mlakar.geoconverter.kml.generator;

import org.junit.Before;
import org.junit.Test;

import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.testhelper.KmlResources;

public class KmlModelGeneratorTest
{
	private MKml mKml;
	
	@Before
	public void before() 
	{
		KmlModelGenerator kmlModelGenerator = new KmlModelGenerator();
		mKml = kmlModelGenerator.getModel(KmlResources.TESTFILE_KML);
	}
	
	@Test
	public void test()
	{
		System.out.println(mKml.getDocument().getName());
		
		System.out.println(mKml.getDocument().getPlacemarkList().get(0).getName());
		System.out.println(mKml.getDocument().getPlacemarkList().get(0).getGeometry().getCoordinatesList());
		
	}

}
