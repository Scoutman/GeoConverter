package at.mlakar.geoconverter.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.mlakar.geoconverter.webapp.repository.Geodata;
import at.mlakar.geoconverter.webapp.repository.GeodataRepository;

@Service
public abstract class GeodataService
{
	@Autowired
	private GeodataRepository geodataRepository;
	
	public abstract JsonResponse create(String geodata);
	
	public abstract JsonResponse get(Integer geodataId);

	public JsonResponse delete(Integer geodataId)
	{
		JsonResponse jsonResponse = new JsonResponse();
		Optional<Geodata> geodataDbEntity = geodataRepository.findById(geodataId);
		
		if (geodataDbEntity.isPresent())
		{
			geodataRepository.deleteById(geodataId);
		}
		else
		{
			jsonResponse.setError(Messages.ERR_DELETE_ID_NOT_FOUND);
			jsonResponse.setErrorCode(Messages.ERR_DELETE_ID_NOT_FOUND_CODE);
		}
		
		return jsonResponse;
	}
}