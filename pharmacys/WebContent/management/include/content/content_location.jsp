<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*, util.ServerConfig" %>

<!-- Bloque central que ocupa un 75% de la pantalla, 100% en responsive movil -->
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main location">
  
 	<div class="row" style="background: #fff; margin-left: 0;">
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
    
    String cif = (String) session.getAttribute("cif");
    
    if(cif != null && cif != "" && !cif.equals(null)){
    %>
    <!-- Informacion de la localizacion -->
    	<div class="col-sm-4 info-map text-center" style="padding: 0px 10px 0px 0px">
    		<h4 id="markerStatus">Finding your position...</h4>
    		
    		<form method="post" action="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/pharmacy" role="form">
    			<input type="hidden" name="editCif" id="editCif" value="<% out.print(cif); %>"/>
    			
		    	<div class="form-group text-left" style="padding: 0px 35px 0px 20px; margin-top: 30px">
					<label for="editAddress">Address</label>
					<input type="text" class="form-control" id="editAddress" name="editAddress" />
		  		</div>  				
		  				
		  		<div class="form-group text-left" style="padding: 0px 35px 0px 20px;">
		    		<label for="editLat">Latitude</label>
		    		<input type="text" class="form-control" id="editLat" name="editLat" />
		  		</div>
		  				
		  		<div class="form-group text-left" style="padding: 0px 35px 0px 20px;">
		    		<label for="editLong">Longitude</label>
		    		<input type="text" class="form-control" id="editLong" name="editLong" />
		  		</div>
		  		
		  		<!-- Indicates a successful or positive action -->
				<button type="submit" name="action" value="location" class="btn btn-success">Save</button>
			</form>
  		</div>
          	
        <!-- Map -->
        <div class="col-md-8 map-holder">
        	<div id="mapa"></div>
        </div>
	<%
	} else {%>
		<div class="col-md-8">
    		<h3 style="padding: 30px">First at all you need to insert a Pharmacy <a href="pharmacy.jsp">here</a></h3>
    	</div>
    <%} %>                     
    </div>    
</div>