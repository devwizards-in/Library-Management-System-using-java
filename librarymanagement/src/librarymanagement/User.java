package librarymanagement;

import java.util.*;
import java.sql.SQLException;

public class User extends Database{

	Scanner sc = new Scanner(System.in);
	
	public User() {
		super();
	}

	
	//--------------------- Fetch user name to check new user or not ---------------------
			public String fetchusername(String email) {
				try {
		            preparedStatement = connection.prepareStatement("SELECT name FROM users WHERE email =?");
		            preparedStatement.setString(1,email);
		            resultSet = preparedStatement.executeQuery();
				   
		            while (resultSet.next()) {
				        String name = resultSet.getString("name");
				        return name;
				    }
				    return "";
				} catch (SQLException e) {
					return "";
				}
			}
			
			//--------------------- Insert data in user table  ---------------------
			public void insertIntoUser(String []arr) {

			    try {
			        preparedStatement = connection.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?)");
			        preparedStatement.setString(1, arr[0]);
			        preparedStatement.setString(2, arr[1]);
			        preparedStatement.executeUpdate();
			        preparedStatement.close();
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			}
			
			//  --------------------- Fetch All Books  ---------------------
			
			public void allBooks() {
				try {
		            statement = connection.createStatement();
		            resultSet = statement.executeQuery("SELECT book_id, title, author, genre, publication_year FROM books");
		            System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		            System.out.println("| Book ID |     Title            |    Author      |   Genre  | Publication Year  |");
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		            while (resultSet.next())
		            	System.out.printf("| %-7s | %-20s | %-14s | %-8s |    %-12s   |\n", resultSet.getString("book_id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("genre"),resultSet.getString("publication_year"));
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		        } catch (SQLException e) {
		            System.out.println(e);
		        }
			}
			
			
		//  ------------------------- Fetch All Journals -------------------------
			public void allJournals() {
				try {
		            statement = connection.createStatement();
		            resultSet = statement.executeQuery("SELECT journal_id, title, field_of_study, publication_year FROM journals");
		            System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		            System.out.println("| Journal Id | Title                                    | Field Of Study | Publication Year  |");
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		            while (resultSet.next())
		            	System.out.printf("| %-10s | %-40s | %-14s | %-17s |\n", resultSet.getString("journal_id"), resultSet.getString("title"), resultSet.getString("field_of_study"), resultSet.getString("publication_year"));
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		        } catch (SQLException e) {
		            System.out.println(e);
		        }
			}
			
			// ------------------------- Book borrowed entries -------------------------
			public void borrowingBooks(String ch,String userEmail) {
				try {
					// Get user id by email
					preparedStatement = connection.prepareStatement("SELECT user_id FROM users WHERE email =?");
		            preparedStatement.setString(1,userEmail);
		            resultSet = preparedStatement.executeQuery();
		            int userId=0;
		            while (resultSet.next()) {
				        userId = resultSet.getInt("user_id");
				    }
		            
		            // Enter data in borrowingbooks table
			        preparedStatement = connection.prepareStatement("INSERT INTO borrowingbooks (book_id, user_id) VALUES (?,?)");
		            preparedStatement.setString(1,ch);
			        preparedStatement.setInt(2,userId);
			        preparedStatement.executeUpdate();
			        
			        // Update borrowed count
			        preparedStatement = connection.prepareStatement("UPDATE books SET borredcount = borredcount+1 WHERE book_id =?");
			        preparedStatement.setString(1,ch);
			        preparedStatement.executeUpdate();
			        
			        System.out.println("you have borrowed the book");
		            
				} catch (SQLException e) {
					System.out.println("you already borrowed this book");
				}
			}
			
			// ------------------------- Journals borrowed entries -------------------------
			public void borrowingJournals(String ch,String userEmail) {
				try {
					// Get user id by email 
		            preparedStatement = connection.prepareStatement("SELECT user_id FROM users WHERE email =?");
		            preparedStatement.setString(1,userEmail);
		            resultSet = preparedStatement.executeQuery();
		            int userId=0;
		            while (resultSet.next()) {
				        userId = resultSet.getInt("user_id");
				    }
		            
		            // Enter data in borrowingjournals table
		            preparedStatement = connection.prepareStatement("INSERT INTO borrowingjournals (journal_id, user_id) VALUES (?, ?)");
			        preparedStatement.setString(1,ch);
			        preparedStatement.setInt(2,userId);
			        preparedStatement.executeUpdate();
			        
			        // Update borrowed count
			        preparedStatement = connection.prepareStatement("UPDATE journals SET borredcount = borredcount+1 WHERE journal_id =?");
			        preparedStatement.setString(1,ch);
			        preparedStatement.executeUpdate();
			        
			        System.out.println("\nyou have borrowed the journal\n\n");
				} catch (SQLException e) {
					System.out.println("\nyou already borrowed this journal\n\n");
				}
			}
			
		// ---------------------------------- User borrowed list ----------------------------------	
		public void borrowingList(String userEmail) {
			try {
				// Get user id by email
				preparedStatement = connection.prepareStatement("SELECT user_id FROM users WHERE email =?");
	            preparedStatement.setString(1,userEmail);
	            resultSet = preparedStatement.executeQuery();
	            int userId=0;
	            while (resultSet.next()) {
			        userId = resultSet.getInt("user_id");
			    }
				
	            //Display Borrowed Books
				preparedStatement = connection.prepareStatement("SELECT book_id, title, author, genre, publication_year FROM books where book_id in(SELECT book_id FROM borrowingbooks where user_id = ?)");
	            preparedStatement.setInt(1,userId);
	            resultSet = preparedStatement.executeQuery();
	            System.out.println("\n\n-------------------------------------- Borrowed books --------------------------------------");
	            if (resultSet.next()) {
	            	System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	 	            System.out.println("| Book ID |     Title            |    Author      |   Genre  | Publication Year  |");
	 	            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");            	
	 	            do {
	 	            	System.out.printf("| %-7s | %-20s | %-14s | %-8s |    %-12s   |\n", resultSet.getString("book_id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("genre"),resultSet.getString("publication_year"));            	}while (resultSet.next());
	 	           System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");   
	     		 }
	            else
	            	System.out.println("\nYou have not Borrowed any book\n");
	            
	            //Display Borrowed Journals
	            preparedStatement = connection.prepareStatement("SELECT journal_id, title, field_of_study, publication_year FROM journals where journal_id in(SELECT journal_id FROM borrowingjournals where user_id = ?)");
	            preparedStatement.setInt(1,userId);
	            resultSet = preparedStatement.executeQuery();
	            System.out.println("\n\n------------------------------- Borrowed Journals -------------------------------");
	            if (resultSet.next()) {
	            	System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		            System.out.println("| Journal Id | Title                                    | Field Of Study | Publication Year  |");
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	            	do {
	                	System.out.printf("| %-10s | %-40s | %-14s | %-17s |\n", resultSet.getString("journal_id"), resultSet.getString("title"), resultSet.getString("field_of_study"), resultSet.getString("publication_year"));
	            	}while (resultSet.next());
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	            }
	            else
	            	System.out.println("\nYou have not Borrowed any journal\n\n");
	            
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
		}
		
		
		//-------------------------------------- Returning Book or Journal --------------------------------------
		public void returnBookJournal(String userEmail, String id) {
			int userId=0;
			try {
				// Get user id by email
				preparedStatement = connection.prepareStatement("SELECT user_id FROM users WHERE email =?");
	            preparedStatement.setString(1,userEmail);
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()) {
			        userId = resultSet.getInt("user_id");
			    }
			}catch (SQLException e) {
	            System.out.println(e);
	        }
			
			// If Enter id is journals id
			if(id.charAt(0)=='j') {
				try {
					preparedStatement = connection.prepareStatement("DELETE FROM borrowingjournals where user_id=? and journal_id=?");
		            preparedStatement.setInt(1,userId);
		            preparedStatement.setString(2,id);
		            preparedStatement.executeUpdate();
		            journalReviewandRating(id,userId);
			        System.out.println("\nYou have returning Journal successfuly\n\n");
				}catch (SQLException e) {
		            System.out.println("\nYou have not borrowing journal\\n\\n");
		        }
			}
			
			// If Enter id is books id
			if(id.charAt(0)=='b') {
				try {
					preparedStatement = connection.prepareStatement("DELETE FROM borrowingbooks where user_id=? and book_id=?");
		            preparedStatement.setInt(1,userId);
		            preparedStatement.setString(2,id);
		            preparedStatement.executeUpdate();
		            
		            bookReviewandRating(id,userId);
		            
			        System.out.println("\nYou have returning Book successfuly\n\n");
				}catch (SQLException e) {
		            System.out.println("\nYou have not borrowing book journal\\n\\n");
		        }
			}
		}
		
		
		//--------------------- Search Operations on Book table ---------------------
		
		// Title wise Search
		public void bookTitleSearch(String title) {
			try {      	
	            preparedStatement = connection.prepareStatement("SELECT book_id, title, author, genre, publication_year FROM books where title=?");
	            preparedStatement.setString(1,title);
	            resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	            	System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	 	            System.out.println("| Book ID |     Title            |    Author      |   Genre  | Publication Year  |");
	 	            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"); 
	            	do {
	            		System.out.printf("| %-7s | %-20s | %-14s | %-8s |    %-12s   |\n", resultSet.getString("book_id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("genre"),resultSet.getString("publication_year"));            	}while (resultSet.next());
	            	System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"); 
	     		 }
	            else
	            	System.out.println("\nRecord not found\n");
	            
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
		}
		
		// Author wise search
		public void bookAuthorSearch(String author) {
			try {      	
	            preparedStatement = connection.prepareStatement("SELECT book_id, title, author, genre, publication_year FROM books where author=?");
	            preparedStatement.setString(1,author);
	            resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	            	System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	 	            System.out.println("| Book ID |     Title            |    Author      |   Genre  | Publication Year  |");
	 	            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"); 
	            	do {
	            		System.out.printf("| %-7s | %-20s | %-14s | %-8s |    %-12s   |\n", resultSet.getString("book_id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("genre"),resultSet.getString("publication_year"));            	}while (resultSet.next());
	            	System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"); 
	     		 }
	            else
	            	System.out.println("\nRecord not found\n");
	            
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
		}
		
		// Specific genre wise count
		public void specificgenrecount(String genre) {
			try {      	
	            preparedStatement = connection.prepareStatement("select sum(borredcount) from books where genre=?");
	            preparedStatement.setString(1,genre);
	            resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	            	System.out.println("\n\nTotal users who have borroed "+genre+" genre is "+resultSet.getInt(1));
	     		 }
	            
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
		}
		
		//--------------------- Search Operations on Journals table ---------------------
		
		// Title wise Search
		public void journalTitleSearch(String title) {
			try {      	
	            preparedStatement = connection.prepareStatement("SELECT journal_id, title, field_of_study, publication_year FROM journals where title=?");
	            preparedStatement.setString(1,title);
	            resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	            	System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		            System.out.println("| Journal Id | Title                                    | Field Of Study | Publication Year  |");
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		            do {
	                	System.out.printf("| %-10s | %-40s | %-14s | %-17s |\n", resultSet.getString("journal_id"), resultSet.getString("title"), resultSet.getString("field_of_study"), resultSet.getString("publication_year"));
	            	}while (resultSet.next());
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	     		 }
	            else
	            	System.out.println("\nRecord not found\n");
	            
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
		}
		
		// Field of study wise searching 
		public void journalFieldOfStudySearch(String field) {
			try {      	
	            preparedStatement = connection.prepareStatement("SELECT journal_id, title, field_of_study, publication_year FROM journals where field_of_study=?");
	            preparedStatement.setString(1,field);
	            resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	            	System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		            System.out.println("| Journal Id | Title                                    | Field Of Study | Publication Year  |");
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");            	
		            do {
	                	System.out.printf("| %-10s | %-40s | %-14s | %-17s |\n", resultSet.getString("journal_id"), resultSet.getString("title"), resultSet.getString("field_of_study"), resultSet.getString("publication_year"));
	            	}while (resultSet.next());
		            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");            	
	     		 }
	            else
	            	System.out.println("\nRecord not found\n");
	            
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
		}
		
		// Field of study wise mostly frequently Borrowed 
		public void mostFrequentBorroedField() {
			try {
	            statement = connection.createStatement();
	            resultSet = statement.executeQuery("SELECT * FROM journals WHERE (field_of_study, borredcount) IN (SELECT field_of_study, MAX(borredcount) FROM journals GROUP BY field_of_study)");
	            System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	            System.out.println("| Journal Id | Title                                    | Field Of Study | Publication Year  |");
	            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	            while (resultSet.next())
	            	System.out.printf("| %-10s | %-40s | %-14s | %-17s |\n", resultSet.getString("journal_id"), resultSet.getString("title"), resultSet.getString("field_of_study"), resultSet.getString("publication_year"));
	            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
		}
	
		// ------------------------------------- Review and Rating --------------------------------------
		
		// Book 
		public void bookReviewandRating(String bookId,int userId) {
			try {
		        preparedStatement = connection.prepareStatement("INSERT INTO bookreviewandraintg VALUES (?,?,?,?)");
	            preparedStatement.setInt(1,userId);
		        preparedStatement.setString(2,bookId);
		        System.out.println("Enter Book Rating Between 1 to 10 : ");
		        preparedStatement.setInt(4,sc.nextInt());
		        System.out.println("Enter Book review: ");
		        sc.nextLine();
		        preparedStatement.setString(3,sc.nextLine());
		        preparedStatement.executeUpdate();
	            
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		
		// Journal
		public void journalReviewandRating(String journalId,int userId) {
			try {
		        preparedStatement = connection.prepareStatement("INSERT INTO journalreviewandraintg VALUES (?,?,?,?)");
	            preparedStatement.setInt(1,userId);
		        preparedStatement.setString(2,journalId);
		        System.out.println("Enter Journal Rating Between 1 to 10 : ");
		        preparedStatement.setInt(4,sc.nextInt());
		        System.out.println("Enter Journal review: ");
		        sc.nextLine();
		        preparedStatement.setString(3,sc.nextLine());
		        preparedStatement.executeUpdate();
	            
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		
		// ----------------------------------------------- Reserve or not ----------------------------------------------- 
		public void reserve(String userEmail, String id) {
			int userId=0;
			try {
				// Get user id by email
				preparedStatement = connection.prepareStatement("SELECT user_id FROM users WHERE email =?");
	            preparedStatement.setString(1,userEmail);
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()) {
			        userId = resultSet.getInt("user_id");
			    }
			}catch (SQLException e) {
	            System.out.println(e);
	        }
			
			// If Enter id is journals id
			if(id.charAt(0)=='j') {
				try {
					// Check given journal is someone borrowed or not
					preparedStatement = connection.prepareStatement("SELECT user_id FROM borrowingjournals WHERE journal_id = ?");
		            preparedStatement.setString(1,id);
		            int a = preparedStatement.executeUpdate();
		          
		            
		            // Check journal reserved or not
		            preparedStatement = connection.prepareStatement("SELECT journal_id FROM journals WHERE journal_id = ? and reserve='yes'");
		            preparedStatement.setString(1,id);
		            int b = preparedStatement.executeUpdate();
		        
		            
		            
		            if(a == 0 && b ==0) {
		            	// Reserve book
		            	preparedStatement = connection.prepareStatement("UPDATE journals set reserve='yes' where journal_id =?");
			            preparedStatement.setString(1,id);
			            preparedStatement.executeUpdate();
			            System.out.println("Journal is reserved");
		            }
		            else {
		            	System.out.println("Currently this book is not available for reserve");
		            }
		            
			        System.out.println("\nYou have returning Journal successfully\n\n");
				}catch (SQLException e) {
		            System.out.println(e);
		        }
			}
			
			// If Enter id is books id
			if(id.charAt(0)=='b') {
				try {
					preparedStatement = connection.prepareStatement("DELETE FROM borrowingbooks where user_id=? and book_id=?");
		            preparedStatement.setInt(1,userId);
		            preparedStatement.setString(2,id);
		            preparedStatement.executeUpdate();
		            
			        System.out.println("\nYou have returning Book successfuly\n\n");
				}catch (SQLException e) {
		            System.out.println("\nYou have not borrowing book journal\\n\\n");
		        }
			}
			
		}
}
