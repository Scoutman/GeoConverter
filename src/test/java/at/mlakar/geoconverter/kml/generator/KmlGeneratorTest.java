package at.mlakar.geoconverter.kml.generator;

import org.junit.Test;

import at.mlakar.geoconverter.kml.model.MCoordinate;
import at.mlakar.geoconverter.kml.model.MDocument;
import at.mlakar.geoconverter.kml.model.MFolder;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.kml.model.MLinearRing;
import at.mlakar.geoconverter.kml.model.MPlacemark;

public class KmlGeneratorTest
{

	@Test
	public void test()
	{
		MKml mKml = new MKml();
		
		//MLineString mPoint = new MLineString();
		MLinearRing mPoint = new MLinearRing();
		
		MCoordinate mCoordinate = new MCoordinate(new Double(47.123), new Double(15.456), 360);
		mPoint.addCoordinate(mCoordinate);
		
		MFolder mFolder = new MFolder();
		mFolder.setGeometry(mPoint);
		mFolder.setName("ich bin a Folder");
		
		MPlacemark mPlacemark = new MPlacemark();
		mPlacemark.setName("ich bin placemark");
		mPlacemark.setGeometry(mPoint);
		
		
		MDocument mDocument = new MDocument();
		mDocument.setName("ich bin ein document");
		mDocument.addPlacemark(mPlacemark);
		mDocument.addFolder(mFolder);
		
		mKml.setDocument(mDocument);
		
		
		KmlGenerator kmlGenerator = new KmlGenerator();
		String kml = kmlGenerator.getKml(mKml);
		
		System.out.println(kml);
	}

}
