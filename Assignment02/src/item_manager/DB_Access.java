package item_manager;

import java.sql.*;
import java.util.ArrayList;

public class DB_Access implements DB_Vars {
	private static Connection con;
	private static Statement st;
	private PreparedStatement pst;
	private ResultSet rs1;
	private ResultSetMetaData rsmd1;
	
	
	public DB_Access() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, uname, upass);
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int checkUserLogin(String uname, String upass) {
		int uid = -1; // -1 means the login was not successfull
		
		String sql = "select uid from t_users "+
						"where loginname = '"+uname+"' "+
						"and loginpass = '"+upass+"'";
		
		try {
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				uid = rs.getInt(1);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uid;
	}
	
	public String getUserName(int uid) {
		String sql = "select name from t_users where uid = " + uid;
		String name = "";
		
		try {
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				name = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return name;
	}
	
	public ArrayList<Item> getAllUserItems(int uid) {
		ArrayList<Item> all = new ArrayList<Item>();
		
		String sql = "select iid, itemname, qty, uid from t_items " +
					"where uid = " + uid;
		
		ResultSet rs;
		try {
			rs = st.executeQuery(sql);
			while(rs.next()) {
				all.add(new Item(rs.getInt(1), 
								rs.getString(2), 
								rs.getInt(3), 
								rs.getInt(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return all;
	}
	
	public boolean insertItem(Item i) {
		boolean success = true;
		
		String sql = "insert into t_items(itemname, qty, uid) values(?, ?, ?)";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, i.getItemName());
			pst.setInt(2, i.getQty());
			pst.setInt(3, i.getUid());
			if(pst.executeUpdate() == 0) success = false;
		} catch (SQLException e) {
			success = false;
			e.printStackTrace();
		}
		
		
		return success;
	}
	
	public static int createUser(String name, String uname, String upass){
		try {	
			String sql = "INSERT INTO `t_users` (`uid`, `LoginName`, `Name`, `LoginPass`) VALUES (NULL, ?, ?,?);";
			PreparedStatement ps = con.prepareStatement(sql);	           		
			ps.setString(1, name);
			ps.setString(2, uname);
			ps.setString(3, upass);
			ps.executeUpdate();
		return 1;
		}
		catch (Exception e) {
			System.out.print("There is a problem");
		
		return -1;
	
		}
	}
	
	public static boolean ModifyItem(String itemName, int itemQty, int iid) throws Exception{
		boolean success = true;
		
		String sql = "UPDATE `t_items` SET `ItemName` = '"+itemName+"', `Qty` = '"+itemQty+"' WHERE `t_items`.`iid` = "+iid;		
		try {
			int res = st.executeUpdate(sql);
			if(res == 0) success = false;
		} catch (Exception e) {
			success = false;
			}
		
		
		return success;
	}
	
	public boolean modifyAccount(int uid, String userName, String loginPass) {
		boolean success = true;
		
		String sql = "UPDATE `t_users` SET `LoginName` = '"+userName+"', `loginPass` = '"+loginPass+"' WHERE `t_users`.`uid` = "+uid;		
		try {
			int res = st.executeUpdate(sql);
			if(res == 0) success = false;
		} catch (Exception e) {
			success = false;
		}
		
		
		return success;
	}
	
	public void DeleteItem(int iid) {

		
		String sql = "DELETE FROM `t_items` WHERE `t_items`.`iid` = "+iid;		
		try {
			int res = st.executeUpdate(sql);
			
		} catch (Exception e) {
			System.out.print("There is a problem");
			
		}
		
		
	}

public static Item getItemDetails(int uid, int iid) {
	
	String sql = "SELECT itemName, qty from t_items where iid = " + iid + " and uid = " + uid;
	
	ResultSet rs;
	Item item = new Item();
	item.setIid(iid);
	item.setUid(uid);
	try {
		rs = st.executeQuery(sql);
		rs.next();
		item.setItemName(rs.getString(1));
		item.setQty(rs.getInt(2));
		return item;
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.print("There is a problem");
		}
	return item;
	
}

}








