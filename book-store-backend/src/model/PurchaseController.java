package model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import bean.AddressBean;
import bean.BookBean;
import bean.PurchaseItemBean;
import bean.PurchaseOrderBean;
import model.dao.AddressDAO;
import model.dao.BookDAO;
import model.dao.CustomerDAO;
import model.dao.PurchaseDAO;
import model.dao.UserDAO;
import util.InputValidation;
import util.RestApiHelper;

public class PurchaseController {
	private static PurchaseController instance;
	private UserDAO userDao;
	private CustomerDAO customerDao;
	private PurchaseDAO purchaseDao;
	private AddressDAO addressDao;
	private BookDAO bookDao;
	
	private PurchaseController() {}
	
	public static PurchaseController getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new PurchaseController();
			instance.userDao = new UserDAO();
			instance.customerDao = new CustomerDAO();
			instance.purchaseDao = new PurchaseDAO();
			instance.addressDao = new AddressDAO();
			instance.bookDao = new BookDAO();
		}
		return instance;
	}
	
	
	public String createPurchaseOrder (String purchaseInfo) throws Exception {
		if (InputValidation.emptyInput(purchaseInfo)) {
			System.out.println("ERROR: Missing purchase information");
			return RestApiHelper.prepareErrorJson("Missing purchase information");
		}
		
		// Parse JSON content in request
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(purchaseInfo);
		System.out.println(json);
		
		// Name of Visitor/Customer
		String lname = json.get("lname").toString();
		String fname = json.get("fname").toString();
		
		
		if (InputValidation.emptyInput(lname) || InputValidation.emptyInput(fname)) {
			System.out.println("ERROR: First name or last name must not be empty");
			return RestApiHelper.prepareErrorJson("First name or last name must not be empty");
		} else if (!InputValidation.isCapitalized(lname) || !InputValidation.isCapitalized(fname)) {
			System.out.println("ERROR: First name or last name not capitalized");
			return RestApiHelper.prepareErrorJson("First name or last name not capitalized");
		} 
		
		// Address
		String addressInfo = json.get("address").toString();
		JSONObject jsonAddress = (JSONObject) parser.parse(addressInfo);
		
		String street = jsonAddress.get("street").toString();
		String province_state = jsonAddress.get("province_state").toString();
		String country = jsonAddress.get("country").toString();
		String zip = jsonAddress.get("zip").toString();
		String phone = jsonAddress.get("phone").toString();
		
		// Check address input
		if (InputValidation.emptyInput(street) || InputValidation.emptyInput(province_state) || InputValidation.emptyInput(country)
				|| InputValidation.emptyInput(zip) || InputValidation.emptyInput(phone)) {
			System.out.println("ERROR: Missing address information");
			return RestApiHelper.prepareErrorJson("Missing address information");
		} else if (!InputValidation.isAlpha(province_state)) {
			System.out.println("ERROR: Province/state must be alphabetical");
			return RestApiHelper.prepareErrorJson("Province/state must be alphabetical");
		} else if (!InputValidation.isAlphaMultiWord(country) || !InputValidation.isCapitalized(country) ) {
			System.out.println("ERROR: Country must be alphabetical and first letter capitalized");
			return RestApiHelper.prepareErrorJson("Country must be alphabetical and first letter capitalized");
		} else if (InputValidation.invalidPhoneNum(phone)) {
			System.out.println("ERROR: Phone number must be in format xxx-xxx-xxxx where all x are numerical.");
			return RestApiHelper.prepareErrorJson("Phone number must be in format xxx-xxx-xxxx where all x are numerical.");
		} 		
		
		
		// If address with same street and zip does NOT exist in DB, create new address
		if (!AddressController.getInstance().checkExistingAddress(street, zip)) {
			AddressController.getInstance().createNewAddress(addressInfo);
		} 
		
		// Retrieve the address by street and zip
		AddressBean addr = this.addressDao.retrieveAddressByStreetAndZip(street, zip);
		if (addr == null) {
			System.out.println("ERROR: Expected to retrieve address, but returned null.");
			return RestApiHelper.prepareErrorJson("Expected to retrieve address, but returned null.");
		}
		
		// get address id from queried address above
		String address_id = addr.getId();
		System.out.println("address_id: " + address_id);
		
		// create a unique order id
		String poID = this.createUniqueOrderId();
		System.out.println("New PO_id: " + poID);
		
		//PO Items
		Set <PurchaseItemBean> poItemsSet = new HashSet<>();
		JSONArray jsonPurchaseOrder = (JSONArray) json.get("purchaseOrderItems");
		if (jsonPurchaseOrder == null) {
			System.out.println("ERROR: List of purchase order items must not be empty");
			return RestApiHelper.prepareErrorJson("List of purchase order items must not be empty");
		}
		System.out.println("purchaseOrder: " + jsonPurchaseOrder);
		
		// calculate subtotal while traversing through purchase order items
		double orderSubtotal = 0;
		
		// iterate through PO items in the JSON array, check for errors, and if valid, add to poItemsSet
		for (int i=0; i < jsonPurchaseOrder.size(); i++) {
			JSONObject jsonPOItem = (JSONObject) jsonPurchaseOrder.get(i);
			System.out.println(jsonPOItem);
			
			// get "quantity" and check if valid number
		    String quantityStr = jsonPOItem.get("quantity").toString();
		    if (!InputValidation.isNumerical(quantityStr)) {
				System.out.println("ERROR: Quantity must be numerical.");
				return RestApiHelper.prepareErrorJson("Quantity must be numerical.");
		    } else if (Integer.parseInt(quantityStr) <= 0) {
				System.out.println("ERROR: Quantity must be positve number.");
				return RestApiHelper.prepareErrorJson("Quantity must be positve number.");
		    }
		    
		    // get "bid" and check if book exists
		    String bid = jsonPOItem.get("bid").toString();
		    BookBean book = this.bookDao.retrieveBookById(bid);
		    if (book == null) {
				System.out.println("ERROR: Book with bid " + bid + " does not exist");
				return RestApiHelper.prepareErrorJson("Book with bid " + bid + " does not exist");
		    } 
		    
		    // After check valid quantity and if book exists so we can get price, we calculate subtotal
		    int quantity = Integer.parseInt(quantityStr); 
		    double price = book.getPrice();
		    orderSubtotal += quantity * price;
		    System.out.println("  quantity: " + quantity + " , price: " + price);
		    System.out.println("  current orderSubtotal: " + orderSubtotal);
		    
		    // Create purchaseItemBean and add to set
		    PurchaseItemBean poItem = new PurchaseItemBean(poID, bid, price, quantity);
		    poItemsSet.add(poItem);   
		}
		
		// Calculate order costs and round to 2 decimal places
		orderSubtotal =  Math.round( 100.0 * (orderSubtotal * 0.13) ) / 100.0;
		double orderTax =  Math.round( 100.0 * (orderSubtotal * 0.13) ) / 100.0;
		double orderTotal = Math.round( 100.0 * (orderSubtotal * 1.13) ) / 100.0;
		
		// Set purchase status
		String status = "ORDERED";
		
		// Get current date
		Date poDate = new Date(Calendar.getInstance().getTime().getTime());
		System.out.println("purchase date: " + poDate);
		
		PurchaseOrderBean purchaseOrder = new PurchaseOrderBean(poID, lname, fname, status, address_id, poDate, poItemsSet);
		// Insert purchaseOrder to PO table in DB
	    int insertedPurchaseOrderRow = this.purchaseDao.insertPurchaseOrder(poID, lname, fname, status, address_id, poDate);
	    if (insertedPurchaseOrderRow != 1) {
	    	System.out.println("ERROR: Problem with inserting purchase order for " + lname);
			return RestApiHelper.prepareErrorJson("Problem with inserting purchase order for " + lname);
	    }
		
	    // insert poItems to POItem table in DB
	    // Note: POItem insertion must come after inserting the Address (if it didn't exist) 
	    // 		and the PO in the DB since POItem references these tables
	    for (PurchaseItemBean p : poItemsSet) {
		    // Insert purchaseItem to POItem table in DB
		    int insertedPOitemRow = this.purchaseDao.insertPurchaseItem(p.getOrder_id(), p.getBid(), p.getPrice(), p.getQuantity());
		    if (insertedPOitemRow != 1) {
		    	System.out.println("ERROR: Problem with inserting purchase item for bid " + p.getBid() + " and poID " + p.getOrder_id());
				return RestApiHelper.prepareErrorJson("Problem with inserting purchase item for bid " + p.getBid() + " and poID " + p.getOrder_id());
		    }
	    }
	    
		// Prepare successful response content if no errors
		JSONObject respContent = new JSONObject();
		respContent.put("successful", "true");
		respContent.put("message", "Successful purchase order.");
		
		JSONObject orderInfo = new JSONObject();
		orderInfo.put("order_id", poID);
		orderInfo.put("status", status);
		orderInfo.put("address_id", address_id);
		orderInfo.put("purchase_date", poDate.toString());
		respContent.put("order_info", orderInfo);
		
		JSONObject orderCost = new JSONObject();
		orderCost.put("subtotal", orderSubtotal);
		orderCost.put("tax", orderTax);
		orderCost.put("taxRate", "13%");
		orderCost.put("total", orderTotal);
		respContent.put("order_cost", orderCost);
		
		System.out.println(respContent);
		return RestApiHelper.prepareResultJson(respContent);
	}
	
	public String createUniqueOrderId () {
		try {
			String maxPO_id = this.purchaseDao.getMaxPOId();
			// If there exist no address in DB, therefore no max address id either in DB
			// then start from "address-0" + 1 for address_id
			if (maxPO_id == null) {
				maxPO_id = "order-1000000";
			}
			int idNum = Integer.parseInt(maxPO_id.replace("order-", ""));
			idNum += 1;
			return "order-" + idNum;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
