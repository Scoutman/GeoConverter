package at.mlakar.geoconverter.geojson.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.geojson.model.GeojsonElement;
import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MLineString;
import at.mlakar.geoconverter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.geojson.model.MMultiPoint;
import at.mlakar.geoconverter.geojson.model.MMultiPolygon;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.geojson.model.MPolygon;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;
import at.mlakar.geoconverter.kml.model.MCoordinatesList;
import at.mlakar.geoconverter.kml.model.MDocument;
import at.mlakar.geoconverter.kml.model.MGeometry;
import at.mlakar.geoconverter.kml.model.MKml;
import at.mlakar.geoconverter.kml.model.MLinearRing;
import at.mlakar.geoconverter.kml.model.MMultiGeometry;
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

		// geometry single object
		if (visitSingleType(geojsonFeature.getGeometry().getType()) != null)
		{
			kmlGeometry = visitSingleGeometry(geojsonFeature.getGeometry());
			kmlPlacemark.setGeometry(kmlGeometry);
		}
		// geometry multi obect
		else if (visitMultiType(geojsonFeature.getGeometry().getType()) != null)
		{
			MMultiGeometry multiGeometry = visitMultiGeometry(geojsonFeature.getGeometry());
			kmlPlacemark.setMultiGeometry(multiGeometry);
		}

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
	public MGeometry visitSingleGeometry(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry)
	{
		MGeometry kmlGeometry = new MGeometry();
		MCoordinatesList kmlCoordinatesList = new MCoordinatesList();
		
		// single type
		kmlGeometry = visitSingleType(geojsonGeometry.getType());
		
		// coordinates
		kmlCoordinatesList = visitSingleCoordinates(geojsonGeometry.getCoordinates().getCoordinateList(), kmlGeometry.getClass());
		kmlGeometry.setCoordinateObjectList(kmlCoordinatesList);	
		
		return kmlGeometry;
	}
	
	@Override
	public MMultiGeometry visitMultiGeometry(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry)
	{
		MMultiGeometry multiGeometry = new MMultiGeometry();
		MType geojsonType = geojsonGeometry.getType();

		if (geojsonType instanceof MMultiPoint)
		{
			multiGeometry = visitMultiPoint(geojsonGeometry);
		}
		else if (geojsonType instanceof MMultiLineString)
		{
			multiGeometry = visitMultiLineString(geojsonGeometry);
		}
		else if (geojsonType instanceof MMultiPolygon)
		{
			multiGeometry = visitMultiPolygon(geojsonGeometry);
		}
		
		return multiGeometry;
	}

	@Override
	public MCoordinatesList visitSingleCoordinates(List<MCoordinate> geojsonCoordinateList, Class<? extends MGeometry> geometryTypeClass)
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
			for (MCoordinate geojsonCoordinate : geojsonCoordinateList.get(0).getCoordinateList().get(0).getCoordinateList())
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
	public MMultiGeometry visitMultiPoint(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry)
	{
		MMultiGeometry multiGeometry = new MMultiGeometry();
		
		for (MCoordinate coordinate : geojsonGeometry.getCoordinates().getCoordinateList().get(0).getCoordinateList())
		{
			MGeometry kmlGeometry = new at.mlakar.geoconverter.kml.model.MPoint();
			MCoordinatesList kmlCoordinateList = new MCoordinatesList();
			at.mlakar.geoconverter.kml.model.MCoordinate kmlCoordinate = new at.mlakar.geoconverter.kml.model.MCoordinate();
			
			kmlCoordinate.setLat(((MCoordinatePosition)coordinate).getLat());
			kmlCoordinate.setLon(((MCoordinatePosition)coordinate).getLon());
			kmlCoordinate.setAltitude(0);
			
			kmlCoordinateList.addCoordinate(kmlCoordinate);
			
			kmlGeometry.setCoordinateObjectList(kmlCoordinateList);
			multiGeometry.addGeometry(kmlGeometry);
		}
		
		return multiGeometry;
	}
	
	@Override
	public MMultiGeometry visitMultiLineString(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry)
	{
		MMultiGeometry multiGeometry = new MMultiGeometry();
		
		for (MCoordinate multiCoordinate : geojsonGeometry.getCoordinates().getCoordinateList().get(0).getCoordinateList())
		{
			MGeometry kmlGeometry = new at.mlakar.geoconverter.kml.model.MLineString();
			MCoordinatesList kmlCoordinateList = new MCoordinatesList();
			
			for (MCoordinate coordinate : multiCoordinate.getCoordinateList())
			{
				at.mlakar.geoconverter.kml.model.MCoordinate kmlCoordinate = new at.mlakar.geoconverter.kml.model.MCoordinate();
				
				kmlCoordinate.setLat(((MCoordinatePosition)coordinate).getLat());
				kmlCoordinate.setLon(((MCoordinatePosition)coordinate).getLon());
				kmlCoordinate.setAltitude(0);
				
				kmlCoordinateList.addCoordinate(kmlCoordinate);
			}
			
			kmlGeometry.setCoordinateObjectList(kmlCoordinateList);
			multiGeometry.addGeometry(kmlGeometry);
		}
		
		return multiGeometry;
	}
	
	@Override
	public MMultiGeometry visitMultiPolygon(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry)
	{
		MMultiGeometry multiGeometry = new MMultiGeometry();
		
		for (MCoordinate multiCoordinate : geojsonGeometry.getCoordinates().getCoordinateList().get(0).getCoordinateList())
		{
			MGeometry kmlGeometry = new at.mlakar.geoconverter.kml.model.MLinearRing();
			MCoordinatesList kmlCoordinateList = new MCoordinatesList();
			
			for (MCoordinate coordinate : multiCoordinate.getCoordinateList().get(0).getCoordinateList())
			{
				at.mlakar.geoconverter.kml.model.MCoordinate kmlCoordinate = new at.mlakar.geoconverter.kml.model.MCoordinate();
				
				kmlCoordinate.setLat(((MCoordinatePosition)coordinate).getLat());
				kmlCoordinate.setLon(((MCoordinatePosition)coordinate).getLon());
				kmlCoordinate.setAltitude(0);
				
				kmlCoordinateList.addCoordinate(kmlCoordinate);
			}
			
			kmlGeometry.setCoordinateObjectList(kmlCoordinateList);
			multiGeometry.addGeometry(kmlGeometry);
		}		
		
		return multiGeometry;
	}
	
	@Override
	public MGeometry visitSingleType(MType geojsonType)
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
			return null;
		}
	}
	
	@Override
	public MGeometry visitMultiType(MType geojsonType)
	{
		if (geojsonType instanceof MMultiPoint)
		{
			return new at.mlakar.geoconverter.kml.model.MPoint();
		}
		else if (geojsonType instanceof MMultiLineString)
		{
			return new at.mlakar.geoconverter.kml.model.MLineString();
		}
		else if (geojsonType instanceof MMultiPolygon)
		{
			return new MLinearRing();
		}
		else 
		{
			throw new IllegalArgumentException("Illegal MultiType: " + geojsonType.getClass()); 
		}
	}	
}
