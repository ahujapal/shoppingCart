package item_manager;

public class Helper {
	static DB_Access db = new DB_Access();
	
	public static boolean isValueValid(String val) {
		boolean isValid = true;
		
		if(val.trim().equalsIgnoreCase("")) isValid = false;
		if(val.trim().length() < 5) isValid = false;
		
		return isValid;
	}
	public static boolean isItemNameValid(String val) {
		boolean isValid = true;
		if(val.trim().equalsIgnoreCase("")) isValid = false;
		return isValid;
	}
	
	public static boolean isItemQtyValid(String val) {
		boolean isValid = true;
		try {
			Integer.parseInt(val);
		}
		catch(Exception e) {
			isValid = false;
		}
		return isValid;
	}
	
	public static int validateNamePass(String uname, String upass) {		
		int uid = db.checkUserLogin(uname, upass);
		
		return uid;
	}
	
	public static int createUser(String name, String uname, String upass) throws IllegalArgumentException{

		try{
		
				int uid2 = db.createUser(uname,name, upass);
				return uid2;
				
		}
			catch (IllegalArgumentException e) {
				System.out.print("Invalid values");
				return -1;
			}
			
			
			
	}
	
	public static Item getItemdetails(int uid,int iid) {
		return db.getItemDetails(uid, iid);
		}
}







