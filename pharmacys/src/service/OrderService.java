package service;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.DBConnector;
import model.Pharmacy;
import model.Product;
import util.SendEmailUsingGMAILSMTP;

@Path("/order")
public class OrderService {
	
	private DBConnector dbc = new DBConnector();
	
	// http://stackoverflow.com/questions/29563907/curl-post-with-multipart-form-data-and-json
	/*
	
	PAYMENT IN JSON
	{
		“orderId” : orderId,
		“email” : email,
		“date”: (long) date,
		“productList” : [
	   		 {“cif”: cif, “productId”: productId, “quantity” : quantity}, 
	   		 {“cif”: cif, “productId”: productId, “quantity” : quantity}, 
	   		 {“cif”: cif, “productId”: productId, “quantity” : quantity}
		],
		“totalPrice” : totalPrice
	}
	curl -v -X POST -H "Content-Type:application/json" -d "data={\"orderId\":1,\"email\":\"pharmacysds@gmail.com\",\"date\":\"18712872183\",\"profiles\":[{7\",\"productId\":5,\"quantity\":3}],\"totalPrice\":123.34};type=application/json" http://localhost:8080/pharmacys/rest/order/payment

	*/
	@POST
	@Path("/payment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String payment(@FormParam("data") JSONObject jsondata){
		String result = "{\"status\":\"not ok\"}";
		
		if(jsondata != null){		
			Integer orderId = (Integer) jsondata.getInt("orderId");
			String email = jsondata.getString("email");
			Date date = new Date(jsondata.getLong("date"));
			JSONArray jsonProductList = jsondata.getJSONArray("profiles");
			Double totalPrice = jsondata.getDouble("totalPrice");
			
			// check parameters
			if(orderId > 0 && jsonProductList != null && totalPrice > 0 && (email != "" && !email.equals(null)) && date != null){
				int size = jsonProductList.length();
			    ArrayList<JSONObject> productList = new ArrayList<JSONObject>();
			    for (int i = 0; i < size; i++) {
			        JSONObject aux = jsonProductList.getJSONObject(i);			           
			        productList.add(aux);
			    }
			    
			    // check empty arraylist
			    if(productList.size() > 0){
			    	
			    	System.out.println(email+" requested to issue a recepit for order "+orderId);
			    	
			    	boolean hasErrors = false;
			    	
			    	// preparing the email
			    	SendEmailUsingGMAILSMTP smtp = new SendEmailUsingGMAILSMTP();
			    	String msgContent ="Your receipt at date "+date.toString()+" is: ";
			    	for(JSONObject o : productList){
			    		
			    		String cif = o.getString("cif");
			    		Pharmacy pharmacy = dbc.getPharmacyByCIF(cif);						    							
						Product product = dbc.getProductById(o.getInt("productId"));
			    		Integer quantity = o.getInt("quantity");
			    		
						if(pharmacy != null && product != null && quantity > 0){							
							msgContent = msgContent+pharmacy.getName()+" "+product.getName()+" x"+quantity+"\n";				    		
						}
			    		else{
			    			hasErrors = true;
			    		}
			    	}
			    	msgContent = msgContent + " with TOTALPRICE "+totalPrice;
			    	smtp.setContent(msgContent);
					smtp.setRecipient(email);
					
					if(smtp.send())
						hasErrors = false;
					else
						hasErrors = true;
			    	
			    	if(hasErrors == false)
			    		result = "{\"status\":\"ok\"}";	    	
			    }
			}
		}
		
		return result;
	}
}
