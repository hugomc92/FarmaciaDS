$('#delete').on('show.bs.modal', function (event) {
	var trash = $(event.relatedTarget); 	// Button that triggered the modal
	var tr = trash.parent().parent();
	var childrens = tr.find('*');
	  
	// get data of the selected row
	var email = childrens[0].innerHTML;
	var productId = childrens[1].innerHTML;
	 
	// update #edit inputs values
	var modal = $(this)
	modal.find('#deleteReservationProductId').val(productId);
	modal.find('#deleteReservationEmail').val(email);
});