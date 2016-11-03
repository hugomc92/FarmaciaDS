<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>

<jsp:include page="management/include/heads/login.jsp" />
<body>
	
	<!-- Top content -->
    <div class="top-content">
        	
   		<div class="inner-bg">
   			<div class="container">
   			<%   				
				// si la sesion es nueva o el atributo user no esta definido
				if(session.getAttribute("userEmail") != null){ 	
			%>
				<div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>PharmacyS</strong> Reset Password Form</h1>
                    <div class="description">
	                    <p>
		                Set your new password below!
	                    </p>
                    </div>
                </div>
               	</div> <!-- end row -->
       			
       			<div class="row">
            	<div class="col-sm-6 col-sm-offset-3 form-box">                	
                    <!-- body div form -->
                    <div class="form-bottom">
			        	<form role="form" action="login" method="post" class="login-form" id="reset">
			            	<div class="form-group">
			            		<label class="sr-only" for="email">New Password</label>
			                	<input type="password" name="newPassword" placeholder="New password..." class="form-username form-control" id="newPassword" required>
			                </div>
			                
			                <div class="form-group">
			                	<label class="sr-only" for="password">Repeat Password</label>
			                    <input type="password" name="repeatPassword" placeholder="Repeat Password..." class="form-password form-control" id="repeatPassword" required>
			               	</div>
			                <button type="submit" name="action" value="reset" class="btn">Save</button>
			          	</form>
		        	</div>
            	</div>
               	</div> <!-- end row -->               	           		
			<%} else {%>
				<div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1>Please back to the index page</h1>                                       
                </div>
                
               	</div> <!-- end row -->
				<div class="row">
                	<div class="col-sm-5 logged">
                	<a class="btn btn-link-1 btn-link-1-facebook" href="index.jsp">
	                    <i class="fa fa-sign-out"></i> Back
					</a>
					</div>
				</div>             
			<% } %>
            </div> <!-- end container -->
        </div> <!-- end inner-bg -->
    </div> <!-- end top-content -->

	<jsp:include page="management/include/scripts/login.jsp"></jsp:include>
	<script>
	$("#reset").submit(function( event ) {	
		var newPassword = document.getElementById("newPassword").value;
		var repeatPassword = document.getElementById("repeatPassword").value;
		if(newPassword != repeatPassword){
			alert("Passwords dont match");
			event.preventDefault();
		}
	});
	</script>
</body>
</html>