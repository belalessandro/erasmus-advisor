package it.unipd.dei.bding.erasmusadvisor.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.postgresql.util.Base64;

/**
 * Test class
 *
 */
public class TestBase64 {
	
	public static String password = "5Ow3X1vSOMwqfjEfKeQVPrKmjPbu1kVDsLKiLL3V3NQ=";
	public static String salt = "123456";

	public static void main(String[] args) {
		
		
		System.out.println( hashPassword("provààààààààààààààààààòè+~", "123456") );
		System.out.println( "" + checkPassword("provààààààààààààààààààòè+~") );
	}
	
	/**
	 * Method used for hash a particular password with the salt given.
	 * 
	 * @param password 
	 *                              password to hash
	 * @param salt 
	 *                              salt
	 * @return the hashed password
	 */
	private static String hashPassword(String password, String salt) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			String salted = password + salt;
			try {
				byte[] hash = digest.digest(salted.getBytes("UTF-8"));
				String base64 = Base64.encodeBytes(hash);
			    return base64;
			} 
			catch (UnsupportedEncodingException e) {
				throw new IllegalStateException();
			}
		} 
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException();
		}
	}

	
	/**
	 * Checks if the password is correct
	 * @param pass The password
	 * @return true if the password is correct
	 * @throws IllegalStateException if there is a problem with the encoding
	 */
	public static boolean checkPassword(String pass) throws IllegalStateException {
		MessageDigest digest;
		try {
			
			digest = MessageDigest.getInstance("SHA-256");
			String salted = pass + salt;
			try {
				byte[] hash = digest.digest(salted.getBytes("UTF-8"));

				String base64 = Base64.encodeBytes(hash);
				
				if (password.compareTo(base64) == 0) {
					return true;
				}
			} 
			catch (UnsupportedEncodingException e) 
			{
				throw new IllegalStateException();
			}
		} catch (NoSuchAlgorithmException e) 
		{
			throw new IllegalStateException();
		}
		return false;
	}
}
