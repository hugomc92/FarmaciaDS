function printJSON(jsondata){
	var text = "";
	var str = JSON.stringify(jsondata, undefined, 4);

	text += "<pre>";
	text += formatJson(str);
	text += "</pre>";

	$("#output").append(text);
}

function printXML(xml){
	var text = "";

	text += "<pre>";
	xml_formatted = formatXml(xml);
	xml_escaped = xml_formatted.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/ /g, '&nbsp;').replace(/\n/g,'<br />');
	text += xml_escaped;
	text += "</pre>";

	$("#output").append(text);
}

$(function(){

	$('#loading')
	.hide()
    .ajaxStart(function() {
        $(this).show();
    })
    .ajaxStop(function() {
        $(this).hide();
    });

	var uri_selected = "http://IP:8080/pharmacys/rest/";
	var json = true;
	$("#button").click(function(){
		$("#output").empty();

		if($('input[name=format]:checked').val() == "xml")
			json = false;

		uri_selected += $("#selected").val();
		// si el usuario ha introducido en el campo de json
		if(json) {

			$.ajax({
				type: "GET",
				url: uri_selected,
				datatype: "json",
				success: function(jsondata){
					$("#output").append("<p>Peticion realizada correctamente</p>");
					printJSON(jsondata);
				},
				error : function(xhr, status) {
			        alert('Se ha producido un problema');
			    }
			});
		}
		// si el usuario ha introducido en el campo de xml
		else{

			$.ajax({
				type: "GET",
				url: uri_selected,
				datatype: "xml",
				success: function(xml){
					$("#output").append("<p>Peticion realizada correctamente</p>");
					printXML(new XMLSerializer().serializeToString(xml));
				},
				error : function(xhr, status) {
			        alert('Se ha producido un problema');
			    }
			});
		}

		uri_selected = "http://IP:8080/pharmacys/rest/";
		json = true;
	});

});
