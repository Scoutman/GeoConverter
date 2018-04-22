package at.mlakar.geoconverter.kml.generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.mlakar.geoconverter.kml.model.MCoordinate;
import at.mlakar.geoconverter.kml.model.MDocument;
import at.mlakar.geoconverter.kml.model.MFolder;
import at.mlakar.geoconverter.kml.model.MGeometry;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.kml.model.MLineString;
import at.mlakar.geoconverter.kml.model.MLinearRing;
import at.mlakar.geoconverter.kml.model.MMultiGeometry;
import at.mlakar.geoconverter.kml.model.MPlacemark;
import at.mlakar.geoconverter.kml.model.MPoint;

public class KmlModelGeneratorTest
{
	private MKml mKml = new MKml();

	@Before
	public void before()
	{
		MGeometry mPoint = new MPoint();
		MCoordinate mCoordinate1 = new MCoordinate(new Double(47.362176), new Double(15.423645), 1720);
		mPoint.addCoordinate(mCoordinate1);

		MGeometry mLineString = new MLineString();
		MCoordinate mCoordinate2 = new MCoordinate(new Double(47.32113549139689), new Double(15.36869054453513), 0);
		MCoordinate mCoordinate3 = new MCoordinate(new Double(47.32754731084825), new Double(15.39147691669904), 0);
		MCoordinate mCoordinate4 = new MCoordinate(new Double(47.33228691802673), new Double(15.40369884168651), 0);
		mLineString.addCoordinate(mCoordinate2);
		mLineString.addCoordinate(mCoordinate3);
		mLineString.addCoordinate(mCoordinate4);

		MGeometry mLinearRing = new MLinearRing();
		MCoordinate mCoordinate5 = new MCoordinate(new Double(47.34776178510898), new Double(15.42138288166528), 0);
		MCoordinate mCoordinate6 = new MCoordinate(new Double(47.33994511674428), new Double(15.42680263822824), 0);
		MCoordinate mCoordinate7 = new MCoordinate(new Double(47.3433327980027), new Double(15.44210783210553), 0);
		MCoordinate mCoordinate8 = new MCoordinate(new Double(47.35022485830221), new Double(15.43027664029841), 0);
		MCoordinate mCoordinate9 = new MCoordinate(new Double(47.34776178510898), new Double(15.42138288166528), 0);
		mLinearRing.addCoordinate(mCoordinate5);
		mLinearRing.addCoordinate(mCoordinate6);
		mLinearRing.addCoordinate(mCoordinate7);
		mLinearRing.addCoordinate(mCoordinate8);
		mLinearRing.addCoordinate(mCoordinate9);

		MGeometry mLineStringMulti1 = new MLineString();
		mLineStringMulti1.addCoordinate(mCoordinate5);
		mLineStringMulti1.addCoordinate(mCoordinate6);

		MGeometry mLineStringMulti2 = new MLineString();
		mLineStringMulti2.addCoordinate(mCoordinate6);
		mLineStringMulti2.addCoordinate(mCoordinate7);

		MMultiGeometry mMultiGeometry = new MMultiGeometry();
		mMultiGeometry.addGeometry(mLineStringMulti1);
		mMultiGeometry.addGeometry(mLineStringMulti2);

		MPlacemark mPlacemarkPoint = new MPlacemark();
		mPlacemarkPoint.setName("Hochlantsch");
		mPlacemarkPoint.setGeometry(mPoint);

		MPlacemark mPlacemarkLine = new MPlacemark();
		mPlacemarkLine.setName("Route Rote Wand");
		mPlacemarkLine.setGeometry(mLineString);

		MPlacemark mPlacemarkRing = new MPlacemark();
		mPlacemarkRing.setName("Tyrnauer Alm");
		mPlacemarkRing.setGeometry(mLinearRing);

		MPlacemark mPlacemarkMultiGeometry = new MPlacemark();
		mPlacemarkMultiGeometry.setName("Track");
		mPlacemarkMultiGeometry.setMultiGeometry(mMultiGeometry);

		MFolder mFolder = new MFolder();
		mFolder.setName("Folder Test");
		mFolder.addPlacemark(mPlacemarkPoint);

		MDocument mDocument = new MDocument();
		mDocument.setName("Punkt, Route und Polygon Test");
		mDocument.addPlacemark(mPlacemarkPoint);
		mDocument.addPlacemark(mPlacemarkLine);
		mDocument.addPlacemark(mPlacemarkRing);
		mDocument.addPlacemark(mPlacemarkMultiGeometry);
		mDocument.addFolder(mFolder);
		mKml.setDocument(mDocument);

	}

	@Test
	public void documentNameTest()
	{
		Assert.assertEquals("Punkt, Route und Polygon Test", mKml.getDocument().getName());
	}

	@Test
	public void placemarkNameTest()
	{
		Assert.assertEquals("Route Rote Wand", mKml.getDocument().getPlacemarkList().get(1).getName());
	}

	@Test
	public void folderNameTest()
	{
		Assert.assertEquals("Folder Test", mKml.getDocument().getFolderList().get(0).getName());
	}

	@Test
	public void placemarkSizeTest()
	{
		Assert.assertEquals(4, mKml.getDocument().getPlacemarkList().size());
	}

	@Test
	public void folderSizeTest()
	{
		Assert.assertEquals(1, mKml.getDocument().getFolderList().size());
	}

	@Test
	public void typeTest()
	{
		Assert.assertEquals(MPoint.class, mKml.getDocument().getPlacemarkList().get(0).getGeometry().getClass());
		Assert.assertEquals(MLineString.class, mKml.getDocument().getPlacemarkList().get(1).getGeometry().getClass());
		Assert.assertEquals(MLinearRing.class, mKml.getDocument().getPlacemarkList().get(2).getGeometry().getClass());
	}

	@Test
	public void coordinateTest()
	{
		Assert.assertEquals("15.423645,47.362176,1720 ",
				mKml.getDocument().getPlacemarkList().get(0).getGeometry().getCoordinateList());
		Assert.assertEquals(new Double(47.362176), mKml.getDocument().getPlacemarkList().get(0).getGeometry()
				.getCoordinates().getCoordinateList().get(0).getLat());
		Assert.assertEquals(new Double(15.423645), mKml.getDocument().getPlacemarkList().get(0).getGeometry()
				.getCoordinates().getCoordinateList().get(0).getLon());
		Assert.assertEquals(1720, mKml.getDocument().getPlacemarkList().get(0).getGeometry().getCoordinates()
				.getCoordinateList().get(0).getAltitude());
	}

	@Test
	public void multiGeometryTest()
	{
		Assert.assertEquals(2, mKml.getDocument().getPlacemarkList().get(3).getMultiGeometry().getGeometry().size());
		Assert.assertEquals(MLineString.class,
				mKml.getDocument().getPlacemarkList().get(3).getMultiGeometry().getGeometry().get(0).getClass());
		Assert.assertEquals(new Double(47.34776178510898), mKml.getDocument().getPlacemarkList().get(3)
				.getMultiGeometry().getGeometry().get(0).getCoordinates().getCoordinateList().get(0).getLat());
		Assert.assertEquals(new Double(15.42138288166528), mKml.getDocument().getPlacemarkList().get(3)
				.getMultiGeometry().getGeometry().get(0).getCoordinates().getCoordinateList().get(0).getLon());
	}
}
