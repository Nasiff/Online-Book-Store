package ctrl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SIS;

import bean.StudentBean;

/**
 * Servlet implementation class StudentDAO
 */
@WebServlet({ "/Start", "/Start/*" })
public class Sis extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = getServletContext();

		// Instantiate SIS object
		try {
			context.setAttribute("mSIS", SIS.getInstance());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = this.getServletContext();

		// Fresh visit
		if (!request.getParameterMap().containsKey("report") && !request.getParameterMap().containsKey("generateXML") && !request.getParameterMap().containsKey("generateJSON")) {
			System.out.println("-- Report Fresh Visit");
			request.getSession().setAttribute("tableOutput", null);
			String target = "/Form.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		} else if (request.getParameterMap().containsKey("generateXML")) {
			System.out.println("-- Generate XML Btn Clicked");
			request.getSession().setAttribute("tableOutput", null);
			request.getSession().setAttribute("students", null);
			
			String namePrefix = request.getParameter("namePrefix");
			String creditsTaken = request.getParameter("creditsTaken");
			
			SIS sis = (SIS) context.getAttribute("mSIS");
			String xmlOutput = "";
			try {
				xmlOutput = sis.exportXML(namePrefix, creditsTaken);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			response.setContentType("application/xml");
			response.getWriter().write(xmlOutput);
		} else if (request.getParameterMap().containsKey("generateJSON")) {
			System.out.println("-- Generate JSON Btn Clicked");
			request.getSession().setAttribute("tableOutput", null);
			request.getSession().setAttribute("students", null);
			
			String namePrefix = request.getParameter("namePrefix");
			String creditsTaken = request.getParameter("creditsTaken");
			
			SIS sis = (SIS) context.getAttribute("mSIS");
			String jsonOutput = "";
			try {
				jsonOutput = sis.exportJSON(namePrefix, creditsTaken);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			response.setContentType("application/json");
			response.getWriter().write(jsonOutput);
		}
		else {// Report button was clicked
			System.out.println("-- Report Btn Was Clicked");
			String namePrefix = request.getParameter("namePrefix");
			String creditsTaken = request.getParameter("creditsTaken");

			SIS sis = (SIS) context.getAttribute("mSIS");
			Map<String, StudentBean> students;

			try {
				students = sis.retriveStudent(namePrefix, creditsTaken);
				request.getSession().setAttribute("students", students);

			} catch (Exception e) {
				e.printStackTrace();
			}

			String target = "/Form.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
