package at.mlakar.geoconverter.converter.geojson.transformer;

import java.util.List;

import at.mlakar.geoconverter.converter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.converter.geojson.model.MFeature;
import at.mlakar.geoconverter.converter.geojson.model.MProperty;
import at.mlakar.geoconverter.converter.geojson.model.MType;
import at.mlakar.geoconverter.converter.kml.model.MCoordinatesList;
import at.mlakar.geoconverter.converter.kml.model.MGeometry;
import at.mlakar.geoconverter.converter.kml.model.MMultiGeometry;
import at.mlakar.geoconverter.converter.kml.model.MPlacemark;

public interface GeojsonKmlTransformerInterface
{

	List<MPlacemark> visitFeatureList(List<MFeature> geojsonFeaturesList);

	MPlacemark visitFeature(MFeature geojsonFeature);

	MGeometry visitSingleGeometry(at.mlakar.geoconverter.converter.geojson.model.MGeometry geojsonGeometry);

	MMultiGeometry visitMultiGeometry(at.mlakar.geoconverter.converter.geojson.model.MGeometry geojsonGeometry);

	MCoordinatesList visitSingleCoordinates(List<MCoordinate> geojsonCoordinateList, Class<? extends MGeometry> geometryTypeClass);

	MMultiGeometry visitMultiPolygon(at.mlakar.geoconverter.converter.geojson.model.MGeometry geojsonGeometry);

	MMultiGeometry visitMultiLineString(at.mlakar.geoconverter.converter.geojson.model.MGeometry geojsonGeometry);

	MMultiGeometry visitMultiPoint(at.mlakar.geoconverter.converter.geojson.model.MGeometry geojsonGeometry);

	MGeometry visitSingleType(MType geojsonType);

	MGeometry visitMultiType(MType geojsonType);

	String visitPropertyName(List<MProperty> properties);
}