package librarymanagement;

import java.sql.*;

public class Database {
	Connection connection =null;
	Statement statement;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	// Database Connection
	Database(){
		try
        {
        	Class.forName("org.postgresql.Driver");
        	connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/libraymanagement","postgres","12345678");
        }
        catch(Exception e)
        {
        	System.out.println(e);
        }
	}
}
