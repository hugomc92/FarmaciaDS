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
				if(session.isNew()||session.getAttribute("user") == null){ 	
				%>
			
				<jsp:include page="management/include/content/modal_forgotPassword_login.jsp"></jsp:include>
				<div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
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
                    <h1><strong>PharmacyS</strong> Login Form</h1>
                    <div class="description">
	                    <p>
		                First things first, log in to continue to management system of your pharmacy!
	                    </p>
                    </div>
                </div>
               	</div> <!-- end row -->
       			
       			<div class="row">
            	<div class="col-sm-6 col-sm-offset-3 form-box">
                	<!-- top div form -->
                	<div class="form-top">
                    	<div class="form-top-left">
                        	<h3>Login to our site</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        
                        <!-- key icon -->
                        <div class="form-top-right"><i class="fa fa-key"></i></div>
                  	</div>
                    
                    <!-- body div form -->
                    <div class="form-bottom">
			        	<form role="form" action="login" method="post" class="login-form">
			            	<div class="form-group">
			            		<label class="sr-only" for="email">Username</label>
			                	<input type="text" name="email" placeholder="Email..." class="form-username form-control" id="email">
			                </div>
			                
			                <div class="form-group">
			                	<label class="sr-only" for="password">Password</label>
			                    <input type="password" name="password" placeholder="Password..." class="form-password form-control" id="password">
			               	</div>
			                <button type="submit" name="action" value="login" class="btn">Sign in!</button>
			          	</form>
			          	
			          	<p style="margin-top: 10px"><a id="forgottenPassword" href="javascript:;" data-toggle="modal" data-target="#forgotPassword">Forgot your Password?</a></p>
			          	
		        	</div>
            	</div>
               	</div> <!-- end row -->
               	
           		<div class="row">
                <div class="col-sm-6 col-sm-offset-3 social-login">
                	<h3>...or login with:</h3>
                    <div class="social-login-buttons">
	                	<a class="btn btn-link-1 btn-link-1-facebook" href="#">
	                    	<i class="fa fa-facebook"></i> Facebook
						</a>
	                    <a class="btn btn-link-1 btn-link-1-twitter" href="#">
	                        <i class="fa fa-twitter"></i> Twitter
						</a>
	                    <a class="btn btn-link-1 btn-link-1-google-plus" href="#">
	                        <i class="fa fa-google-plus"></i> Google Plus
	                    </a>
                     </div>
              	</div>
                </div> <!-- end row -->
			<%					
				}
				else { // la sesion no es nueva y session tiene definidos atributos
					String username = null;
					if(session.getAttribute("user") != null)
						username = session.getAttribute("user").toString();
					
					if(username != null){
			%>
				<div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1>Welcome <strong><% out.print(username); %></strong> to PharmacyS </h1>                                       
                </div>
               	</div> <!-- end row -->
               	
               	<div class="row">
                <div class="col-sm-5 logged">
                	<a class="btn btn-link-1 btn-link-1-facebook" href="management/product.jsp">
	                    <i class="fa fa-sign-in"></i> Continue
					</a>
                    <form role="form" action="login" method="post" class="login-form">
                    	<button type="submit" name="action" value="logout" class="btn btn-link-1 signout"><i class="fa fa-sign-out"></i>Sign out</button>
                    </form>
                </div>
                </div>    	            	
                <%
					} // end if email
                } // end else 
                %>
                
            </div> <!-- end container -->
        </div> <!-- end inner-bg -->
    </div> <!-- end top-content -->

	<jsp:include page="management/include/scripts/login.jsp"></jsp:include>
</body>
</html>