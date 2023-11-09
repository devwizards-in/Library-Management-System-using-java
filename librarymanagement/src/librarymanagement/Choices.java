package librarymanagement;
import java.util.*;

public class Choices {
	
	User u = new User();
	Scanner sc = new Scanner(System.in);
	
	void showAllBooks(String userEmail) {
		outer:while(true) {
			System.out.println("\n\n------------------ Books ------------------");
			u.allBooks();
			System.out.println("\nTo borrowing books enter Book Id :");
			System.out.println("\tOr");
			System.out.println("a. Search Book");
			System.out.println("b. Back to home page");
			System.out.print("Enter your choice: ");
			String ch = sc.next();
			if(ch.length()==1) {
				switch(ch) {
				case "a":
					inner:while(true) {
						System.out.println("\n\n1. Search book by Title ");
						System.out.println("2. Search book by  Author");
						System.out.println("3. Total number of users who have borred books : ");
						System.out.println("4. Back to Books page ");
						System.out.println("5. Back to Home page ");
						System.out.print("Enter your choice number:  ");
						int num = sc.nextInt();
						switch(num) {
						case 1:
							System.out.print("Enter Title : ");
							sc.nextLine();
							String title  = sc.nextLine();
							u.bookTitleSearch(title);
							break;
						case 2:
							System.out.print("Enter Title : ");
							sc.nextLine();
							String author  = sc.nextLine();
							u.bookAuthorSearch(author);
							break;
						case 3:
							System.out.print("Enter genre : ");
							String genre = sc.next();
							u.specificgenrecount(genre);
							break;
						case 4:
							break inner;
						case 5:
							break outer;
						default:
							System.out.println("Enter valid choice");
						}
					}
					 break;
				case "b":
					break outer;
				}
			}
			else if(ch.length()==2)
				u.borrowingBooks(ch,userEmail);
			else
				System.out.println("Enter Valid Choice!");
		}
	}
	
	void showAllJournal(String userEmail) {
		outer:while(true) {
			System.out.println("\n\n------------------------------------ Journal ------------------------------------");
			u.allJournals();
			System.out.println("\nTo borrowing journal enter Journal number :");
			System.out.println("\tOr");
			System.out.println("a. Search Journal");
			System.out.println("b. Back to home page");
			System.out.print("Enter your choice: ");
			String ch = sc.next();
			if(ch.length()==1) {
				switch(ch) {
				case "a":
					inner:while(true) {
						System.out.println("\n\n1. Search journal by Title ");
						System.out.println("2. Search journal by Field of study");
						System.out.println("3. Most frequently borroed journals ");
						System.out.println("4. Back to journal page ");
						System.out.println("5. Back to Home page ");
						System.out.print("Enter your choice number:  ");
						int num = sc.nextInt();
						switch(num) {
						case 1:
							System.out.print("Enter Title : ");
							sc.nextLine();
							String title  = sc.nextLine();
							u.journalTitleSearch(title);
							break;
						case 2:
							System.out.print("Enter Field of study : ");
							sc.nextLine();
							String field  = sc.nextLine();
							u.journalFieldOfStudySearch(field);
							break;
						case 3:
							u.mostFrequentBorroedField();
							break;
						case 4:
							break inner;
						case 5:
							break outer;
						default:
							System.out.println("Enter valid choice");
						}
					}
					 break;
				case "b":
					break outer;
				}
			}
			else if(ch.length()==2)
				u.borrowingJournals(ch,userEmail);
			else
				System.out.println("Enter Valid Choice!");
		}
   }
}
