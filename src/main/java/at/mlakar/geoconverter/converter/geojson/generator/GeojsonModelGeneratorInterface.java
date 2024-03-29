package at.mlakar.geoconverter.converter.geojson.generator;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.mlakar.geoconverter.converter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.converter.geojson.model.MFeature;
import at.mlakar.geoconverter.converter.geojson.model.MGeometry;
import at.mlakar.geoconverter.converter.geojson.model.MProperty;
import at.mlakar.geoconverter.converter.geojson.model.MType;

public interface GeojsonModelGeneratorInterface
{
	public List<MFeature> parseFeatures(JsonObject o);

	public List<MProperty> parseProperties(JsonElement e);

	public MGeometry parseGeometry(JsonElement e);

	public MCoordinate parseCoordinates(JsonElement e);

	public MCoordinate parseCoordinateList(JsonElement e);

	public MType parseGeometryType(String type);
}