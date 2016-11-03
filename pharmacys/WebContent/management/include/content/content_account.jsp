<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*, java.util.*" %>
<%@ page import="dao.DBConnector, model.UserRefinedAbstraction, util.ServerConfig" %>

 <!-- Bloque central que ocupa un 75% de la pantalla, 100% en responsive movil -->
 <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        
 <div class="row">
 	<%  
    List<String> msg = (ArrayList<String>) session.getAttribute("msg");
    List<String> errors = (ArrayList<String>) session.getAttribute("errors");
    if(msg != null && !msg.isEmpty()){
    	
    	out.println("<div class=\"alert alert-success\" role=\"alert\">");
    	out.println("<p class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\">");
  		for(String s : msg)
  			out.println(s);
  		out.println("</p>");    	
		out.println("</div>");	
    }
    session.removeAttribute("msg");
    		
    if(errors != null && !errors.isEmpty()){
       	out.println("<div class=\"alert alert-danger\" role=\"alert\">");
    	out.println("<p class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\">");   		        	
    	for(String s : errors)
      		out.println(s);
      	out.println("</span>");
        	
    	out.println("</div>");    	
    }
    session.removeAttribute("errors");
    
 	DBConnector dbc = new DBConnector();
 	
 	String email = (String) session.getAttribute("userEmail");
 	UserRefinedAbstraction user = dbc.getUserById(email);
 	if(email != null && !email.equals(null) && email != ""){
 	%>
 	<!-- Cuadro de amdinistracion de mi cuenta que ocupa un 58% de la pantalla -->
 	 	
    <div class="col-md-7 text-center" style="height: 600px; background: #ebeff2; margin: 0 20%">
    	<h1 style="font-size:40px;">My Account</h1>
        <hr style="border-top: 1px dashed #cccccc">
        
        <form role="form" action="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/login" method="post" class="login-form">
	  		<div class="form-group text-left">
	    		<label for="editName">Name</label>
	    		<input type="text" class="form-control" id="editName" name="editName" placeholder="Fullname..." value="<% out.print(user.getName());%>">
	  		</div>
	  		<div class="form-group text-left">
	    		<label for="editSurname">Surname</label>
	    		<input type="text" class="form-control" id="editSurname" name="editSurname" placeholder="Surname..." value="<% out.print(user.getSurname());%>">
	  		</div>
	  		
	  		<h3>Change your password</h3>
	  		<div class="form-group text-left">
	    		<label for="editCurrentPassword">Current password</label>
	    		<input type="password" class="form-control" id="editCurrentPassword" name="editCurrentPassword" placeholder="Current password...">
	  		</div>
	  		<div class="form-group text-left">
	    		<label for="editNewPassword">New password</label>
	    		<input type="password" class="form-control" id="editNewPassword" name="editNewPassword" placeholder="New password...">
	  		</div>
	  		
	  		<!-- Indicates a successful or positive action -->
			<button type="submit" class="btn btn-success" name="action" value="edit">Save</button>
		</form>
	</div>
	<%} %>
</div>
</div>