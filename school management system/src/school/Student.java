package school;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
class StudentDetails{
	@Override 
	public String toString(){
		return name+" "+standard+" "+roll_no+" "+guardian+" "+phone;
	}
	String name;
	String standard;
	int roll_no;
	String guardian;
	String phone;
}

public class Student {
	static Menu menu = new Menu();
	String[] menuOptions = {"View All Students","Search Student","Add Student", "Delete Student","Add multiple students","Back"};
	String insertStudent= "INSERT INTO STUDENTS(name,class,roll_no,guardian,phone) VALUES(?,?,?,?,?)";
	String searchStudent= "SELECT * FROM  STUDENTS WHERE LOWER(name) LIKE LOWER(?) OR LOWER(guardian) LIKE LOWER(?)";
	public void presentStudentMenu() throws SQLException {
		System.out.println("++++ Student Menu +++:");
		PreparedStatement insert = Database.connection.prepareStatement(insertStudent);
		PreparedStatement search  = Database.connection.prepareStatement(searchStudent);
		for(int i=0;i<menuOptions.length;i++) {
			int index = i+1;
			System.out.println(index+". "+menuOptions[i]);
		}
		
		loop:while(true) {
			System.out.println("\nEnter an option from student Menu : ");
			Scanner scanner = new Scanner(System.in);
			try {
				int selectedOption = scanner.nextInt();
				switch(selectedOption) {
				case 1:System.out.println("view all student details \n");
				Statement st  =  Database.connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM students");
//				System.out.println("Student Id\tName\tStandard\tRoll No\tGuardian\tPhone");
				try {
					while(rs.next()) {
						Integer id = rs.getInt("std_id");
						String name = rs.getString("name");
						String standard = rs.getString("class");
						Integer roll_no = rs.getInt("roll_no");
						String guardian = rs.getString("guardian");
						String phone = rs.getString("phone");
					
					System.out.println(id+"\t"+name+"\t"+standard+"\t"+roll_no+"\t"+guardian+"\t"+phone);
					}	
				}
				catch(Exception e){
					System.out.println(e);
				}
				
				break;
				case 2:System.out.println("Search selected\n");
				System.out.println("Enter name of the student or guardian.\n");
				scanner.nextLine();
				String keyword = scanner.nextLine();
				System.out.println("Searching for  "+keyword);
				
				try {
					search.setString(1,"%"+keyword+"%");
					search.setString(2,"%"+keyword+"%");
					ResultSet searchResult = search.executeQuery();

				
					try {
						if(searchResult.next()) {
							do {
								Integer id = searchResult.getInt("std_id");
								String name = searchResult.getString("name");
								String standard = searchResult.getString("class");
								Integer roll_no = searchResult.getInt("roll_no");
								String guardian = searchResult.getString("guardian");
								String phone = searchResult.getString("phone");
							
							System.out.println(id+"\t"+name+"\t"+standard+"\t"+roll_no+"\t"+guardian+"\t"+phone);
							}
							while(searchResult.next());
						}
						else {
							System.out.print("No student records found modify your search");
						}
						
					}
					catch(Exception e){
						System.out.print(e);
					}
					
					
				}
				catch(Exception e){
					System.out.print(e);
				}
				break;
				case 3:System.out.println("Add selected\n");
				try {
					System.out.println("Enter student name:");
				
					insert.setString(1, "Arun");
					insert.setString(2, "x");
					insert.setInt(3, 5);
					insert.setString(4, "Kumar");
					insert.setString(5, "9946188789");
					int result = insert.executeUpdate();
					System.out.println(result);
				}
				catch(Exception e){
					System.out.println(e);
				}
				break;
				case 4:System.out.println("Delete selected\n");
				break;
				case 5:System.out.println("ADD MULTIPLE STUDENT DETAILS\n");
				ArrayList<StudentDetails> student = new ArrayList<StudentDetails>();
				while(true) {
					
					System.out.println("Enter 1 to add new or 2 to submit all and exit to menu\n");
					StudentDetails sd = new StudentDetails();
					int option = scanner.nextInt();
					scanner.nextLine();
					String[] keys = {"name","guardian","phone","standard","roll no"};
					
					if(option ==2) {
						for(StudentDetails x:student) {
							try {
							
								insert.setString(1, x.name);
								insert.setString(2, x.standard);
								insert.setInt(3, x.roll_no);
								insert.setString(4, x.guardian);
								insert.setString(5, x.phone);
								int result = insert.executeUpdate();
								if(result == 1) {
									System.out.println("Successfully added one record");
								}
							}
							catch(Exception e){
								System.out.println(e);
							}
						}
						break;
					}
					else {
						for(String item:keys) {
							System.out.println("Enter "+item+" \n");
							if(item =="name") {
								sd.name = scanner.nextLine();
							}
							else if(item == "guardian") {
								sd.guardian = scanner.nextLine();
							}
							else if(item == "phone") {
								sd.phone = scanner.nextLine();
							}
							else if(item == "standard") {
								sd.standard = scanner.nextLine();
							}
							else if(item == "roll no") {
								sd.roll_no = scanner.nextInt();
							}
						}
						student.add(sd);
//						System.out.println(student.get(0).name);
						
						
//						System.out.println("Enter name:\t");
//						sd.name = scanner.nextLine();
//						System.out.println("Enter Gauardian name:\t");
//						sd.guardian = scanner.nextLine();
//						System.out.println("Enter phone:\t");
//						sd.phone = scanner.nextLine();
//						System.out.println("Enter standard:\t");
//						sd.standard = scanner.nextLine();
//						System.out.println("Enter roll number:\t");
//						sd.roll_no = scanner.nextInt();
//						student.add(sd);
					}
				}
				break ;
				case 6:System.out.println("BACK\n");
				scanner.close();
				break loop;
				
				default:System.out.println("Please select a valid student menu option.\n");
				
				}	
			}
			catch(Exception e){
				System.out.println("Please select a student valid menu option.\n");
			}
			
		}
		return;
		
	}
	
	
	public void addStudent() {
		
	}

	
}
