package at.mlakar.geoconverter.geojson.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.geojson.model.GeojsonElement;
import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MLineString;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.geojson.model.MPolygon;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;
import at.mlakar.geoconverter.kml.model.MCoordinatesList;
import at.mlakar.geoconverter.kml.model.MDocument;
import at.mlakar.geoconverter.kml.model.MGeometry;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.kml.model.MLinearRing;
import at.mlakar.geoconverter.kml.model.MPlacemark;

public class GeojsonKmlTransformer implements GeojsonKmlTransformerInterface
{
	public MKml getKmlModel(MGeojson mGeojson)
	{
		MKml mKml = new MKml();
		MDocument kmlDocument = new MDocument();

		List<MPlacemark> kmlPlacemarkList = visitFeatureList(mGeojson.getFeaturesList());

		kmlDocument.getPlacemarkList().addAll(kmlPlacemarkList);
		mKml.setDocument(kmlDocument);

		return mKml;
	}

	@Override
	public List<MPlacemark> visitFeatureList(List<MFeature> geojsonFeaturesList)
	{
		List<MPlacemark> kmlPlacemarkList = new ArrayList<>();

		for (MFeature geojsonFeature : geojsonFeaturesList)
		{
			kmlPlacemarkList.add(visitFeature(geojsonFeature));
		}

		return kmlPlacemarkList;
	}

	@Override
	public MPlacemark visitFeature(MFeature geojsonFeature)
	{
		MPlacemark kmlPlacemark = new MPlacemark();
		MGeometry kmlGeometry = new MGeometry();

		// get feature name
		String placemarkName = visitPropertyName(geojsonFeature.getProperties());

		if (placemarkName != null)
		{
			kmlPlacemark.setName(placemarkName);
		}

		// geometry
		kmlGeometry = visitGeometry(geojsonFeature.getGeometry());
		kmlPlacemark.setGeometry(kmlGeometry);

		return kmlPlacemark;
	}

	@Override
	public String visitPropertyName(List<MProperty> properties)
	{
		for (MProperty geojsonProperty : properties)
		{
			if (geojsonProperty.getName().equals(GeojsonElement.NAME))
			{
				return geojsonProperty.getValue();
			}
		}

		return null;
	}

	@Override
	public MGeometry visitGeometry(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry)
	{
		MGeometry kmlGeometry = new MGeometry();
		MCoordinatesList kmlCoordinatesList = new MCoordinatesList();
		
		// type
		kmlGeometry = visitType(geojsonGeometry.getType());

		// coordinates
		kmlCoordinatesList = visitCoordinates(geojsonGeometry.getCoordinates().getCoordinateList(),
				kmlGeometry.getClass());
		kmlGeometry.setCoordinateObjectList(kmlCoordinatesList);

		return kmlGeometry;
	}
	
	@Override
	public MCoordinatesList visitCoordinates(List<MCoordinate> geojsonCoordinateList, Class<? extends MGeometry> geometryTypeClass)
	{
		MCoordinatesList kmlCoordinatesList = new MCoordinatesList();

		if (geometryTypeClass.isInstance(new at.mlakar.geoconverter.kml.model.MPoint()))
		{
			at.mlakar.geoconverter.kml.model.MCoordinate kmlCoordinate = new at.mlakar.geoconverter.kml.model.MCoordinate();

			kmlCoordinate.setLat(((MCoordinatePosition) geojsonCoordinateList.get(0)).getLat());
			kmlCoordinate.setLon(((MCoordinatePosition) geojsonCoordinateList.get(0)).getLon());

			kmlCoordinatesList.addCoordinate(kmlCoordinate);
		}

		if (geometryTypeClass.isInstance(new at.mlakar.geoconverter.kml.model.MLineString()))
		{
			for (MCoordinate geojsonCoordinate : geojsonCoordinateList.get(0).getCoordinateList())
			{
				at.mlakar.geoconverter.kml.model.MCoordinate kmlCoordinate = new at.mlakar.geoconverter.kml.model.MCoordinate();

				kmlCoordinate.setLat(((MCoordinatePosition) geojsonCoordinate).getLat());
				kmlCoordinate.setLon(((MCoordinatePosition) geojsonCoordinate).getLon());

				kmlCoordinatesList.addCoordinate(kmlCoordinate);
			}
		}

		if (geometryTypeClass.isInstance(new at.mlakar.geoconverter.kml.model.MLinearRing()))
		{
			for (MCoordinate geojsonCoordinate : geojsonCoordinateList.get(0).getCoordinateList().get(0)
					.getCoordinateList())
			{
				at.mlakar.geoconverter.kml.model.MCoordinate kmlCoordinate = new at.mlakar.geoconverter.kml.model.MCoordinate();

				kmlCoordinate.setLat(((MCoordinatePosition) geojsonCoordinate).getLat());
				kmlCoordinate.setLon(((MCoordinatePosition) geojsonCoordinate).getLon());

				kmlCoordinatesList.addCoordinate(kmlCoordinate);
			}
		}

		return kmlCoordinatesList;
	}

	@Override
	public MGeometry visitType(MType geojsonType)
	{
		if (geojsonType instanceof MPoint)
		{
			return new at.mlakar.geoconverter.kml.model.MPoint();
		}
		else if (geojsonType instanceof MLineString)
		{
			return new at.mlakar.geoconverter.kml.model.MLineString();
		}
		else if (geojsonType instanceof MPolygon)
		{
			return new MLinearRing();
		}
		else
		{
			throw new IllegalArgumentException("Illegal GeoJSON type " + geojsonType.getClass());
		}
	}
}
