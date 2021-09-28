package Project; //password to be added for maven to work

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class PersonQueries {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/closecontact";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "max1439root";

    private Connection connection = null; // manages connection
    private PreparedStatement selectAllPeople = null; //display all person
    private PreparedStatement insertNewPerson = null; //add person
    private PreparedStatement deletePerson = null; //delete person
    private PreparedStatement deleteRecord = null; //delete close contact record
    private PreparedStatement addRecord = null; //add close contact record
    private PreparedStatement searchPerson = null; //find a person
    private PreparedStatement findRecord = null; //find a person's close contact record
    private PreparedStatement displayDateRecord = null; //display record of a date
    private PreparedStatement saveData = null; //save all data into text file

    private PreparedStatement deletePersonRecord1 = null;
    private PreparedStatement deletePersonRecord2 = null;
    private PreparedStatement saveData2 = null;
    private PreparedStatement selectAllRecord = null;

    public PersonQueries(){
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            selectAllPeople = connection.prepareStatement("SELECT * FROM Person");
            insertNewPerson = connection.prepareStatement(
                    "INSERT INTO Person" +
                            "(FirstName, MiddleName, LastName, Phone, Email, PersonId)" +
                            "VALUES (?, ?, ?, ?, ?, ?);"
            );
            deletePerson = connection.prepareStatement(
                "DELETE FROM Person WHERE PersonId = ?;"
            );
            deletePersonRecord1 = connection.prepareStatement(
                    "DELETE FROM Record WHERE PersonId1 = ?;"
            );
            deletePersonRecord2 = connection.prepareStatement(
                    "DELETE FROM Record WHERE PersonId2 = ?;"
            );
            deleteRecord = connection.prepareStatement(
                    "DELETE FROM Record WHERE (PersonId1 = ?)" +
                            "AND (PersonId2 = ?)" +
                            "AND (ContactDate = ?);"
            );
            addRecord = connection.prepareStatement(
                    "INSERT INTO Record" +
                            "(PersonId1, PersonId2, ContactDate)" +
                            "VALUES (?, ?, ?)"
            );
            searchPerson = connection.prepareStatement(
                    "SELECT * FROM Person WHERE PersonId = ?"
            );
            findRecord = connection.prepareStatement(
                    "SELECT * FROM Record WHERE (PersonId1 = ?) OR (PersonId2 = ?)"
            );
            displayDateRecord = connection.prepareStatement(
                    "SELECT * FROM Record WHERE ContactDate <= ?"
            );
            saveData = connection.prepareStatement( //Sql error 1290
                    "SELECT FirstName, MiddleName, LastName, Phone, Email, PersonId FROM Person" +
                            "INTO OUTFILE 'D:\\CIT\\Year 2\\Semester 2\\Object Oriented Programming\\PersonData.txt' "

            );
            saveData2 = connection.prepareStatement( //Sql error 1290
                    "SELECT PersonId1, PersonId2, ContactDate FROM Record" +
                            "INTO OUTFILE 'D:\\CIT\\Year 2\\Semester 2\\Object Oriented Programming\\RecordData.txt' "
            );
            selectAllRecord = connection.prepareStatement(
                    "SELECT * FROM Record"
            );
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public List <Person> getAllPeople(){
        List <Person> results =null;
        ResultSet resultSet = null;

        try{
            resultSet = selectAllPeople.executeQuery();
            results = new ArrayList<Person>();

            while(resultSet.next()){
                results.add(new Person(
                    resultSet.getString("firstName"),
                        resultSet.getString("middleName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getInt("personId")
                ) );
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally{
            try{
                resultSet.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                //close();
            }
        }

        return results;
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

            result = insertNewPerson.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            //close();
        }

        return result;
    }

    public int deleteContact(int id){
        int result = 0;

        try{
            deletePerson.setInt(1, id);
            deletePersonRecord1.setInt(1, id);
            deletePersonRecord2.setInt(1, id);

            result = deletePerson.executeUpdate();
            deletePersonRecord1.executeUpdate();
            deletePersonRecord2.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            //close();
        }

        return result;
    }

    public int deleteCloseContactRecord(int id1, int id2, Date date){
        int result = 0;

        try{
            deleteRecord.setInt(1, id1);
            deleteRecord.setInt(2, id2);
            deleteRecord.setDate(3, date);

            result = deleteRecord.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            //close();
        }

        return result;
    }

    public int addCloseContactRecord(int id1, int id2, Date date){
        int result = 0;

        try{
            addRecord.setInt(1, id1);
            addRecord.setInt(2, id2);
            addRecord.setDate(3, date);

            result = addRecord.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            //close();
        }

        return result;
    }

    public List <Person> findSelectedPerson(int id){
        List <Person> results =null;
        ResultSet resultSet = null;

        try{
            searchPerson.setInt(1, id);
            resultSet = searchPerson.executeQuery();

            results = new ArrayList<Person>();

            while(resultSet.next()){
                if(resultSet.getInt("personId") == id){
                    results.add(new Person(
                            resultSet.getString("firstName"),
                            resultSet.getString("middleName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getInt("personId")
                    ) );
                    break;
                }
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally{
            try{
                resultSet.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                //close();
            }
        }

        return results;
    }

    public List<Contact> findCloseContactRecord(int id){
        List <Contact> results = null;
        ResultSet resultSet = null;

        try{
            findRecord.setInt(1, id);
            findRecord.setInt(2, id);

            resultSet = findRecord.executeQuery();

            results = new ArrayList<Contact>();

            while(resultSet.next()){
                results.add(new Contact(
                    resultSet.getInt("personId1"),
                        resultSet.getInt("personId2"),
                        resultSet.getDate("contactDate")
                ) );
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                //close();
            }
        }

        return results;
    }

    public List<Contact> displaySelectedDateRecord(Date date){
        List <Contact> results = null;
        ResultSet resultSet = null;

        try{
            displayDateRecord.setDate(1, date);

            resultSet = displayDateRecord.executeQuery();

            results = new ArrayList<Contact>();

            while(resultSet.next()){
                results.add(new Contact(
                        resultSet.getInt("personId1"),
                        resultSet.getInt("personId2"),
                        resultSet.getDate("contactDate")
                ) );
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                //close();
            }
        }

        return results;
    }

    public void saveUpdate(){
        try{
            saveData.executeQuery();
            saveData2.executeQuery();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public List <Contact> getAllRecord() {
        List<Contact> results = null;
        ResultSet resultSet = null;

        try {
            resultSet = selectAllRecord.executeQuery();
            results = new ArrayList<Contact>();

            while (resultSet.next()) {
                results.add(new Contact(
                        resultSet.getInt("personId1"),
                        resultSet.getInt("personId2"),
                        resultSet.getDate("contactDate")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                //close();
            }
        }
        return results;
    }

    public void close(){
        try{
            connection.close();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
