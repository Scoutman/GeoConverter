package at.mlakar.geoconverter.converter.geojson.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.converter.geojson.model.GeojsonElement;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.converter.geojson.model.MFeature;
import at.mlakar.geoconverter.converter.geojson.model.MGeojson;
import at.mlakar.geoconverter.converter.geojson.model.MLineString;
import at.mlakar.geoconverter.converter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.converter.geojson.model.MMultiPoint;
import at.mlakar.geoconverter.converter.geojson.model.MMultiPolygon;
import at.mlakar.geoconverter.converter.geojson.model.MPoint;
import at.mlakar.geoconverter.converter.geojson.model.MPolygon;
import at.mlakar.geoconverter.converter.geojson.model.MProperty;
import at.mlakar.geoconverter.converter.geojson.model.MType;
import at.mlakar.geoconverter.converter.gpx.model.MGpx;
import at.mlakar.geoconverter.converter.gpx.model.MRoute;
import at.mlakar.geoconverter.converter.gpx.model.MTrack;
import at.mlakar.geoconverter.converter.gpx.model.MTrackSegment;
import at.mlakar.geoconverter.converter.gpx.model.MWaypoint;

public class GeojsonGpxTransformer implements GeojsonGpxTransformerInterface
{

	public MGpx getGpxModel(MGeojson mGeojson)
	{
		MGpx mGpx = new MGpx();

		// waypoints
		List<MWaypoint> gpxWaypointList = generatePoints(mGeojson.getFeaturesList());
		mGpx.getWaypointsList().addAll(gpxWaypointList);

		// routes
		List<MRoute> gpxRouteList = generateRoutes(mGeojson.getFeaturesList());
		mGpx.getRoutesList().addAll(gpxRouteList);

		// tracks
		List<MTrack> gpxTrackList = generateTracks(mGeojson.getFeaturesList());
		mGpx.getTracksList().addAll(gpxTrackList);

		return mGpx;
	}

	@Override
	public List<MWaypoint> generatePoints(List<MFeature> geojsonFeaturesList)
	{
		List<MWaypoint> gpxWaypointList = new ArrayList<>();

		for (MFeature geojsonFeature : geojsonFeaturesList)
		{
			MType geojsonType = geojsonFeature.getGeometry().getType();
			List<MWaypoint> coordinatesList = null;

			if (geojsonType instanceof MPoint)
			{
				// coordinates
				coordinatesList = visitSinglePoint(geojsonFeature);

				// waypoint name
				String pointName = visitPropertyName(geojsonFeature.getProperties());
				coordinatesList.get(0).setName(pointName);
			}
			else if (geojsonType instanceof MMultiPoint)
			{
				coordinatesList = visitMultiPoint(geojsonFeature);
			}

			if (coordinatesList != null)
			{
				gpxWaypointList.addAll(coordinatesList);
			}
		}

		return gpxWaypointList;
	}

	@Override
	public List<MRoute> generateRoutes(List<MFeature> geojsonFeaturesList)
	{
		List<MRoute> gpxRouteList = new ArrayList<>();

		for (MFeature geojsonFeature : geojsonFeaturesList)
		{
			MType geojsonType = geojsonFeature.getGeometry().getType();
			List<MWaypoint> coordinatesList = null;

			if (geojsonType instanceof MLineString)
			{
				coordinatesList = visitSingleLineString(geojsonFeature);
			}
			else if (geojsonType instanceof MPolygon)
			{
				coordinatesList = visitSinglePolygon(geojsonFeature);
			}

			if (coordinatesList != null)
			{
				MRoute gpxRoute = new MRoute();
				gpxRoute.getWaypointsList().addAll(coordinatesList);

				// route name
				String routeName = visitPropertyName(geojsonFeature.getProperties());
				gpxRoute.setName(routeName);

				gpxRouteList.add(gpxRoute);
			}
		}

		return gpxRouteList;
	}

	@Override
	public List<MTrack> generateTracks(List<MFeature> geojsonFeaturesList)
	{
		List<MTrack> gpxTrackList = new ArrayList<>();

		for (MFeature geojsonFeature : geojsonFeaturesList)
		{
			MType geojsonType = geojsonFeature.getGeometry().getType();
			List<MTrack> trackList = null;

			if (geojsonType instanceof MMultiLineString)
			{
				trackList = visitMultiLineString(geojsonFeature);
			}
			else if (geojsonType instanceof MMultiPolygon)
			{
				trackList = visitMultiPolygonString(geojsonFeature);
			}

			if (trackList != null)
			{
				gpxTrackList.addAll(trackList);
			}
		}

		return gpxTrackList;
	}

