package at.mlakar.geoconverter.kml.transformer;

import java.util.List;

import at.mlakar.geoconverter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.geojson.model.MFeature;
import at.mlakar.geoconverter.geojson.model.MGeometry;
import at.mlakar.geoconverter.kml.model.MCoordinatesList;
import at.mlakar.geoconverter.kml.model.MFolder;
import at.mlakar.geoconverter.kml.model.MPlacemark;

public interface KmlGeojsonTransformerInterface
{

	List<MFeature> visitFolder(List<MFolder> folderList);

	List<MFeature> visitPlacemark(List<MPlacemark> kmlPLacemarkList);

	MGeometry visitGeometry(at.mlakar.geoconverter.kml.model.MGeometry kmlGeometry);

	MCoordinate visitCoordinatesPoint(MCoordinatesList kmlCoordinates);

	MCoordinate visitCoordinatesLineString(MCoordinatesList kmlCoordinates);

	MCoordinate visitCoordinatesLinearRing(MCoordinatesList kmlCoordinates);

}