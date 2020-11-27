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

import bean.BookBean;

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

	public Set<BookBean> retrieveAllBooks() throws SQLException {
		String query = "select * from Book";
		Set<BookBean> allBooks = new HashSet<BookBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String bid = r.getString("bid");
			String title = r.getString("title");
			String author = r.getString("author");
			String category = r.getString("category");
			double review_score = r.getDouble("review_score");
			int number_of_reviews = r.getInt("number_of_reviews");
			String image_url = r.getString("image_url");
			allBooks.add(new BookBean(bid, title, author, category, review_score, number_of_reviews, image_url));
		}
		
		r.close();
		p.close();
		con.close();
		return allBooks;
	}
	
	public BookBean retrieveBookById(String bid) throws SQLException {
		String query = "select * from Book where bid = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, bid);
		
		BookBean book = null;
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String title = r.getString("title");
			String author = r.getString("author");
			String category = r.getString("category");
			double review_score = r.getDouble("review_score");
			int number_of_reviews = r.getInt("number_of_reviews");
			String image_url = r.getString("image_url");
			book = new BookBean(bid, title, author, category, review_score, number_of_reviews, image_url);
			break;
		}
		
		r.close();
		p.close();
		con.close();
		return book;
	}
	
	public Set<String> retrieveAllBids() throws SQLException {
		String query = "select bid from Book";
		Set<String> allBids = new HashSet<String>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String bid = r.getString("bid");
			allBids.add(bid);
		}
		r.close();
		p.close();
		con.close();
		return allBids;
	}
	
	public Set<String> retrieveAllCategories() throws SQLException {
		String query = "select category from Book";
		Set<String> allCategories = new HashSet<String>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String category = r.getString("category");
			allCategories.add(category);
		}
		r.close();
		p.close();
		con.close();
		return allCategories;
	}
	
	public Set<BookBean> retrieveBooksByCategory(String category) throws SQLException {
		String query = "select * from Book where category = ?";
		Set<BookBean> booksOfCategory = new HashSet<BookBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, category);
		
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String bid = r.getString("bid");
			String title = r.getString("title");
			String author = r.getString("author");
			double review_score = r.getDouble("review_score");
			int number_of_reviews = r.getInt("number_of_reviews");
			String image_url = r.getString("image_url");
			booksOfCategory.add(new BookBean(bid, title, author, category, review_score, number_of_reviews, image_url));
		}
		
		r.close();
		p.close();
		con.close();
		return booksOfCategory;
	}
	
	public int updateBookAvgScoreAndNumOfReviews(double avgScore, int num_of_reviews, String bid) throws SQLException {
		String query = "update Book set review_score = ?, number_of_reviews = ? where bid = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setDouble(1, avgScore);
		p.setInt(2, num_of_reviews);
		p.setString(3, bid);

		int rows = p.executeUpdate();
		System.out.println("DB: Updated " + rows + " book " + bid + " with new review_score and number_of_reviews to the database");
		
		p.close();
		con.close();
		return rows;
	}
	
}
