package item_manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession sess = request.getSession(false);
		if(sess == null) {
			response.sendRedirect("Form?msg=must login first");
		}
		else {
			// session exists, but check if the user is logged in.
			if(sess.getAttribute("uid") != null) {
				// user previously logged in, the user name value is stored in the session
				int uid = (Integer) sess.getAttribute("uid");
				DB_Access db = new DB_Access();
				String uname = db.getUserName(uid);
				
				out.println("<center><table width=70%>");
				out.println("<tr>");
				out.println("	<td><h2>Welcome "+uname+"</h2></td>");
				out.println("	<td align=right>");
				out.println("		<a href=ModifyAccount>Modify</a> ");
				out.println("		<a href=LogOut>Logout</a>");
				out.println("	</td>");
				out.println("</tr>");
				out.println("<tr><td colspan=2 align=center>");
				
				ArrayList<Item> all = db.getAllUserItems(uid);
				
				out.println("<h2>All User Items</h2>");
				out.println("<table>");
				out.println("<tr><th>ACTIONS</th><th>NAME</th><th>QTY</th></tr>");
				for(Item i : all) {
					out.println("<tr>");
					out.println("	<td>");
					out.println("		<a href=ViewItem?iid="+i.getIid()+">View</a> ");
					out.println("		<a href=ModifyItem?iid="+i.getIid()+">Modify</a> ");
					out.println("		<a href=DeleteItem?iid="+i.getIid()+">Delete</a>");
					out.println("	</td>");
					out.println("	<td>"+i.getItemName()+"</td>");
					out.println("	<td>"+i.getQty()+"</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				
				out.println("<h2>Add New Item</h2>");
				
				String msg = "";
				if(request.getParameter("msg") != null) msg = request.getParameter("msg");
				
				String iqty= "", iname = "";
				if(sess.getAttribute("iname") != null) {
					iname = (String) sess.getAttribute("iname");
					sess.removeAttribute("iname");
				}
				if(sess.getAttribute("iqty") != null) {
					iqty = (String) sess.getAttribute("iqty");
					sess.removeAttribute("iqty");
				}
				
				out.println("<h3 style='color:red;'>"+msg+"</h3>");
				out.println("<form method=post action=AddItem>");
				out.println("Item Name: <input type=text name=iname value=\""+iname+"\"><br>");
				out.println("Item Quantity: <input type=text name=iqty value=\""+iqty+"\"><br>");
				out.println("<input type=submit value='Add Item'>");
				out.println("</form>");
				
				
				out.println("</td></tr>");
				out.println("</table></center>");
				
			}
			else {
				// not a valid login, send the user back to the login form
				response.sendRedirect("Form?msg=must login first");				
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
