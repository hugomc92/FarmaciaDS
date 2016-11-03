package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnector;
import model.Pharmacy;
import model.UserRefinedAbstraction;
import util.ServerConfig;
import util.UploadFile;

@WebServlet("/pharmacy")
@MultipartConfig
public class SPharmacy extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DBConnector dbc;
	private List<String> msg;
	private List<String> errors;
	
    public SPharmacy() {
        super();
        this.dbc = new DBConnector();
        this.msg = new ArrayList<String>();
        this.errors = new ArrayList<String>();
    }

    private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String cif, name, description;
		int phoneNumber, startSchedule, endSchedule;
		Pharmacy pharmacy;
		
    	cif = request.getParameter("insertCif");
		name = request.getParameter("insertName");
		description = request.getParameter("insertDescription");
		phoneNumber = Integer.parseInt(request.getParameter("insertPhone"));
		startSchedule = Integer.parseInt(request.getParameter("insertSched"));
		endSchedule = Integer.parseInt(request.getParameter("insertEsched"));
		
		pharmacy = new Pharmacy();
		pharmacy.setCif(cif);
		pharmacy.setName(name);
		pharmacy.setPhoneNumber(phoneNumber);
		pharmacy.setDescription(description);
		pharmacy.setStartSchedule(startSchedule);
		pharmacy.setEndSchedule(endSchedule);
		
		// UPLOAD IMAGE
		String resultUploadImg = UploadFile.upload(request, response, "insertImg");
		if(resultUploadImg == ""){
			this.errors.add("Cant upload your image");
			pharmacy.setUrlImg("http://"+ServerConfig.server+":8080/pharmacys/img/img_no_aviable.png");
		}
		else{
			this.msg.add("Image uploaded successfully");
			pharmacy.setUrlImg(resultUploadImg.replace(ServerConfig.dataDirectory+"/", "http://"+ServerConfig.server+":8080/pharmacys/data/"));
		}
		
		if(!dbc.insertPharmacy(pharmacy)){
			this.msg.add("Pharamcy inserted successfully");
			request.getSession().setAttribute("cif", cif);
			
			UserRefinedAbstraction user = dbc.getUserById(request.getSession().getAttribute("userEmail").toString());
			user.setCifPharmacy(cif);
			if(!dbc.updateUser(user))
				this.msg.add("Pharamcy linked to user successfully");
			else
				this.errors.add("Pharamcy cannot be linked to the user");					
		}
		else
			this.errors.add("The Pharmacy cannot be inserted");
    }
    
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cif, name, description;
		int phoneNumber, startSchedule, endSchedule;
		Pharmacy pharmacy;
    	
    	cif = request.getParameter("editCif");
		name = request.getParameter("editName");
		description = request.getParameter("editDescription");
		phoneNumber = Integer.parseInt(request.getParameter("editPhone"));
		startSchedule = Integer.parseInt(request.getParameter("editSched"));
		endSchedule = Integer.parseInt(request.getParameter("editEsched"));
		
		pharmacy = dbc.getPharmacyByCIF(cif);
		pharmacy.setName(name);
		pharmacy.setPhoneNumber(phoneNumber);
		pharmacy.setDescription(description);
		pharmacy.setStartSchedule(startSchedule);
		pharmacy.setEndSchedule(endSchedule);
		
		// UPLOAD IMAGE
    	long imgSize = request.getPart("editImg").getSize(); 
    	if( imgSize > 0){
    		String resultUploadImg = UploadFile.upload(request, response, "editImg");
    		if(resultUploadImg == ""){
    			this.errors.add("Cant upload your image");
    			pharmacy.setUrlImg("http://"+ServerConfig.server+":8080/pharmacys/img/img_no_aviable.png");
    		}
    		else{
    			this.msg.add("Image uploaded successfully");
    			pharmacy.setUrlImg(resultUploadImg.replace(ServerConfig.dataDirectory+"/", "http://"+ServerConfig.server+":8080/pharmacys/data/"));
    		}
    	}
    	else {
    		this.msg.add("No image selected");
    	}
    	
		if(!dbc.updatePharmacy(pharmacy))
			this.msg.add("Pharamcy updated successfully");
		else
			this.errors.add("The Pharmacy cannot be updated");
		
    }
    
    private void editLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String address, cif;
    	double latitude, longitude;
    	Pharmacy pharmacy;
    	
    	cif = request.getParameter("editCif");
    	address = request.getParameter("editAddress");
    	latitude = Double.parseDouble(request.getParameter("editLat"));
    	longitude = Double.parseDouble(request.getParameter("editLong"));
    	
    	pharmacy = dbc.getPharmacyByCIF(cif);
    	pharmacy.setAddress(address);
    	pharmacy.setLatitude(latitude);
    	pharmacy.setLongitude(longitude);
    	
    	if(!dbc.updatePharmacy(pharmacy))
    		this.msg.add("Pharamcy updated successfully");
    	else
			this.errors.add("The Pharmacy cannot be updated");
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// Limpiar los mensajes que hubiera anteriormente
		if(!this.msg.isEmpty()) 	this.msg.clear();
		if(!this.errors.isEmpty()) 	this.errors.clear();
		
		String submit = request.getParameter("action");
		String redirect = "";
		
		switch(submit){
			case "insert":
				insert(request, response);
				redirect = "/pharmacys/management/pharmacy.jsp";
				break;
			case "edit":
				edit(request, response);
				redirect = "/pharmacys/management/pharmacy.jsp";
				break;
			case "location":
				editLocation(request, response);
				redirect = "/pharmacys/management/location.jsp";
				break;
			
			default:
				request.getSession().setAttribute("msg", "Something was wrong");
				break;
		}
		request.getSession().setAttribute("msg", this.msg);
		request.getSession().setAttribute("errors", this.errors);		
		response.sendRedirect(redirect);		
	}
}
