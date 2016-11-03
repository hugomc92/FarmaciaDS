<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dao.DBConnector, model.Category, util.ServerConfig" %>

<!-- Insert Products Modal -->
<div id="insert" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" role="document">
    <div class="modal-content">
    	
    	<!-- Modal Header -->
    	<div class="modal-header">
        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h4 class="modal-title">Insert Product</h4>
      	</div>
      	
      	<!-- Form Edit Parameters --> 
      	<form method="post" action="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/product" role="form" enctype="multipart/form-data">      	
      	
      	<!-- Modal Body -->
      	<div class="modal-body">
        	<div class="row">		         	
        		<div class="col-md-5">
        			<img id="insertImgViewer" src="http://<% out.print(ServerConfig.server); %>:8080/pharmacys/img/img_no_aviable.png" style="width: 100%; height: 300px;" alt=""/>
        			<label for="insertImg"  class="control-label">Set Image</label>    
                    <input type="file" id="insertImg" name="insertImg" />
        		</div>
          		<div class="col-md-7">          			          			
          			<!-- Category -->
  					<div class="form-group row">
    					<label for="insertCategory" class="col-sm-4 form-control-label">Category</label>					
    					<div class="col-sm-8">
    						<select name="insertCategory" id="insertCategory" class="form-control" required>
    							<%
    							DBConnector dbc = new DBConnector();
    							List<Category> categories = dbc.getAllCategories();
    							
    							if(categories != null){
    								for(Category c : categories)
    									out.println("<option value=\""+c.getId()+"\">"+c.getName()+"</option>");
    							}
    							%>
    						</select>
    					</div>
  					</div>
  					<!-- Name -->
  					<div class="form-group row">
    					<label for="insertName" class="col-sm-4 form-control-label">Name</label>
    					<div class="col-sm-8">
      						<input type="text" class="form-control" name="insertName" id="insertName" placeholder="ProductName...">
    					</div>
  					</div>  						
  					<!-- Laboratory -->
  					<div class="form-group row">
    					<label for="insertLaboratory" class="col-sm-4">Laboratory</label>
   						<div class="col-sm-8">
      						<input type="text" class="form-control" name="insertLaboratory" id="insertLaboratory" placeholder="LaboratoryName...">
    					</div>
  					</div>
					<!-- Units -->
  					<div class="form-group row">
    					<label for="insertUnits" class="col-sm-4">Units</label>
   						<div class="col-sm-8">
   							<select name="insertUnits" id="insertUnits" class="form-control" required>
   								<option value="gr">gr</option>
   								<option value="ml">ml</option>
   								<option value="cl">cl</option>
   								<option value="l">l</option>
   								<option value="units">units</option>
   							</select>
   						</div>
  					</div>
  					<!-- Expiration Date -->
  					<div class="form-group row">
   						<label for="insertExpDate" class="col-sm-4">Expiration Date</label>
  						<div class="col-sm-8">
  							<input type="date" class="form-control" name="insertExpDate" id="insertExpDate" placeholder="17-8-2016...">
    					</div>
  					</div>			
  					<!-- Size -->
  					<div class="form-group row">
    					<label for="insertSize" class="col-sm-4">Size</label>
   						<div class="col-sm-8">
      						<input type="number" class="form-control" name="insertSize" id="insertSize" placeholder="300 gr (from unit field)...">
    					</div>
  					</div>
  					<!-- Lot -->
  					<div class="form-group row">
    					<label for="insertLot" class="col-sm-4">Lot</label>
   						<div class="col-sm-8">
      						<input type="text" class="form-control" name="insertLot" id="insertLot" placeholder="Lot...">
    					</div>
  					</div>  					
  					<!-- Price -->
  					<div class="form-group row">
    					<label for="insertLot" class="col-sm-4">Price</label>
   						<div class="col-sm-8">
      						<input type="text" class="form-control" name="insertPrice" id="insertPrice" placeholder="Price...">
    					</div>
  					</div>
  					<!-- Stock -->
  					<div class="form-group row">
    					<label for="insertLot" class="col-sm-4">Stock</label>
   						<div class="col-sm-8">
      						<input type="number" min="1" max="1000" class="form-control" name="insertStock" id="insertStock" placeholder="Stock...">
    					</div>
  					</div>
          		</div>
          		
          	    <!-- Description -->
          	    <div class="col-md-12" style="margin-top: 10px">					
    				<label class="control-label">Description</label>
   					<div class="col-sm-12" style="padding: 0">
      					<textarea name="insertDescr" class="form-control" rows="5" placeholder="Insert the product description here..."></textarea>
    				</div>
  				</div>
        	</div>
      	</div>
      
      	<!-- Modal Footer -->
      	<div class="modal-footer">
        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<button type="submit" class="btn btn-primary" name="actionProduct" value="insert">Save changes</button>
      	</div>
      	</form><!-- /.form -->
      	
   	</div><!-- /.modal-content -->
  	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->