package item_manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ModifyAccount")
public class ModifyAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession sess = request.getSession(false);
		if(sess.getAttribute("uid")==null) {
			response.sendRedirect("Form?msg=must login first");
			
		}
		else {
			int uid = (Integer)sess.getAttribute("uid");
			DB_Access db = new DB_Access();
			String msg = "";
			if(request.getParameter(msg)!=null) {
				msg = request.getParameter("msg");
				
			}
			
			out.println("<center>");
			out.println("<span style='color:red;'>"+msg+"</span>");
			out.println("<form method=post>");
			out.println("User Name : <input type=text name=uname><br>");
			out.println("Password : <input type=text name=upass><br>");
			out.println("<input type=submit value=Go>");
			out.println("</form>");
			out.println("</center>");
		
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean canProceed = true;
		HttpSession sess = request.getSession(false);
		if(!Helper.isValueValid(request.getParameter("uname"))) {
			canProceed=false;
		}
		if(!Helper.isValueValid(request.getParameter("upass"))) {
			canProceed=false;
		}
		
		if(canProceed) {
			int uid = (int) sess.getAttribute("uid");
			String userName = request.getParameter("uname");
			String userPass = request.getParameter("upass");
			DB_Access db = new DB_Access();
			boolean success = db.modifyAccount(uid, userName, userPass);
			if(success) {

				response.sendRedirect("Home?msg=Values updated.");
			}
		}
		else {
			response.sendRedirect("Home?msg=values are wrong, try again");
		}
	}
}
