package at.mlakar.geoconverter.geojson.transformer;

import java.util.List;

import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;
import at.mlakar.geoconverter.gpx.model.MRoute;
import at.mlakar.geoconverter.gpx.model.MTrack;
import at.mlakar.geoconverter.gpx.model.MWaypoint;

public interface GeojsonGpxTransformerInterface
{

	List<MWaypoint> generatePoints(List<MFeature> geojsonFeaturesList);

	List<MRoute> generateRoutes(List<MFeature> geojsonFeaturesList);

	List<MTrack> generateTracks(List<MFeature> geojsonFeaturesList);
	
	List<MWaypoint> visitSingleCoordinates(List<MCoordinate> geojsonCoordinateList, Class<? extends MType> geometryTypeClass);

	String visitPropertyName(List<MProperty> properties);

	List<MWaypoint> visitMultiPoint(MFeature geojsonFeature);

	List<MTrack> visitMultiLineString(MFeature geojsonFeature);

	List<MTrack> visitMultiPolygonString(MFeature geojsonFeature);

}