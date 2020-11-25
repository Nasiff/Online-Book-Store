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
	
	public ReviewBean retrieveBookReviewByBidAndUid(String bid, int uid) throws SQLException {
		String query = "select * from Book join BookReview on Book.bid = BookReview.bid where Book.bid = ? and BookReview.uid = ?";
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, bid);
		p.setInt(2, uid);
		
		ReviewBean reviewBean = null;
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String rid = r.getString("rid");
			String review = r.getString("review");
			int score = r.getInt("score");
			reviewBean = new ReviewBean(rid, bid, uid, review, score);
		}
		
		r.close();
		p.close();
		con.close();
		return reviewBean;
	}
	
	public String getMaxReviewId() throws SQLException {
		String query = "select max(rid) from BookReview";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		String maxReviewId = null;
		ResultSet r = p.executeQuery();
		if (r.next()) {
			System.out.println("maxReviewId: " + r.getString(1));
			maxReviewId = r.getString(1);
		}
		
		r.close();
		p.close();
		con.close();
		return maxReviewId;
	}
	
	public int insertBookReview(int uid, String lname, String fname, String email,
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
		System.out.println("DB: Added " + rows + " user to the database");
		
		p.close();
		con.close();
		return rows;
	}	
	
	
	
}
