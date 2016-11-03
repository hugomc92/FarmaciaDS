<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.DBConnector, model.Pharmacy" %>
    
    <jsp:include page="general.jsp" />
	
	<!-- Get current position by GPS -->
	<script src="../assets/js/geo.js"></script>
	
	<!-- Drag Marker Google Maps -->
    <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script src="../js/googleMapsDraggableMarker.js"></script>
	
	<%
	String cif = (String) session.getAttribute("cif");
	
	if(cif != null || cif != ""){
		DBConnector dbc = new DBConnector();
		Pharmacy pharmacy = dbc.getPharmacyByCIF(cif);
		
		if(pharmacy != null){
			if(pharmacy.getLatitude() != 0.0 && pharmacy.getLongitude() != 0.0)
				out.println("<script>loadMap("+pharmacy.getLatitude()+","+pharmacy.getLongitude()+");</script>");
			else {
				String browserType = request.getHeader("User-Agent");
				 
				/*
								SKIP CHROME BUG 
				
				getCurrentPosition() and watchPosition() are deprecated on insecure origins.
				To use this feature, you should consider switching your application to a secure origin, such as HTTPS.
				See https://goo.gl/rStTGz for more details.
				*/
				if(browserType.indexOf("Chrome") > -1)
					out.println("<script>loadMap(37.18425,-3.6024603);</script>");				
				else
					out.println("<script>asyncGetGeo(); checkCurrentGeo();</script>");
			}
		}
	}
	%>