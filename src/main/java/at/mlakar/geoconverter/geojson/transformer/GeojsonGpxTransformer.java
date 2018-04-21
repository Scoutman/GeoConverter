package at.mlakar.geoconverter.geojson.transformer;

import java.util.ArrayList;
import java.util.List;

import at.mlakar.geoconverter.geojson.model.GeojsonElement;
import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MGeojson;
import at.mlakar.geoconverter.geojson.model.MLineString;
import at.mlakar.geoconverter.geojson.model.MPoint;
import at.mlakar.geoconverter.geojson.model.MPolygon;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;
import at.mlakar.geoconverter.gpx.model.MGpx;
import at.mlakar.geoconverter.gpx.model.MRoute;
import at.mlakar.geoconverter.gpx.model.MWaypoint;

public class GeojsonGpxTransformer
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
		
		
		return mGpx;
	}

	public List<MWaypoint> generatePoints(List<MFeature> geojsonFeaturesList)
	{
		List<MWaypoint> gpxWaypointList = new ArrayList<>();

		for (MFeature geojsonFeature : geojsonFeaturesList)
		{
			if (geojsonFeature.getGeometry().getType() instanceof MPoint)
			{
				MWaypoint gpxWaypoint;

				// coordinates
				List<MWaypoint> coordinatesList = visitCoordinates(geojsonFeature.getGeometry().getCoordinates().getCoordinateList(), MPoint.class);
				gpxWaypoint = coordinatesList.get(0);

				// waypoint name
				String pointName = visitPropertyName(geojsonFeature.getProperties());
				gpxWaypoint.setName(pointName);

				gpxWaypointList.add(gpxWaypoint);
			}

		}

		return gpxWaypointList;
	}
	
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
				
				if(geojsonFeature.getGeometry().getType() instanceof MLineString)
				{
					coordinatesList = visitCoordinates(geojsonFeature.getGeometry().getCoordinates().getCoordinateList(), MLineString.class);
				}
				else if (geojsonFeature.getGeometry().getType() instanceof MPolygon) 
				{
					coordinatesList = visitCoordinates(geojsonFeature.getGeometry().getCoordinates().getCoordinateList(), MPolygon.class);
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

	public List<MWaypoint> visitCoordinates(List<MCoordinate> geojsonCoordinateList, Class<? extends MType> geometryTypeClass)
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
