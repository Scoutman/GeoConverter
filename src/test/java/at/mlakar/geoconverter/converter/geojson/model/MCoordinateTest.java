package at.mlakar.geoconverter.converter.geojson.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.mlakar.geoconverter.converter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinateList;
import at.mlakar.geoconverter.converter.geojson.model.MCoordinatePosition;
import at.mlakar.geoconverter.converter.geojson.model.MFeature;
import at.mlakar.geoconverter.converter.geojson.model.MGeometry;
import at.mlakar.geoconverter.converter.geojson.model.MLineString;
import at.mlakar.geoconverter.converter.geojson.model.MMultiLineString;
import at.mlakar.geoconverter.converter.geojson.model.MMultiPoint;
import at.mlakar.geoconverter.converter.geojson.model.MMultiPolygon;
import at.mlakar.geoconverter.converter.geojson.model.MPoint;
import at.mlakar.geoconverter.converter.geojson.model.MPolygon;
import at.mlakar.geoconverter.converter.geojson.model.MProperty;

public class MCoordinateTest
{


	@Test
	public void MCoordinatePositionTest()
	{
		Double lat = new Double(12.34);
		Double lon = new Double(-46.78);
		
		MCoordinate coordinate = new MCoordinateList();
		MCoordinatePosition position = new MCoordinatePosition(lat, lon);
		coordinate.addCoordinateList(position);
		
		Assert.assertEquals(lat, (((MCoordinatePosition) coordinate.getCoordinateList().get(0)).getLat()));
		Assert.assertEquals(lon, (((MCoordinatePosition) coordinate.getCoordinateList().get(0)).getLon()));
	}

	@Test
	public void MCoordinateList()
	{
		Double lat = new Double(12.34);
		Double lon = new Double(46.78);
		
		MCoordinate coordinate1 = new MCoordinateList();
		MCoordinate coordinate2 = new MCoordinateList();
		MCoordinatePosition position = new MCoordinatePosition(lat, lon);
		coordinate2.addCoordinateList(position);
		coordinate1.addCoordinateList(coordinate2);
		
		Assert.assertEquals(lat, ((MCoordinatePosition) coordinate1.getCoordinateList().get(0).getCoordinateList().get(0)).getLat());
		Assert.assertEquals(lon, ((MCoordinatePosition) coordinate1.getCoordinateList().get(0).getCoordinateList().get(0)).getLon());
	}
	
	@Test
	public void MGeometryTypePointTest()
	{
		MGeometry geometry = new MGeometry(new MPoint());
		Assert.assertTrue(geometry.getType() instanceof MPoint);
	}
	
	@Test
	public void MGeometryTypeMultiPointTest()
	{
		MGeometry geometry = new MGeometry(new MMultiPoint());
		Assert.assertTrue(geometry.getType() instanceof MMultiPoint);
	}

	@Test
	public void MGeometryTypeLineStringTest()
	{
		MGeometry geometry = new MGeometry(new MLineString());
		Assert.assertTrue(geometry.getType() instanceof MLineString);
	}	

	@Test
	public void MGeometryTypeMultiLineStringTest()
	{
		MGeometry geometry = new MGeometry(new MMultiLineString());
		Assert.assertTrue(geometry.getType() instanceof MMultiLineString);
	}
	
	@Test
	public void MGeometryTypePolygonTest()
	{
		MGeometry geometry = new MGeometry(new MPolygon());
		Assert.assertTrue(geometry.getType() instanceof MPolygon);
	}
	
	@Test
	public void MGeometryTypeMultiPolygonTest()
	{
		MGeometry geometry = new MGeometry(new MMultiPolygon());
		Assert.assertTrue(geometry.getType() instanceof MMultiPolygon);
	}	
	
	@Test
	public void MFeaturePropertyTest()
	{
		String propName = "name";
		String propValue = "zuhause";
		
		MProperty property = new MProperty(propName, propValue);
		
		List<MProperty> properties = new ArrayList<>();
		properties.add(property);
		
		MFeature feature = new MFeature();
		feature.setProperties(properties);
		
		Assert.assertEquals(propName, feature.getProperties().get(0).getName());
		Assert.assertEquals(propValue, feature.getProperties().get(0).getValue());
	}	
}
