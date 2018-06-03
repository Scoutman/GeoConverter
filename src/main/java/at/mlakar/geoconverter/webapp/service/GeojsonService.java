package at.mlakar.geoconverter.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.mlakar.geoconverter.converter.geojson.generator.GeojsonGenerator;
import at.mlakar.geoconverter.converter.geojson.generator.GeojsonModelGenerator;
import at.mlakar.geoconverter.converter.geojson.model.MGeojson;
import at.mlakar.geoconverter.webapp.repository.Geodata;
import at.mlakar.geoconverter.webapp.repository.GeodataRepository;

@Service
public class GeojsonService extends GeodataService
{
	private static final String GEODATA_TYPE = "geojson";

	@Autowired
	private GeodataRepository geodataRepository;

	@Override
	public JsonResponse create(String geodata)
	{
		GeojsonModelGenerator geojsonModelGenerator = new GeojsonModelGenerator();
		MGeojson geojsonModel = geojsonModelGenerator.getModel(geodata);

		GeojsonGenerator geojsonGenerator = new GeojsonGenerator();
		String geojsonString = geojsonGenerator.getGeojson(geojsonModel);

		Geodata geodataEntity = new Geodata();
		geodataEntity.setGeodata(geojsonString);
		Geodata geodataDbEntity = geodataRepository.save(geodataEntity);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setGeodata(geojsonString);
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
			jsonResponse.setGeodata(geodataDbEntity.get().getGeodata());
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
