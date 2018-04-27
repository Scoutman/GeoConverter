package at.mlakar.geoconverter.geojson.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.geojson.model.GeojsonElement;
import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MLineString;
import at.mlakar.geoconverter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.geojson.model.MMultiPoint;
import at.mlakar.geoconverter.geojson.model.MMultiPolygon;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.geojson.model.MPolygon;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;
import at.mlakar.geoconverter.gpx.model.MGpx;
import at.mlakar.geoconverter.gpx.model.MRoute;
import at.mlakar.geoconverter.gpx.model.MTrack;
import at.mlakar.geoconverter.gpx.model.MTrackSegment;
import at.mlakar.geoconverter.gpx.model.MWaypoint;

public class GeojsonGpxTransformer implements GeojsonGpxTransformerInterface
{

	public MGpx getModel(MGeojson mGeojson)
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

			if (geojsonType instanceof MPoint || geojsonType instanceof MMultiPoint)
			{
				MWaypoint gpxWaypoint = null;
				List<MWaypoint> coordinatesList = null;

				if (geojsonType instanceof MPoint)
				{
					// coordinates
					coordinatesList = visitSingleCoordinates(geojsonFeature.getGeometry().getCoordinates().getCoordinateList(), MPoint.class);
					gpxWaypoint = coordinatesList.get(0);

					// waypoint name
					String pointName = visitPropertyName(geojsonFeature.getProperties());
					gpxWaypoint.setName(pointName);
				}
				else if (geojsonType instanceof MMultiPoint)
				{
					// coordinates
					coordinatesList = visitMultiPoint(geojsonFeature);
					gpxWaypoint = coordinatesList.get(0);
				}

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
			if (geojsonFeature.getGeometry().getType() instanceof MLineString || geojsonFeature.getGeometry().getType() instanceof MPolygon)
			{
				// coordinates
				MRoute gpxRoute = new MRoute();
				List<MWaypoint> coordinatesList = null;

				if (geojsonFeature.getGeometry().getType() instanceof MLineString)
				{
					coordinatesList = visitSingleCoordinates(geojsonFeature.getGeometry().getCoordinates().getCoordinateList(), MLineString.class);
				}
				else if (geojsonFeature.getGeometry().getType() instanceof MPolygon)
				{
					coordinatesList = visitSingleCoordinates(geojsonFeature.getGeometry().getCoordinates().getCoordinateList(), MPolygon.class);
				}

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
			if (geojsonFeature.getGeometry().getType() instanceof MMultiLineString || geojsonFeature.getGeometry().getType() instanceof MMultiPolygon)
			{
				List<MTrack> trackList = null;

				if (geojsonFeature.getGeometry().getType() instanceof MMultiLineString)
				{
					trackList = visitMultiLineString(geojsonFeature);
				}
				else if (geojsonFeature.getGeometry().getType() instanceof MMultiPolygon)
				{
					trackList = visitMultiPolygonString(geojsonFeature);
				}


				gpxTrackList.addAll(trackList);
			}
		}

		return gpxTrackList;
	}

	@Override
	public List<MTrack> visitMultiLineString(MFeature geojsonFeature)
	{
		List<MTrack> gpxTrackList = new ArrayList<>();

		// name
		String trackName = visitPropertyName(geojsonFeature.getProperties());

		for (MCoordinate multiCoordinate : geojsonFeature.getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList())
		{
			MTrack gpxTrack = new MTrack();
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
			gpxTrackList.add(gpxTrack);
		}

		return gpxTrackList;
	}

	@Override
	public List<MTrack> visitMultiPolygonString(MFeature geojsonFeature)
	{
		List<MTrack> gpxTrackList = new ArrayList<>();

		// name
		String trackName = visitPropertyName(geojsonFeature.getProperties());

		for (MCoordinate multiCoordinate : geojsonFeature.getGeometry().getCoordinates().getCoordinateList().get(0).getCoordinateList())
		{
			MTrack gpxTrack = new MTrack();
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
			gpxTrackList.add(gpxTrack);
		}

		return gpxTrackList;
	}	
	
	@Override
	public List<MWaypoint> visitSingleCoordinates(List<MCoordinate> geojsonCoordinateList, Class<? extends MType> geometryTypeClass)
	{
		List<MWaypoint> gpxWaypointList = new ArrayList<>();

		if (geometryTypeClass.isInstance(new MPoint()))
		{
			MWaypoint gpxWaypoint = new MWaypoint();

			gpxWaypoint.setLat(((MCoordinatePosition) geojsonCoordinateList.get(0)).getLat());
			gpxWaypoint.setLon(((MCoordinatePosition) geojsonCoordinateList.get(0)).getLon());

			gpxWaypointList.add(gpxWaypoint);
		}

		if (geometryTypeClass.isInstance(new MLineString()))
		{
			for (MCoordinate geojsonCoordinate : geojsonCoordinateList.get(0).getCoordinateList())
			{
				MWaypoint gpxWaypoint = new MWaypoint();

				gpxWaypoint.setLat(((MCoordinatePosition) geojsonCoordinate).getLat());
				gpxWaypoint.setLon(((MCoordinatePosition) geojsonCoordinate).getLon());

				gpxWaypointList.add(gpxWaypoint);
			}
		}

		if (geometryTypeClass.isInstance(new MPolygon()))
		{
			for (MCoordinate geojsonCoordinate : geojsonCoordinateList.get(0).getCoordinateList().get(0).getCoordinateList())
			{
				MWaypoint gpxWaypoint = new MWaypoint();

				gpxWaypoint.setLat(((MCoordinatePosition) geojsonCoordinate).getLat());
				gpxWaypoint.setLon(((MCoordinatePosition) geojsonCoordinate).getLon());

				gpxWaypointList.add(gpxWaypoint);
			}
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
