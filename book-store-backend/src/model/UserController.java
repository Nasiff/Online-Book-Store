package model;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import bean.BookSalesBean;
import bean.PurchaseItemBean;
import bean.UserBean;
import model.dao.BookDAO;
import model.dao.CustomerDAO;
import model.dao.PurchaseDAO;
import model.dao.UserDAO;
import util.InputValidation;
import util.RestApiHelper;

public class UserController {
	private static UserController instance;
	private UserDAO userDao;
	private CustomerDAO customerDao;
	private PurchaseDAO purchaseDao;
	private BookDAO bookDAO;
	
	
	private UserController() {}
	
	public static UserController getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new UserController();
			instance.userDao = new UserDAO();
			instance.customerDao = new CustomerDAO();
			instance.purchaseDao = new PurchaseDAO();
			instance.bookDAO = new BookDAO();
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
				respContent.put("successful", true);
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
		} 
		
		/*
		//Commented out because we no longer need this condition in which
		else if (user_type.equals("CUSTOMER")) {
			// Check if user is customer and attempting to register with an already existing address.
			String street = ((JSONObject) json.get("address")).get("street").toString();
			String zip = ((JSONObject) json.get("address")).get("zip").toString();
			System.out.println("Check if address already exists with: " + street + ", " + zip);
			if (AddressController.getInstance().checkExistingAddress((street, province_state, country, zip, phone)) {
				System.out.println("ERROR: Customer attempting to register with existing address.");
				return RestApiHelper.prepareErrorJson("Customer attempting to register with existing address.");
			}
		} */
		
		
		JSONObject respContent = new JSONObject();
		int uid = this.userDao.getMaxUid() + 1; // get unique uid
		
		int insertedUserRow = this.userDao.insertUser(uid, lname, fname, email, password, user_type);
		// if 0, no insertion occurred. if 1, it was successful
		if (insertedUserRow == 1) {
			// setup response content
			UserBean user = this.userDao.retrieveUser(email);
			respContent.put("successful", true);
			respContent.put("message", "Successful user registration.");
			respContent.put("uid", user.getUid());
			respContent.put("email", user.getEmail());
			respContent.put("user_type", user.getUser_type());
			
			// Only create/return Address and create CustomerAccount for CUSTOMER
			if (user_type.equals("CUSTOMER")) {
				// create Address by calling AddressController
				String strAddressInfo = json.get("address").toString();
				System.out.println("Provided address info: " + strAddressInfo);
				
				String createAddrResponse = AddressController.getInstance().createOrReturnAddress(strAddressInfo);
				JSONObject jsonCreateAddrResponse = (JSONObject) ((JSONObject) parser.parse(createAddrResponse)).get("result");
				
				// check if creating address is successful or not
				if (jsonCreateAddrResponse.get("successful").equals(false)) {
					// returns the error response from the prepareErrorJson called by createOrReturnAddress
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

	
	public String userAuthentication(String uidStr, String userType) throws Exception {
		
		if (InputValidation.emptyInput(uidStr)) {
			System.out.println("ERROR: uid must not be empty.");
			return RestApiHelper.prepareErrorJson("uid must not be empty.");
		} else if (!InputValidation.isNumerical(uidStr)) {
			System.out.println("ERROR: uid must contain only numerical characters");
			return RestApiHelper.prepareErrorJson("uid must contain only numerical characters");
		}
		
		int uid = Integer.parseInt(uidStr);
		UserBean user = this.userDao.retrieveUserByUid(uid);
		
		if (user == null) {
			System.out.println("ERROR: user with uid " + uid + " does not exist");
			return RestApiHelper.prepareErrorJson("user with uid " + uid + " does not exist");
		} else if (!user.getUser_type().equals(userType)) {
			System.out.println("ERROR: user with uid " + uid + " is not " + userType);
			return RestApiHelper.prepareErrorJson("user with uid " + uid + " is not " + userType);
		}
		
		// if no errors
		return "valid";
	}
	
	
	public String topSellersJsonBuilder(List<BookSalesBean> topBookSales, String salesTimeFrame) throws Exception {
		JSONObject respContent = new JSONObject();
		JSONArray jsonArrayTopSellers = new JSONArray();
		
		for (BookSalesBean book : topBookSales) { 	
			JSONObject i = new JSONObject();
			i.put("rank", book.getRank());
			i.put("sales", book.getSales());
			i.put("bid", book.getBid());
			i.put("title", book.getTitle());
			i.put("author", book.getAuthor());
			i.put("category", book.getCategory());
			i.put("review_score", book.getReview_score());
			i.put("number_of_reviews", book.getNumber_of_reviews());
			jsonArrayTopSellers.add(i);
		}
		
		respContent.put(salesTimeFrame, jsonArrayTopSellers);
		respContent.put("successful", true);
		respContent.put("message", "Successful retrieval of current monthly book top sellers.");
		return RestApiHelper.prepareResultJson(respContent);
	}
	
	public String adminGetCurrentMonthlyTopSellers(String uidStr) throws Exception {

		String adminAuth = this.userAuthentication(uidStr, "ADMIN");
		if (!adminAuth.equals("valid")) {
			return adminAuth; // returns error from adminAuthentication
		}
		
		Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
		String currentMonth = currentDate.toString().substring(5, 7);
		System.out.println("currentMonth: " + currentMonth);
		
		List<BookSalesBean> topBookSales = this.purchaseDao.retrieveMonthlyTopSellers(currentMonth);

		return topSellersJsonBuilder(topBookSales, "current_month");
	}
	
	public String adminGetAllTimeTopSellers(String uidStr) throws Exception {

		String adminAuth = this.userAuthentication(uidStr, "ADMIN");
		if (!adminAuth.equals("valid")) {
			return adminAuth; // returns error from adminAuthentication
		}
		
		List<BookSalesBean> topBookSales = this.purchaseDao.retrieveAllTimeTopSellers();
		
		return topSellersJsonBuilder(topBookSales, "all_time");
	}
	
	
	public String partnerGetAllOrdersForBook(String uidStr, String bid) throws Exception {
		String partnerAuth = this.userAuthentication(uidStr, "PARTNER");
		if (!partnerAuth.equals("valid")) {
			return partnerAuth; // returns error from adminAuthentication
		} else if (this.bookDAO.retrieveBookById(bid) == null) {
			System.out.println("ERROR: book with bid " + bid + " does not exist");
			return RestApiHelper.prepareErrorJson("book with bid " + bid + " does not exist");
		}
		
		List<PurchaseItemBean> ordersForBook = this.purchaseDao.retieveAllOrdersForBook(bid);
		
		JSONObject respContent = new JSONObject();
		JSONArray jsonArrayOrders = new JSONArray();
		
		int totalQuantity = 0;
		
		for (PurchaseItemBean order : ordersForBook) { 	
			JSONObject i = new JSONObject();
			i.put("order_id", order.getOrder_id());
			i.put("quantity", order.getQuantity());
			jsonArrayOrders.add(i);
			
			totalQuantity += order.getQuantity();
		}
		
		respContent.put("orders", jsonArrayOrders);
		respContent.put("total_quantity", totalQuantity);
		respContent.put("bid", bid);
		
		respContent.put("successful", true);
		respContent.put("message", "Successful retrieval of all orders for book with bid " + bid);
		return RestApiHelper.prepareResultJson(respContent);
	}
	
	
	public String partnerGetProductInfo(String uidStr, String bid) throws Exception {
		String partnerAuth = this.userAuthentication(uidStr, "PARTNER");
		if (!partnerAuth.equals("valid")) {
			return partnerAuth; // returns error from adminAuthentication
		} else if (this.bookDAO.retrieveBookById(bid) == null) {
			System.out.println("ERROR: book with bid " + bid + " does not exist");
			return RestApiHelper.prepareErrorJson("book with bid " + bid + " does not exist");
		}
		
		return BookController.getInstance().retrieveBook(bid);
	}
	
}
