$('#edit').on('show.bs.modal', function (event) {
	var pencil = $(event.relatedTarget); 	// Button that triggered the modal
	var tr = pencil.parent().parent();
	var childrens = tr.find('*');

	// get data via api rest of the selected row
	var id = childrens[0].innerHTML;
	var cif = document.getElementById("editCif").value;
	var url = "http://IP:8080/pharmacys/rest/inventory/getByIdJSON/"+cif+"/"+id;
	var modal = $(this)
	$.ajax({
		type: "GET",
		url: url,
		datatype: "json",
		success: function(jsondata){
			// update #edit inputs values
			modal.find('#editId').val(jsondata.id);
			modal.find('#editQueryCount').val(jsondata.queryCount);

			// loop through options in editCategory select
			for (var i = 0; i < document.getElementById("editCategory").length; ++i){
	            if (document.getElementById("editCategory").options[i].value == jsondata.category.id){
	               document.getElementById("editCategory").options[i].selected = true;
	            }
	        }

			modal.find('#editName').val(parse(jsondata.name));
			modal.find('#editLaboratory').val(parse(jsondata.laboratory));
			modal.find('#editUnits').val(jsondata.units);

			modal.find('#editImgViewer').attr('src',jsondata.urlImg);

			modal.find('#editSize').val(jsondata.size);
			modal.find('#editExpDate').val(jsondata.expirationDate);
			modal.find('#editLot').val(jsondata.lot);
			modal.find('#editDescr').val(parse(jsondata.description.join("\n")));
			modal.find('#editStock').val(jsondata.stock);
			modal.find('#editPrice').val(jsondata.price);
		},
		error : function(xhr, status) {
	        alert('Se ha producido un problema');
	        console.log(xhr);
	        console.log(status);
	    }
	});
});

$('#delete').on('show.bs.modal', function (event) {
	var trash = $(event.relatedTarget); 	// Button that triggered the modal
	var tr = trash.parent().parent();
	var childrens = tr.find('*');

	// get data of the selected row
	var id = childrens[0].innerHTML;

	// update #edit inputs values
	var modal = $(this)
	modal.find('#deleteId').val(id);
});
