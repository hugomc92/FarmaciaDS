<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="dao.DBConnector, model.Category" %>

<!-- Bloque central que ocupa un 75% de la pantalla, 100% en responsive movil -->
<div id="main" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        
	<div class="row">
		<%
		DBConnector dbc = new DBConnector();
		List<Category> categories = dbc.getAllCategories();
		
		if(categories != null){
			
			for(Category c : categories){
				out.print("<div id=\""+c.getName()+"\" class=\"col-md-6 maincategory\" style=\"height: 220px;\">");
				out.print("<img class=\"img-thumbnail\" src=\""+c.getUrlImg()+"\" alt=\"\" style=\"height: 200px;\"/>");
				out.print("</div>");
			}
		}
		%>
	</div>
</div>