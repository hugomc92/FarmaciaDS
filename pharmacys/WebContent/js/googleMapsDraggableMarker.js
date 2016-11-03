var geocoder = new google.maps.Geocoder();

function geocodePosition(pos) {
	geocoder.geocode({
		latLng: pos
	}, function(responses) {
		if (responses && responses.length > 0)
			updateMarkerAddress(responses[0].formatted_address); 
		else 
			updateMarkerAddress('Cannot determine address at this location.');
	});
}

function updateMarkerStatus(str) {
	document.getElementById('markerStatus').innerHTML = str;
}

function updateMarkerPosition(latLng) {
	var latitude = latLng.lat();
	var longitude = latLng.lng();
	
	document.getElementById('editLat').value = latitude;
	document.getElementById('editLong').value = longitude;
}

function updateMarkerAddress(str) {	
	document.getElementById('editAddress').value = str;
}

var geoTTL = 10000;
var contTTL = 0;
var lat = 0, lng = 0;
function onSuccess(data) {
	document.getElementById('markerStatus').innerHTML = "Here is your position";
	lat = data.coords.latitude;
	lng = data.coords.longitude;
	
	loadMap(lat, lng);
}

function onError(err) {
	document.getElementById('markerStatus').innerHTML = "Sorry but we cant geolocate your position";
	
	loadMap(0,0);
}

function onProgress(){
	document.getElementById('markerStatus').innerHTML = "Finding your position...";
	document.getElementById('mapa').innerHTML = "<img src='../img/ajax-loader.gif' style='margin: 0 44%; margin-top: 30%' alt='loader' width=100 height=100/>";	
}

function loadMap(lat, lng) {	
	
	var latLng = new google.maps.LatLng(lat, lng);
	var map = new google.maps.Map(document.getElementById('mapa'), {
		zoom: 8,
		center: latLng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	});
  
	var marker = new google.maps.Marker({
		position: latLng,
		title: 'Point A',
		map: map,
		draggable: true
	});
  
	// Update current position info.
	updateMarkerPosition(latLng);
	geocodePosition(latLng);
  
	// Add dragging event listeners.
	google.maps.event.addListener(marker, 'dragstart', function() {
		updateMarkerAddress('Dragging...');
	});
  
	google.maps.event.addListener(marker, 'drag', function() {
		updateMarkerStatus('Dragging...');
		updateMarkerPosition(marker.getPosition());
	});
  
	google.maps.event.addListener(marker, 'dragend', function() {
		updateMarkerStatus('Drag ended');
		geocodePosition(marker.getPosition());
	});
}

function checkCurrentGeo(){
	// mientras la latitud y longitud inicial no cambien los valores por defecto seguimos esperando y comprobando cada 50ms
	if(lat == 0 && lng == 0){
		contTTL = contTTL + 50; // añadimos el tiempo de espera al contador, mientras tanto se ejecuta onProgress
		setTimeout(checkCurrentGeo, 50);
		return;
	}
	else if (contTTL == geoTTL) // cuando el contador alcance el tiempo de vida estimado abortamos
		console.log("ABORTED GEO");
	else  // las variables al fin cambiaron y se ejecutara la funcion callback onSuccess con los datos adquiridos
		console.log("LOADED");
}

function asyncGetGeo(){
	navigator.geolocation.getAccurateCurrentPosition(onSuccess, onError, onProgress, {desiredAccuracy:20, maxWait:geoTTL});
}