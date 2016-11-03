<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.ServerConfig" %>
<!-- Delete Products Modal -->
<div id="delete" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" role="document">
    <div class="modal-content">
    	
    	<!-- Modal Header -->
    	<div class="modal-header">
        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h4 class="modal-title">Delete Product</h4>
      	</div>
      	
      	<!-- Form Edit Parameters --> 
      	<form method="post" action="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/product" role="form" enctype="multipart/form-data">
      	<input type="hidden" name="deleteId" id="deleteId" />
      	
      	<!-- Modal Body -->
      	<div class="modal-body">
        	<div class="row">		         	
        		<div class="col-md-12">
        			<h4>Are you sure that do you want to delete this product?</h4>
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
        	<button type="submit" class="btn btn-primary" name="actionProduct" value="delete">Accept</button>
      	</div>
      	</form><!-- /.form -->
      	
   	</div><!-- /.modal-content -->
  	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->