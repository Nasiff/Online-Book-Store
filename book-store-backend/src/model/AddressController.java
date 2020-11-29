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
			return RestApiHelper.prepareErrorJson("uid must be numerical only.");
		} else {
			AddressBean address = this.addressDao.retrieveAddressByUid(uid);
			if (address != null) {
				// setup response content
				JSONObject respContent = new JSONObject();
				respContent.put("successful", true);
				respContent.put("message", "Successful address retrieval.");
				
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
	
	public boolean checkExistingAddress(String street, String province_state, String country, String zip, String phone) throws Exception {
		AddressBean address = this.addressDao.retrieveAddressByAddressInfo(street, province_state, country, zip, phone);
		if (address != null) {
			return true;
		}
		return false;
	}
	
	public String createUniqueAddressId () {
		try {
			String maxAddress_id = this.addressDao.getMaxAddressId();
			// If there exist no address in DB, therefore no max address id either in DB
			// then start from "address-0" + 1 for address_id
			if (maxAddress_id == null) {
				maxAddress_id = "address-1000000";
			}
			int idNum = Integer.parseInt(maxAddress_id.replace("address-", ""));
			idNum += 1;
			return "address-" + idNum;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String createOrReturnAddress(String addressInfo) throws Exception {
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
		
		
		// setup response content
		JSONObject respContent = new JSONObject();

		// address does not exist, need to create address before returning address_id
		if (!this.checkExistingAddress(street, province_state, country, zip, phone)) {
			// generate unique address id 
			String address_id = this.createUniqueAddressId();
			System.out.println("New address_id: " + address_id);
			int insertedRow = 0;
			if (address_id == null) {
				System.out.println("ERROR: Problem creating unique address id");
				return RestApiHelper.prepareErrorJson("Problem creating unique address id");
			} else {
				System.out.println("  Attempting creating address with address_id " + address_id);
				insertedRow = this.addressDao.insertAddress(address_id, street, province_state, country, zip, phone);
			}
			
			// If 1 row was not inserted into DB, then error
			if (insertedRow != 1) {
				return RestApiHelper.prepareErrorJson("New address was not inserted into DB");
			} else {
				respContent.put("message", "Successful address registration with address_id " + address_id);
			}
		} else { 
			// address exists so just need to get get address_id
			System.out.println("  Getting address_id for existing address");
			respContent.put("message", "Successful retrieval of existing address. Not neccessary to create new address.");
		}
		
		// get address whether it was just created or already existed
		AddressBean address = this.addressDao.retrieveAddressByAddressInfo(street, province_state, country, zip, phone);
		
		// add to response content
		respContent.put("successful", true);
		respContent.put("address_id", address.getId());
		respContent.put("street", address.getStreet());
		respContent.put("province_state", address.getProvince_state());
		respContent.put("country", address.getCountry());
		respContent.put("zip", address.getZip());
		respContent.put("phone", address.getPhone());
		
		return RestApiHelper.prepareResultJson(respContent);
	}
}
