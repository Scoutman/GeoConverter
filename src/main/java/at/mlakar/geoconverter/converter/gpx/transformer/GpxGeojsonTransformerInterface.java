package at.mlakar.geoconverter.converter.gpx.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.converter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.converter.geojson.model.MFeature;
import at.mlakar.geoconverter.converter.geojson.model.MProperty;
import at.mlakar.geoconverter.converter.gpx.model.MRoute;
import at.mlakar.geoconverter.converter.gpx.model.MTrack;
import at.mlakar.geoconverter.converter.gpx.model.MWaypoint;

public interface GpxGeojsonTransformerInterface
{

	List<MFeature> visitWaypointElements(List<MWaypoint> gpxWaypointList);

	List<MFeature> visitRouteElements(List<MRoute> gpxRouteList);

	List<MFeature> visitTrackElements(List<MTrack> gpxTrackList);

	MFeature visitTrack(MTrack gpxTrack);

	MFeature visitRoute(MRoute gpxRoute);

	MCoordinate visitWaypointList(ArrayList<MWaypoint> gpxWaypointsList);

	MCoordinatePosition visitWaypoint(MWaypoint gpxWaypoint);

	List<MProperty> visitPropertyName(MRoute gpxRoute);

	List<MProperty> visitPropertyName(MWaypoint gpxWaypoint);

	List<MProperty> visitPropertyName(MTrack gpxTrack);

}