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
import model.Reservation;

@WebServlet("/reservation")
public class SReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DBConnector dbc;
	private List<String> msg;
	private List<String> errors;
	private String redirect;
	
    public SReservation() {
        super();
        this.dbc = new DBConnector();
        this.msg = new ArrayList<String>();
        this.errors = new ArrayList<String>();
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cif = request.getParameter("deleteReservationCIF");
    	int productId = Integer.parseInt(request.getParameter("deleteReservationProductId"));
    	String email = request.getParameter("deleteReservationEmail");
		String option = request.getParameter("deleteOption");
		Reservation reservation;
		
		if(option.equals("yes")){
			reservation = dbc.getReservation(cif, productId, email);
			
			if(reservation != null){				
				if(!dbc.deleteReservation(reservation))
					this.msg.add("The reservation was deleted successfully");
				else
					this.errors.add("The reservation cant be deleted");				
			}
		}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	
    	// Limpiar los mensajes que hubiera anteriormente
		if(!this.msg.isEmpty()) 	this.msg.clear();
		if(!this.errors.isEmpty()) 	this.errors.clear();
		
		redirect = "";
		
		String submit = request.getParameter("actionReservation");
				
		switch(submit){		
			case "delete":
				delete(request, response);
				break;
				
			default:
				this.msg.add("Something was wrong");
				break;
		}
		this.redirect = "/pharmacys/management/reservation.jsp";
		request.getSession().setAttribute("msg", this.msg);
		request.getSession().setAttribute("errors", this.errors);
		response.sendRedirect(this.redirect);
	}

}
