package svc;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.BookController;
import util.RestApiHelper;

@Path("books")
public class BookService {
	
	// http://localhost:8080/book-store-backend/rest/books
	@GET
	@Produces("application/json")
	public Response getAllBooks() throws Exception {		
		System.out.println("GET all books");
		try {
			String content = BookController.getInstance().retrieveBooks();
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem getting all books");
			return RestApiHelper.responseHelper(content);
		} 
	}
	
	
	// http://localhost:8080/book-store-backend/rest/books/{bid}
	@GET
	@Path("{bid}")
	@Produces("application/json")
	public Response getBook(@PathParam("bid") String bid) throws Exception {		
		System.out.println("GET book with bid: " + bid);
		try {
			String content = BookController.getInstance().retrieveBook(bid);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem getting book with bid: " + bid);
			return RestApiHelper.responseHelper(content);
		} 
	}
	
	
	// http://localhost:8080/book-store-backend/rest/books/categories
	@GET
	@Path("/categories/")
	@Produces("application/json")
	public Response getBookCategories() throws Exception {		
		System.out.println("GET all book catgories");
		try {
			String content = BookController.getInstance().retrieveCategories();
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem getting all book categories");
			return RestApiHelper.responseHelper(content);
		} 
	}
	
	
	// http://localhost:8080/book-store-backend/rest/books/categories/{categoryName}
	@GET
	@Path("/categories/{categoryName}")
	@Produces("application/json")
	public Response getBooksOfCategory(@PathParam("categoryName") String categoryName) throws Exception {		
		System.out.println("GET books of category: " + categoryName);
		try {
			String content = BookController.getInstance().retrieveBooksOfCategory(categoryName);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem getting books of category: " + categoryName);
			return RestApiHelper.responseHelper(content);
		} 
	}
	
	
	// http://localhost:8080/book-store-backend/rest/books/{bid}/review
	@GET
	@Path("{bid}/review")
	@Produces("application/json")
	public Response getReviewsOfBook(@PathParam("bid") String bid) throws Exception {		
		System.out.println("GET book with bid: " + bid);
		try {
			String content = BookController.getInstance().retrieveReviewsByBid(bid);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem getting reviews for book with bid: " + bid);
			return RestApiHelper.responseHelper(content);
		} 
	}
	
	
	// http://localhost:8080/book-store-backend/rest/books/{bid}/review
	// In the request headers there will be the uid: 12312  
	@POST
	@Path("{bid}/review")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createReview(@PathParam("bid") String bid, @HeaderParam("uid") String uid, String jsonReview) throws Exception {
		System.out.println("POST review for book with bid: " + bid + " from user: " + uid);
		try {
			String content = BookController.getInstance().createUpdateCustomerBookReview("create", bid, uid, jsonReview);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem creating review for book with bid: " + bid  + " from user: " + uid);
			return RestApiHelper.responseHelper(content);
		} 
	}
	
	
	// http://localhost:8080/book-store-backend/rest/books/{bid}/review
	// In the request headers there will be the uid: 12312  
	@PUT
	@Path("{bid}/review")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateReview(@PathParam("bid") String bid, @HeaderParam("uid") String uid, String jsonReview) throws Exception {
		System.out.println("POST review for book with bid: " + bid + " from user: " + uid);
		try {
			String content = BookController.getInstance().createUpdateCustomerBookReview("update", bid, uid, jsonReview);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem creating review for book with bid: " + bid  + " from user: " + uid);
			return RestApiHelper.responseHelper(content);
		} 
	}
	
}