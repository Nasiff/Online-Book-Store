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

import bean.ReviewBean;

public class ReviewDAO {
	DataSource ds;

	public ReviewDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			System.out.println("Error looking up /comp/env/jdbc/EECS");
			e.printStackTrace();
		}
	}
	
	
	public Set<ReviewBean> retrieveBookReviewsByBid(String bid) throws SQLException {
		String query = "select * from Book join BookReview on Book.bid = BookReview.bid where Book.bid = ?";
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, bid);
		
		Set<ReviewBean> reviews = new HashSet<ReviewBean>();
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String rid = r.getString("rid");
			int uid = r.getInt("uid");
			String review = r.getString("review");
			int score = r.getInt("score");
			reviews.add(new ReviewBean(rid, bid, uid, review, score));
		}
		
		r.close();
		p.close();
		con.close();
		return reviews;
	}
}
