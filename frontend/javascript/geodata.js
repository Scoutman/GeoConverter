"use strict";

var serverUrl = "http://localhost:8080";

var mymap;

var geodataId;
var geojsonFeature;
var geojsonLayer;

var geodataFile;
var geodataFileData;
var btnUpload;
var btnGetGeojson;
var btnGetGpx;
var btnGetKml;
var btnDelete;
var txtGeodata;
var geodataType;


document.addEventListener('DOMContentLoaded', function() { 

	geodataFile = document.getElementById("geodataFile");
	btnUpload = document.getElementById("btnUpload");
	btnGetGeojson = document.getElementById("btnGetGeojson");
	btnGetGpx = document.getElementById("btnGetGpx");
	btnGetKml = document.getElementById("btnGetKml");
	btnDelete = document.getElementById("btnDelete");
	txtGeodata = document.getElementById("txtGeodata");
	geodataType = document.getElementById("geodataType");

	initMap();

	geodataFile.addEventListener("change", function() {

		console.log("change selected geodataFile");

		readGeodataFile();
	});

	btnUpload.addEventListener("click", function() {

		console.log("click btnUpload");

		sendGeodataFile();
	});

	btnGetGeojson.addEventListener("click", function() {

		console.log("click btnGetGeojson");

		getGeodata("geojson", setConverterGeodata);
	});

	btnGetGpx.addEventListener("click", function() {

		console.log("click btnGetGpx");

		getGeodata("gpx", setConverterGeodata);
	});

	btnGetKml.addEventListener("click", function() {

		console.log("click btnGetKml");

		getGeodata("kml", setConverterGeodata);
	});

	btnDelete.addEventListener("click", function() {

		console.log("click btnDelete");

		deleteGeodata();
	});			
});

function initMap()
{
	mymap = L.map('mapid').setView([47.335, 15.378], 13);

	var BasemapAT_grau = L.tileLayer('https://maps{s}.wien.gv.at/basemap/bmapgrau/normal/google3857/{z}/{y}/{x}.{format}', {
		maxZoom: 19,
		attribution: 'Datenquelle: <a href="https://www.basemap.at">basemap.at</a>',
		subdomains: ["", "1", "2", "3", "4"],
		format: 'png'
	}).addTo(mymap);	
}

function readGeodataFile()
{
	var file = geodataFile.files[0];
	var reader = new FileReader();

	console.log(file);

	reader.readAsText(file);
	setGeodataTypeSelect(file.name);

	reader.onload = function() {
		geodataFileData = reader.result;
	};
}

function sendGeodataFile()
{
	console.log("sendGeodataFile");

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	
		if (this.readyState == 4 && this.status == 200)
		{
			console.log("Geodata sended");
			console.log("AJAX response: " + this.responseText);

			var response = JSON.parse(this.responseText);
			geodataId = response.geodataId;
			
			console.log(response.geodataId);

			getGeodata("geojson", setMapFeatures);
		}
	};

	console.log(geodataType.value);

	xhttp.open("POST", serverUrl + "/api/geodata/"+ geodataType.value, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("geodata="+geodataFileData);	
}

function getGeodata(type, callback)
{
	console.log("getGeodata");

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	
		if (this.readyState == 4 && this.status == 200)
		{
			console.log("Get " + type + " data. ID: " + geodataId);
			console.log("AJAX response: " + this.responseText);

			callback(this.responseText);
		}
	};

	xhttp.open("GET", serverUrl + "/api/geodata/" + type + "/" + geodataId, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send();
}

function setMapFeatures(responseText)
{
	var response = JSON.parse(responseText);
	geojsonFeature = JSON.parse(response.geodata);
	geojsonLayer = L.geoJSON(geojsonFeature).addTo(mymap);
}

function setConverterGeodata(responseText)
{
	var response = JSON.parse(responseText);
	var geodata = response.geodata;
	txtGeodata.innerHTML = geodata;
}


function deleteGeodata()
{
	console.log("deleteGeodata");

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	
		if (this.readyState == 4 && this.status == 200)
		{
			console.log("AJAX response: " + this.responseText);
			var response = JSON.parse(this.responseText);

			if (response.errorCode == 0)
			{
				console.log("Geodata object deleted");

				txtGeodata.innerHTML = "";
				mymap.removeLayer(geojsonLayer);
				geodataFile.value = "";
				geodataId = null;
				geojsonFeature = null;

			}
		}
	};

	xhttp.open("DELETE", serverUrl + "/api/geodata/" + geodataId, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send();	
}

function setGeodataTypeSelect(filename)
{
	var extension = filename.substr(filename.lastIndexOf(".") + 1);

	switch(extension)
	{
		case "geojson":
			geodataType.selectedIndex = 0;
		break;
		
		case "json":
			geodataType.selectedIndex = 0;
		break;

		case "gpx":
			geodataType.selectedIndex = 1;
		break;

		case "kml":
			geodataType.selectedIndex = 2;
		break;
	}
}

