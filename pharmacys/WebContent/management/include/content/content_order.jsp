<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="dao.DBConnector, model.Order" %>

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
</div>

<div class="col-md-12" style="background: white; height: auto; min-height: 90%; border-radius: 12px;">
	<h2>Order List</h2>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
	        	<tr>
	            	<th>#</th>
	                <th>Date</th>
	                <th>Price</th>
	                <th>Details</th>
	            </tr>
	       	</thead>
	       	<tbody>
	       		<%
	       		DBConnector dbc = new DBConnector();
	       		
	       		String cif = (String) session.getAttribute("cif");
	        	
	        	if(cif != null && !cif.equals(null) && cif != ""){
	       			List<Order> orderList = dbc.getAllOrdersByCIF(cif);
	       			if(orderList != null){
	       				
	       				for(Order o : orderList){
	       					out.println("<tr>");
	       					out.println("<td>"+o.getId()+"</td>");
	       					out.println("<td>"+o.getDate()+"</td>");
	       					out.println("<td>"+o.getPrice()+"</td>");
	       					out.println("<td><i class=\"fa fa-eye\" aria-hidden=\"true\"></i></td>");
	       					out.println("</tr>");
	       				}
	       			}
	        	}
	       		%>
	       		<tr></tr>
	       	</tbody>
		</table>
	</div>
</div>

</div>