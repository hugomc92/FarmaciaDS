<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="dao.DBConnector, model.Pharmacy, util.ServerConfig" %>

 <!-- Bloque central que ocupa un 75% de la pantalla, 100% en responsive movil -->
 <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" style="min-height: 100%; height: 100vh;">
  
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
    %>
 	<!-- Cuadro de amdinistracion de mi cuenta que ocupa un 58% de la pantalla -->
    <div class="col-md-7 text-center" style="height: auto; padding: 20px; background: #ebeff2; margin: 0 20%; margin-top: 50px;">    	
    	<%
    	String cif = (String) session.getAttribute("cif");
    	if(cif == null || cif == "" || cif.equals(null)){
    	%>
    	<h1 style="font-size:40px;">Insert your pharmacy below</h1>
    	<hr style="border-top: 1px dashed #cccccc">
    	
    	<form method="post" action="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/pharmacy" role="form" enctype="multipart/form-data">    	
    	<div class="form-group text-left row">
			<img id="insertImgViewer" src="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/img/img_no_aviable.png" style="width: 50%; height: 200px; margin: 0 25%" alt=""/>
			<br>
        	<label for="insertImg" class="col-sm-4 form-control-label">Set Image</label>
        	<div class="col-sm-8">    
            	<input type="file" id="insertImg" name="insertImg" />
            </div>
        </div>
    	<div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="insertCif">CIF</label>
    		<div class="col-sm-8">
    			<input type="text" name="insertCif" class="form-control" id="insertCif" placeholder="CIF...">
    		</div>
  		</div>
  		<div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="insertName">Name</label>
    		<div class="col-sm-8">
    			<input type="text" name="insertName" class="form-control" id="insertName" placeholder="Name...">
    		</div>
  		</div>
  		<div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="insertPhone">Phone Number</label>
    		<div class="col-sm-8">
    			<input type="text" name="insertPhone" class="form-control" id="insertPhone" placeholder="Phone...">
    		</div>
  		</div>
  		<div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="insertSched">Start Schedule</label>
    		<div class="col-sm-8">
    			<input type="number" name="insertSched" class="form-control" id="insertSched" value="0">
    		</div>
  		</div>
  		<div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="insertEsched">End Schedule</label>
    		<div class="col-sm-8">
    			<input type="number" name="insertEsched" class="form-control" id="insertEsched" value="0">
    		</div>
  		</div>
  		
  		<!-- Description -->
        <div class="col-md-12 text-left row" style="margin-top: 10px">					
    		<label class="control-label">Description</label>
   			<div class="col-sm-12" style="padding: 0">
      			<textarea name="insertDescription" class="form-control" rows="5"></textarea>
			</div>
  		</div>
  		
  		<!-- Indicates a successful or positive action -->
		<button type="submit" class="btn btn-success" name="action" value="insert" style="margin-top: 10px">Accept</button>
		</form>
		
    	<%} else { 
    		DBConnector dbc = new DBConnector();
    		Pharmacy pharmacy = dbc.getPharmacyByCIF(cif);    		
    	%>
    	
    	<h1 style="font-size:40px;"><% out.print(pharmacy.getName()); %></h1>
        <hr style="border-top: 1px dashed #cccccc">
        
        <form method="post" action="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/pharmacy" role="form" enctype="multipart/form-data">
        <div class="form-group text-left row">
			<img id="editImgViewer" src="<% out.print(pharmacy.getUrlImg()); %>" style="width: 50%; height: 200px; margin: 0 25%" alt=""/>
			<br>
        	<label for="editImg" class="col-sm-4 form-control-label">Set Image</label>
        	<div class="col-sm-8">    
            	<input type="file" id="editImg" name="editImg" />
            </div>
        </div>
        <div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="editCif">CIF</label>
    		<div class="col-sm-8">
    			<input type="text" name="editCif" class="form-control" id="editCif" value="<% out.print(pharmacy.getCif());%>">
    		</div>
  		</div>
  		<div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="editName">Name</label>
    		<div class="col-sm-8">
    			<input type="text" name="editName" class="form-control" id="editName" value="<% out.print(pharmacy.getName());%>">
    		</div>
  		</div>
  		<div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="editPhone">Phone Number</label>
    		<div class="col-sm-8">
    			<input type="text" name="editPhone" class="form-control" id="editPhone" value="<% out.print(pharmacy.getPhoneNumber());%>">
    		</div>
  		</div>
  		<div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="editSched">Start Schedule</label>
    		<div class="col-sm-8">
    			<input type="number" name="editSched" class="form-control" id="editSched" value="<% out.print(pharmacy.getStartSchedule());%>">
    		</div>
  		</div>
  		<div class="form-group text-left row">
    		<label class="col-sm-4 form-control-label" for="editEsched">End Schedule</label>
    		<div class="col-sm-8">
    			<input type="number" name="editEsched" class="form-control" id="editEsched" value="<% out.print(pharmacy.getEndSchedule());%>">
    		</div>
  		</div>
  		
  		<!-- Description -->
        <div class="col-md-12 text-left row" style="margin-top: 10px">					
    		<label class="control-label">Description</label>
   			<div class="col-sm-12" style="padding: 0">
      			<textarea name="editDescription" class="form-control" rows="5"><% out.print(pharmacy.getDescription()); %></textarea>
			</div>
  		</div>
  		
		<button type="submit" class="btn btn-success" name="action" value="edit" style="margin-top: 10px">Accept</button>
		</form>
		<%} %>
    	
	</div>
</div>
</div>