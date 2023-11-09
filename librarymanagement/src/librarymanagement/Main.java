package librarymanagement;
import java.sql.ResultSet;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		Admin a = new Admin();
		
		// User class
		User u = new User();
		
		Choices ch = new Choices();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("1. Admin");
		System.out.println("2. User");
		System.out.println("Enter your choice number : ");
		int flag = sc.nextInt();
		
		// Admin
		if(flag == 1) {
			System.out.println("Welcome to Our Library\n");
			System.out.print("Enter Your Name : ");
			String name = sc.next();
			System.out.print("Enter Your Password : ");
			String password = sc.next();
			if(a.adminVerification(name,password)) {
				System.out.println("---------- Admin ---------- ");
				outer:while(true) {
					System.out.println("1. Users");
					System.out.println("2. Display Books");
					System.out.println("3. Add Book");
					System.out.println("4. Display journals");
					System.out.println("5. Add journal");
					System.out.println("6. Exit");
					System.out.println("\nEnter your choice number ");
					int num = sc.nextInt();
					switch(num) {
					case 1:
						a.userDisplay();
						break;
					case 2:
						a.bookDisplay();
						break;
					case 3:
						a.addBook();
						break;
					case 4:
						a.journalsDisplay();
						break;
					case 5:
						a.addJournals();
						break;
					case 6:
						break outer;
					default:
						System.out.println("\nInvalid choice!!\n");
					}
				}
			}
			
			
		}
		else if(flag == 2){
			// Taking name and email from user
			String userName,userEmail;
			System.out.println("Welcome to Our Library\n");
			System.out.print("Enter Your Name : ");
			userName = sc.next();
			System.out.print("Enter Your Email : ");
			userEmail = sc.next();
			
			if(!userName.isEmpty() && !userEmail.isEmpty()) {
				// If user name and email is not null then check user is already exists or not
				String name = u.fetchusername(userEmail);
				if(name.equals("")) {
					// New user data store in database
					String[] data = new String[2];
					data[0]= userName;
					data[1]= userEmail;
					u.insertIntoUser(data);
					System.out.print("\nNew User is Register");
				}
				outer:while(true) {
					System.out.println("\n\n------------------------ Welcome to the Home page ------------------------ ");
					System.out.println("1. Books ");
					System.out.println("2. Journals");
					System.out.println("3. Borrowing list");
					System.out.println("4. Returning Book or Journal");
					System.out.println("5. Reserve or free Book or Journal");
					System.out.println("6. Exit");
					System.out.print("Enter your choice number : ");
					switch(sc.nextInt()) {
					case 1:
						ch.showAllBooks(userEmail);
						break;
					case 2:
						ch.showAllJournal(userEmail);
						break;
					case 3:
						u.borrowingList(userEmail);
						break;
					case 4:
						u.borrowingList(userEmail);
						System.out.println("\n\nEnter ID to return book or journal");
						u.returnBookJournal(userEmail,sc.next());
						break;
					case 5:
						inner:while(true) {
							System.out.println("\n\n1. Reserve Book or journal");
							System.out.println("2. Free Book or journal");
							System.out.println("3. Back to Home page");
							System.out.println("Enter your choice number : ");
							int n = sc.nextInt();
							switch(n) {
							case 1:
								u.allBooks();
								u.allJournals();
								System.out.println("Book or journal Id");
								String id = sc.next();
								u.reserve(userEmail,id);
								break;
							case 2:
								break;
							case 3:
								break inner;
							default:
								System.out.println("Invalid Choice!!");
							}
						}
					case 6:
						break outer;
					}
				}
				
			}
			else 
				System.out.print("Email and Name are required");
		}
		else
			System.out.println("Invalid!!!");

	}

}
