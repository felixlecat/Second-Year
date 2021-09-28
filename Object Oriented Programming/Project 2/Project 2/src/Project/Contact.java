package Project;

import java.io.Serializable;
import java.sql.Date;

public class Contact implements Serializable {

    private Date contactDate;
    private int personId1;
    private int personId2;

    public Contact(int id1, int id2, Date date){
        this.personId1 = id1;
        this.personId2 = id2;
        this.contactDate = date;
    }

    public void setContactDate(Date date){
        this.contactDate = date;
    }

    public Date getContactDate(){
        return contactDate;
    }

    public void setPersonId1(int id1){
        this.personId1 = id1;
    }

    public int getPersonId1(){
        return personId1;
    }

    public void setPersonId2(int id2){
        this.personId2 = id2;
    }

    public int getPersonId2(){
        return personId2;
    }

    /*public String toString(){
        return "(" + contactDate + ") " + personId1 + " - " + personId2;
    }*/
}
