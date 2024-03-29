package at.mlakar.geoconverter.converter.geojson.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
 * Generiert aus JSON String, im Geojson Format, ein Java Datenmodell <code>MGeojson</code>.
 *
 */
public class GeojsonModelGenerator implements GeojsonModelGeneratorInterface
{

	/**
	 * Gibt erstelltes <code>MGeojson</code> Datenmodell zurück.
	 * 
	 * @param jsonString Geojson String
	 * @return Datenmodell
	 */
	public MGeojson getModel(String jsonString)
	{
		MGeojson mGeojson = new MGeojson();
		
		JsonParser parser = new JsonParser();
		JsonObject jsonRootObject = parser.parse(jsonString).getAsJsonObject();		
		
		List<MFeature> featureList = parseFeatures(jsonRootObject);
		mGeojson.setFeatureList(featureList);
		
		return mGeojson;		
	}
	
	public List<MFeature> parseFeatures(JsonObject o)
	{
		List<MFeature> featuresList = new ArrayList<>();
		JsonArray featuresElement = o.get(GeojsonElement.FEATURES).getAsJsonArray();
		
		for (JsonElement featureElement : featuresElement)
		{
			MFeature feature = new MFeature();
			
			List<MProperty> propertiesList = parseProperties(featureElement);
			feature.setProperties(propertiesList);
			
			MGeometry geometry = parseGeometry(featureElement);
			
			featuresList.add(feature);
			feature.setGeometry(geometry);
		}
		
		return featuresList;
	}
	
	public List<MProperty> parseProperties(JsonElement e)
	{
		JsonElement propertiesElement = e.getAsJsonObject().get(GeojsonElement.PROPERTIES);
		List<MProperty> propertiesList = new ArrayList<>();
		
		// Keine properties in JSON vorhanden
		if (propertiesElement == null)
		{
			return null;
		}
		
		for (Entry<String, JsonElement> jsonEntry : propertiesElement.getAsJsonObject().entrySet())
		{
			String key = jsonEntry.getKey();
			String value = jsonEntry.getValue().getAsString();
			
			MProperty property = new MProperty(key, value);
			
			propertiesList.add(property);
		}
		
		return propertiesList;		
	}
	
	public MGeometry parseGeometry(JsonElement e)
	{
		JsonElement geometryElement = e.getAsJsonObject().get(GeojsonElement.GEOMETRY);
		
		String typeElement = geometryElement.getAsJsonObject().get(GeojsonElement.TYPE).getAsString();
		MType type = parseGeometryType(typeElement);
		MGeometry geometry = new MGeometry(type);
		
		MCoordinate coordinates = parseCoordinates(geometryElement);
		geometry.setCoordinates(coordinates);
		
		return geometry;
	}

	public MCoordinate parseCoordinates(JsonElement e)
	{
		JsonElement coordinatesElement = e.getAsJsonObject().get(GeojsonElement.COORDINATES);
		MCoordinate coordinatesList = new MCoordinateList();
		
		coordinatesList.addCoordinateList(parseCoordinateList(coordinatesElement));
		
		return coordinatesList;
	}
	
	public MCoordinate parseCoordinateList(JsonElement e)
	{
		MCoordinate coordinatesList = new MCoordinateList();
		
		if (!e.getAsJsonArray().get(0).isJsonArray())
		{			
			Double lat = e.getAsJsonArray().get(1).getAsDouble();
			Double lon = e.getAsJsonArray().get(0).getAsDouble();
			
			coordinatesList = new MCoordinatePosition(lat, lon);
			
			return coordinatesList;
		}
		
		for (JsonElement el : e.getAsJsonArray())
		{
			if (el.getAsJsonArray().get(0).isJsonArray())
			{			
				coordinatesList.addCoordinateList(parseCoordinateList(el.getAsJsonArray()));
			}
			else
			{				
				Double lat = el.getAsJsonArray().get(1).getAsDouble();
				Double lon = el.getAsJsonArray().get(0).getAsDouble();
				
				coordinatesList.addCoordinateList(new MCoordinatePosition(lat, lon));				
			}	
		}	
		
		return coordinatesList;
	}
	
	public MType parseGeometryType(String type)
	{
		switch (type)
		{
			case GeojsonElement.POINT:
				return new MPoint();
			
			case GeojsonElement.MULTI_POINT:
				return new MMultiPoint();
				
			case GeojsonElement.LINE_STRING:
				return new MLineString();
				
			case GeojsonElement.MULTI_LINE_STRING:
				return new MMultiLineString();
				
			case GeojsonElement.POLYGON:
				return new MPolygon();
				
			case GeojsonElement.MULTI_POLYGON:
				return new MMultiPolygon();

			default:
				throw new IllegalArgumentException("Illegal argument: " + type);
		}
	}
}
