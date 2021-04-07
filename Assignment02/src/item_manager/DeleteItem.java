package item_manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteItem")
public class DeleteItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DB_Access db = new DB_Access();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession sess = request.getSession(true);
		if(sess.getAttribute("uid") == null) {
			// user bypassed the login page. send the user back to loginpage
			response.sendRedirect("Login?msg=must login first");
		} else {
			int uid = (Integer) sess.getAttribute("uid");
			//String itemName = sess.getAttribute("iname").toString();
			
			String msg = "";
			if(request.getParameter("msg") != null) {
				msg = request.getParameter("msg");
			}
			
		int id= Integer.parseInt(request.getParameter("iid"));
		out.println("<center>");
		out.println("<form method=post>");
		out.println("<h1>Item Deleted!</h1><br>");
		out.print("<input type=submit name=ok value=OK>");
		out.print("</form>");
		out.print("</center>");
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id= Integer.parseInt(request.getParameter("iid"));
		if(request.getParameter("ok")!=null) {
			db.DeleteItem(id);
			response.sendRedirect("Home?msg=Item successfully deleted");
		}
		else if (request.getParameter("no")!=null) {
			response.sendRedirect("Home?msg=item still exists");
		}
	}
	

}
