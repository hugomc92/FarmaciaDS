<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page 
	import="java.util.Map"
	import="java.util.LinkedHashMap"
%>
      	<!-- Barra de navegacion izquierda -->
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
          	 <% 
          	 	
        		String uri = request.getServletPath();
        		String [] uriSplitted = uri.split("/");
        		String myPage = uriSplitted[uriSplitted.length-1];
        	
        		Map<String, String> menuList = new LinkedHashMap<String,String>();
        		menuList.put("pharmacy.jsp", "My Pharmacy");
        		menuList.put("product.jsp", "Products");
        		menuList.put("location.jsp", "Location");
        		menuList.put("category.jsp", "Categories");
        		
        		for(Map.Entry<String, String> entry : menuList.entrySet()){
        			//System.out.println(entry.getKey() + " " + entry.getValue());
        			if(myPage.equals(entry.getKey()))
        				out.println("<li class='active'><a href='#'>"+entry.getValue()+"</a></li>");
        			else
        				out.println("<li><a href='"+entry.getKey()+"'>"+entry.getValue()+"</a></li>");        			
        		}
        	%>       
          </ul>
        </div>
                    