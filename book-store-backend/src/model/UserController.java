package model;

import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import bean.UserBean;
import model.dao.CustomerDAO;
import model.dao.UserDAO;
import util.InputValidation;
import util.RestApiHelper;

public class UserController {
	private static UserController instance;
	private UserDAO userDao;
	private CustomerDAO customerDao;
	
	private UserController() {}
	
	public static UserController getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new UserController();
			instance.userDao = new UserDAO();
			instance.customerDao = new CustomerDAO();
		}
		return instance;
	}
	
	public String authenticateUser(String email, String password) throws Exception {
		// validate JSON object sent from request
		if (InputValidation.invalidEmail(email)) {
			System.out.println("ERROR: Invalid email format. " + email);
			return RestApiHelper.prepareErrorJson("Invalid email format. " + email);
		} else if (InputValidation.invalidPassword(password)) {
			System.out.println("ERROR: Password must be at least 8 characters long.");
			return RestApiHelper.prepareErrorJson("Password must be at least 8 characters long.");
		} else {
			UserBean user = this.userDao.retrieveUser(email);
			
			if (user == null) {
				System.out.println("ERROR: User does not exist");
				return RestApiHelper.prepareErrorJson("User does not exist");
			} else if (!password.equals(user.getPassword())){
				System.out.println("ERROR: Password is incorrect for email: " + email);
				return RestApiHelper.prepareErrorJson("Password is incorrect for email: " + email);
			} else {
				// setup response content
				JSONObject respContent = new JSONObject();
				respContent.put("successful", "true");
				respContent.put("message", "Successful login.");
				respContent.put("uid", user.getUid());
				respContent.put("lname", user.getLname());
				respContent.put("fname", user.getFname());
				respContent.put("email", user.getEmail());
				respContent.put("user_type", user.getUser_type());
				
				return RestApiHelper.prepareResultJson(respContent);
			}
		}
	}
	
	
	public String createNewUser(String userInfo) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(userInfo);
		
		String email = json.get("email").toString();
		String password = json.get("password").toString();
		String user_type = json.get("type").toString();
		String fname = json.get("fname").toString();
		String lname = json.get("lname").toString();
		
		if (InputValidation.invalidEmail(email)) {
			System.out.println("ERROR: Invalid email format. " + email);
			return RestApiHelper.prepareErrorJson("Invalid email format. " + email);
		} else if (this.userDao.existingUserWithEmail(email)) {
			System.out.println("ERROR: User with email <" + email + "> already exists.");
			return RestApiHelper.prepareErrorJson("User with email <" + email + "> already exists.");
		} else if (InputValidation.invalidPassword(password)) {
			System.out.println("ERROR: Password must be at least 8 characters long.");
			return RestApiHelper.prepareErrorJson("Password must be at least 8 characters long.");
		} else if (InputValidation.invalidUserType(user_type)) {
			System.out.println("ERROR: User type must be CUSTOMER, ADMIN or PARTNER.");
			return RestApiHelper.prepareErrorJson("User type must be CUSTOMER, ADMIN or PARTNER.");
		} else if (InputValidation.invalidName(fname, lname)) {
			System.out.println("ERROR: First and last name must be alpabetical and first letter capitalized.");
			return RestApiHelper.prepareErrorJson("First and last name must be alpabetical and first letter capitalized.");
		} else if (user_type.equals("CUSTOMER")) {
			// Check if user is customer and attempting to register with an already existing address.
			String street = ((JSONObject) json.get("address")).get("street").toString();
			String zip = ((JSONObject) json.get("address")).get("zip").toString();
			System.out.println("Check if address already exists with: " + street + ", " + zip);
			if (AddressController.getInstance().checkExistingAddress(street, zip)) {
				System.out.println("ERROR: Customer attempting to register with existing address.");
				return RestApiHelper.prepareErrorJson("Customer attempting to register with existing address.");
			}
		}

		
		JSONObject respContent = new JSONObject();
		int uid = this.userDao.getMaxUid() + 1; // get unique uid
		
		int insertedUserRow = this.userDao.insertUser(uid, lname, fname, email, password, user_type);
		// if 0, no insertion occurred. if 1, it was successful
		if (insertedUserRow == 1) {
			// setup response content
			UserBean user = this.userDao.retrieveUser(email);
			respContent.put("successful", "true");
			respContent.put("message", "Successful user registration.");
			respContent.put("uid", user.getUid());
			respContent.put("email", user.getEmail());
			respContent.put("user_type", user.getUser_type());
			
			// Only create new Address and CustomerAccount for CUSTOMER
			if (user_type.equals("CUSTOMER")) {
				// create Address by calling AddressController
				String strAddressInfo = json.get("address").toString();
				System.out.println("Provided address info: " + strAddressInfo);
				
				String createAddrResponse = AddressController.getInstance().createNewAddress(strAddressInfo);
				JSONObject jsonCreateAddrResponse = (JSONObject) ((JSONObject) parser.parse(createAddrResponse)).get("result");
				
				// check if creating address is successful or not
				if (jsonCreateAddrResponse.get("successful").equals("false")) {
					// returns the error response from the prepareErrorJson called by createNewAddress
					return createAddrResponse;
				} else {
					// create CustomerAccount with UserAccount.uid and Address.id
					String address_id = jsonCreateAddrResponse.get("address_id").toString();
					int insertedCustomerRow = this.customerDao.insertCustomer(uid, address_id);
					
					// if 0, no insertion occurred. if 1, it was successful
					if (insertedCustomerRow == 1) {
						// add address_id to response content
						respContent.put("address_id", address_id);
					} else {
						return RestApiHelper.prepareErrorJson("New customer was not inserted into DB");
					}
				}
			}
			
			//if no errors, return the final response content
			return RestApiHelper.prepareResultJson(respContent);
		} else {
			return RestApiHelper.prepareErrorJson("New user was not inserted into DB");
		}

	}

	
}