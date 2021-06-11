package school;

import java.util.Scanner;

public class Menu {
	String[] menuOptions= {"Students","Faculty","Exit"};
	static Student students = new Student();
	static Faculty faculty = new Faculty();
	
	public void presentMenu() {
		
		while(true) {
			System.out.println("++++ Menu +++:");
			for(int i=0;i<menuOptions.length;i++) {
				int index = i+1;
				System.out.println(index+". "+menuOptions[i]);
			}
			System.out.println("Enter an option : ");
			Scanner scanner = new Scanner(System.in);
			int selectedOption = scanner.nextInt();
			System.out.println(" selected option:"+selectedOption);
			try {
				switch(selectedOption) {
				case 1:System.out.println("Students selected\n");
				students.presentStudentMenu();
				break;
				case 2:System.out.println("Faculty selected\n");
				faculty.presentFacultyMenu();
				break;
				case 3:System.out.println("Application is exiting\n");
				System.exit(0);
				scanner.close();
				break;
				default:System.out.println("Please select a valid menu option.\n");
				}	
			}
			catch(Exception e){
				System.out.println(selectedOption+"Please select a valid menu option IN EXCEPTION.\n");
				System.out.println(e+"Please select a valid menu option IN EXCEPTION.\n");
			}
			
		}
		
	}
	
}
