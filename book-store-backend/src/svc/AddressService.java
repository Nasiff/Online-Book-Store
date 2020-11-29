package svc;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.AddressController;
import model.UserController;
import util.RestApiHelper;

@Path("address")
public class AddressService {
	
	// http://localhost:8080/SIS-3/rest/address
	@GET
	@Produces("application/json")
	public Response getAddress(@HeaderParam("uid") String uid) throws Exception {
		System.out.println("GET: retrieve address");
		try {
			String content = AddressController.getInstance().getAddressByUid(uid);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem getting address");
			return RestApiHelper.responseHelper(content);
		}
	}
	
	
	// http://localhost:8080/SIS-3/rest/address
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response createAddress(String jsonAddress) throws Exception {
		System.out.println("POST: create address");
		try {
			String content = AddressController.getInstance().createOrReturnAddress(jsonAddress);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem creating new address");
			return RestApiHelper.responseHelper(content);
		}
	}
	
}
