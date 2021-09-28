package Project;

import java.io.Serializable;

public class CloseContact implements Serializable, Comparable<CloseContact>{

    private Contact contact;
    private String date;
    private String time;

    public CloseContact(Contact contact, String date, String time){
        this.contact = contact;
        this.date = date;
        this.time = time;
    }

    public void setContact(Contact contact){
        this.contact = contact;
    }

    public Contact getContact(){
        return contact;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }

    public String toString(){
        return " | " + getDate() + " | " + getTime() + " | ID: " + getContact().getUid() + " - "
                + getContact().getFirstName() + " " + getContact().getLastName() + " - Tel: "
                + getContact().getPhoneNumber();
    }

    // Sort contact based on date
    public int compareTo(CloseContact c) {
        int lastCmp = date.compareTo(c.getDate());
        if(lastCmp != 0){
            return lastCmp;
        }
        else{
            return contact.compareTo(c.getContact());
        }
    }

}
