package at.mlakar.geoconverter.geojson.generator;


import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MCoordinateList;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MGeometry;
import at.mlakar.geoconverter.geojson.model.MLineString;
import at.mlakar.geoconverter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.geojson.model.MMultiPoint;
import at.mlakar.geoconverter.geojson.model.MMultiPolygon;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.geojson.model.MPolygon;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;

/**
 * Generiert aus Java Datenmodell ein JSON String. 
 *
 */
public class GeojsonGenerator
{

	public String getJson(MGeojson geojsonModel)
	{
		JsonObject jsonObject = visitGeojson(geojsonModel);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		return gson.toJson(jsonObject);
	}

	private JsonObject visitGeojson(MGeojson geojson)
	{
		JsonObject jsonObject = new JsonObject();
		JsonArray featureArray = new JsonArray();
		
		jsonObject.addProperty("type", "FeatureCollection");
		
		List<MFeature> featuresList = geojson.getFeaturesList();
		
		for (MFeature feature : featuresList)
		{
			JsonObject featureObject = visitFeature(feature);
			featureArray.add(featureObject);
		}
		
		jsonObject.add("features", featureArray);
		
		return jsonObject;
	}

	private JsonObject visitFeature(MFeature feature)
	{
		JsonObject jsonObject = new JsonObject();
		JsonObject jsonProperties = new JsonObject();
		JsonObject jsonGeometry = new JsonObject();
		
		jsonObject.addProperty("type", "Feature");
		
		if (feature.getProperties() != null)
		{	
			jsonProperties = visitProperties(feature.getProperties());
			jsonObject.add("properties", jsonProperties);
		}
		
		jsonGeometry = visitGeometry(feature.getGeometry());
		jsonObject.add("geometry", jsonGeometry);
		
		return jsonObject;
	}

	private JsonObject visitProperties(List<MProperty> propertiesList)
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

	private JsonObject visitGeometry(MGeometry geometry)
	{
		JsonObject jsonObject = new JsonObject();
		JsonArray jsonCoordinate = new JsonArray();
		
		String type = visitType(geometry.getType());
		jsonObject.addProperty("type", type);
		
		jsonCoordinate = visitCoordinate(geometry.getCoordinates());
		
		// Löschen des äußersten Arrays. Ansonsten ist JSON eine Ebene zu tief verschachtelt. 
		jsonCoordinate = (JsonArray) jsonCoordinate.get(0);
		
		jsonObject.add("coordinates", jsonCoordinate);
				
		return jsonObject;
	}
	
	private JsonArray visitCoordinate(MCoordinate coordinates)
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
	
	private String visitType(MType type)
	{
		if (type instanceof MPoint)
		{
			return "Point";
		}
		else if (type instanceof MMultiPoint)
		{
			return "MultiPoint";
		}
		else if (type instanceof MLineString) 
		{
			return "LineString";
		}
		else if (type instanceof MMultiLineString) 
		{
			return "MultiLineString";
		}
		else if (type instanceof MPolygon)
		{
			return "Polygon";
		}
		else if (type instanceof MMultiPolygon)
		{
			return "MultiPolygon";
		}
		else
		{
			throw new IllegalArgumentException("Illegal argument: " + type);
		}
	}
}
