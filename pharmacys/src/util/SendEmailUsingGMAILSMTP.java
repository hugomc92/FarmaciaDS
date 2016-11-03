package util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailUsingGMAILSMTP {
	private String to;
	private String from;
	private String content;
	
	private final String username;
	private final String password;
	
	private String host;
	private Properties props;
	
	private Session session;
	
	public SendEmailUsingGMAILSMTP(){
		
		// Recipient
		this.to = "";
		
		// Sender information
		this.from = "pharmacysds@gmail.com";
		this.username = "pharmacysds@gmail.com";
		this.password = "123_abcd";
		this.host = "smtp.gmail.com";
		
		this.props = new Properties();
		this.props.put("mail.smtp.auth", "true");
		this.props.put("mail.smtp.starttls.enable", "true");
		this.props.put("mail.smtp.host", host);
		this.props.put("mail.smtp.port", "587");
		
		this.content = "";
		
		authenticate();
	}
	
	private void authenticate(){
		
	    // Get the Session object.
		this.session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
			    }
			}
		);
	}
	
	public void setRecipient(String to){
		this.to = to;
	}
	
	public void setContent(String s){
		this.content = s;
	}
	
	public boolean send(){
		
		boolean result = false;
		
		try {
			// Create a default MimeMessage object.
	        Message message = new MimeMessage(session);

	        // Set From: header field of the header.
	        message.setFrom(new InternetAddress(from));

	        // Set To: header field of the header.
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

	        // Set Subject: header field
	        message.setSubject("Testing Subject");

	        // Now set the actual message
	        message.setText(content);

	        // Send message
	        Transport.send(message);

	        result = true;

		}
		catch (MessagingException e) {
			throw new RuntimeException(e);
	    }
		
		return result;
	}
}
