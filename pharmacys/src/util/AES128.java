package util;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public final class AES128 {
	
	private static String ALGO;
	private static byte[] keyValue;
	
	public AES128(){
		AES128.ALGO = "AES";
		// 16 * 8 bytes = 128 bytes de clave
		AES128.keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't','S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
	}
	
	private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
	}
	
	@SuppressWarnings("restriction")
	public static String encrypt(String data){
		String result = "";
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
	        
			byte[] encVal = c.doFinal(data.getBytes());
	        result = new BASE64Encoder().encode(encVal);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	@SuppressWarnings("restriction")
	public static String decrypt(String data){
		String result = "";
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
	        
			byte[] decodedValue = new BASE64Decoder().decodeBuffer(data);
	        byte[] decValue = c.doFinal(decodedValue);
	        
	        result = new String(decValue);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
}
