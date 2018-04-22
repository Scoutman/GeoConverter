package at.mlakar.geoconverter.gpx.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.geojson.model.GeojsonElement;
import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MCoordinateList;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MGeometry;
import at.mlakar.geoconverter.geojson.model.MLineString;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.gpx.model.MGpx;
import at.mlakar.geoconverter.gpx.model.MRoute;
import at.mlakar.geoconverter.gpx.model.MTrack;
import at.mlakar.geoconverter.gpx.model.MTrackSegment;
import at.mlakar.geoconverter.gpx.model.MWaypoint;

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
			geojsonFeatureList.addAll(visitTrack(gpxTrack));
		}
		
		return geojsonFeatureList;
	}
	
	@Override
	public List<MFeature> visitTrack(MTrack gpxTrack)
	{
		List<MFeature> geojsonFeatureList = new ArrayList<>();
		
		for (MTrackSegment gpxSegment : gpxTrack.getSegmentsList())
		{
			MFeature gpxFeature;
			
			// features
			gpxFeature = visitTrackSegment(gpxSegment);
			
			// feature name
			List<MProperty> trackName = visitPropertyName(gpxTrack);
			gpxFeature.setProperties(trackName);
			
			geojsonFeatureList.add(gpxFeature);
		}
		
		return geojsonFeatureList;
	}

	@Override
	public MFeature visitTrackSegment(MTrackSegment gpxSegment)
	{
		MFeature geojsonFeature = new MFeature();
		MGeometry geojsonGeometry = new MGeometry(new MLineString());
		
		// coordinates
		MCoordinate geojsonCoordinate = visitWaypointList(gpxSegment.getWaypointsList());
		geojsonGeometry.setCoordinates(geojsonCoordinate);
		
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
