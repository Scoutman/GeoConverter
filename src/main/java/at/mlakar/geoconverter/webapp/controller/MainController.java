package at.mlakar.geoconverter.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import at.mlakar.geoconverter.webapp.service.GeojsonService;
import at.mlakar.geoconverter.webapp.service.GpxService;
import at.mlakar.geoconverter.webapp.service.JsonResponse;
import at.mlakar.geoconverter.webapp.service.KmlService;

@RestController
@RequestMapping(path = "/api/geodata")
public class MainController
{

	@Autowired
	GeojsonService geojsonService;

	@Autowired
	GpxService gpxService;

	@Autowired
	KmlService kmlService;

	@GetMapping("")
	public @ResponseBody String info()
	{
		return "Geodata API";
	}

	@DeleteMapping("/{id}")
	public @ResponseBody JsonResponse deleteUser(@PathVariable Integer id)
	{
		JsonResponse jsonResponse = geojsonService.delete(id);

		return jsonResponse;
	}

	@PostMapping("/geojson")
	public @ResponseBody JsonResponse postGeojson(@RequestParam String geodata)
	{
		JsonResponse jsonResponse = geojsonService.create(geodata);

		return jsonResponse;
	}

	@GetMapping("/geojson/{id}")
	public @ResponseBody JsonResponse getGeojson(@PathVariable Integer id)
	{
		JsonResponse jsonResponse = geojsonService.get(id);

		return jsonResponse;
	}

	@PostMapping("/gpx")
	public @ResponseBody JsonResponse postGpx(@RequestParam String geodata)
	{
		JsonResponse jsonResponse = gpxService.create(geodata);

		return jsonResponse;
	}

	@GetMapping("/gpx/{id}")
	public @ResponseBody JsonResponse getGpx(@PathVariable Integer id)
	{
		JsonResponse jsonResponse = gpxService.get(id);

		return jsonResponse;
	}

	@PostMapping("/kml")
	public @ResponseBody JsonResponse postKml(@RequestParam String geodata)
	{
		JsonResponse jsonResponse = kmlService.create(geodata);

		return jsonResponse;
	}

	@GetMapping("/kml/{id}")
	public @ResponseBody JsonResponse getKml(@PathVariable Integer id)
	{
		JsonResponse jsonResponse = kmlService.get(id);

		return jsonResponse;
	}

}
