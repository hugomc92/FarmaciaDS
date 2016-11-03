<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

    <!-- Barra de navegacion superior -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      
      <div class="container-fluid">
      	
      	<!-- Menu de hamburguesa para el responsive -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#"><span><img src="../img/pharmacyslogo.png" width="35px" alt="" style="position: relative; top:-5px">PharmacyS</span></a>
        </div>
        
        <!-- Cuerpo barra de navegacion -->
        <div id="navbar" class="navbar-collapse collapse">         
          
          <!-- Botones de menu principal -->
          <ul class="nav navbar-nav navbar-right">
          <%
        		String uri = request.getServletPath();
        		String [] uriSplitted = uri.split("/");
        		String myPage = uriSplitted[uriSplitted.length-1];
        	%>
        	
            <li><a class="<% if(myPage.equals("reservation.jsp")) out.print("active"); %> btn btn-default btn-sm" href="reservation.jsp"><i class="fa fa-clock-o" aria-hidden="true"></i><span>Reservation</span></a></li>
            <li><a class="<% if(myPage.equals("order.jsp"))out.print("active"); %> btn btn-default btn-sm" href="order.jsp"><i class="fa fa-th-list" aria-hidden="true"></i><span>Orders</span></a></li>
            
            <!-- Dropdown Usuario -->
            <li class="dropdown">
			  <a class="<% if(myPage.equals("account.jsp")) out.print("active"); %> btn btn-default btn-sm dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			  <%
			    String username = null;
				if(session.getAttribute("user") != null)
					username = session.getAttribute("user").toString();
				%>
			    <i class="fa fa-user" aria-hidden="true"></i><span><% out.print(username); %></span>
			    <span class="caret"></span>
			  </a>
			  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
			    <li><a href="account.jsp">Preferences</a></li>
			    <li role="separator" class="divider"></li>
			    <li><a href="../login?action=logout">Sign out</a></li>
			  </ul>
			</li>
          </ul>
          
        </div> <!-- Cuerpo barra navegacion -->
      </div><!-- .container-fluid -->
    </nav>