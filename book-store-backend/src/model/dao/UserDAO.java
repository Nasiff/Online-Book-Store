package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.UserBean;

public class UserDAO {
	DataSource ds;

	public UserDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			System.out.println("Error looking up /comp/env/jdbc/EECS");
			e.printStackTrace();
		}
	}

	public UserBean retrieveUser(String email, String password) throws SQLException {
		String query = "select * from UserAccount where email = ? and password = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, email);
		p.setString(2, password);
		
		UserBean user = null;
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String uid = r.getString("uid");
			String lname = r.getString("lname");
			String fname = r.getString("fname");
			String user_type = r.getString("user_type");
			user = new UserBean(uid, lname, fname, email, password, user_type);
			break;
		}
		
		r.close();
		p.close();
		con.close();
		return user;
	}
	
	public UserBean retrieveUserByUid(String uid) throws SQLException {
		String query = "select * from UserAccount where uid = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, uid);
		
		UserBean user = null;
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String lname = r.getString("lname");
			String fname = r.getString("fname");
			String email = r.getString("fname");
			String password = r.getString("password");
			String user_type = r.getString("user_type");
			user = new UserBean(uid, lname, fname, email, password, user_type);
			break;
		}
		
		r.close();
		p.close();
		con.close();
		return user;
	}
	
	public boolean existingUserWithEmail(String email) throws SQLException {
		// example: nameOfParam = "email" and paramValue = "john.white@gmail.com"
		// example: nameOfParam = "uid" and paramValue = "43225"
		String query = "select * from UserAccount where email = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);	
		
		p.setString(1, email);
		
		boolean userExists = false;
		ResultSet r = p.executeQuery();
		while (r.next()) {
			userExists = true;
			break;
		}
		
		r.close();
		p.close();
		con.close();
		return userExists;
	}
	
	public int getMaxUid() throws SQLException {
		String query = "select max(uid) from UserAccount";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		int maxUid = 0;
		ResultSet r = p.executeQuery();
		if (r.next()) {
			System.out.println("maxUid: " + r.getInt(1));
			maxUid = r.getInt(1);
		}
		
		r.close();
		p.close();
		con.close();
		return maxUid;
	}
	
	public int insertUser(int uid, String lname, String fname, String email,
			String password, String user_type) throws SQLException {
		String query = "insert into UserAccount values(?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setInt(1, uid);
		p.setString(2, lname);
		p.setString(3, fname);
		p.setString(4, email);
		p.setString(5, password);
		p.setString(6, user_type);
		
		int rows = p.executeUpdate();
		System.out.println("DB: Added " + rows + "user to the database");
		
		p.close();
		con.close();
		return rows;
	}
}
