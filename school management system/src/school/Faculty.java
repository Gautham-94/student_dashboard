package school;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
class GradeCard{
	@Override 
	public String toString(){
		return "name : "+name+"\n"+"Standard : "+standard+"\n"+"Roll no : "+roll_no+"\n"+"Guardian : "+guardian+"\n"+"Phone : "+phone+"\n"+english+"\n"+maths+"\n"+science+"\n"+grade+"\n";
	}
	String name;
	String standard;
	int roll_no;
	String guardian;
	String phone;
	int english;
	int maths;
	int science;
	String grade;
}


public class Faculty {
	String[] menuOptions = {"Add new standard","Delete a standard","Add Student to a class", "Delete Student from a class","Insert mark","Generate grade card for a class","Back"};
	String fetchData= "SELECT std_id,roll_no FROM students where class LIKE ?";
	public void presentFacultyMenu() throws SQLException {
//		Statement st  =  Database.connection.createStatement();
		PreparedStatement fetchdata = Database.connection.prepareStatement(fetchData);
		System.out.println("++++ Faculty Menu +++:");
		Scanner scanner = new Scanner(System.in);
		for(int i=0;i<menuOptions.length;i++) {
			int index = i+1;
			System.out.println(index+". "+menuOptions[i]);
		}
		
		loop:while(true) {
			System.out.println("\nEnter an option from Faculty Menu : ");
			
			try {
				int selectedOption = scanner.nextInt();
				switch(selectedOption) {
				
				
				case 1:System.out.println("Add new student class \n");
				System.out.println("Enter the standard \n");
				scanner.nextLine();
				String standard = scanner.nextLine();
				System.out.println(standard);
				String createnNewClass= "CREATE TABLE class_"+standard+" (std_id int, roll_no int PRIMARY KEY,english int,maths int,science int,grade varchar(1))";
				
				PreparedStatement addNewClass = Database.connection.prepareStatement(createnNewClass);
				int result = addNewClass.executeUpdate();
				if(result == 1) {
					System.out.println("Successfully created class"+standard);
				}
				fetchdata.setString(1,standard);
				ResultSet rs = fetchdata.executeQuery();
				String initialData= "INSERT INTO class_"+standard+"(std_id,roll_no) values(?,?)";
				PreparedStatement initialDataInsert = Database.connection.prepareStatement(initialData);
				while(rs.next()) {
					initialDataInsert.setInt(1, rs.getInt("std_id"));
					initialDataInsert.setInt(2, rs.getInt("roll_no"));
					int resultInsertInitial = initialDataInsert.executeUpdate();
					if(resultInsertInitial == 1) {
						System.out.println("Successfully initialized one record");
					}
				}
				break;
				
				
				
				case 5:System.out.println("Enter class  of the student\n");
				scanner.nextLine();
				standard = scanner.nextLine();
				System.out.println("Enter roll no of the student\n");
				String insertMarks = "UPDATE class_"+standard+" SET english= ?,maths= ?,science= ?,grade = ? WHERE roll_no = ?";
				PreparedStatement insertStudentMarks = Database.connection.prepareStatement(insertMarks);
				int roll_no = scanner.nextInt();
				HashMap<String,Integer> marks = new HashMap<String,Integer>();
				String [] subjects = {"English","Maths","Science"};
				
				for(String sub:subjects) {
					System.out.println("Enter the mark of"+sub+":");	
					int mark = scanner.nextInt();
					marks.put(sub, mark);	
				}
				
				String grade = calculateGrade(marks.get("English"),marks.get("Maths"),marks.get("Science")); 
				
				System.out.println("Marks for each subject");	
				for(String i:marks.keySet()) {
					System.out.println(i+" = "+marks.get(i));
				}
					
				insertStudentMarks.setInt(1,marks.get("English"));
				insertStudentMarks.setInt(2,marks.get("Maths"));
				insertStudentMarks.setInt(3,marks.get("Science"));
				insertStudentMarks.setString(4,grade);
				insertStudentMarks.setInt(5,roll_no);
				int updateMarks = insertStudentMarks.executeUpdate();
				if(updateMarks ==1) {
					System.out.println("Marks updated successfully");
				}
				break;
				
				case 6:System.out.println("Generate grade card for a class\n");
				System.out.println("Enter class  of the student\n");
				scanner.nextLine();
				standard = scanner.nextLine();System.out.println("Enter roll number of the student\n");
				roll_no = scanner.nextInt();
				System.out.println(standard+roll_no);
				String g_card = "SELECT s.name,s.guardian, s.phone, s.class,c.roll_no,c.maths,c.science,c.english,c.grade FROM class_"+standard+" "+"AS c JOIN students AS s ON c.std_id = s.std_id";
				PreparedStatement generate_card = Database.connection.prepareStatement(g_card);
				ResultSet g_card_details  = generate_card.executeQuery();
				ArrayList<GradeCard> grade_card = new ArrayList<GradeCard>();
				while(g_card_details.next()) {
					GradeCard g =new GradeCard();
					g.name  = g_card_details.getString("name");
					g.guardian  = g_card_details.getString("guardian");
					g.phone  = g_card_details.getString("phone");
					g.roll_no  = g_card_details.getInt("roll_no");
					g.english  = g_card_details.getInt("english");
					g.maths  = g_card_details.getInt("maths");
					g.science  = g_card_details.getInt("science");
					g.grade  = g_card_details.getString("grade");
					grade_card.add(g);
				}
				for(GradeCard x:grade_card) {
					System.out.println(x);
				}
				break;
				
				case 7:System.out.println("BACK\n");
				scanner.close();
//				return(2);
				break loop;
				
				
				default:System.out.println("Please select a valid faculty menu option.\n");
				
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}
	
	public static String calculateGrade(int a,int b,int c) {
		float sum = a+b+c;
		System.out.println(sum+" sum");
		float percentage = (sum/300)*100;
		String grade;
		System.out.println(percentage+" percentage");
		if(percentage>=90) {
			grade = "A";
		}
		else if(percentage>=80 && percentage<90) {
			grade = "B";
		}
		else if(percentage>=50 && percentage<80) {
			grade = "C";
		}
		else {
			grade = "F";
		}
		
		return(grade);
	}
}
