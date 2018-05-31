# GeoConverter

Converter for KML, GPX and GeoJSON files.


## Database setup (MySQL)


	src/main/resources/application.properties

## Start backend application

	src/main/java/at/mlakar/geoconverter/Application.java


## Start frontend application

	...


## API Schnittstelle

POST /api/geodata/geojson
GET /api/geodata/geojson/:id

POST /api/geodata/gpx
GET /api/geodata/gpx/:id

POST /api/geodata/kml
GET /api/geodata/kml/:id

DELETE /api/geodata/:id
