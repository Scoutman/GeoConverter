package at.mlakar.geoconverter.geojson.transformer;

import java.util.List;

import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MProperty;
import at.mlakar.geoconverter.geojson.model.MType;
import at.mlakar.geoconverter.kml.model.MCoordinatesList;
import at.mlakar.geoconverter.kml.model.MGeometry;
import at.mlakar.geoconverter.kml.model.MPlacemark;

public interface GeojsonKmlTransformerInterface
{

	List<MPlacemark> visitFeatureList(List<MFeature> geojsonFeaturesList);

	MPlacemark visitFeature(MFeature geojsonFeature);

	String visitPropertyName(List<MProperty> properties);

	MGeometry visitSingleGeometry(at.mlakar.geoconverter.geojson.model.MGeometry geojsonGeometry);

	MCoordinatesList visitCoordinates(List<MCoordinate> geojsonCoordinateList, Class<? extends MGeometry> geometryTypeClass);

	MGeometry visitSingleType(MType geojsonType);

	MGeometry visitMultiType(MType geojsonType);

}