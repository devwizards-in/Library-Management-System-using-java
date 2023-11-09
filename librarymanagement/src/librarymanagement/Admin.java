package librarymanagement;

import java.util.*;
import java.sql.SQLException;

public class Admin extends Database{
	
	Scanner sc = new Scanner(System.in);
	
	public Admin() {
		super();
	}
	
	// ------------------------------ Admin Verification ------------------------------ 
	public boolean adminVerification(String name, String password) {
		try {
            preparedStatement = connection.prepareStatement("SELECT name FROM admin WHERE name =? and password=?");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            	return true;
            else 
            	return false;
		} catch (SQLException e) {
			return false;
		}
	}
	
	// ----------------------------------- Display users -----------------------------------
	public void userDisplay() {
		try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");
            System.out.println("\n- - - - - - - - - - - - - - - - -");
            System.out.println("| User Id |   Name     |  Email  |");
            System.out.println("- - - - - - - - - - - - - - - - -  ");
            while (resultSet.next())
            	System.out.printf("|   %-5s |   %-8s |  %-6s |\n", resultSet.getString("user_id"), resultSet.getString("name"), resultSet.getString("email"));
            System.out.println("- - - - - - - - - - - - - - - - - ");
        } catch (SQLException e) {
            System.out.println(e);
        }
	}
	
	// ----------------------------------- Display books -----------------------------------
	public void bookDisplay() {
		try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM books");
            System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("| Book ID |     Title            |    Author      |   Genre  | Publication Year  |  Borred Count |  Book Reserved  |");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            while (resultSet.next())
            	System.out.printf("| %-7s | %-20s | %-14s | %-8s |    %-12s   |    %-7s    |      %-7s    |\n", resultSet.getString("book_id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("genre"),resultSet.getString("publication_year"),resultSet.getInt("borredcount"),resultSet.getString("reserve"));
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        } catch (SQLException e) {
            System.out.println(e);
        }
	}
	
	// ----------------------------------- Display Journals -----------------------------------
	public void journalsDisplay() {
		try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM journals");
            System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("| Journal Id | Title                                    | Field Of Study | Publication Year  |  Borred Count |  Journal Reserved |");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            while (resultSet.next())
            	System.out.printf("| %-10s | %-40s | %-14s | %-17s |    %-7s    |        %-7s    |\n", resultSet.getString("journal_id"), resultSet.getString("title"), resultSet.getString("field_of_study"), resultSet.getString("publication_year"),resultSet.getInt("borredcount"),resultSet.getString("reserve"));
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        } catch (SQLException e) {
            System.out.println(e);
        }
	}
	
	// ----------------------------------- Add book -----------------------------------
	public void addBook() {
		try {
	        preparedStatement = connection.prepareStatement("INSERT INTO books VALUES (?,?,?,?,?,?)");
	        System.out.println("Enter Book Id : ");
            preparedStatement.setString(1,sc.next());
            System.out.println("Enter Book Title : ");
            sc.nextLine();
	        preparedStatement.setString(2,sc.nextLine());
	        System.out.println("Enter Book Author : ");
	        preparedStatement.setString(3,sc.nextLine());
	        System.out.println("Enter Book Genre : ");
	        preparedStatement.setString(4,sc.next());
	        System.out.println("Enter Book Publication Year : ");
	        preparedStatement.setInt(5,sc.nextInt());
	        System.out.println("Enter Book Borred Count: ");
	        preparedStatement.setInt(6,sc.nextInt());
	        preparedStatement.executeUpdate();
	        System.out.println("\nBook Added successfully\n\n");
            
		} catch (SQLException e) {
			System.out.println("Book Not Added");
		}
	}
	
	// ----------------------------------- Add Journal -----------------------------------
	public void addJournals() {
		try {
	        preparedStatement = connection.prepareStatement("INSERT INTO journals VALUES (?,?,?,?,?)");
	        System.out.println("Enter Journal Id : ");
            preparedStatement.setString(1,sc.next());
            System.out.println("Enter Journal Title : ");
            sc.nextLine();
	        preparedStatement.setString(2,sc.nextLine());
	        System.out.println("Enter Journal Field Of Study : ");
	        preparedStatement.setString(3,sc.next());
	        System.out.println("Enter Journal Publication Year : ");
	        preparedStatement.setInt(4,sc.nextInt());
	        System.out.println("Enter Journal Borred Count: ");
	        preparedStatement.setInt(5,sc.nextInt());
	        preparedStatement.executeUpdate();
	        System.out.println("\nJournal Added successfully\n\n");
            
		} catch (SQLException e) {
			System.out.println("Journal Not Added");
		}
	}
	
	
}
