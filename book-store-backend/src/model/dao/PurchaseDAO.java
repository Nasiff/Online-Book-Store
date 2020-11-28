package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PurchaseDAO {
	DataSource ds;

	public PurchaseDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			System.out.println("Error looking up /comp/env/jdbc/EECS");
			e.printStackTrace();
		}
	}
	
	public String getMaxPOId() throws SQLException {
		String query = "select max(id) from PO";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		String maxPOId = null;
		ResultSet r = p.executeQuery();
		if (r.next()) {
			System.out.println("maxPOId: " + r.getString(1));
			maxPOId = r.getString(1);
		}
		
		r.close();
		p.close();
		con.close();
		return maxPOId;
	}
	
	public int insertPurchaseItem(String order_id, String bid, double price, int quantity) throws SQLException {
		String query = "insert into POItem values(?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, order_id);
		p.setString(2, bid);
		p.setDouble(3, price);
		p.setInt(4, quantity);
		
		int rows = p.executeUpdate();
		System.out.println("DB: Added " + rows + " POItem row to the database");
		
		p.close();
		con.close();
		return rows;
	}
	
	public int insertPurchaseOrder(String order_id, String lname, String fname, String status, 
			String address_id, Date po_date) throws SQLException {
		String query = "insert into PO values(?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, order_id);
		p.setString(2, lname);
		p.setString(3, fname);
		p.setString(4, status);
		p.setString(5, address_id);
		p.setDate(6, po_date);
		
		int rows = p.executeUpdate();
		System.out.println("DB: Added " + rows + " PO row to the database");
		
		p.close();
		con.close();
		return rows;
	}
	
}
