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

import bean.StudentBean;

public class StudentDAO {
	DataSource ds;

	public StudentDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			System.out.println("Error looking up /comp/env/jdbc/EECS");
			e.printStackTrace();
		}
	}

	public void insert(String sid, String givenName, String surname, int creditsTaken,
			int creditsToGraduate) throws SQLException {
		String query = "insert into STUDENTS values(?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setString(1, sid);
		p.setString(2, givenName);
		p.setString(3, surname);
		p.setInt(4, creditsTaken);
		p.setInt(5, creditsToGraduate);
		int rows = p.executeUpdate();
		
		System.out.println("DB: Added " + rows + " to the database");
		
		p.close();
		con.close();
	}
	
	public void delete(String sid) throws SQLException {
		String query = "delete from STUDENTS where sid = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setString(1, sid);

		int rows = p.executeUpdate();
		
		System.out.println("DB: Deleted " + rows + " from the database");
		
		p.close();
		con.close();
	}

	public Map<String, StudentBean> retrieve(String namePrefix, String credit_taken) throws SQLException {
		String query = "select * from students where surname like '%" + namePrefix + "%' and credit_taken >= "
				+ credit_taken;
		Map<String, StudentBean> rv = new HashMap<String, StudentBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String sid = r.getString("SID");
			String name = r.getString("GIVENNAME") + ", " + r.getString("SURNAME");
			String creditTaken = r.getString("CREDIT_TAKEN");
			String creditGraduate = r.getString("CREDIT_GRADUATE");
			rv.put(sid, new StudentBean(sid, name, creditTaken, creditGraduate));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
}
