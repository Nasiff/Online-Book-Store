package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.dao.BookDAO;
import model.dao.EnrollmentDAO;
import model.dao.StudentDAO;
import util.RestApiHelper;
import bean.StudentBean;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bean.EnrollmentBean;
import bean.ListWrapper;

public class BookController {
	private static BookController instance;
	private BookDAO bookDao;
	
	private BookController() {}
	
	public static BookController getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new BookController();
			instance.bookDao = new BookDAO();
		}
		
		return instance;
	}

	public String retrieveAllBooks() throws Exception {
		Set<StudentBean> s = this.bookDao.retrieveAllBooks();
		JSONArray content = new JSONArray();

		for (StudentBean b : s) {
			JSONObject i = new JSONObject();
			i.put("sid", b.getSid());
			i.put("name", b.getName());
			i.put("credit_taken", b.getCreditsTaken());
			i.put("credit_graduate", b.getCreditsGraduate());
			content.add(i);
		}
		
		return RestApiHelper.prepareResultJson(content);
	}
	
//	@SuppressWarnings("unchecked")
//	public String exportJSON(String namePrefix, String creditsTaken) throws Exception {
//		ArrayList<StudentBean> listOfStudents = new ArrayList<StudentBean>();
//		Map<String, StudentBean> mapOfStudents = this.retriveStudent(namePrefix, creditsTaken);
//		
//		for (String s : mapOfStudents.keySet()) {
//			listOfStudents.add(mapOfStudents.get(s));
//		}
//		
//		JSONObject json = new JSONObject();
//		json.put("namePrefix", namePrefix);
//		json.put("creditsTaken", creditsTaken);
//		
//		JSONArray students = new JSONArray();
//		for (StudentBean s : listOfStudents) {
//			JSONObject i = new JSONObject();
//			i.put("sid", s.getSid());
//			i.put("name", s.getName());
//			i.put("credit_taken", s.getCreditsTaken());
//			i.put("credit_graduate", s.getCreditsGraduate());
//			students.add(i);
//		}
//		
//		json.put("studentList", students);
//		
//		return json.toJSONString();
//	}
}
