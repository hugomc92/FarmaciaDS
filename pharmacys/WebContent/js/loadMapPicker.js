//The callback function executed when the location is fetched successfully.
function onGeoSuccess(location) {
	//console.log(location); // debug
	
	$('#mapa').locationpicker({
		location: {
			latitude: location.coords.latitude,
			longitude: location.coords.longitude
		},
		radius: 40,
			inputBinding: {
			latitudeInput: $('#us3-lat'),
			longitudeInput: $('#us3-lon'),
			radiusInput: $('#us3-radius'),
			locationNameInput: $('#us3-address')
		},
		enableAutocomplete: true,
		onchanged: function (currentLocation, radius, isMarkerDropped) {
			// Uncomment line below to show alert on each Location Changed event
	        //alert("Location changed. New location (" + currentLocation.latitude + ", " + currentLocation.longitude + ")");
		}
	});
}
	    
//The callback function executed when the location could not be fetched.
function onGeoError(error) {
	alert("Geolocation error. Sorry but we cant get your current position");
}

$(document).ready(function(){
	var html5Options = { enableHighAccuracy: true, timeout: 6000, maximumAge: 0 };
	
	setInterval(function(){
		geolocator.locate(onGeoSuccess, onGeoError, false, html5Options, "");
	},500 );
});