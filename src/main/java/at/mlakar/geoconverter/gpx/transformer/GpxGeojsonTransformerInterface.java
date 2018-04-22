package at.mlakar.geoconverter.gpx.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.gpx.model.MRoute;
import at.mlakar.geoconverter.gpx.model.MTrack;
import at.mlakar.geoconverter.gpx.model.MTrackSegment;
import at.mlakar.geoconverter.gpx.model.MWaypoint;

public interface GpxGeojsonTransformerInterface
{

	List<MFeature> visitWaypointElements(List<MWaypoint> gpxWaypointList);

	List<MFeature> visitRouteElements(List<MRoute> gpxRouteList);

	List<MFeature> visitTrackElements(List<MTrack> gpxTrackList);

	List<MFeature> visitTrack(MTrack gpxTrack);

	MFeature visitTrackSegment(MTrackSegment gpxSegment);

	MFeature visitRoute(MRoute gpxRoute);

	MCoordinate visitWaypointList(ArrayList<MWaypoint> gpxWaypointsList);

	MCoordinatePosition visitWaypoint(MWaypoint gpxWaypoint);

	List<MProperty> visitPropertyName(MRoute gpxRoute);

	List<MProperty> visitPropertyName(MWaypoint gpxWaypoint);

	List<MProperty> visitPropertyName(MTrack gpxTrack);

}