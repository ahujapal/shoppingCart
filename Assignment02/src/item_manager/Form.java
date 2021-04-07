package item_manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Form")
public class Form extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String msg = "";
		if(request.getParameter("msg") != null) {
			msg = request.getParameter("msg");
		}
		out.println("<center>");
		out.println("<span style='color:red;'>"+msg+"</span>");
		out.println("<form method=POST action=Form>");
		out.println("<h2>Login Form</h2>");
		out.println("User Name: <input type=text name=uname><br>");
		out.println("User Pass: <input type=text name=upass><br>");
		out.println("<input type=submit value=LoginIn><br>");
		out.println("<a href=CreateAccount> Create new Account </a>");
		out.println("</form>");
		out.println("</center>");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean canProceed = true;
		if(!Helper.isValueValid(request.getParameter("uname"))) {
			canProceed = false;
		}
		if(!Helper.isValueValid(request.getParameter("upass"))) {
			canProceed = false;
		} 
		if(canProceed) {
			// values received from the form are valid, use them
			// initial validation has passed, now we need to see if the
			// values of user name and password are correct
			String uname = request.getParameter("uname");
			String upass = request.getParameter("upass");
			
			int uid = Helper.validateNamePass(uname, upass);
			
			if(uid != -1) {
				// values are ok, proceed to the Home page
				HttpSession sess = request.getSession(true);
				sess.setAttribute("uid", uid);
				response.sendRedirect("Home");
			}
			else {
				// user name and password values are wrong. show the login
				// form with error message again.
				response.sendRedirect("Form?msg=either user name or password is wrong, try again");
			}
		}
		else {
			// values received from the form are NOT valid
			response.sendRedirect("Form?msg=either user name or password is wrong, try again");
		}
	}
}









