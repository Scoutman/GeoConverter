# GeoConverter

Konvertiert GeoJSON, GPX and KML Dateien.


## Datenbank Setup (MySQL)

	src/main/resources/application.properties

## Backend Application starten

	src/main/java/at/mlakar/geoconverter/Application.java

## Frontend Application starten

	GeoConverter/frontend/index.htm

## API Schnittstelle

Default URI: http://localhost:8080/api/geodata

### POST /api/geodata/geojson

**Beschreibung:** Sendet GeoJSON Daten an das Webservice.
**Parameter:** geodata

**Beispiel Server Antwort:**	

	{
	    "geodataId": 2,
	    "type": "geojson",
	    "geodata": "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"name\":\"rote wand\"},...",
	    "error": "",
	    "errorCode": 0
	}

### GET /api/geodata/geojson/:id

**Beschreibung:** Gibt Datensatz mit der übergebenen ID im GeoJSON Format zurück.

**Beispiel Server Antwort:**	

	{
	    "geodataId": 1,
	    "type": "geojson",
	    "geodata": "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"name\":\"Hochlantsch\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[15.423645,47.362176]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Route Rote Wand\"},...",
	    "error": "",
	    "errorCode": 0
	}

### POST /api/geodata/gpx

**Beschreibung:** Sendet GPX Daten an das Webservice.
**Parameter:** geodata

**Beispiel Server Antwort:**	

	{
	    "geodataId": 3,
	    "type": "gpx",
	    "geodata": "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gpx version=\"1.1\"><wpt lat=\"47.32923199357016\" lon=\"15.362652534697418\"><name>Mixnitz</name></wpt><wpt lat=\"47.36261372836141\" lon=\"15.425823921416168\"><name>Hochlantsch</name></wpt>...",
	    "error": "",
	    "errorCode": 0
	}

### GET /api/geodata/gpx/:id

**Beschreibung:** Gibt Datensatz mit der übergebenen ID im GPX Format zurück.

**Beispiel Server Antwort:**	
	
	{
	    "geodataId": 1,
	    "type": "gpx",
	    "geodata": "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gpx version=\"1.1\"><wpt lat=\"47.362176\" lon=\"15.423645\"><name>Hochlantsch</name></wpt><wpt lat=\"47.362176\" lon=\"15.423645\"><name>Hochlantsch</name></wpt><rte>...",
	    "error": "",
	    "errorCode": 0
	}


### POST /api/geodata/kml

**Beschreibung:** Sendet KML Daten an das Webservice.
**Parameter:** geodata

**Beispiel Server Antwort:**	

	{
	    "geodataId": 1,
	    "type": "kml",
	    "geodata": "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><kml><Document><name>Punkt, Route und Polygon Test</name><Placemark><name>Hochlantsch</name><Point><coordinates>15.423645,47.362176,1720 </coordinates>...",
	    "error": "",
	    "errorCode": 0
	}

### GET /api/geodata/kml/:id

**Beschreibung:** Gibt Datensatz mit der übergebenen ID im KML Format zurück.

**Beispiel Server Antwort:**	

	{
	    "geodataId": 1,
	    "type": "kml",
	    "geodata": "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><kml><Document><Placemark><name>Hochlantsch</name><Point><coordinates>15.423645,47.362176,0 </coordinates></Point></Placemark><Placemark><name>Route Rote Wand</name><LineString>...",
	    "error": "",
	    "errorCode": 0
	}

### DELETE /api/geodata/:id

**Beschreibung:** Löscht Datensatz mit der übergebenen ID.

**Beispiel Server Antwort:**	

	{
	    "geodataId": null,
	    "type": "",
	    "geodata": "[]",
	    "error": "",
	    "errorCode": 0
	}


## Error Codes

**Error Code:** 10
**Error:** Geodata object ID not found.

**Error Code:** 11
**Error:** Delete error. Geodata object ID not found.



