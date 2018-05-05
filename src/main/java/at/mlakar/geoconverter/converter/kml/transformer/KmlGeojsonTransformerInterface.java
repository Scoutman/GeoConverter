package at.mlakar.geoconverter.converter.kml.transformer;

import java.util.List;

import at.mlakar.geoconverter.converter.geojson.model.MCoordinate;
import at.mlakar.geoconverter.converter.geojson.model.MFeature;
import at.mlakar.geoconverter.converter.geojson.model.MGeometry;
import at.mlakar.geoconverter.converter.kml.model.MCoordinatesList;
import at.mlakar.geoconverter.converter.kml.model.MFolder;
import at.mlakar.geoconverter.converter.kml.model.MMultiGeometry;
import at.mlakar.geoconverter.converter.kml.model.MPlacemark;

public interface KmlGeojsonTransformerInterface
{

	List<MFeature> visitFolder(List<MFolder> folderList);

	List<MFeature> visitPlacemark(List<MPlacemark> kmlPLacemarkList);

	MGeometry visitSingleGeometry(at.mlakar.geoconverter.converter.kml.model.MGeometry kmlGeometry);

	MCoordinate visitCoordinatesPoint(MCoordinatesList kmlCoordinates);

	MCoordinate visitCoordinatesLineString(MCoordinatesList kmlCoordinates);

	MCoordinate visitCoordinatesLinearRing(MCoordinatesList kmlCoordinates);

	MGeometry visitMultiGeometry(MMultiGeometry multiGeometry);

	MCoordinate visitCoordinatesMultiLinearRing(MMultiGeometry kmlMultiGeometry);

	MCoordinate visitCoordinatesMultiLineString(MMultiGeometry kmlMultiGeometry);

	MCoordinate visitCoordinatesMultiPoint(MMultiGeometry kmlMultiGeometry);

}