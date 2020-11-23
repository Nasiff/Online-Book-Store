package model;

import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import bean.UserBean;
import model.dao.UserDAO;
import util.InputValidation;
import util.RestApiHelper;

public class UserController {
	private static UserController instance;
	private UserDAO userDao;
	
	private UserController() {}
	
	public static UserController getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new UserController();
			instance.userDao = new UserDAO();
		}
		return instance;
	}
	
	public String authenticateUser(String email, String password) throws Exception {
		// validate JSON object sent from request
		if (InputValidation.invalidEmail(email)) {
			System.out.println("ERROR: Invalid email format. " + email);
			return "ERROR: Invalid email format. " + email;
		} else if (InputValidation.invalidPassword(password)) {
			System.out.println("ERROR: Password must be at least 8 characters long.");
			return "ERROR: Password must be at least 8 characters long.";
		} else {
			UserBean user = this.userDao.retrieveUser(email, password);
			
			if (user != null) {
				// setup response content
				JSONObject respContent = new JSONObject();
				respContent.put("uid", user.getUid());
				respContent.put("lname", user.getLname());
				respContent.put("fname", user.getFname());
				respContent.put("email", user.getEmail());
				respContent.put("user_type", user.getUser_type());
				
				return RestApiHelper.prepareResultJson(respContent);
			} else {
				return "ERROR: User does not exist";
			}
		}
	}
	
	
	public String createNewUser(String userInfo) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(userInfo);
		
		String email = json.get("email").toString();
		String password =  json.get("password").toString();
		String user_type =  json.get("type").toString();
		String fname =  json.get("fname").toString();
		String lname =  json.get("lname").toString();
		
		if (InputValidation.invalidEmail(email)) {
			System.out.println("ERROR: Invalid email format. " + email);
			return "ERROR: Invalid email format. " + email;
		} else if (this.userDao.existingUserWithEmail(email)) {
			System.out.println("ERROR: User with email <" + email + "> already exists.");
			return "ERROR: User with email <" + email + "> already exists.";
		} else if (InputValidation.invalidPassword(password)) {
			System.out.println("ERROR: Password must be at least 8 characters long.");
			return "ERROR: Password must be at least 8 characters long.";
		} else if (InputValidation.invalidUserType(user_type)) {
			System.out.println("ERROR: User type must be CUSTOMER, ADMIN or PARTNER.");
			return "ERROR: User type must be CUSTOMER, ADMIN or PARTNER.";
		} else if (InputValidation.invalidName(fname, lname)) {
			System.out.println("ERROR: First and last name must be alpabetical and first letter capitalized.");
			return "ERROR: First and last name must be alpabetical and first letter capitalized.";
		} 		

		JSONObject respContent = new JSONObject();
		int uid = this.userDao.getMaxUid() + 1;
		int insertedRow = this.userDao.insertUser(uid, lname, fname, email, password, user_type);
		// if 0, no insertion occured. if 1, it was successful
		if (insertedRow == 1) {
			// setup response content
			UserBean user = this.userDao.retrieveUser(email, password);
			
			respContent.put("uid", user.getUid());
			respContent.put("email", user.getEmail());
			respContent.put("user_type", user.getUser_type());
			
			// Only create new Address and CustomerAccount for CUSTOMER
			if (user_type.equals("CUSTOMER")) {
				// NOT DONE YET
				// create Address
				// will call AddressService for this
				
				// create CustomerAccount with UserAccount.uid and Address.id
				
			}
			return RestApiHelper.prepareResultJson(respContent);
		} else {
			return "ERROR: New user was not inserted into DB";
		}

	}

	
}
