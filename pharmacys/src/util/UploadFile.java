package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class UploadFile {
	
	 private static String getSubmittedFileName(Part part) {
		 for (String cd : part.getHeader("content-disposition").split(";")) {
			 if (cd.trim().startsWith("filename")) {
				 String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	             return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	         }
	     }
		 return null;
	 }
	 
	 public static String upload(HttpServletRequest request, HttpServletResponse response, String inputName) throws ServletException, IOException {
		 response.setContentType("text/html;charset=UTF-8");
		 String result = "";
		 
		// Create path components to save the file
		String path = ServerConfig.dataDirectory;
		Part filePart = request.getPart(inputName);
		String fileName;
		
		if(filePart != null){
			
			fileName = getSubmittedFileName(filePart);
	
			OutputStream out = null;
		    InputStream filecontent = null;
		    
		    try {
		        out = new FileOutputStream(new File(path + File.separator + fileName));
		        filecontent = filePart.getInputStream();
	
		        int read = 0;
		        final byte[] bytes = new byte[1024];
	
		        while ((read = filecontent.read(bytes)) != -1) {
		            out.write(bytes, 0, read);
		        }
		        
		        System.out.println("New file " + fileName + " created at " + path);
		        result = path+"/"+fileName;
		        
		    } catch (FileNotFoundException fne) {
		        System.out.println("You either did not specify a file to upload or are "
		                + "trying to upload a file to a protected or nonexistent "
		                + "location.");
		        System.out.println("ERROR: " + fne.getMessage());	        
		    } finally {
		        if (out != null) {
		            out.close();
		        }
		        if (filecontent != null) {
		            filecontent.close();
		        }
		    }
	 }
	 
		return result;
	 }
}
