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
	
	public AddressBean retrieveAddressByStreetAndZip(String street, String zip) throws SQLException {
		// Need to insert single quotes around string since it contains whitespace for Derby to properly detect the string in the DB query
		//String query = "select * from Address where street = '" + street + "' and zip = '" + zip + "'";
		String query = "select * from Address where street = ? and zip = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, street);
		p.setString(2, zip);
		
		AddressBean address = null;
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String id = r.getString("id");
			//String street = r.getString("street");
			String province_state = r.getString("province_state");
			String country = r.getString("country");
			//String zip = r.getString("zip");
			String phone = r.getString("phone");
			address = new AddressBean(id, street, province_state, country, zip, phone);
			System.out.println("Found existing address with id: " + id + ", for street: " + street + ", zip: " + zip);
		}
		
		r.close();
		p.close();
		con.close();
		return address;
	}
	
	public String getMaxAddressId() throws SQLException {
		String query = "select max(id) from Address";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		String maxAddressId = null;
		ResultSet r = p.executeQuery();
		if (r.next()) {
			System.out.println("maxAddressId: " + r.getString(1));
			maxAddressId = r.getString(1);
		}
		
		r.close();
		p.close();
		con.close();
		return maxAddressId;
	}
	
	public int insertAddress(String address_id, String street, String province_state, String country,
			String zip, String phone) throws SQLException {
		String query = "insert into Address values(?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setString(1, address_id);
		p.setString(2, street);
		p.setString(3, province_state);
		p.setString(4, country);
		p.setString(5, zip);
		p.setString(6, phone);
		
		int rows = p.executeUpdate();
		System.out.println("DB: Added " + rows + " address to the database");
		
		p.close();
		con.close();
		return rows;
	}
	
	
}