	@Override
	public List<MWaypoint> visitSinglePoint(MFeature geojsonFeature)
	{
		List<MWaypoint> gpxWaypointList = new ArrayList<>();
		MWaypoint gpxWaypoint = new MWaypoint();

		gpxWaypoint.setLat(((MCoordinatePosition) geojsonFeature.getGeometry().getCoordinates().getCoordinateList().get(0)).getLat());
		gpxWaypoint.setLon(((MCoordinatePosition) geojsonFeature.getGeometry().getCoordinates().getCoordinateList().get(0)).getLon());

		gpxWaypointList.add(gpxWaypoint);

		return gpxWaypointList;
	}

	@Override
	public List<MWaypoint> visitSingleLineString(MFeature geojsonFeature)
	{
		List<MWaypoint> gpxWaypointList = new ArrayList<>();

		for (MCoordinate geojsonCoordinate : geojsonFeature.getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList())
		{
			MWaypoint gpxWaypoint = new MWaypoint();

			gpxWaypoint.setLat(((MCoordinatePosition) geojsonCoordinate).getLat());
			gpxWaypoint.setLon(((MCoordinatePosition) geojsonCoordinate).getLon());

			gpxWaypointList.add(gpxWaypoint);
		}

		return gpxWaypointList;
	}

	@Override
	public List<MWaypoint> visitSinglePolygon(MFeature geojsonFeature)
	{
		List<MWaypoint> gpxWaypointList = new ArrayList<>();

		for (MCoordinate geojsonCoordinate : geojsonFeature.getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList().get(0)
				.getCoordinateList())
		{
			MWaypoint gpxWaypoint = new MWaypoint();

			gpxWaypoint.setLat(((MCoordinatePosition) geojsonCoordinate).getLat());
			gpxWaypoint.setLon(((MCoordinatePosition) geojsonCoordinate).getLon());

			gpxWaypointList.add(gpxWaypoint);
		}

		return gpxWaypointList;
	}

	@Override
	public List<MWaypoint> visitMultiPoint(MFeature geojsonFeature)
	{
		List<MWaypoint> gpxWaypointList = new ArrayList<>();

		// waypoint name
		String pointName = visitPropertyName(geojsonFeature.getProperties());

		for (MCoordinate coordinate : geojsonFeature.getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList())
		{
			MWaypoint gpxWaypoint = new MWaypoint();

			// name
			gpxWaypoint.setName(pointName);

			// coordinates
			gpxWaypoint.setLat(((MCoordinatePosition) coordinate).getLat());
			gpxWaypoint.setLon(((MCoordinatePosition) coordinate).getLon());

			gpxWaypointList.add(gpxWaypoint);
		}

		return gpxWaypointList;
	}

	@Override
	public List<MTrack> visitMultiLineString(MFeature geojsonFeature)
	{
		List<MTrack> gpxTrackList = new ArrayList<>();
		MTrack gpxTrack = new MTrack();
		
		// name
		String trackName = visitPropertyName(geojsonFeature.getProperties());

		for (MCoordinate multiCoordinate : geojsonFeature.getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList())
		{
			MTrackSegment gpxSegment = new MTrackSegment();

			// set name
			gpxTrack.setName(trackName);

			for (MCoordinate coordinate : multiCoordinate.getCoordinateList())
			{
				MWaypoint gpxWaypoint = new MWaypoint();

				gpxWaypoint.setLat(((MCoordinatePosition) coordinate).getLat());
				gpxWaypoint.setLon(((MCoordinatePosition) coordinate).getLon());

				gpxSegment.addWaypoint(gpxWaypoint);
			}

			gpxTrack.addSegment(gpxSegment);
		}
		
		gpxTrackList.add(gpxTrack);
		
		return gpxTrackList;
	}

	@Override
	public List<MTrack> visitMultiPolygonString(MFeature geojsonFeature)
	{
		List<MTrack> gpxTrackList = new ArrayList<>();
		MTrack gpxTrack = new MTrack();
		
		// name
		String trackName = visitPropertyName(geojsonFeature.getProperties());

		for (MCoordinate multiCoordinate : geojsonFeature.getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList())
		{
			MTrackSegment gpxSegment = new MTrackSegment();

			// set name
			gpxTrack.setName(trackName);

			for (MCoordinate coordinate : multiCoordinate.getCoordinateList().get(0).getCoordinateList())
			{
				MWaypoint gpxWaypoint = new MWaypoint();

				gpxWaypoint.setLat(((MCoordinatePosition) coordinate).getLat());
				gpxWaypoint.setLon(((MCoordinatePosition) coordinate).getLon());

				gpxSegment.addWaypoint(gpxWaypoint);
			}

			gpxTrack.addSegment(gpxSegment);
		}
		
		gpxTrackList.add(gpxTrack);

		return gpxTrackList;
	}

	@Override
	public String visitPropertyName(List<MProperty> properties)
	{
		for (MProperty geojsonProperty : properties)
		{
			if (geojsonProperty.getName().equals(GeojsonElement.NAME))
			{
				return geojsonProperty.getValue();
			}
		}

		return null;
	}
}
