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
import model.Category;
import model.Inventory;
import model.Product;
import model.Reservation;
import util.DateUtil;
import util.SendEmailUsingGMAILSMTP;
import util.ServerConfig;
import util.TextParser;
import util.UploadFile;

@WebServlet("/product")
@MultipartConfig
public class SProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private DBConnector dbc;
	private List<String> msg;
	private List<String> errors;
	private String redirect;
	
    public SProduct() {
        super();
        this.dbc = new DBConnector();
        this.msg = new ArrayList<String>();
        this.errors = new ArrayList<String>();
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int category, size;
		String name, laboratory, units, expirationDate, lot, description;
		
		Product product;
		
		// get the input data
        name = request.getParameter("insertName");
        description = request.getParameter("insertDescr");
        laboratory = request.getParameter("insertLaboratory");
        units = request.getParameter("insertUnits"); 
        expirationDate = request.getParameter("insertExpDate");               
        size = Integer.parseInt(request.getParameter("insertSize"));
        lot = request.getParameter("insertLot");        
        category = Integer.parseInt(request.getParameter("insertCategory"));
        
        product = new Product();
		product.setName(TextParser.parseLatinToHTML(name));
		product.setDescription(TextParser.parseLatinToHTML(description));
		product.setLaboratory(TextParser.parseLatinToHTML(laboratory));
		product.setUnits(units);
		
		// PARSE EXPIRATION_DATE
     	java.sql.Date sqlDate = DateUtil.toSQLDate(expirationDate);
     	if(sqlDate != null){
     		product.setExpirationDate(sqlDate);
     	}
     	else
     		this.errors.add("Cant insert your expiration date");
     	
     	product.setSize(size);
		product.setLot(lot);
		
		// UPLOAD IMAGE
		String resultUploadImg = UploadFile.upload(request, response, "insertImg");
		if(resultUploadImg == ""){
			this.errors.add("Cant upload your image");
			product.setUrlImg("http://"+ServerConfig.server+":8080/pharmacys/img/img_no_aviable.png");
		}
		else{
			this.msg.add("Image uploaded successfully");
			product.setUrlImg(resultUploadImg.replace(ServerConfig.dataDirectory+"/", "http://"+ServerConfig.server+":8080/pharmacys/data/"));
		}
		
		Category c = dbc.getCategoryById(category);		
		if(c != null){
			System.out.println(c.getName());
			product.setCategory(c);
		}
		else {
			this.errors.add("Cant get the category data");
		}
		
		if(!dbc.insertProduct(product)){
			this.msg.add("Product inserted successfully");
			
			//	INSERT PRODUCT TO INVENTORY TABLE			
			Product lastProduct = dbc.getLastProductInserted();			
			if(lastProduct != null){
				Integer idProduct = lastProduct.getId();
				String cif = (String) request.getSession().getAttribute("cif");
				
				if(idProduct != null && cif != null){
					float price = Float.parseFloat(request.getParameter("insertPrice"));
					Integer stock = Integer.parseInt(request.getParameter("insertStock"));
					
					if(price > 0 && stock != null){
						Inventory i = new Inventory();						
						i.setPharmacyId(cif);
						i.setProductId(idProduct);
						i.setPrice(price);
						i.setStock(stock);
						i.setQueryCount(0);
						
						if(!dbc.insertInventory(i))
							this.msg.add("Product linked to your pharmacy successfully");
						else
							this.errors.add("Error when product tried to link to your pharmacy");
					}					
				}
			}			
		}
		else
			this.errors.add("The product cannot be inserted");
    }
       
    protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int productId;
    	productId = Integer.parseInt(request.getParameter("editId"));
    	
    	Product product = dbc.getProductById(productId);    	
    	if(product != null){
    		int size, categoryId;
        	String name, description, expirationDate, laboratory, units, lot;
        	
        	name = request.getParameter("editName");
        	description = request.getParameter("editDescr");        	     
        	laboratory = request.getParameter("editLaboratory");
        	units = request.getParameter("editUnits");
        	lot = request.getParameter("editLot");
        	size = Integer.parseInt(request.getParameter("editSize"));
        	expirationDate = request.getParameter("editExpDate");        	
        	categoryId = Integer.parseInt(request.getParameter("editCategory"));
        	
        	product.setName(name);
        	product.setDescription(description);
        	product.setLaboratory(laboratory);
        	product.setUnits(units);
        	product.setLot(lot);
        	product.setSize(size);
        	
        	// UPLOAD IMAGE
        	long imgSize = request.getPart("editImg").getSize(); 
        	if( imgSize > 0){
	    		String resultUploadImg = UploadFile.upload(request, response, "editImg");
	    		if(resultUploadImg == ""){
	    			this.errors.add("Cant upload your image");
	    			product.setUrlImg("http://"+ServerConfig.server+":8080/pharmacys/img/img_no_aviable.png");
	    		}
	    		else{
	    			this.msg.add("Image uploaded successfully");
	    			product.setUrlImg(resultUploadImg.replace(ServerConfig.dataDirectory+"/", "http://"+ServerConfig.server+":8080/pharmacys/data/"));
	    		}
        	}
        	else {
        		this.msg.add("No image selected");
        	}

    		// PARSE EXPIRATION_DATE
         	java.sql.Date sqlDate = DateUtil.toSQLDate(expirationDate);
         	if(sqlDate != null){
         		product.setExpirationDate(sqlDate);
         	}
         	else
         		this.errors.add("Cant insert your expiration date");
    		
        	Category c = dbc.getCategoryById(categoryId);		
    		if(c != null){
    			System.out.println(c.getName());
    			product.setCategory(c);
    		}
    		else {
    			this.errors.add("Cant get the category data");
    		}
    		
    		if(!dbc.updateProduct(product)){
    			this.msg.add("Product updated successfully");
    			
    			String cif = (String) request.getSession().getAttribute("cif");
    			if(cif != null){
    				float price = Float.parseFloat(request.getParameter("editPrice"));
        			Integer stock = Integer.parseInt(request.getParameter("editStock"));
        			
        			if(price > 0 && stock != null){
						Inventory i = dbc.getInventoryById(cif, productId);
						i.setPrice(price);
						i.setStock(stock);
						i.setQueryCount(i.getQueryCount()+1);
						
						if(!dbc.updateInventory(i)){
							this.msg.add("Inventory updated successfully");
														
							// COMPROBAR STOCK DE LOS USUARIOS QUE TENGAN PRODUCTOS RESERVADOS PARA ENVIARLES UN CORREO
							// INDICANDOLES QUE YA TIENEN LOS PRODUCTOS DISPONIBLES
							List<Reservation> reservationList = dbc.getAllReservationByCIFProductId(cif, productId);							
							if(reservationList != null){
								
								List<String> userEmails = new ArrayList<String>();
								for(Reservation r : reservationList){
									userEmails.add(r.getEmail());
								}
								
								if(userEmails != null){																																		
									for(String email : userEmails){
										String msgContent = "Hello "+email+" product booked " +product.getName()+" now is aviable in our application.";
										SendEmailUsingGMAILSMTP smtp = new SendEmailUsingGMAILSMTP();
										smtp.setContent(msgContent);
										smtp.setRecipient(email);
										smtp.send();
									}
								}
							}
						}
						else
							this.errors.add("Has failed to update the inventory");
					}
    			}    			    			
    		}
    		
    	}
    	else {
    		this.errors.add("The selected product dont exist");
    	}    	
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int id = Integer.parseInt(request.getParameter("deleteId"));
		String option = request.getParameter("deleteOption");
		Product product;
		
		if(option.equals("yes")){
			product = dbc.getProductById(id);
			
			if(product != null){
				// primero borro el registro de la tabla PHARMACY_PRODUCT
				String cif = (String) request.getSession().getAttribute("cif");
				Inventory inventory = dbc.getInventoryById(cif, id);
				
				if(inventory != null){
					if(!dbc.deleteInventory(inventory)){
						this.msg.add("Link beetween product and pharmacy deleted");
						
						// si todo ha ido bien procedo a eliminar el producto de esa farmacia
						if(!dbc.deleteProduct(product)){
							this.msg.add("Product deleted successfully");
						}	
						else
							this.errors.add("The product cannot be deleted");
					}	
					else {
						this.errors.add("Link beetween product and pharmacy couldnt be deleted");
					}
				}
			}
		}
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		// Limpiar los mensajes que hubiera anteriormente
		if(!this.msg.isEmpty()) 	this.msg.clear();
		if(!this.errors.isEmpty()) 	this.errors.clear();
		
		redirect = "";
		
		String submit = request.getParameter("actionProduct");
				
		switch(submit){
			case "insert":				
		    	insert(request, response);
				break;		
			case "edit":
				edit(request, response);
				break;			
			case "delete":
				delete(request, response);
				break;
				
			default:
				this.msg.add("Something was wrong");
				break;
		}
		this.redirect = "/pharmacys/management/product.jsp";
		request.getSession().setAttribute("msg", this.msg);
		request.getSession().setAttribute("errors", this.errors);
		response.sendRedirect(this.redirect);
	}
}
