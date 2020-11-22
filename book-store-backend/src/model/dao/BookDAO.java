package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.StudentBean;

public class BookDAO {
	DataSource ds;

	public BookDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			System.out.println("Error looking up /comp/env/jdbc/EECS");
			e.printStackTrace();
		}
	}

	public Set<StudentBean> retrieveAllBooks() throws SQLException {
		String query = "select * from students";
		Set<StudentBean> rv = new HashSet<StudentBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String sid = r.getString("SID");
			String name = r.getString("GIVENNAME") + ", " + r.getString("SURNAME");
			String creditTaken = r.getString("CREDIT_TAKEN");
			String creditGraduate = r.getString("CREDIT_GRADUATE");
			rv.add(new StudentBean(sid, name, creditTaken, creditGraduate));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
}
