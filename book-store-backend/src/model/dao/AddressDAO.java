package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.simple.JSONObject;

import bean.AddressBean;
import bean.StudentBean;

public class AddressDAO {
	DataSource ds;
	
	public AddressDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			System.out.println("Error looking up /comp/env/jdbc/EECS");
			e.printStackTrace();
		}
	}

	public AddressBean retrieveAddressByUid(String uid) throws SQLException {
		String query = "select * from UserAccount join CustomerAccount on UserAccount.uid = CustomerAccount.uid " 
				+ "join Address on CustomerAccount.address_id = Address.id where CustomerAccount.uid = ?";
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, uid);
		
		AddressBean addr = null;
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			String id = r.getString("id");
			String street = r.getString("street");
			String province_state = r.getString("province_state");
			String country = r.getString("country");
			String zip = r.getString("zip");
			String phone = r.getString("phone");
			addr = new AddressBean(id, street, province_state, country, zip, phone);
			break;
		}
		
		r.close();
		p.close();
		con.close();
		return addr;
	}
	
	public Map<String, AddressBean> retrieveAddress(JSONObject json) throws SQLException {
		String query = "select * from address where street = ? and zip = ?";
		Map<String, AddressBean> rv = new HashMap<String, AddressBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, json.get("street").toString());
		p.setString(2, json.get("zip").toString());
		
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			String id = r.getString("id");
			String street = r.getString("street");
			String province_state = r.getString("province_state");
			String country = r.getString("country");
			String zip = r.getString("zip");
			String phone = r.getString("phone");
			rv.put(id, new AddressBean(id, street, province_state, country, zip, phone));
		}
		
		r.close();
		p.close();
		con.close();
		return rv;
	}
	
}
