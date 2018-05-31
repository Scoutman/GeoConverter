package at.mlakar.geoconverter.converter.geojson.generator;


import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import at.mlakar.geoconverter.converter.geojson.model.GeojsonElement;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinateList;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.converter.geojson.model.MFeature;
import at.mlakar.geoconverter.converter.geojson.model.MGeojson;
import at.mlakar.geoconverter.converter.geojson.model.MGeometry;
import at.mlakar.geoconverter.converter.geojson.model.MLineString;
import at.mlakar.geoconverter.converter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.converter.geojson.model.MMultiPoint;
import at.mlakar.geoconverter.converter.geojson.model.MMultiPolygon;
import at.mlakar.geoconverter.converter.geojson.model.MPoint;
import at.mlakar.geoconverter.converter.geojson.model.MPolygon;
import at.mlakar.geoconverter.converter.geojson.model.MProperty;
import at.mlakar.geoconverter.converter.geojson.model.MType;

/**
 * Generiert aus Java Datenmodell ein JSON String. 
 *
 */
public class GeojsonGenerator implements GeojsonGeneratorInterface
{

	public String getGeojson(MGeojson geojsonModel)
	{
		JsonObject jsonObject = visitGeojson(geojsonModel);
		
		Gson gson = new GsonBuilder().create();
		
		return gson.toJson(jsonObject);
	}

	public JsonObject visitGeojson(MGeojson geojson)
	{
		JsonObject jsonObject = new JsonObject();
		JsonArray featureArray = new JsonArray();
		
		jsonObject.addProperty(GeojsonElement.TYPE, GeojsonElement.FEATURE_COLLECTION);
		
		List<MFeature> featuresList = geojson.getFeaturesList();
		
		for (MFeature feature : featuresList)
		{
			JsonObject featureObject = visitFeature(feature);
			featureArray.add(featureObject);
		}
		
		jsonObject.add(GeojsonElement.FEATURES, featureArray);
		
		return jsonObject;
	}

	public JsonObject visitFeature(MFeature feature)
	{
		JsonObject jsonObject = new JsonObject();
		JsonObject jsonProperties = new JsonObject();
		JsonObject jsonGeometry = new JsonObject();
		
		jsonObject.addProperty(GeojsonElement.TYPE, GeojsonElement.FEATURE);
		
		if (feature.getProperties() != null)
		{	
			jsonProperties = visitProperties(feature.getProperties());
			jsonObject.add(GeojsonElement.PROPERTIES, jsonProperties);
		}
		
		jsonGeometry = visitGeometry(feature.getGeometry());
		jsonObject.add(GeojsonElement.GEOMETRY, jsonGeometry);
		
		return jsonObject;
	}

	public JsonObject visitProperties(List<MProperty> propertiesList)
	{
		JsonObject jsonObject = new JsonObject();
		
		for (MProperty property : propertiesList)
		{
			String name = property.getName();
			String value = property.getValue();
			
			jsonObject.addProperty(name, value);
		}
		
		return jsonObject;
	}

	public JsonObject visitGeometry(MGeometry geometry)
	{
		JsonObject jsonObject = new JsonObject();
		JsonArray jsonCoordinate = new JsonArray();
		
		String type = visitType(geometry.getType());
		jsonObject.addProperty(GeojsonElement.TYPE, type);
		
		jsonCoordinate = visitCoordinate(geometry.getCoordinates());
		
		// Löschen des äußersten Arrays. Ansonsten ist JSON eine Ebene zu tief verschachtelt. 
		jsonCoordinate = (JsonArray) jsonCoordinate.get(0);
		
		jsonObject.add(GeojsonElement.COORDINATES, jsonCoordinate);
				
		return jsonObject;
	}
	
	public JsonArray visitCoordinate(MCoordinate coordinates)
	{
		JsonArray jsonArray = new JsonArray();	

		for (MCoordinate coordinate : coordinates.getCoordinateList())
		{
			if (coordinate instanceof MCoordinateList)
			{
				jsonArray.add(visitCoordinate(coordinate));			
			}			
			else if (coordinate instanceof MCoordinatePosition)
			{	
				MCoordinatePosition position = (MCoordinatePosition)coordinate;
				
				JsonArray posArray = new JsonArray();
				
				posArray.add(position.getLon());
				posArray.add(position.getLat());
				
				jsonArray.add(posArray);
			}

		}		
		return jsonArray;
	}		
	
	public String visitType(MType type)
	{
		if (type instanceof MPoint)
		{
			return GeojsonElement.POINT;
		}
		else if (type instanceof MMultiPoint)
		{
			return GeojsonElement.MULTI_POINT;
		}
		else if (type instanceof MLineString) 
		{
			return GeojsonElement.LINE_STRING;
		}
		else if (type instanceof MMultiLineString) 
		{
			return GeojsonElement.MULTI_LINE_STRING;
		}
		else if (type instanceof MPolygon)
		{
			return GeojsonElement.POLYGON;
		}
		else if (type instanceof MMultiPolygon)
		{
			return GeojsonElement.MULTI_POLYGON;
		}
		else
		{
			throw new IllegalArgumentException("Illegal argument: " + type);
		}
	}
}
