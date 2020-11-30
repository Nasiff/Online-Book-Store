package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model.dao.BookDAO;
import model.dao.ReviewDAO;
import model.dao.UserDAO;
import util.InputValidation;
import util.RestApiHelper;

import java.sql.SQLException;
import java.util.Set;

import bean.BookBean;
import bean.ReviewBean;
import bean.UserBean;

public class BookController {
	private static BookController instance;
	private BookDAO bookDao;
	private ReviewDAO reviewDao;
	private UserDAO userDao;
	
	private BookController() {}
	
	public static BookController getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new BookController();
			instance.bookDao = new BookDAO();
			instance.reviewDao = new ReviewDAO();
			instance.userDao = new UserDAO();
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
			i.put("price", b.getPrice());
			i.put("author", b.getAuthor());
			i.put("category", b.getCategory());
			i.put("review_score", b.getReview_score());
			i.put("number_of_reviews", b.getNumber_of_reviews());
			i.put("image_url", b.getImage_url());
			books.add(i);
		}
		respContent.put("successful", true);
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
			respContent.put("successful", true);
			respContent.put("message", "Successful retrieval of book by bid.");
			respContent.put("bookExists", "true");
			
			b.put("bid", book.getBid());
			b.put("title", book.getTitle());
			b.put("price", book.getPrice());
			b.put("author", book.getAuthor());
			b.put("category", book.getCategory());
			b.put("review_score", book.getReview_score());
			b.put("number_of_reviews", book.getNumber_of_reviews());
			b.put("image_url", book.getImage_url());
			respContent.put("book", b);
		} else {
			respContent.put("successful", true);
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
		respContent.put("successful", true);
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
			i.put("price", b.getPrice());
			i.put("author", b.getAuthor());
			i.put("category", b.getCategory());
			i.put("review_score", b.getReview_score());
			i.put("number_of_reviews", b.getNumber_of_reviews());
			i.put("image_url", b.getImage_url());
			books.add(i);
		}
		respContent.put("successful", true);
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
		respContent.put("successful", true);
		respContent.put("message", "Successful retrieval of book reviews for bid " + bid);
		respContent.put("bid", bid);
		respContent.put("number_of_reviews", s.size());
		respContent.put("reviews", reviews);
		
		System.out.println(respContent);
		return RestApiHelper.prepareResultJson(respContent);
	}
	
	
	public String createUniqueReviewId () {
		try {
			String maxReview_id = this.reviewDao.getMaxReviewId();
			// If there exist no review in DB, therefore no max review id either in DB
			// then start from "review-0" + 1 for rid
			if (maxReview_id == null) {
				maxReview_id = "review-1000000";
			}
			int idNum = Integer.parseInt(maxReview_id.replace("review-", ""));
			idNum += 1;
			return "review-" + idNum;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String createUpdateCustomerBookReview(String createUpdate, String bid, String uidStr, String reviewInfo) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(reviewInfo);
		
		String review_score = json.get("review_score").toString();
		String review_body = json.get("review_body").toString();
		
		if (InputValidation.emptyInput(bid) || InputValidation.emptyInput(uidStr) 
				|| InputValidation.emptyInput(review_score) || InputValidation.emptyInput(review_body)) {
			System.out.println("ERROR: Parameters (bid, uid, reviewInfo) provided must not be empty");
			return RestApiHelper.prepareErrorJson("Parameters (bid, uid, reviewInfo) provided must not be empty");
		}  else if (!InputValidation.isNumerical(review_score) || !InputValidation.isNumerical(uidStr)) {
			System.out.println("ERROR: Review_score & uid must be numerical");
			return RestApiHelper.prepareErrorJson("Review_score & uid must be numerical");
		} 
		
		int score = Integer.parseInt(review_score);
		if (score < 0 || score > 5) {
			System.out.println("ERROR: Review_score must be within 0-5 integer range");
			return RestApiHelper.prepareErrorJson("Review_score must be within 0-5 integer range");
		}
		
		// Check if book with bid exists
		BookBean book = this.bookDao.retrieveBookById(bid);
		if (book == null) {
			System.out.println("ERROR: Book with bid " + bid + " does not exist");
			return RestApiHelper.prepareErrorJson("Book with bid " + bid + " does not exist");
		}
		
		// Check if user with uid exist and is of type CUSTOMER
		int uid = Integer.parseInt(uidStr);
		UserBean user = this.userDao.retrieveUserByUid(uid);
		if (user == null) {
			System.out.println("ERROR: User with uid " + uid + " does not exist");
			return RestApiHelper.prepareErrorJson("User with bid " + uid + " does not exist");
		} else {
			if (!user.getUser_type().equals("CUSTOMER")) {
				System.out.println("ERROR: User with uid " + uid + " is not a customer. This user cannot write a review.");
				return RestApiHelper.prepareErrorJson("User with bid " + uid + " is not a customer. This user cannot write a review.");
			}
		}
		
		
		ReviewBean existingReview = this.reviewDao.retrieveBookReviewByBidAndUid(bid, uid);
		
		if (createUpdate.equals("create")) {
			return this.createCustomerBookReviewHelper(bid, uid, score, review_body, book, existingReview);
		} else if (createUpdate.equals("update")) { 
			return this.updateCustomerBookReviewHelper(bid, uid, score, review_body, book, existingReview);
		} else {
			System.out.println("ERROR: Book review changes must be via create or update");
			return RestApiHelper.prepareErrorJson("Book review changes must be via create or update");
		}
		
	}
	
	
	public String createCustomerBookReviewHelper(String bid, int uid, int score, String review_body, 
			BookBean book, ReviewBean existingReview) throws Exception {
		if (existingReview != null) {
			System.out.println("ERROR: Cannot create new review since review " + existingReview.getRid() 
				+ " for user " + uid + " and book " + bid + " already exists.");
			return RestApiHelper.prepareErrorJson("Cannot create new review since review " + existingReview.getRid() 
				+ " for user " + uid + " and book " + bid + " already exists.");
		} else {
			// generate unique review id 
			String rid = this.createUniqueReviewId();
			System.out.println("New review_id: " + rid);
			int insertedReviewRow = 0;
			if (rid == null) {
				System.out.println("ERROR: Problem creating unique review id");
				return RestApiHelper.prepareErrorJson("Problem creating unique review id");
			} else {
				insertedReviewRow = this.reviewDao.insertBookReview(rid, bid, uid, review_body, score);
			}
			
			// if 0, no insertion occurred. if 1, it was successful
			if (insertedReviewRow == 1) {
				Set<ReviewBean> setOfReviewsForBook = this.reviewDao.retrieveBookReviewsByBid(bid);
				double sumOfScoresForBook = 0;
				
				for (ReviewBean b : setOfReviewsForBook) {
					sumOfScoresForBook += b.getScore();
				}
				
				int numOfReviews = setOfReviewsForBook.size();
				double newAvgScore = Math.round(10.0 * sumOfScoresForBook / numOfReviews ) / 10.0;
				
				int updatedBookRow = this.bookDao.updateBookAvgScoreAndNumOfReviews(newAvgScore, numOfReviews, bid);
				// if 0, no update occurred. if 1, it was successful
				if (updatedBookRow == 1) {
					// setup response content
					ReviewBean reviewBean = this.reviewDao.retrieveBookReviewByBidAndUid(bid, uid);
					JSONObject respContent = new JSONObject();
					
					respContent.put("successful", "true");
					respContent.put("message", "Successful insertion of review and update of book review statistics.");
					respContent.put("rid", reviewBean.getRid());
					respContent.put("bid", reviewBean.getBid());
					respContent.put("uid", reviewBean.getUid());
					respContent.put("review", reviewBean.getReview());
					respContent.put("score", reviewBean.getScore());
					
					JSONObject bookStats = new JSONObject();
					bookStats.put("average_book_score", newAvgScore);
					bookStats.put("number_of_reviews", numOfReviews);
					respContent.put("book_stats", bookStats);
					
					return RestApiHelper.prepareResultJson(respContent);
				} else {
					return RestApiHelper.prepareErrorJson("Book review average score and number of reviews was not updated in Book DB");
				}
					
			} else {
				return RestApiHelper.prepareErrorJson("New review was not inserted in BookReview DB");
			}
			
		}

	}
	
	
	public String updateCustomerBookReviewHelper(String bid, int uid, int score, String review_body, 
			BookBean book, ReviewBean existingReview) throws Exception {
		if (existingReview == null) {
			System.out.println("ERROR: Cannot update since review for user " + uid + " and book " + bid + " does not exist.");
			return RestApiHelper.prepareErrorJson("Cannot update review since review for user " + uid + " and book " + bid + " does not exist.");
		} else {
			
			// generate unique review id 
			String rid = existingReview.getRid();
			System.out.println("Existing review_id: " + rid);
			
			int updatedReviewRow = this.reviewDao.updateBookReviewAndScore(review_body, score, rid);
			
			// if 0, no insertion occurred. if 1, it was successful
			if (updatedReviewRow == 1) {
				Set<ReviewBean> setOfReviewsForBook = this.reviewDao.retrieveBookReviewsByBid(bid);
				double sumOfScoresForBook = 0;
				
				for (ReviewBean b : setOfReviewsForBook) {
					sumOfScoresForBook += b.getScore();
				}
				
				int numOfReviews = setOfReviewsForBook.size();
				double newAvgScore = Math.round(10.0 * sumOfScoresForBook / numOfReviews ) / 10.0;
				
				int updatedBookRow = this.bookDao.updateBookAvgScoreAndNumOfReviews(newAvgScore, numOfReviews, bid);
				if (updatedBookRow == 1) {
					// setup response content
					ReviewBean reviewBean = this.reviewDao.retrieveBookReviewByBidAndUid(bid, uid);
					JSONObject respContent = new JSONObject();
					respContent.put("successful", true);
					respContent.put("message", "Successful update of review and update of book review statistics.");
					respContent.put("rid", reviewBean.getRid());
					respContent.put("bid", reviewBean.getBid());
					respContent.put("uid", reviewBean.getUid());
					respContent.put("review", reviewBean.getReview());
					respContent.put("score", reviewBean.getScore());
					
					JSONObject bookStats = new JSONObject();
					bookStats.put("average_book_score", newAvgScore);
					bookStats.put("number_of_reviews", numOfReviews);
					respContent.put("book_stats", bookStats);
					
					return RestApiHelper.prepareResultJson(respContent);
				} else {
					return RestApiHelper.prepareErrorJson("Book review average score and number of reviews was not updated in Book DB");
				}
					
			} else {
				return RestApiHelper.prepareErrorJson("New review was not inserted in BookReview DB");
			}
			
		} 
	}

}
