package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnector;
import model.UserRefinedAbstraction;
import util.DateUtil;
import util.SHA512;
import util.SendEmailUsingGMAILSMTP;
import util.ServerConfig;

@WebServlet("/login")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DBConnector dbc;
	private List<String> msg;
	private List<String> errors;
	private String redirect;

    public Login() {
        super();
        this.dbc = new DBConnector();
        this.msg = new ArrayList<String>();
        this.errors = new ArrayList<String>();
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
        	password = SHA512.hashText(password);
        }
        catch(Exception e) {
        	e.getStackTrace();
        }
        
        UserRefinedAbstraction user = dbc.getUserAdminByEmailPassword(email, password);
        
        if (user != null) {
            request.getSession().setAttribute("user", user.getName());
            request.getSession().setAttribute("userEmail", user.getEmail());		            
            request.getSession().setAttribute("cif", user.getCifPharmacy());
            
            this.redirect = "/pharmacys/management/pharmacy.jsp";
        }
        else {
        	this.errors.add("Unknown user, please try again");
            this.redirect = "/pharmacys/index.jsp";
        }
    }
    
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String email, name, surname, currentPassword, newPassword;
    	
    	email = (String) request.getSession().getAttribute("userEmail");
    	name = request.getParameter("editName");
    	surname = request.getParameter("editSurname");
    	currentPassword = request.getParameter("editCurrentPassword");
    	newPassword = request.getParameter("editNewPassword");
    	
    	UserRefinedAbstraction user = dbc.getUserById(email);
    	if(email != null && !email.equals(null) && email != ""){    		
    		
    		if(name != null && !name.equals(null) && name != "")
    			user.setName(name);
    		
    		if(surname != null && !surname.equals(null) && surname != "")
    			user.setSurname(surname);
    		
    		if(currentPassword != null && !currentPassword.equals(null) && currentPassword != ""){
    			if(newPassword != null && !newPassword.equals(null) && newPassword != ""){
    				
    				// las contraseñas coinciden
    				if(currentPassword.equals(newPassword)){
    					try {
    			        	newPassword = SHA512.hashText(newPassword);
    			        }
    			        catch(Exception e) {
    			        	e.getStackTrace();
    			        }
    					
    					user.setPassword(newPassword);
    				}
    				else {
    					this.errors.add("Password mismatch");
    				}
    			}
    		}
    		
    		if(!dbc.updateUser(user))
    			this.msg.add("User has been updated successfully");
    		else
    			this.errors.add("The user cannot be updated");
    	}
    }
    
    private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String newPassword, repeatPassword, email;
    	newPassword = request.getParameter("newPassword");
    	repeatPassword = request.getParameter("repeatPassword");
    	email = (String) request.getSession().getAttribute("userEmail");
    	
    	if(email != null){
    		if(newPassword.equals(repeatPassword)){
    			UserRefinedAbstraction user = dbc.getUserById(email);
    			
    			if(user != null){
    				try {
			        	newPassword = SHA512.hashText(newPassword);
			        }
			        catch(Exception e) {
			        	e.getStackTrace();
			        }
    				user.setPassword(newPassword);
    				user.setResetHash("");
    				
    				this.redirect = "/pharmacys/index.jsp";
    				if(!dbc.updateUser(user))
    					this.msg.add("User changed his password successfully");
    				else
    					this.errors.add("Error produced when user tried to change his password");    			
    			}
    		}
    	}
    }
    
    private void forgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String email = request.getParameter("forgotEmail");
    	
    	if(email != null){
    		UserRefinedAbstraction user = dbc.getUserById(email);
    		
    		if(user != null){
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
    				user.setResetHash(resetHash);
    					
    				if(!dbc.updateUser(user)){
    					System.out.println(email+" requested to reset his password");
    						
    					SendEmailUsingGMAILSMTP smtp = new SendEmailUsingGMAILSMTP();
    						
    					String link = "http://"+ServerConfig.server+":8080/pharmacys/login?action=resetPassword&hash="+resetHash;
    					String msgContent = "Please clic on the next link to reset your password: "+link;
    					smtp.setContent(msgContent);
    					smtp.setRecipient(email);
    					    					
    					if(smtp.send()){
    						this.msg.add("We have sent a email with the reset link");
    					}
    					else {
    						this.errors.add("An error occurred when we tried to send mail to");
    					}
    				}
    				else
    					this.errors.add(email+" requested to reset his password but failed");    						
    			}
    		}
        	else
        		this.errors.add("Unknow user email");
    	}
    	
    	this.redirect = "/pharmacys/index.jsp";
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// Limpiar los mensajes que hubiera anteriormente
		if(!this.msg.isEmpty()) 	this.msg.clear();
		if(!this.errors.isEmpty()) 	this.errors.clear();
		
		String action  = request.getParameter("action");
		
		switch(action){
			case "logout":				
				request.getSession().invalidate();
				this.redirect = "/pharmacys/index.jsp";
				//request.getRequestDispatcher("/index.jsp").forward(request, response);
				break;
			
			case "resetPassword":
				String hash = request.getParameter("hash");
				if(hash != null){
					
					UserRefinedAbstraction user = dbc.getUserByResetHash(hash);
					
					if(user != null){
						this.redirect = "/pharmacys/resetPassword.jsp";
						request.getSession().setAttribute("userEmail", user.getEmail());						
					}
					else {
						System.out.println("hash "+ hash + " received but cant find user ");
					}					
				}
				break;
			
			default:
				System.out.println("Something was wrong");
				break;
		}
		request.getSession().setAttribute("msg", this.msg);
		request.getSession().setAttribute("errors", this.errors);
		response.sendRedirect(this.redirect);
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// Limpiar los mensajes que hubiera anteriormente
		if(!this.msg.isEmpty()) 	this.msg.clear();
		if(!this.errors.isEmpty()) 	this.errors.clear();
				
		String submit = request.getParameter("action");
		
		redirect = "";
		
		switch(submit){
			case "login":
				login(request, response);
				break;
			
			case "edit":
				edit(request, response);
				this.redirect = "http://localhost:8080/pharmacys/management/account.jsp";
				break;
				
			case "logout":
				request.getSession().invalidate();
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				break;
				
			case "reset":
				resetPassword(request, response);
				break;
				
			case "forgotPassword":
				forgotPassword(request, response);
				break;
			default:
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				break;
		}
		request.getSession().setAttribute("msg", this.msg);
		request.getSession().setAttribute("errors", this.errors);
		response.sendRedirect(this.redirect);
    }
}
