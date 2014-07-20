package uyjj.nicechat;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by juan on 7/18/14.
 */
public class Utils {

    public static byte[] SHA512(String toEncode) throws java.security.NoSuchAlgorithmException{

        if (toEncode == null)
            return null;
        try {
            return SHA512(toEncode.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e){
            return null;
        }

    }

    public static byte[] SHA512(byte[] toEncode) throws java.security.NoSuchAlgorithmException{

        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");

        return digest.digest(toEncode);
    }
}
