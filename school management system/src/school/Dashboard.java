package school;

public class Dashboard {
	static Menu menu = new Menu();
	static Database db = new Database();
	
	public static void main(String args[]) throws Exception {
		db.initializeDB();
		System.out.println("Hello world");
		menu.presentMenu();
		
		
	}

}
