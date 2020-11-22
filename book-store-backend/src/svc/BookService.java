package svc;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.BookController;

@Path("books")
public class BookService {
	@GET
	@Produces("application/json")
	public Response getBooks() throws Exception {
		String content = BookController.getInstance().retrieveAllBooks();

		return Response
				.status(Response.Status.OK)
				.entity(content)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
