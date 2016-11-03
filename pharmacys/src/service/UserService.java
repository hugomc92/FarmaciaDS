package service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.DBConnector;
import model.UserRefinedAbstraction;
import util.DateUtil;
import util.SHA512;
import util.SendEmailUsingGMAILSMTP;
import util.ServerConfig;

@Path("/user")
public class UserService {
	
	private DBConnector dbc = new DBConnector();
	
	@GET
	@Path("/getByEmailXML/{email}")
	@Produces(MediaType.APPLICATION_XML)
	public UserRefinedAbstraction getByEmailXML(@PathParam("email") String email){
		return dbc.getUserById(email);
	}
	
	@GET
	@Path("/getByEmailJSON/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserRefinedAbstraction getByEmailJSON(@PathParam("email") String email){
		System.out.println(email+" recibido");
		return dbc.getUserById(email);
	}
	
	@GET
	@Path("/getAllInXML")
	@Produces(MediaType.APPLICATION_XML)
	public List<UserRefinedAbstraction> getAllInXML(){
		return dbc.getAllUsers();
	}
	
	@GET
	@Path("/getAllInJSON")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserRefinedAbstraction> getAllInJSON(){
		return dbc.getAllUsers();
	}
	
	@GET
	@Path("/resetPassword2/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public String resetPassword2(@PathParam("email") String email){
		String result = "{\"status\":\"not ok\"}";						
		
		String currentDate = DateUtil.getCurrentDateTime();
		String resetHash = "";
		try {
        	resetHash = SHA512.hashText(currentDate);
        }
        catch(Exception e) {
        	e.getStackTrace();
        }
		
		resetHash = resetHash.substring(0, 20);
		
		if(resetHash != null && !resetHash.equals(null) && resetHash != ""){
			UserRefinedAbstraction user = dbc.getUserById(email);
			
			if(user != null){
				user.setResetHash(resetHash);
				
				if(!dbc.updateUser(user)){
					System.out.println(email+" requested to reset his password");
					
					SendEmailUsingGMAILSMTP smtp = new SendEmailUsingGMAILSMTP();
					
					String link = "http://"+ServerConfig.server+":8080/pharmacys/login?action=resetPassword&hash="+resetHash;
					String msgContent = "Please clic on the next link to reset your password: "+link;
					smtp.setContent(msgContent);
					smtp.setRecipient(email);
					smtp.send();
					
					result = "{\"status\":\"ok\"}";
				}
				else
					System.out.println(email+" requested to reset his password but failed");
			}			
		}
		
		return result;	
	}
	
	@POST
	@Path("/resetPassword/")
	@Produces(MediaType.APPLICATION_JSON)
	public String resetPassword(@FormParam("email") String email){
		String result = "{\"status\":\"not ok\"}";						
		
		String currentDate = DateUtil.getCurrentDateTime();
		String resetHash = "";
		try {
        	resetHash = SHA512.hashText(currentDate);
        }
        catch(Exception e) {
        	e.getStackTrace();
        }
		
		resetHash = resetHash.substring(0, 20);
		
		if(resetHash != null && !resetHash.equals(null) && resetHash != ""){
			UserRefinedAbstraction user = dbc.getUserById(email);
			
			if(user != null){
				user.setResetHash(resetHash);
				
				if(!dbc.updateUser(user)){
					System.out.println(email+" requested to reset his password");
					
					SendEmailUsingGMAILSMTP smtp = new SendEmailUsingGMAILSMTP();
					
					String link = "http://"+ServerConfig.server+":8080/pharmacys/login?action=resetPassword&hash="+resetHash;
					String msgContent = "Please clic on the next link to reset your password: "+link;
					smtp.setContent(msgContent);
					smtp.setRecipient(email);
					smtp.send();
					
					result = "{\"status\":\"ok\"}";
				}
				else
					System.out.println(email+" requested to reset his password but failed");
			}			
		}
		
		return result;	
	}
	
	@GET
	@Path("/login2/{email}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public String login2(@PathParam("email") String email, @PathParam("password") String password){
		String result = "{\"status\":\"not ok\"}";
		UserRefinedAbstraction u = dbc.getUserByEmailPassword(email, password);
		
		if(u != null)
			result = "{\"status\":\"ok\"}";
		
		return result;
	}
	
	@POST
	@Path("/login/")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@FormParam("email") String email, @FormParam("password") String password){
		String result = "{\"status\":\"not ok\"}";
		UserRefinedAbstraction u = dbc.getUserByEmailPassword(email, password);
		
		if(u != null)
			result = "{\"status\":\"ok\"}";
		
		return result;
	}
	
	@POST
	@Path("/insert2/{email}/{password}/{name}/{surname}")
	@Produces(MediaType.APPLICATION_JSON)
	public String insertUser2(
			@PathParam("email") String email,
			@PathParam("name") String name,
			@PathParam("surname") String surname,
			@PathParam("password") String password
			){
		String result = "{\"status\":\"not ok\"}";
		UserRefinedAbstraction u = new UserRefinedAbstraction();
		u.setEmail(email);
		u.setName(name);
		u.setSurname(surname);
		u.setPassword(password);
		u.setActive();
		u.setRole(0);
		
		if(!dbc.insertUser(u))
			result = "{\"status\":\"ok\"}";
		
		return result;	
	}
	
	/* 
	Ejemplo de uso:
	
	curl -X POST -d "email=pepicoooooor@nosepomail.com&name=pepico&surname=manolete&password=SHA512" http://localhost:8080/pharmacys/rest/user/insert
	
	*/
	@POST
	@Path("/insert/")
	@Produces(MediaType.APPLICATION_JSON)
	public String insertUser(
			@FormParam("email") String email,
			@FormParam("name") String name,
			@FormParam("surname") String surname,
			@FormParam("password") String password
			){
		String result = "{\"status\":\"not ok\"}";
		
		if(email != null && name != null && surname != null && password != null){
			UserRefinedAbstraction u = new UserRefinedAbstraction();
			u.setEmail(email);
			u.setName(name);
			u.setSurname(surname);
			u.setPassword(password);
			u.setActive();
			u.setRole(0);
			
			if(!dbc.insertUser(u))
				result = "{\"status\":\"ok\"}";
		}
					
		return result;
	}
	
	@POST
	@Path("/changePassword/")
	@Produces(MediaType.APPLICATION_JSON)
	public String changePassword(
			@FormParam("email") String email,
			@FormParam("oldPassword") String oldPassword,
			@FormParam("newPassword") String newPassword
			){
		String result = "{\"status\":\"not ok\"}";
		
		if(email != null && oldPassword != null && newPassword != null){
			UserRefinedAbstraction u = dbc.getUserByEmailPassword(email, oldPassword);
			
			if(u != null){
				u.setPassword(newPassword);
				
				if(!dbc.updateUser(u))
					result = "{\"status\":\"ok\"}";
			}						
		}
					
		return result;
	}
	
	@PUT
	@Path("/setPharmacy/{email}/{cif}")
	@Produces(MediaType.APPLICATION_JSON)
	public String setPharmacy(@PathParam("userIde") String email, @PathParam("cif") String cif){
		String result = "{\"status\":\"not ok\"}";
		UserRefinedAbstraction u = dbc.getUserById(email);
		u.setCifPharmacy(cif);
		
		if(!dbc.updateUser(u))
			result = "{\"status\":\"ok\"}";
		
		return result;
	}
	
}
