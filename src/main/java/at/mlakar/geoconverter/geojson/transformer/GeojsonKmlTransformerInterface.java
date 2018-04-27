package at.mlakar.geoconverter.geojson.transformer;

import java.util.List;

import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;
import at.mlakar.geoconverter.kml.model.MCoordinatesList;
import at.mlakar.geoconverter.kml.model.MGeometry;
import at.mlakar.geoconverter.kml.model.MMultiGeometry;
import at.mlakar.geoconverter.kml.model.MPlacemark;

public interface GeojsonKmlTransformerInterface
{

	List<MPlacemark> visitFeatureList(List<MFeature> geojsonFeaturesList);

	MPlacemark visitFeature(MFeature geojsonFeature);

	MGeometry visitSingleGeometry(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry);

	MMultiGeometry visitMultiGeometry(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry);

	MCoordinatesList visitSingleCoordinates(List<MCoordinate> geojsonCoordinateList, Class<? extends MGeometry> geometryTypeClass);

	MMultiGeometry visitMultiPolygon(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry);

	MMultiGeometry visitMultiLineString(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry);

	MMultiGeometry visitMultiPoint(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry);

	MGeometry visitSingleType(MType geojsonType);

	MGeometry visitMultiType(MType geojsonType);

	String visitPropertyName(List<MProperty> properties);
}