package Project;

import java.util.ArrayList;
import java.io.Serializable;

public class Contact implements Serializable, Comparable<Contact>{

    private String firstName;
    private String lastName;
    private String uid;
    private String phoneNumber;

    private ArrayList<CloseContact> closeContacts = new ArrayList<>();

    public Contact(String fn, String ln, String id, String pn){
        firstName = fn;
        lastName = ln;
        uid = id;
        phoneNumber = pn;
        closeContacts = new ArrayList<CloseContact>();
    }

    //Part 3
    public Contact(){

    }

    public void setFirstName(String fn){
        this.firstName = fn;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String ln){
        this.lastName = ln;
    }

    public String getLastName(){
        return lastName;
    }

    public void setUid(String id){
        this.uid = id;
    }

    public String getUid(){
        return uid;
    }

    public void setPhoneNumber(String pn){
        this.phoneNumber = pn;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String toString(){
        return firstName + "," + lastName + "," + uid + "," + phoneNumber;
    }

    public void setCloseContacts(ArrayList<CloseContact> ccl){
        this.closeContacts = ccl;
    }

    public ArrayList<CloseContact> getCloseContacts(){
        return closeContacts;
    }

    public int getSize(){
        return closeContacts.size();
    }

    //Add close contact record to a contact
    public void addCloseContact(Contact c, String date, String time){
        CloseContact cc = new CloseContact(c, date, time);
        closeContacts.add(cc);
    }

    //Get contact from close contact list
    public CloseContact getCContact(int i){
        if ((i > -1) && (i < closeContacts.size())){
            return closeContacts.get(i);
        }
        else{
            return null;
        }
    }

    //Sort contact based on name
    public int compareTo(Contact c) {
        int lastCmp = firstName.compareTo(c.getFirstName());
        if(lastCmp != 0){
            return lastCmp;
        }
        else{
            return lastName.compareTo(c.getLastName());
        }
    }

}
