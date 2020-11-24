package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.dao.BookDAO;
import model.dao.ReviewDAO;
import util.InputValidation;
import util.RestApiHelper;

import java.util.Set;

import bean.BookBean;
import bean.ReviewBean;

public class BookController {
	private static BookController instance;
	private BookDAO bookDao;
	private ReviewDAO reviewDao;
	
	private BookController() {}
	
	public static BookController getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new BookController();
			instance.bookDao = new BookDAO();
			instance.reviewDao = new ReviewDAO();
		}
		
		return instance;
	}

	public String retrieveBooks() throws Exception {
		Set<BookBean> s = this.bookDao.retrieveAllBooks();
		JSONArray books = new JSONArray();
		JSONObject respContent = new JSONObject();
		
		for (BookBean b : s) {
			JSONObject i = new JSONObject();
			i.put("bid", b.getBid());
			i.put("title", b.getTitle());
			i.put("author", b.getAuthor());
			i.put("category", b.getCategory());
			i.put("review_score", b.getReview_score());
			i.put("number_of_reviews", b.getNumber_of_reviews());
			i.put("image_url", b.getImage_url());
			books.add(i);
		}
		respContent.put("successful", "true");
		respContent.put("message", "Successful retrieval of books.");
		respContent.put("number_of_books", s.size());
		respContent.put("books", books);
		
		System.out.println(respContent);
		return RestApiHelper.prepareResultJson(respContent);
	}
	
	public String retrieveBook(String bid) throws Exception {
		if (InputValidation.emptyInput(bid)) {
			System.out.println("ERROR: bid provided must not be empty");
			return RestApiHelper.prepareErrorJson("bid provided must not be empty");
		}  
		
		BookBean book = this.bookDao.retrieveBookById(bid);
		JSONObject respContent = new JSONObject();
		
		JSONObject b = new JSONObject();
		if (book != null ) {
			respContent.put("successful", "true");
			respContent.put("message", "Successful retrieval of book by bid.");
			respContent.put("bookExists", "true");
			
			b.put("bid", book.getBid());
			b.put("title", book.getTitle());
			b.put("author", book.getAuthor());
			b.put("category", book.getCategory());
			b.put("review_score", book.getReview_score());
			b.put("number_of_reviews", book.getNumber_of_reviews());
			b.put("image_url", book.getImage_url());
			respContent.put("book", b);
		} else {
			// *** NEED TO CHECK WITH TEAM ***
			// Is a non-existent book still considered successful since no errors? Or is it false?
			respContent.put("successful", "true");
			respContent.put("message", "Query executed, but book with bid: " + bid + " does not exist");
			respContent.put("bookExists", "false");
		}
		
		System.out.println(respContent);
		return RestApiHelper.prepareResultJson(respContent);
	}
	
	public String retrieveCategories() throws Exception {
		Set<String> s = this.bookDao.retrieveAllCategories();
		JSONArray categories = new JSONArray();
		JSONObject respContent = new JSONObject();
		
		for (String categ : s) {
			categories.add(categ);
		}
		respContent.put("successful", "true");
		respContent.put("message", "Successful retrieval of categories.");
		respContent.put("number_of_categories", s.size());
		respContent.put("categories", categories);
		
		System.out.println(respContent);
		return RestApiHelper.prepareResultJson(respContent);
	}
	
	public String retrieveBooksOfCategory(String category) throws Exception {
		Set<String> categoriesSet = this.bookDao.retrieveAllCategories();
		
		if(!categoriesSet.contains(category)) {
			System.out.println("ERROR: Category provided does not exist");
			return RestApiHelper.prepareErrorJson("Category provided does not exist");
		}
		
		Set<BookBean> s = this.bookDao.retrieveBooksByCategory(category);
		JSONArray books = new JSONArray();
		JSONObject respContent = new JSONObject();
		
		for (BookBean b : s) {
			JSONObject i = new JSONObject();
			i.put("bid", b.getBid());
			i.put("title", b.getTitle());
			i.put("author", b.getAuthor());
			i.put("category", b.getCategory());
			i.put("review_score", b.getReview_score());
			i.put("number_of_reviews", b.getNumber_of_reviews());
			i.put("image_url", b.getImage_url());
			books.add(i);
		}
		respContent.put("successful", "true");
		respContent.put("message", "Successful retrieval of books of category " + category);
		respContent.put("category", category);
		respContent.put("number_of_books", s.size());
		respContent.put("books", books);
		
		System.out.println(respContent);
		return RestApiHelper.prepareResultJson(respContent);
	}
	
	public String retrieveReviewsByBid(String bid) throws Exception {
		Set<String> bidSet = this.bookDao.retrieveAllBids();
		
		if(!bidSet.contains(bid)) {
			System.out.println("ERROR: bid provided does not exist");
			return RestApiHelper.prepareErrorJson("bid provided does not exist");
		}
		
		Set<ReviewBean> s = this.reviewDao.retrieveBookReviewsByBid(bid);
		JSONArray reviews = new JSONArray();
		JSONObject respContent = new JSONObject();
		
		for (ReviewBean b : s) {
			JSONObject i = new JSONObject();
			i.put("bid", b.getBid());
			i.put("rid", b.getRid());
			i.put("uid", b.getUid());
			i.put("review", b.getReview());
			i.put("score", b.getScore());
			reviews.add(i);
		}
		respContent.put("successful", "true");
		respContent.put("message", "Successful retrieval of book reviews for bid " + bid);
		respContent.put("bid", bid);
		respContent.put("number_of_reviews", s.size());
		respContent.put("reviews", reviews);
		
		System.out.println(respContent);
		return RestApiHelper.prepareResultJson(respContent);
	}
	
}
