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
import at.mlakar.geoconverter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.geojson.model.MMultiPoint;
import at.mlakar.geoconverter.geojson.model.MMultiPolygon;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.geojson.model.MPolygon;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.kml.model.MCoordinatesList;
import at.mlakar.geoconverter.kml.model.MFolder;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.kml.model.MLineString;
import at.mlakar.geoconverter.kml.model.MLinearRing;
import at.mlakar.geoconverter.kml.model.MMultiGeometry;
import at.mlakar.geoconverter.kml.model.MPlacemark;

public class KmlGeojsonTransformer implements KmlGeojsonTransformerInterface
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

	@Override
	public List<MFeature> visitFolder(List<MFolder> folderList)
	{
		List<MFeature> geojsonFeatureList = new ArrayList<>();

		for (MFolder mFolder : folderList)
		{
			geojsonFeatureList.addAll(visitPlacemark(mFolder.getPlacemarkList()));
		}

		return geojsonFeatureList;
	}

	@Override
	public List<MFeature> visitPlacemark(List<MPlacemark> kmlPLacemarkList)
	{
		List<MFeature> geojsonFeatureList = new ArrayList<>();

		for (MPlacemark kmlPlacemark : kmlPLacemarkList)
		{
			MFeature geojsonFeature = new MFeature();

			// Geometry (Placemark mit Geometry und ohne MultiGeometry)
			if (kmlPlacemark.isSetGeometry())
			{
				MGeometry geojsonGeometry = visitSingleGeometry(kmlPlacemark.getGeometry());
				geojsonFeature.setGeometry(geojsonGeometry);
			}
			else if (kmlPlacemark.isSetMultiGeometry())
			{
				MGeometry geojsonGeometry = visitMultiGeometry(kmlPlacemark.getMultiGeometry());
				geojsonFeature.setGeometry(geojsonGeometry);
			}

			// Properties
			String placemarkName = kmlPlacemark.getName();
			List<MProperty> geojsonProperties = new ArrayList<>();
			geojsonProperties.add(new MProperty(GeojsonElement.NAME, placemarkName));
			geojsonFeature.setProperties(geojsonProperties);

			geojsonFeatureList.add(geojsonFeature);
		}

		return geojsonFeatureList;
	}

	@Override
	public MGeometry visitSingleGeometry(at.mlakar.geoconverter.kml.model.MGeometry kmlGeometry)
	{
		MGeometry geojsonGeometry;
		MCoordinate geojsonCoordinates = null;

		if (kmlGeometry instanceof at.mlakar.geoconverter.kml.model.MPoint)
		{
			geojsonGeometry = new MGeometry(new MPoint());
			geojsonCoordinates = visitCoordinatesPoint(kmlGeometry.getCoordinates());
		}
		else if (kmlGeometry instanceof MLineString)
		{
			geojsonGeometry = new MGeometry(new at.mlakar.geoconverter.geojson.model.MLineString());
			geojsonCoordinates = visitCoordinatesLineString(kmlGeometry.getCoordinates());
		}
		else if (kmlGeometry instanceof MLinearRing)
		{
			geojsonGeometry = new MGeometry(new MPolygon());
			geojsonCoordinates = visitCoordinatesLinearRing(kmlGeometry.getCoordinates());
		}
		else
		{
			throw new IllegalArgumentException("Illegal argument: " + kmlGeometry.getClass());
		}

		geojsonGeometry.setCoordinates(geojsonCoordinates);

		return geojsonGeometry;
	}

	@Override
	public MGeometry visitMultiGeometry(MMultiGeometry kmlMultiGeometry)
	{
		MGeometry geojsonGeometry = null;
		MCoordinate geojsonCoordinates = null;
		at.mlakar.geoconverter.kml.model.MGeometry kmlGeometry = kmlMultiGeometry.getGeometry().get(0);

		if (kmlGeometry instanceof at.mlakar.geoconverter.kml.model.MPoint)
		{
			geojsonGeometry = new MGeometry(new MMultiPoint());
			geojsonCoordinates = visitCoordinatesMultiPoint(kmlMultiGeometry);
		}
		else if (kmlGeometry instanceof MLineString)
		{
			geojsonGeometry = new MGeometry(new MMultiLineString());
			geojsonCoordinates = visitCoordinatesMultiLineString(kmlMultiGeometry);
		}
		else if (kmlGeometry instanceof MLinearRing)
		{
			geojsonGeometry = new MGeometry(new MMultiPolygon());
			geojsonCoordinates = visitCoordinatesMultiLinearRing(kmlMultiGeometry);

		}
		else
		{
			throw new IllegalArgumentException("Illegal argument: " + kmlGeometry.getClass());
		}

		geojsonGeometry.setCoordinates(geojsonCoordinates);

		return geojsonGeometry;
	}
	
	@Override
	public MCoordinate visitCoordinatesPoint(MCoordinatesList kmlCoordinates)
	{
		MCoordinateList geojsonCoordinateList = new MCoordinateList();

		Double lat = kmlCoordinates.getCoordinateList().get(0).getLat();
		Double lon = kmlCoordinates.getCoordinateList().get(0).getLon();

		geojsonCoordinateList.addCoordinateList(new MCoordinatePosition(lat, lon));

		return geojsonCoordinateList;
	}

	@Override
	public MCoordinate visitCoordinatesLineString(MCoordinatesList kmlCoordinates)
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

	@Override
	public MCoordinate visitCoordinatesLinearRing(MCoordinatesList kmlCoordinates)
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

	@Override
	public MCoordinate visitCoordinatesMultiLinearRing(MMultiGeometry kmlMultiGeometry)
	{
		MCoordinateList geojsonCoordinateListL1 = new MCoordinateList();
		MCoordinateList geojsonCoordinateListL2 = new MCoordinateList();

		for (at.mlakar.geoconverter.kml.model.MGeometry kmlGeometry : kmlMultiGeometry.getGeometry())
		{
			MCoordinateList geojsonCoordinateListL3 = new MCoordinateList();
			MCoordinateList geojsonCoordinateListL4 = new MCoordinateList();

			for (at.mlakar.geoconverter.kml.model.MCoordinate kmlCoordinate : kmlGeometry.getCoordinates().getCoordinateList())
			{
				Double lat = kmlCoordinate.getLat();
				Double lon = kmlCoordinate.getLon();

				geojsonCoordinateListL4.addCoordinateList(new MCoordinatePosition(lat, lon));
			}
			geojsonCoordinateListL3.addCoordinateList(geojsonCoordinateListL4);
			geojsonCoordinateListL2.addCoordinateList(geojsonCoordinateListL3);
		}

		geojsonCoordinateListL1.addCoordinateList(geojsonCoordinateListL2);

		return geojsonCoordinateListL1;
	}

	@Override
	public MCoordinate visitCoordinatesMultiLineString(MMultiGeometry kmlMultiGeometry)
	{
		MCoordinateList geojsonCoordinateListL1 = new MCoordinateList();
		MCoordinateList geojsonCoordinateListL2 = new MCoordinateList();

		for (at.mlakar.geoconverter.kml.model.MGeometry kmlGeometry : kmlMultiGeometry.getGeometry())
		{
			MCoordinateList geojsonCoordinateListL3 = new MCoordinateList();

			for (at.mlakar.geoconverter.kml.model.MCoordinate kmlCoordinate : kmlGeometry.getCoordinates().getCoordinateList())
			{
				Double lat = kmlCoordinate.getLat();
				Double lon = kmlCoordinate.getLon();

				geojsonCoordinateListL3.addCoordinateList(new MCoordinatePosition(lat, lon));
			}

			geojsonCoordinateListL2.addCoordinateList(geojsonCoordinateListL3);
		}

		geojsonCoordinateListL1.addCoordinateList(geojsonCoordinateListL2);

		return geojsonCoordinateListL1;
	}

	@Override
	public MCoordinate visitCoordinatesMultiPoint(MMultiGeometry kmlMultiGeometry)
	{
		MCoordinateList geojsonCoordinateList = new MCoordinateList();
		geojsonCoordinateList.addCoordinateList(new MCoordinateList());

		for (at.mlakar.geoconverter.kml.model.MGeometry kmlGeometry : kmlMultiGeometry.getGeometry())
		{
			Double lat = kmlGeometry.getCoordinates().getCoordinateList().get(0).getLat();
			Double lon = kmlGeometry.getCoordinates().getCoordinateList().get(0).getLon();

			geojsonCoordinateList.getCoordinateList().get(0).addCoordinateList(new MCoordinatePosition(lat, lon));
		}

		return geojsonCoordinateList;
	}


}
