package svc;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import model.PurchaseController;
import util.RestApiHelper;

@Path("purchase")
public class PurchaseService {
	
	// http://localhost:8080/book-store-backend/rest/purchase
	// In the request headers there will be the uid: 12312  
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response createPurchaseOrder(String purchaseInfo) throws Exception {
		System.out.println("POST: Create purchase order");	
		try {
			String content = PurchaseController.getInstance().createPurchaseOrder(purchaseInfo);
			return RestApiHelper.responseHelper(content);
		} catch (Exception e) {
			e.printStackTrace();
			String content = RestApiHelper.prepareErrorJson("Problem creating the purchase order");
			return RestApiHelper.responseHelper(content);
		}
	}

}
