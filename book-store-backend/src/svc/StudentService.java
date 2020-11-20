package svc;

import javax.ws.rs.*;

import model.SIS;

@Path("student")
public class StudentService {
	// http://localhost:8080/SIS-3/rest/student/read?studentName=name
	@GET
	@Path("/read/")
	@Produces("text/plain")
	public String getStudent(@DefaultValue("Nasif") @QueryParam("studentName") String name) throws Exception {
		System.out.println("GET: " + name);
		return SIS.getInstance().exportJSON(name, 0 + "");
	}

	// http://localhost:8080/SIS-3/rest/student/create?sid=cse99999&givenName=Nasif&surname=Haque&creditsTaken=90&creditsToGraduate=20
	@POST
	@Path("/create/")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String createStudent(@QueryParam("sid") String sid, @QueryParam("givenName") String givenName,
			@QueryParam("surname") String surname, @QueryParam("creditsTaken") String creditsTaken,
			@QueryParam("creditsToGraduate") String creditsToGraduate) throws Exception {
		System.out.println("POST: " + sid);
		try {
			SIS.getInstance().insertStudent(sid, givenName, surname, creditsTaken, creditsToGraduate);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR Inserting Student";
		}
	}

	// http://localhost:8080/SIS-3/rest/student/delete?sid=cse99999
	@DELETE
	@Path("/delete/")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String deleteStudent(@QueryParam("sid") String sid) {
		System.out.println("DELETE: " + sid);
		try {
			SIS.getInstance().deleteStudent(sid);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR Deleting Student";
		}
	}

}
