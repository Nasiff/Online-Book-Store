package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import bean.AddressBean;
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
				return "ERROR: Customer does not exist.";
			}
		}
	}
	
	
}
