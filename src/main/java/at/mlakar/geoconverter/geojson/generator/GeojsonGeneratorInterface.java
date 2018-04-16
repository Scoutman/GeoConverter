package at.mlakar.geoconverter.geojson.generator;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MGeometry;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;

public interface GeojsonGeneratorInterface
{
	public JsonObject visitGeojson(MGeojson geojson);

	public JsonObject visitFeature(MFeature feature);

	public JsonObject visitProperties(List<MProperty> propertiesList);

	public JsonObject visitGeometry(MGeometry geometry);

	public JsonArray visitCoordinate(MCoordinate coordinates);

	public String visitType(MType type);
}
