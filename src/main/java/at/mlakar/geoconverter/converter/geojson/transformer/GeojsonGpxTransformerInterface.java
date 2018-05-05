package at.mlakar.geoconverter.converter.geojson.transformer;

import java.util.List;

import at.mlakar.geoconverter.converter.geojson.model.MFeature;
import at.mlakar.geoconverter.converter.geojson.model.MProperty;
import at.mlakar.geoconverter.converter.gpx.model.MRoute;
import at.mlakar.geoconverter.converter.gpx.model.MTrack;
import at.mlakar.geoconverter.converter.gpx.model.MWaypoint;

public interface GeojsonGpxTransformerInterface
{

	List<MWaypoint> generatePoints(List<MFeature> geojsonFeaturesList);

	List<MRoute> generateRoutes(List<MFeature> geojsonFeaturesList);

	List<MTrack> generateTracks(List<MFeature> geojsonFeaturesList);

	List<MWaypoint> visitSinglePoint(MFeature geojsonFeature);

	List<MWaypoint> visitSingleLineString(MFeature geojsonFeature);

	List<MWaypoint> visitSinglePolygon(MFeature geojsonFeature);	

	List<MWaypoint> visitMultiPoint(MFeature geojsonFeature);

	List<MTrack> visitMultiLineString(MFeature geojsonFeature);

	List<MTrack> visitMultiPolygonString(MFeature geojsonFeature);

	String visitPropertyName(List<MProperty> properties);

}