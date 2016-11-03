<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.ServerConfig" %>
<!-- Delete Products Modal -->
<div id="forgotPassword" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" role="document">
    <div class="modal-content">
    	
    	<!-- Modal Header -->
    	<div class="modal-header">
        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h4 class="modal-title">Forgot your Password?</h4>
      	</div>
      	
      	<!-- Form Edit Parameters --> 
      	<form method="post" action="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/login" role="form">
      	
      	<!-- Modal Body -->
      	<div class="modal-body">
        	<div class="row">		         	
        		<div class="col-md-12">
        			<p>Introduce below your email, we are going to send to you a reset link</p>
        			<div class="form-group row">
    					<label for="forgotEmail" class="col-sm-4 form-control-label">Email</label>
    					<div class="col-sm-8">
      						<input type="text" class="form-control" id="forgotEmail" name="forgotEmail" placeholder="Your email...">
    					</div>
  					</div>  
        		</div>          		
        	</div>
      	</div>
      
      	<!-- Modal Footer -->
      	<div class="modal-footer">
        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<button type="submit" class="btn btn-primary" name="action" value="forgotPassword">Accept</button>
      	</div>
      	</form><!-- /.form -->
      	
   	</div><!-- /.modal-content -->
  	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->