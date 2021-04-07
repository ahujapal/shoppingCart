package item_manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession sess = request.getSession(true);
		
		// check if the session is null
		if(sess==null) {
			response.sendRedirect("Form?msg=there is an error with that request");
		}
		else {
			// session exists
			DB_Access db = new DB_Access();
			String msg = "";
			if(request.getParameter("msg") != null) {
				msg = request.getParameter("msg");
			}
	
			
			out.println("<center>");
			out.println("<h3> Welcome new user!<h3>");
			out.println("<span style='color:red;'>"+msg+"</span>");
			out.println("<form method=POST >");
			out.println("Full Name: <input type=text name=name>");
			out.println("Username: <input type=text name=uname1>");
			out.println("Password: <input type=text name=upass1>");
			out.println("<input type=submit value=Create Account");
			out.println("</form>");
			out.println("</center>");
			
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean canProceed = true;
		HttpSession sess = request.getSession(true);
		if(!Helper.isValueValid(request.getParameter("uname1"))) {
			canProceed=false;
		}
		if(!Helper.isValueValid(request.getParameter("upass1"))) {
			canProceed=false;
		}
		
		
		
		String name = request.getParameter("name");
		String uname1 = request.getParameter("uname1");
		String upass1 = request.getParameter("upass1");
		int uid1 = Helper.createUser(name, uname1, upass1);
		
		
		if(canProceed&&uid1==1) {
			response.sendRedirect("Form?msg=Account Created! You can now log in");
		}
		else {
			response.sendRedirect("CreateAccount?msg=Your values are incorrect. Account could not be created");
		}
		
	}

}
