package at.mlakar.geoconverter.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import at.mlakar.geoconverter.webapp.JsonResponse;
import at.mlakar.geoconverter.webapp.repository.Geodata;
import at.mlakar.geoconverter.webapp.repository.GeodataRepository;



@RestController
@RequestMapping(path = "/api/geodata")
public class MainController
{
	@Autowired
	private GeodataRepository geodataRepository;

	@PostMapping(path = "")
	public @ResponseBody String addNewUser(@RequestParam String type, @RequestParam String geodata)
	{
		Geodata n = new Geodata();
		n.setType(type);
		n.setGeodata(geodata);
		geodataRepository.save(n);
		return "Saved";
	}

	@DeleteMapping("/{id}")
	public @ResponseBody String deleteUser(@PathVariable Integer id)
	{
		geodataRepository.deleteById(id);

		return "Deleted";
	}	
	
	@GetMapping("/geojson/{id}")
	public @ResponseBody String getGeojson(@PathVariable Integer id)
	{
		Geodata n = new Geodata();
		Optional<Geodata> optionalUser = geodataRepository.findById(id);
		n = optionalUser.get();

		return n.getId() + " - " + n.getType();
	}
	
	@GetMapping("/gpx/{id}")
	public @ResponseBody String getGpx(@PathVariable Integer id)
	{
		Geodata n = new Geodata();
		Optional<Geodata> optionalUser = geodataRepository.findById(id);
		n = optionalUser.get();

		return n.getId() + " - " + n.getType();
	}	

	@GetMapping("/kml/{id}")
	public @ResponseBody String getKml(@PathVariable Integer id)
	{
		Geodata n = new Geodata();
		Optional<Geodata> optionalUser = geodataRepository.findById(id);
		n = optionalUser.get();

		return n.getId() + " - " + n.getType();
	}

	
	// ------------------------- TEST ---------------------------
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Geodata> getAllUsers()
	{
		return geodataRepository.findAll();
	}

	@GetMapping("/{id}")
	public @ResponseBody String getUser(@PathVariable Integer id)
	{
		Geodata n = new Geodata();
		Optional<Geodata> optionalUser = geodataRepository.findById(id);
		n = optionalUser.get();

		return n.getId() + " - " + n.getType();
	}
	
	@RequestMapping(path = "/test", method = { RequestMethod.GET })
	public JsonResponse test()
	{
		List<String> errors = new ArrayList<>();
		errors.add("eins");
		errors.add("zwei");
		
		return new JsonResponse("der status", "bla bla bla");
	}

	@RequestMapping(path = "/test2", method = { RequestMethod.GET })
	public JsonResponse test2()
	{
		List<String> errors = new ArrayList<>();
		errors.add("eins");
		errors.add("zwei");
		
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.getError().addAll(errors);
		
		return jsonResponse;
	}	
}

