package at.mlakar.geoconverter.kml.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.geojson.model.GeojsonElement;
import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MCoordinateList;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MGeometry;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.geojson.model.MPolygon;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.kml.model.MCoordinatesList;
import at.mlakar.geoconverter.kml.model.MFolder;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.kml.model.MLineString;
import at.mlakar.geoconverter.kml.model.MLinearRing;
import at.mlakar.geoconverter.kml.model.MPlacemark;

public class KmlGeojsonTransformer
{
	/**
	 * Transfomiert KML in GeoJSON Datenmodell.
	 * 
	 * @param mKml
	 * @return
	 */
	public MGeojson getGeojsonModel(MKml mKml)
	{
		MGeojson mGeojson = new MGeojson();

		List<MFeature> mFeatureList = visitPlacemark(mKml.getDocument().getPlacemarkList());
		mGeojson.setFeatureList(mFeatureList);

		List<MFeature> mFolderFeatureList = visitFolder(mKml.getDocument().getFolderList());
		mGeojson.getFeaturesList().addAll(mFolderFeatureList);

		return mGeojson;
	}

	private List<MFeature> visitFolder(List<MFolder> folderList)
	{
		List<MFeature> geojsonFeatureList = new ArrayList<>();

		for (MFolder mFolder : folderList)
		{
			geojsonFeatureList.addAll(visitPlacemark(mFolder.getPlacemarkList()));
		}

		return geojsonFeatureList;
	}

	private List<MFeature> visitPlacemark(List<MPlacemark> kmlPLacemarkList)
	{
		List<MFeature> geojsonFeatureList = new ArrayList<>();

		for (MPlacemark kmlPlacemark : kmlPLacemarkList)
		{
			MFeature geojsonFeature = new MFeature();

			// Geometry
			MGeometry geojsonGeometry = visitGeometry(kmlPlacemark.getGeometry());
			geojsonFeature.setGeometry(geojsonGeometry);

			// Properties
			String placemarkName = kmlPlacemark.getName();
			List<MProperty> geojsonProperties = new ArrayList<>();
			geojsonProperties.add(new MProperty(GeojsonElement.NAME, placemarkName));
			geojsonFeature.setProperties(geojsonProperties);

			geojsonFeatureList.add(geojsonFeature);
		}

		return geojsonFeatureList;
	}

	private MGeometry visitGeometry(at.mlakar.geoconverter.kml.model.MGeometry kmlGeometry)
	{
		MGeometry geojsonGeometry;
		MCoordinate geojsonCoordinates = null;

		// Type
		if (kmlGeometry instanceof at.mlakar.geoconverter.kml.model.MPoint)
		{
			geojsonGeometry = new MGeometry(new MPoint());

			// Coordinate
			geojsonCoordinates = visitCoordinatesPoint(kmlGeometry.getCoordinates());
		}
		else if (kmlGeometry instanceof MLineString)
		{
			geojsonGeometry = new MGeometry(new at.mlakar.geoconverter.geojson.model.MLineString());

			// Coordinate
			geojsonCoordinates = visitCoordinatesLineString(kmlGeometry.getCoordinates());
		}
		else if (kmlGeometry instanceof MLinearRing)
		{
			geojsonGeometry = new MGeometry(new MPolygon());

			// Coordinate
			geojsonCoordinates = visitCoordinatesLinearRing(kmlGeometry.getCoordinates());
		}
		else
		{
			throw new IllegalArgumentException("Illegal argument: " + kmlGeometry.getClass());
		}

		geojsonGeometry.setCoordinates(geojsonCoordinates);

		return geojsonGeometry;
	}

	private MCoordinate visitCoordinatesPoint(MCoordinatesList kmlCoordinates)
	{
		MCoordinateList geojsonCoordinateList = new MCoordinateList();

		Double lat = kmlCoordinates.getCoordinateList().get(0).getLat();
		Double lon = kmlCoordinates.getCoordinateList().get(0).getLon();

		geojsonCoordinateList.addCoordinateList(new MCoordinatePosition(lat, lon));

		return geojsonCoordinateList;
	}

	private MCoordinate visitCoordinatesLineString(MCoordinatesList kmlCoordinates)
	{
		MCoordinateList geojsonCoordinateList = new MCoordinateList();
		MCoordinateList geojsonCoordinateList2 = new MCoordinateList();

		for (at.mlakar.geoconverter.kml.model.MCoordinate coordinate : kmlCoordinates.getCoordinateList())
		{
			Double lat = coordinate.getLat();
			Double lon = coordinate.getLon();

			geojsonCoordinateList2.addCoordinateList(new MCoordinatePosition(lat, lon));
		}

		geojsonCoordinateList.addCoordinateList(geojsonCoordinateList2);

		return geojsonCoordinateList;
	}

	private MCoordinate visitCoordinatesLinearRing(MCoordinatesList kmlCoordinates)
	{
		MCoordinateList geojsonCoordinateList = new MCoordinateList();
		MCoordinateList geojsonCoordinateList2 = new MCoordinateList();
		MCoordinateList geojsonCoordinateList3 = new MCoordinateList();

		for (at.mlakar.geoconverter.kml.model.MCoordinate coordinate : kmlCoordinates.getCoordinateList())
		{
			Double lat = coordinate.getLat();
			Double lon = coordinate.getLon();

			geojsonCoordinateList3.addCoordinateList(new MCoordinatePosition(lat, lon));
		}

		geojsonCoordinateList2.addCoordinateList(geojsonCoordinateList3);
		geojsonCoordinateList.addCoordinateList(geojsonCoordinateList2);

		return geojsonCoordinateList;
	}
}
