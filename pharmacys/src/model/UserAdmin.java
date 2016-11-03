package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import util.SHA512;
import util.SendEmailUsingGMAILSMTP;

public class UserAdmin implements UserImplementator {
	
	@Override
	public boolean sendResetMail() {
		
		// Obtenemos el dia actual, hora minutos y segundos
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String resetHash = sdf.format(cal.getTime());
        
        // Encriptamos el tiempo actual
        try {
        	resetHash = SHA512.hashText(resetHash);
        	
        	// Guardamos el resetHash en la tupla de usuario, para que se compruebe cuando
        	// haga clic en el enlace
        	
        }
        catch(Exception e){
        	e.getStackTrace();
        }
        
        String message = "Haga clic en el siguiente enlace para reestablecer su contraseña, y procure anotarla en un sitio seguro: ";
        message = message + "http://localhost:8080/pharmacys/reset/" + resetHash;
        
        SendEmailUsingGMAILSMTP mail = new SendEmailUsingGMAILSMTP();
        mail.setRecipient("burial92@gmail.com");
        mail.setContent(message);        
        
		return mail.send();
	}

	@Override
	public boolean sendVerificationMail() {
		
		// Obtenemos el dia actual, hora minutos y segundos
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String verificationHash = sdf.format(cal.getTime());
		        
		// Encriptamos el tiempo actual
		try {
			verificationHash = SHA512.hashText(verificationHash);
        	
			// Guardamos el resetHash en la tupla de usuario, para que se compruebe cuando
        	// haga clic en el enlace			
		}
		catch(Exception e){
		   	e.getStackTrace();
		}
		
		String message = "Haga clic en el siguiente enlace para verificar su cuenta de administrador: ";
		message = message + "http://localhost:8080/pharmacys/verify/" + verificationHash;
		        
		SendEmailUsingGMAILSMTP mail = new SendEmailUsingGMAILSMTP();
		mail.setRecipient("burial92@gmail.com");
		mail.setContent(message);        
		        
		return mail.send();
	}

}
