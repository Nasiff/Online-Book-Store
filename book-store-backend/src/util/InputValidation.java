package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

public class InputValidation {
	
	public static boolean emptyInput(String s) {
		return (s == null || (s != null && s.toString().equals("")));
	}
	
	public static boolean isAlpha(String s) {
		return s != null && s.matches("^[a-zA-Z]*$");
	}
	
	public static boolean isNumerical(String s) {
		return s != null && s.matches("^[0-9]*$");
	}
	
	public static boolean isCapitalized(String s) {
		return s.charAt(0) == s.toUpperCase().charAt(0);
	}
	
	public static boolean invalidUid(String uid) {
		// if email not empty
		if (emptyInput(uid)) {
			return true;
		} else if (!isNumerical(uid)) {
			return true;
		} 
		return false;
	}
	
	public static boolean invalidEmail(String email) {
		// if email not empty
		if (emptyInput(email)) {
			return true;
		} else {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
			return !matcher.find(); // returns true if INVALID email pattern
		} 
	}
	
	public static boolean invalidName(String fname, String lname) {
		if (emptyInput(fname) || emptyInput(lname)) {
			return true;
		} else if (!isAlpha(fname) || !isAlpha(lname)) {
			return true;
		} else if (!isCapitalized(fname) || !isCapitalized(lname)) {
			return true;
		}
		return false;
	}
	
	public static boolean invalidPassword(String password) {
		if (emptyInput(password)) {
			return true;
		} else if (password.length() < 8) {
			return true;
		}
		return false;
	}
	
	public static boolean invalidUserType(String user_type) {
		if (emptyInput(user_type)) {
			return true;
		} else if (!isAlpha(user_type)) {
			return true;
		}  
		
		String user_typeCap = user_type.toUpperCase();
		if (!user_typeCap.equals("CUSTOMER") && !user_typeCap.equals("ADMIN") && !user_typeCap.equals("PARTNER")) {
			return true;
		}
		
		return false;
	}
	
	
}
