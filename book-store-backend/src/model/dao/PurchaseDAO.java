package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;
import bean.BookSalesBean;
import bean.PurchaseItemBean;

public class PurchaseDAO {
	DataSource ds;

	public PurchaseDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			System.out.println("Error looking up jdbc/OBS-DB");
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
	
	
	public List<BookSalesBean> retrieveMonthlyTopSellers(String month) throws SQLException {
		List<BookSalesBean> topBookSales = new ArrayList<BookSalesBean>();
		String query = " select * from " 
				+ "(select bid, sum(PoItem.quantity) as sales from PoItem join PO on PoItem.id = PO.id "
				+ "where month(PO.po_date) = ? group by bid) "
				+ "as bookSales inner join Book on bookSales.bid = book.bid "
				+ "order by sales DESC";
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, month);
		int rank = 1;
		
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String bid = r.getString("bid");
			String title = r.getString("title");
			double price = r.getDouble("price");
			String author = r.getString("author");
			String category = r.getString("category");
			double review_score = r.getDouble("review_score");
			int number_of_reviews = r.getInt("number_of_reviews");
			String image_url = r.getString("image_url");
			int sales = r.getInt("sales");
			System.out.println("  sales: " + sales + ", title: " + title);
			topBookSales.add(new BookSalesBean(bid, title, price, author, category, review_score, number_of_reviews, image_url, sales, rank));
			rank++;
		}
		r.close();
		p.close();
		con.close();
		return topBookSales;
	}
	
	public List<BookSalesBean> retrieveAllTimeTopSellers() throws SQLException {
		List<BookSalesBean> topBookSales = new ArrayList<BookSalesBean>();
		String query = " select * from (select bid, sum(PoItem.quantity) as sales from PoItem group by bid) "
				+ "as bookSales inner join Book on bookSales.bid = book.bid order by sales DESC";
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		int rank = 1;
		
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String bid = r.getString("bid");
			String title = r.getString("title");
			double price = r.getDouble("price");
			String author = r.getString("author");
			String category = r.getString("category");
			double review_score = r.getDouble("review_score");
			int number_of_reviews = r.getInt("number_of_reviews");
			String image_url = r.getString("image_url");
			int sales = r.getInt("sales");
			System.out.println("  sales: " + sales + ", title: " + title);
			topBookSales.add(new BookSalesBean(bid, title, price, author, category, review_score, number_of_reviews, image_url, sales, rank));
			rank++;
		}
		
		r.close();
		p.close();
		con.close();
		return topBookSales;
	}
	
	public List<PurchaseItemBean> retieveAllOrdersForBook(String bid) throws SQLException {
		List<PurchaseItemBean> orderList = new ArrayList<PurchaseItemBean>();
		String query = "select * from PoItem join PO on PoItem.id = PO.id where PoItem.bid = ?";
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, bid);
		int rank = 1;
		
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String order_id = r.getString("id");
			//String bid = r.getString("bid");
			double price = r.getDouble("price");
			int quantity = r.getInt("quantity");
			System.out.println("  order_id: " + order_id + ", quantity: " + quantity);
			orderList.add(new PurchaseItemBean(order_id, bid, price, quantity));
			rank++;
		}
		
		r.close();
		p.close();
		con.close();
		return orderList;
	}
	
}
