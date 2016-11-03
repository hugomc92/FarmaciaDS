package com.hugoroman.pharmacys.util;

import java.security.MessageDigest;

public final class SHA512 {

    public static String convertByteToHex(byte data[]){
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));
        
        return hexData.toString();
    }
    
    public static String hashText(String textToHash) throws Exception {
        final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
        sha512.update(textToHash.getBytes());
        
        return convertByteToHex(sha512.digest());
    }
}

