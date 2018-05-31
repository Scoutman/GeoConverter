package at.mlakar.geoconverter.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.mlakar.geoconverter.converter.generator.XmlModelGenerator;
import at.mlakar.geoconverter.converter.geojson.generator.GeojsonGenerator;
import at.mlakar.geoconverter.converter.geojson.generator.GeojsonModelGenerator;
import at.mlakar.geoconverter.converter.geojson.model.MGeojson;
import at.mlakar.geoconverter.converter.geojson.transformer.GeojsonKmlTransformer;
import at.mlakar.geoconverter.converter.kml.generator.KmlGenerator;
import at.mlakar.geoconverter.converter.kml.generator.KmlModelGenerator;
import at.mlakar.geoconverter.converter.kml.model.MKml;
import at.mlakar.geoconverter.converter.kml.transformer.KmlGeojsonTransformer;
import at.mlakar.geoconverter.webapp.repository.Geodata;
import at.mlakar.geoconverter.webapp.repository.GeodataRepository;

@Service
public class KmlService extends GeodataService
{
	private static final String GEODATA_TYPE = "kml";

	@Autowired
	private GeodataRepository geodataRepository;
	
	@Override
	public JsonResponse create(String geodata)
	{
		XmlModelGenerator<MKml> kmlModelGenerator = new KmlModelGenerator<>(MKml.class);
		MKml mKml = kmlModelGenerator.getModel(geodata);
		
		KmlGeojsonTransformer kmlGeojsonTransformer = new KmlGeojsonTransformer();
		MGeojson mGeojson = kmlGeojsonTransformer.getGeojsonModel(mKml);
		GeojsonGenerator geojsonGenerator = new GeojsonGenerator();
		String geojsonString = geojsonGenerator.getGeojson(mGeojson);
		
		KmlGenerator kmlGenerator = new KmlGenerator();
		String kmlString = kmlGenerator.getKml(mKml);
				
		Geodata geodataEntity = new Geodata();
		geodataEntity.setGeodata(geojsonString);
		Geodata geodataDbEntity = geodataRepository.save(geodataEntity);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setGeodata(kmlString);
		jsonResponse.setGeodataId(geodataDbEntity.getId());
		jsonResponse.setType(GEODATA_TYPE);		
		
		return jsonResponse;
	}	
	
	@Override
	public JsonResponse get(Integer geodataId)
	{
		JsonResponse jsonResponse = new JsonResponse();
		Optional<Geodata> geodataDbEntity = geodataRepository.findById(geodataId);

		if (geodataDbEntity.isPresent())
		{
			GeojsonModelGenerator geojsonModelGenerator = new GeojsonModelGenerator();
			MGeojson mGeojson = geojsonModelGenerator.getModel(geodataDbEntity.get().getGeodata());
			
			GeojsonKmlTransformer geojsonKmlTransformer = new GeojsonKmlTransformer();
			MKml mKml = geojsonKmlTransformer.getKmlModel(mGeojson);
			
			KmlGenerator kmlGenerator = new KmlGenerator();
			String kmlString = kmlGenerator.getKml(mKml);
			
			jsonResponse.setGeodata(kmlString);
			jsonResponse.setGeodataId(geodataDbEntity.get().getId());
			jsonResponse.setType(GEODATA_TYPE);
		}
		else
		{
			jsonResponse.setError(Messages.ERR_ID_NOT_FOUND);
			jsonResponse.setErrorCode(Messages.ERR_ID_NOT_FOUND_CODE);
		}

		return jsonResponse;
	}
}
