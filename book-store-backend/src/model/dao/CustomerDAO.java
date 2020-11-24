package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CustomerDAO {
	DataSource ds;

	public CustomerDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			System.out.println("Error looking up /comp/env/jdbc/EECS");
			e.printStackTrace();
		}
	}
	
	public int insertCustomer(int uid, String address_id) throws SQLException {
		String query = "insert into CustomerAccount values(?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setInt(1, uid);
		p.setString(2, address_id);
		
		int rows = p.executeUpdate();
		System.out.println("DB: Added " + rows + " customer to the database");
		
		p.close();
		con.close();
		return rows;
	}
}
