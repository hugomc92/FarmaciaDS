<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.ServerConfig" %>

<!-- Delete Products Modal -->
<div id="delete" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" role="document">
    <div class="modal-content">
    	
    	<!-- Modal Header -->
    	<div class="modal-header">
        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h4 class="modal-title">Delete Reservation</h4>
      	</div>
      	
      	<!-- Form Edit Parameters --> 
      	<form method="post" action="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/reservation" role="form">
      	<input type="hidden" name="deleteReservationCIF" id="deleteReservationCIF" value="<%out.print(session.getAttribute("cif")); %>"/>
      	<input type="hidden" name="deleteReservationProductId" id="deleteReservationProductId" />
      	<input type="hidden" name="deleteReservationEmail" id="deleteReservationEmail" />
      	
      	<!-- Modal Body -->
      	<div class="modal-body">
        	<div class="row">		         	
        		<div class="col-md-12">
        			<h4>Are you sure that do you want to delete this reservation?</h4>
        			<div class="radio">
    					<label>
      						<input type="radio" name="deleteOption" value="yes">
      						Yes, I am sure
    					</label>
  					</div>
  					<div class="radio">
    					<label>
      						<input type="radio" name="deleteOption" value="no" checked>
      						No, maybe in another moment
    					</label>
  					</div>
        		</div>          		
        	</div>
      	</div>
      
      	<!-- Modal Footer -->
      	<div class="modal-footer">
        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<button type="submit" class="btn btn-primary" name="actionReservation" value="delete">Accept</button>
      	</div>
      	</form><!-- /.form -->
      	
   	</div><!-- /.modal-content -->
  	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->