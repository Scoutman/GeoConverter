package at.mlakar.geoconverter.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.mlakar.geoconverter.converter.generator.XmlModelGenerator;
import at.mlakar.geoconverter.converter.geojson.generator.GeojsonGenerator;
import at.mlakar.geoconverter.converter.geojson.generator.GeojsonModelGenerator;
import at.mlakar.geoconverter.converter.geojson.model.MGeojson;
import at.mlakar.geoconverter.converter.geojson.transformer.GeojsonGpxTransformer;
import at.mlakar.geoconverter.converter.gpx.generator.GpxGenerator;
import at.mlakar.geoconverter.converter.gpx.generator.GpxModelGenerator;
import at.mlakar.geoconverter.converter.gpx.model.MGpx;
import at.mlakar.geoconverter.converter.gpx.transformer.GpxGeojsonTransformer;
import at.mlakar.geoconverter.webapp.repository.Geodata;
import at.mlakar.geoconverter.webapp.repository.GeodataRepository;

@Service
public class GpxService extends GeodataService
{
	private static final String GEODATA_TYPE = "gpx";

	@Autowired
	private GeodataRepository geodataRepository;
	
	@Override
	public JsonResponse create(String geodata)
	{
		XmlModelGenerator<MGpx> gpxModelGenerator = new GpxModelGenerator<>(MGpx.class);
		MGpx mGpx = gpxModelGenerator.getModel(geodata);
		
		GpxGeojsonTransformer gpxGeojsonTransformer = new GpxGeojsonTransformer();
		MGeojson mGeojson = gpxGeojsonTransformer.getGeojsonModel(mGpx);
		GeojsonGenerator geojsonGenerator = new GeojsonGenerator();
		String geojsonString = geojsonGenerator.getGeojson(mGeojson);
		
		GpxGenerator gpxGenerator = new GpxGenerator();
		String gpxString = gpxGenerator.getGpx(mGpx);
				
		Geodata geodataEntity = new Geodata();
		geodataEntity.setGeodata(geojsonString);
		Geodata geodataDbEntity = geodataRepository.save(geodataEntity);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setGeodata(gpxString);
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
			
			GeojsonGpxTransformer geojsonGpxTransformer = new GeojsonGpxTransformer();
			MGpx mGpx = geojsonGpxTransformer.getGpxModel(mGeojson);
			
			GpxGenerator gpxGenerator = new GpxGenerator();
			String gpxString = gpxGenerator.getGpx(mGpx);
			
			jsonResponse.setGeodata(gpxString);
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
