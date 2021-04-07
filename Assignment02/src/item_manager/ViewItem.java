package item_manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ViewItem")
public class ViewItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DB_Access db = new DB_Access();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession sess = request.getSession(true);
		
		if(sess.getAttribute("uid") == null) {
			response.sendRedirect("Login?msg=must login first");
		} 
		else {
			int uid = (Integer) sess.getAttribute("uid");
			String iid = request.getParameter("iid");
			int id = Integer.parseInt(iid);
			Item item =Helper.getItemdetails(uid,id);
			
			String iName = item.getItemName();
			int iQty = item.getQty();
			
			
			
			String msg = "";
			if(request.getParameter("msg") != null) {
				msg = request.getParameter("msg");
			}
			
		out.println("<center>");
		out.println("<a href=Home> Back </a><br>");
		out.println("ITEM NAME: " + iName + "<br>");
		out.println("ITEM QUANTITY: " + iQty + "<br>");
		out.print("</center>");
		}
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
