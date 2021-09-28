package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AddP {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/closecontact";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "max1439root";

    private Connection connection = null;

    private PreparedStatement insertNewPerson = null;

    public AddP(){
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            insertNewPerson = connection.prepareStatement(
                    "INSERT INTO Person" +
                "(FirstName, MiddleName, LastName, Phone, Email, PersonId)" +
                            "VALUES (?, ?, ?, ?, ?, ?);"
            );
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public int addPerson(String fn, String mn, String ln, String phone, String email, int id){
        int result = 0;

        try{
            insertNewPerson.setString(1, fn);
            insertNewPerson.setString(2, mn);
            insertNewPerson.setString(3, ln);
            insertNewPerson.setString(4, phone);
            insertNewPerson.setString(5, email);
            insertNewPerson.setInt(6, id);

            result =insertNewPerson.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }

        return result;
    }

    public void close(){
        try{
            connection.close();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

}
