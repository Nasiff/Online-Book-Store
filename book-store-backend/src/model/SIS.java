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

import model.dao.EnrollmentDAO;
import model.dao.StudentDAO;
import bean.StudentBean;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bean.EnrollmentBean;
import bean.ListWrapper;

public class SIS {
	private static SIS instance;
	private StudentDAO studentdao;
	private EnrollmentDAO enrollmentdao;
	
	private SIS() {}
	
	public static SIS getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new SIS();
			instance.studentdao = new StudentDAO();
			instance.enrollmentdao = new EnrollmentDAO();
		}
		
		return instance;
	}

	public Map<String, StudentBean> retriveStudent(String namePrefix, String creditsTaken) throws Exception {
		if (!this.isSpecialChar(namePrefix) && !this.isSpecialChar(creditsTaken)) {			
			return this.studentdao.retrieve(namePrefix, creditsTaken);
		}
		
		return new HashMap<String, StudentBean>();
	}
	
	public void insertStudent(String sid, String givenName, String surname, String creditsTaken,
			String creditsToGraduate) throws Exception {
		int ct = Integer.parseInt(creditsTaken);
		int ctg = Integer.parseInt(creditsToGraduate);

		if (ct < 0 || ct > 200) {
			throw new Exception("Invalid Credits Taken Value");
		}
		
		if (ctg < 0 || ctg > 200) {
			throw new Exception("Invalid Credits To Graduate Value");
		}

		this.studentdao.insert(sid, givenName, surname, ct, ctg);
	}
	
	public void deleteStudent(String sid) throws Exception {
		this.studentdao.delete(sid);
	}
	
	public Map<String, EnrollmentBean> retriveEnrollment() throws Exception {
		return null;
	}
	
	public String exportXML(String namePrefix, String creditsTaken) throws Exception {
		ArrayList<StudentBean> listOfStudents = new ArrayList<StudentBean>();
		Map<String, StudentBean> mapOfStudents = this.retriveStudent(namePrefix, creditsTaken);
		
		for (String s : mapOfStudents.keySet()) {
			listOfStudents.add(mapOfStudents.get(s));
		}

		ListWrapper lw = new ListWrapper(namePrefix, creditsTaken, listOfStudents);
		
		JAXBContext jc = JAXBContext.newInstance(lw.getClass());
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Source schemaFile = new StreamSource(getClass().getClassLoader().getResourceAsStream("/SIS.xsd"));
		Schema schema = sf.newSchema(schemaFile);
		marshaller.setSchema(schema);

		StringWriter sw = new StringWriter();
		sw.write("\n");
		marshaller.marshal(lw, new StreamResult(sw));

		System.out.println(sw.toString()); // for debugging
		// return XML
		return sw.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String exportJSON(String namePrefix, String creditsTaken) throws Exception {
		ArrayList<StudentBean> listOfStudents = new ArrayList<StudentBean>();
		Map<String, StudentBean> mapOfStudents = this.retriveStudent(namePrefix, creditsTaken);
		
		for (String s : mapOfStudents.keySet()) {
			listOfStudents.add(mapOfStudents.get(s));
		}
		
		JSONObject json = new JSONObject();
		json.put("namePrefix", namePrefix);
		json.put("creditsTaken", creditsTaken);
		
		JSONArray students = new JSONArray();
		for (StudentBean s : listOfStudents) {
			JSONObject i = new JSONObject();
			i.put("sid", s.getSid());
			i.put("name", s.getName());
			i.put("credit_taken", s.getCreditsTaken());
			i.put("credit_graduate", s.getCreditsGraduate());
			students.add(i);
		}
		
		json.put("studentList", students);
		
		return json.toJSONString();
	}
	
	private boolean isSpecialChar(String s) {
	     if (s == null || s.trim().isEmpty()) {
	         System.out.println("Incorrect format of string");
	         return true;
	     }
	     Pattern p = Pattern.compile("[^A-Za-z0-9]");
	     Matcher m = p.matcher(s);
	     boolean b = m.find();
	     if (b) {
	    	System.out.println("There is a special character in my string ");
	        return true;
	     }
	     else {
	         System.out.println("There is no special char.");
	    	 return false;	    	 
	     }
	 }
}
