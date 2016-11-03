package service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.DBConnector;
import model.Product;

@Path("/product")
public class ProductService {
	private DBConnector dbc = new DBConnector();
	
	@GET
	@Path("/getByIdXML/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Product getByIdXML(@PathParam("id") String id){
		return dbc.getProductById(Integer.parseInt(id));
	}
	
	@GET
	@Path("/getByIdJSON/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getByIdJSON(@PathParam("id") String id){		
		return dbc.getProductById(Integer.parseInt(id));
	}
	
	@GET
	@Path("/getAllInJSON")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAllInJSON(){
		return dbc.getAllProducts();
	}
	
	@GET
	@Path("/getAllInXML")
	@Produces(MediaType.APPLICATION_XML)
	public List<Product> getAllInXML(){
		return dbc.getAllProducts();
	}
	
	@GET
	@Path("/getLastInserted")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getLastInserted(){
		return dbc.getLastProductInserted();
	}
	
	@GET
	@Path("/getTableBytes")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTableBytes(){
		String result = "{\"status\":\"not ok\"}";
		float size = dbc.getProductSize();
		
		if(size > 0.0f)
			result = "{\"size\":\""+size+"\"}";
		
		return result;
	}
	
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(@PathParam("id") int id){
		String result = "{\"status\":\"not ok\"}";
		Product product = dbc.getProductById(id);
		
		if(!dbc.deleteProduct(product))
			result = "{\"status\":\"ok\"}";
		
		return result;	
	}
}
