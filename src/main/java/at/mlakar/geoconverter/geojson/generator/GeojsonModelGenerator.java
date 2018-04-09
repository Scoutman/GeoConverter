package at.mlakar.geoconverter.geojson.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
 * Generiert aus JSON String, im Geojson Format, ein Java Datenmodell <code>MGeojson</code>.
 *
 */
public class GeojsonModelGenerator
{

	/**
	 * Gibt erstelltes <code>MGeojson</code> Datenmodell zurück.
	 * 
	 * @param geojsonFile Pfad zu Geojson Datei. 
	 * @return Datenmodell
	 */
	public MGeojson getModel(String geojsonFile)
	{
		MGeojson mGeojson = new MGeojson();
		String json = "";
		
		try
		{
			json = new String(Files.readAllBytes(Paths.get(geojsonFile)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		JsonParser parser = new JsonParser();
		JsonObject jsonRootObject = parser.parse(json).getAsJsonObject();		
		
		List<MFeature> featureList = parseFeatures(jsonRootObject);
		mGeojson.setFeatureList(featureList);
		
		return mGeojson;		
	}
	
	private List<MFeature> parseFeatures(JsonObject o)
	{
		List<MFeature> featuresList = new ArrayList<>();
		JsonArray featuresElement = o.get("features").getAsJsonArray();
		
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
	
	private List<MProperty> parseProperties(JsonElement e)
	{
		JsonElement propertiesElement = e.getAsJsonObject().get("properties");
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
	
	private MGeometry parseGeometry(JsonElement e)
	{
		JsonElement geometryElement = e.getAsJsonObject().get("geometry");
		
		String typeElement = geometryElement.getAsJsonObject().get("type").getAsString();
		MType type = parseGeometryType(typeElement);
		MGeometry geometry = new MGeometry(type);
		
		MCoordinate coordinates = parseCoordinates(geometryElement);
		geometry.setCoordinates(coordinates);
		
		return geometry;
	}

	private MCoordinate parseCoordinates(JsonElement e)
	{
		JsonElement coordinatesElement = e.getAsJsonObject().get("coordinates");
		MCoordinate coordinatesList = new MCoordinateList();
		
		coordinatesList.addCoordinateList(parseCoordinateList(coordinatesElement));
		
		return coordinatesList;
	}
	
	private MCoordinate parseCoordinateList(JsonElement e)
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
	
	private MType parseGeometryType(String type)
	{
		switch (type)
		{
			case "Point":
				return new MPoint();
			
			case "MultiPoint":
				return new MMultiPoint();
				
			case "LineString":
				return new MLineString();
				
			case "MultiLineString":
				return new MMultiLineString();
				
			case "Polygon":
				return new MPolygon();
				
			case "MultiPolygon":
				return new MMultiPolygon();

			default:
				throw new IllegalArgumentException("Illegal argument: " + type);
		}
	}
}
