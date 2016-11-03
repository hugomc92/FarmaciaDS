package service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.DBConnector;
import model.Pharmacy;

@Path("/pharmacy")
public class PharmacyService {

	private DBConnector dbc = new DBConnector();
	
	@GET
	@Path("/getByCIFXML/{cif}")
	@Produces(MediaType.APPLICATION_XML)
	public Pharmacy getPharmacyByIdXML(@PathParam("cif") String cif){
		return dbc.getPharmacyByCIF(cif);
	}
	
	@GET
	@Path("/getByCIFJSON/{cif}")
	@Produces(MediaType.APPLICATION_JSON)
	public Pharmacy getPharmacyByIdJSON(@PathParam("cif") String cif){
		return dbc.getPharmacyByCIF(cif);
	}
	
	@GET
	@Path("/getAllInXML")
	@Produces(MediaType.APPLICATION_XML)
	public List<Pharmacy> getAllPharmaciesInXML(){
		return dbc.getAllPharmacies();
	}
		
	@GET
	@Path("/getAllInJSON")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pharmacy> getAllPharmaciesInJSON(){
		return dbc.getAllPharmacies();
	}
	
	@GET
	@Path("/getTableBytes")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTableBytes(){
		String result = "{\"status\":\"not ok\"}";
		float size = dbc.getPharmacySize();
		
		if(size > 0.0f)
			result = "{\"size\":\""+size+"\"}";
		
		return result;
	}
	
	@POST
	@Path("/insert/{cif}/{name}/{phone}/{startSchedule}/{endSchedule}")
	@Produces(MediaType.APPLICATION_JSON)
	public String insert(@PathParam("cif") String cif, @PathParam("name") String name,  @PathParam("phone") int phone, @PathParam("startSchedule") int sched, @PathParam("endSchedule") int esched){
		String result = "{\"status\":\"not ok\"}";
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setCif(cif);
		pharmacy.setName(name);
		pharmacy.setPhoneNumber(phone);
		pharmacy.setDescription("");
		pharmacy.setStartSchedule(sched);
		pharmacy.setEndSchedule(esched);
		
		if(!dbc.insertPharmacy(pharmacy))
			result = "{\"status\":\"ok\"}";
		
		return result;
	}
	
	@DELETE
	@Path("/delete/{cif}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(@PathParam("cif") String cif){
		String result = "{\"status\":\"not ok\"}";
		Pharmacy pharmacy = dbc.getPharmacyByCIF(cif);
		
		if(!dbc.deletePharmacy(pharmacy))
			result = "{\"status\":\"ok\"}";
		
		return result;
	}

	@PUT
	@Path("/updateName/{cif}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateName(@PathParam("cif") String cif, @PathParam("name") String name){
		String result = "{\"status\":\"not ok\"}";
		Pharmacy pharmacy = dbc.getPharmacyByCIF(cif);
		pharmacy.setName(name);
		
		if(!dbc.updatePharmacy(pharmacy))
			result = "{\"status\":\"ok\"}";
		
		return result;
	}
	
	@PUT
	@Path("/updatePhone/{cif}/{phone}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePhone(@PathParam("cif") String cif, @PathParam("phone") int phone){
		String result = "{\"status\":\"not ok\"}";
		Pharmacy pharmacy = dbc.getPharmacyByCIF(cif);
		pharmacy.setPhoneNumber(phone);
		
		if(!dbc.updatePharmacy(pharmacy))
			result = "{\"status\":\"ok\"}";
		
		return result;
	}
}
