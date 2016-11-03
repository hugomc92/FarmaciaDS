<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="dao.DBConnector, model.Product, model.Inventory" %>

<jsp:include page="modal_insert_product.jsp" />
<jsp:include page="modal_edit_product.jsp" />
<jsp:include page="modal_delete_product.jsp" />

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
    %>            
    
    <div class="row placeholders" style="padding:0 20px">
    	<h1 class="page-header text-left">Top Products</h1>
    	<%
    	DBConnector dbc = new DBConnector();
    	String cif = (String) session.getAttribute("cif");
    	
    	if(cif != null && !cif.equals(null) && cif != ""){
	    	List<Product> topProducts = dbc.getTopProducts(4, cif);
	    	if(topProducts != null){
		    	for(Product p : topProducts){
		    		out.println("<div class=\"col-xs-6 col-sm-3 placeholder\">");
			    	out.println("<img src=\""+p.getUrlImg()+"\" width=\"200\" height=\"200\" class=\"img-responsive\" alt=\"Generic placeholder thumbnail\">");
			        out.println("<h4>"+p.getName()+"</h4>");
			        out.println("<span class=\"text-muted\">"+p.getCategory().getName()+"</span>");
			    	out.println("</div>");    
			    }
	    	}
    	}
	    %> 
	</div>
	
	<script>
    function idSorter(a, b) {
        if (a > b) return 1;
        if (a < b) return -1;
        return 0;
    }
	</script>
	<div class="table-responsive" style="background-color: #eee; border-radius: 20px; padding: 20px;">          	
    	<h2 class="sub-header">Product List</h2>
    	
    	<!-- Add product -->
    	<button type="button" class="btn btn-primary" style="position:relative; top: 45px" data-toggle="modal" data-target="#insert">Add</button>
    	
    	<!-- Table -->
       	<table class="table table-striped" data-toggle="table" data-height="460" data-sort-name="id" data-sort-order="desc"
       	data-side-pagination="server" data-show-columns="true" data-pagination="true" data-page-size="10" data-page-list="[10,20,30]" 
       	data-show-toggle="true" data-search="true" data-mobile-responsive="true">
       		<thead>
           		<tr>
                	<th data-field="id" data-sortable="true" data-switchable="false">#</th>
                  	<th data-field="name" data-sortable="true" data-switchable="false">Name</th>
                  	<th data-field="category" data-sortable="true">Category</th>
                  	<th data-field="stock" data-sortable="true" data-visible="true">Stock</th>
                  	<th data-field="price" data-sortable="true" data-visible="false">Price</th>                  	
                  	<th data-sortable="false" data-switchable="false">Edit</th>
                  	<th data-sortable="false" data-switchable="false">Delete</th>
                </tr>
          	</thead>
          	<tbody>
            <%@ page import="dao.DBConnector, java.util.*, model.Product" %>
          	<%          		
          		List<Product> productlist = dbc.getProductsListByCif(cif);
          		if(productlist != null){
	          		for(Product p : productlist){
	          			out.println("<tr>");
	          			out.println("<td>"+p.getId()+"</td>");
	          			out.println("<td>"+p.getName()+"</td>");          			
	          			out.println("<td>"+p.getCategory().getName()+"</td>");
	          			
	          			Inventory i = dbc.getInventoryById(cif, p.getId());
	          			if(i != null){
	          				out.println("<td>"+i.getStock()+"</td>");
	          				out.println("<td>"+i.getPrice()+"</td>");
	          			}
	          			else {
	          				out.println("<td>NaN</td>");
	          				out.println("<td>Nan</td>");
	          			}
	          			
	          			out.println("<td><i class=\"fa fa-pencil\" aria-hidden=\"true\" data-toggle=\"modal\" data-target=\"#edit\"></i></td>");
	          			out.println("<td><i class=\"fa fa-trash\" aria-hidden=\"true\" data-toggle=\"modal\" data-target=\"#delete\"></i></td>");
	          			out.println("</tr>");
	          		}
          		}
        	%>          
            </tbody>
       	</table>
            
       	<!-- Paginador de productos -->
        <nav class="text-center">
			<ul class="pagination">
				<li>
			    	<a href="#" aria-label=Anterior>
			        	<span aria-hidden="true">&laquo;</span>
			      	</a>
			    </li>
			    <li><a href="#">1</a></li>
			    <li><a href="#">2</a></li>
			    <li><a href="#">3</a></li>
			    <li><a href="#">...</a></li>
			    <li><a href="#">5</a></li>
			    <li>
			      	<a href="#" aria-label="Siguiente">
			        	<span aria-hidden="true">&raquo;</span>
			      	</a>
			    </li>
			</ul>
		</nav>
 	</div><!-- table-responsive -->
	
	</div><!-- row -->
</div> <!-- main -->