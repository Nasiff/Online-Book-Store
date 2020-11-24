package model;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import bean.AddressBean;
import bean.UserBean;
import model.dao.AddressDAO;
import util.InputValidation;
import util.RestApiHelper;

public class AddressController {
	private static AddressController instance;
	private AddressDAO addressDao;
	
	private AddressController() {}
	
	public static AddressController getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new AddressController();
			instance.addressDao = new AddressDAO();
		}
		return instance;
	}

	public String getAddressByUid(String uid) throws Exception {
		if (InputValidation.invalidUid(uid)) {
			System.out.println("ERROR: uid must be numerical only.");
			return "ERROR: uid must be numerical only.";
		} else {
			AddressBean address = this.addressDao.retrieveAddressByUid(uid);
			if (address != null) {
				// setup response content
				JSONObject respContent = new JSONObject();
				respContent.put("address_id", address.getId());
				respContent.put("street", address.getStreet());
				respContent.put("province_state", address.getProvince_state());
				respContent.put("country", address.getCountry());
				respContent.put("zip", address.getZip());
				respContent.put("phone", address.getPhone());
				
				return RestApiHelper.prepareResultJson(respContent);
			} else {
				System.out.println("ERROR: Customer does not exist");
				return RestApiHelper.prepareErrorJson("Customer does not exist");
			}
		}
	}
	
	public boolean checkExistingAddress(String street, String zip) throws Exception {
		if (InputValidation.emptyInput(street) || InputValidation.emptyInput(zip)) {
			System.out.println("ERROR: Missing address information");
			return false;
		}  else {
			AddressBean address = this.addressDao.retrieveAddressByStreetAndZip(street, zip);
			if (address != null) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public String createUniqueAddressId () {
		try {
			String maxAddress_id = this.addressDao.getMaxAddressId();
			int idNum = Integer.parseInt(maxAddress_id.replace("address-", ""));
			idNum += 1;
			return "address-" + idNum;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String createNewAddress(String addressInfo) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(addressInfo);
		
		String street = json.get("street").toString();
		String province_state = json.get("province_state").toString();
		String country = json.get("country").toString();
		String zip = json.get("zip").toString();
		String phone = json.get("phone").toString();
		
		
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
		

		JSONObject respContent = new JSONObject();
		
		// generate unique address id 
		String address_id = this.createUniqueAddressId();
		System.out.println("New address_id: " + address_id);
		int insertedRow = 0;
		if (address_id != null) {
			insertedRow = this.addressDao.insertAddress(address_id, street, province_state, country, zip, phone);
		}
		
		// if 0, no insertion occurred. if 1, it was successful
		if (insertedRow == 1) {
			// setup response content
			AddressBean address = this.addressDao.retrieveAddressByStreetAndZip(street, zip);
			respContent.put("successful", "true");
			respContent.put("message", "Successful address registration.");
			respContent.put("address_id", address.getId());
			respContent.put("street", address.getStreet());
			respContent.put("zip", address.getZip());
			

			return RestApiHelper.prepareResultJson(respContent);
		} else {
			return RestApiHelper.prepareErrorJson("New address was not inserted into DB");
		}
		
	}
}
