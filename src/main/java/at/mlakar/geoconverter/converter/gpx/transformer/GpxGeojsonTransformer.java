package at.mlakar.geoconverter.converter.gpx.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.converter.geojson.model.GeojsonElement;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinateList;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.converter.geojson.model.MFeature;
import at.mlakar.geoconverter.converter.geojson.model.MGeojson;
import at.mlakar.geoconverter.converter.geojson.model.MGeometry;
import at.mlakar.geoconverter.converter.geojson.model.MLineString;
import at.mlakar.geoconverter.converter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.converter.geojson.model.MPoint;
import at.mlakar.geoconverter.converter.geojson.model.MProperty;
import at.mlakar.geoconverter.converter.gpx.model.MGpx;
import at.mlakar.geoconverter.converter.gpx.model.MRoute;
import at.mlakar.geoconverter.converter.gpx.model.MTrack;
import at.mlakar.geoconverter.converter.gpx.model.MTrackSegment;
import at.mlakar.geoconverter.converter.gpx.model.MWaypoint;

public class GpxGeojsonTransformer implements GpxGeojsonTransformerInterface
{
	public MGeojson getGeojsonModel(MGpx mGpx)
	{
		MGeojson mGeojson = new MGeojson();

		mGeojson.getFeaturesList().addAll(visitWaypointElements(mGpx.getWaypointsList()));
		mGeojson.getFeaturesList().addAll(visitRouteElements(mGpx.getRoutesList()));
		mGeojson.getFeaturesList().addAll(visitTrackElements(mGpx.getTracksList()));

		return mGeojson;
	}

	@Override
	public List<MFeature> visitWaypointElements(List<MWaypoint> gpxWaypointList)
	{
		List<MFeature> geojsonFeatureList = new ArrayList<>();

		for (MWaypoint gpxWaypoint : gpxWaypointList)
		{
			MFeature geojsonFeature = new MFeature();
			MGeometry geojsonGeometry = new MGeometry(new MPoint());

			// properties
			geojsonFeature.setProperties(visitPropertyName(gpxWaypoint));

			// coordinates
			MCoordinatePosition coordinatePosition = visitWaypoint(gpxWaypoint);
			MCoordinateList geojsonCoordinateList = new MCoordinateList();
			geojsonCoordinateList.addCoordinateList(coordinatePosition);
			geojsonGeometry.setCoordinates(geojsonCoordinateList);
			geojsonFeature.setGeometry(geojsonGeometry);

			geojsonFeatureList.add(geojsonFeature);
		}

		return geojsonFeatureList;
	}

	@Override
	public List<MFeature> visitRouteElements(List<MRoute> gpxRouteList)
	{
		List<MFeature> geojsonFeatureList = new ArrayList<>();

		for (MRoute gpxRoute : gpxRouteList)
		{
			MFeature geojsonFeature = visitRoute(gpxRoute);
			geojsonFeatureList.add(geojsonFeature);
		}

		return geojsonFeatureList;
	}

	@Override
	public List<MFeature> visitTrackElements(List<MTrack> gpxTrackList)
	{
		List<MFeature> geojsonFeatureList = new ArrayList<>();

		for (MTrack gpxTrack : gpxTrackList)
		{
			geojsonFeatureList.add(visitTrack(gpxTrack));
		}

		return geojsonFeatureList;
	}

	@Override
	public MFeature visitTrack(MTrack gpxTrack)
	{
		MFeature geojsonFeature = new MFeature();
		MGeometry geojsonGeometry = new MGeometry(new MMultiLineString());

		// feature name
		List<MProperty> trackName = visitPropertyName(gpxTrack);
		geojsonFeature.setProperties(trackName);

		MCoordinate geojsonCoordinateListL1 = new MCoordinateList();
		MCoordinate geojsonCoordinateListL2 = new MCoordinateList();

		for (MTrackSegment gpxSegment : gpxTrack.getSegmentsList())
		{
			MCoordinate geojsonCoordinateListL3 = new MCoordinateList();

			for (MWaypoint gpxWaypoint : gpxSegment.getWaypointsList())
			{
				Double lat = gpxWaypoint.getLat();
				Double lon = gpxWaypoint.getLon();

				geojsonCoordinateListL3.addCoordinateList(new MCoordinatePosition(lat, lon));
			}

			geojsonCoordinateListL2.addCoordinateList(geojsonCoordinateListL3);
		}

		geojsonCoordinateListL1.addCoordinateList(geojsonCoordinateListL2);
		geojsonGeometry.setCoordinates(geojsonCoordinateListL1);
		geojsonFeature.setGeometry(geojsonGeometry);

		return geojsonFeature;
	}

	@Override
	public MFeature visitRoute(MRoute gpxRoute)
	{
		MFeature geojsonFeature = new MFeature();
		MGeometry geojsonGeometry = new MGeometry(new MLineString());

		// property name
		geojsonFeature.setProperties(visitPropertyName(gpxRoute));

		// coordinates
		MCoordinate geojsonCoordinate = visitWaypointList(gpxRoute.getWaypointsList());
		geojsonGeometry.setCoordinates(geojsonCoordinate);

		geojsonFeature.setGeometry(geojsonGeometry);

		return geojsonFeature;
	}

	@Override
	public MCoordinate visitWaypointList(ArrayList<MWaypoint> gpxWaypointsList)
	{
		MCoordinateList geojsonCoordinateList = new MCoordinateList();
		MCoordinateList geojsonCoordinateList2 = new MCoordinateList();

		for (MWaypoint gpxWaypoint : gpxWaypointsList)
		{
			geojsonCoordinateList2.addCoordinateList(visitWaypoint(gpxWaypoint));
		}

		geojsonCoordinateList.addCoordinateList(geojsonCoordinateList2);

		return geojsonCoordinateList;
	}

	@Override
	public MCoordinatePosition visitWaypoint(MWaypoint gpxWaypoint)
	{
		MCoordinatePosition geojsonCoordinatePosition = new MCoordinatePosition();

		geojsonCoordinatePosition.setLat(gpxWaypoint.getLat());
		geojsonCoordinatePosition.setLon(gpxWaypoint.getLon());

		return geojsonCoordinatePosition;
	}

	@Override
	public List<MProperty> visitPropertyName(MRoute gpxRoute)
	{
		return propertyName(gpxRoute.getName());
	}

	@Override
	public List<MProperty> visitPropertyName(MWaypoint gpxWaypoint)
	{
		return propertyName(gpxWaypoint.getName());
	}

	@Override
	public List<MProperty> visitPropertyName(MTrack gpxTrack)
	{
		return propertyName(gpxTrack.getName());
	}

	private List<MProperty> propertyName(String name)
	{
		List<MProperty> geojsonProperties = new ArrayList<>();
		geojsonProperties.add(new MProperty(GeojsonElement.NAME, name));

		return geojsonProperties;
	}
}
