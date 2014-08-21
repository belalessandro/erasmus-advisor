package it.unipd.dei.bding.erasmusadvisor.beans;

import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email;

	private String password;

	private String salt;

	private UserType type;

	public UserBean() {
		type = UserType.STUDENTE;
	}

	public UserBean(UserBean user, int type) {
		this.email = user.email;
		this.password = user.password;
		this.salt = user.salt;

		if (type == 0) {
			this.type = UserType.STUDENTE;
		} else if (type == 1) {
			this.type = UserType.RESPONSABILE;
		} else {
			this.type = UserType.COORDINATORE;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashedPassword() {
		return password;
	}

	public UserType getType() {
		return type;
	}

	public void setHashedPassword(String hashedPassword) {
		this.password = hashedPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isEmailValid() {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	public boolean checkPassword(String pass) throws IllegalStateException {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			String salted = pass + salt;
			try {
				byte[] hash = digest.digest(salted.getBytes("UTF-8"));
				if (password.compareTo(new String(hash, "UTF-8")) == 0) {
					return true;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new IllegalStateException();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
		return false;
	}
}
