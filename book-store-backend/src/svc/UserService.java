package svc;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.AddressController;
import model.SIS;
import model.UserController;
import util.RestApiHelper;

@Path("user")
public class UserService {
	
	// http://localhost:8080/book-store-backend/rest/user
	/* Example of request HEADERS sent 
	  credentials Headers
	  {
	  	email: "peter.black@yahoo.com",
	  	password: "superst@rOf2001"
	  }
	*/
	@GET
	@Produces("application/json")
	public Response loginUser(@HeaderParam("email") String email, @HeaderParam("password") String password) throws Exception {
		System.out.println("GET login request with email: " + email);
		try {
			String content = UserController.getInstance().authenticateUser(email, password);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem logging in user with email: " + email);
			return RestApiHelper.responseHelper(content);
		} 
	}
	
	
	// http://localhost:8080/book-store-backend/rest/user
	/* Example of JSON request CONTENT sent 
	  jsonCredentials 
	{
		"email": "john.wick@gmail.com",
		"password": "b00geyW00gey!",
		"type": "CUSTOMER",
		"fname": "John",
		"lname": "Wick",
		"address": {
			"street": "637 Princeton St.",
			"province_state": "NY",
			"country": "United States",
			"zip": "G3H 2F9",
			"phone": "412-543-6252"
		}
	}
	*/
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response registerUser(String userInfo) throws Exception {
		System.out.println("POST: registration request");	
		try {
			String content = UserController.getInstance().createNewUser(userInfo);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem creating the user");
			return RestApiHelper.responseHelper(content);
		}
	}
	
	
}
