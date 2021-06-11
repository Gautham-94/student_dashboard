package school;

import java.sql.*;
public class Database {
	public static Connection connection;
	public void initializeDB() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school","root","root");
//		Statement st  =  connection.createStatement();
//		ResultSet rs = st.executeQuery("SELECT * FROM students");
//		while(rs.next()) {
//			String name = rs.getString("name");
//			System.out.println(name);
//		}
	}
}
