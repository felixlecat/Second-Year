package Project;

import java.io.Serializable;

public class Name implements Serializable {

    private String firstName;
    private String middleName;
    private String lastName;

    public Name(String fn, String mn, String ln){
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
    }

    public void setFirstName(String fn){
        this.firstName = fn;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setMiddleName(String mn){
        this.middleName = mn;
    }

    public String getMiddleName(){
        return middleName;
    }

    public void setLastName(String ln){
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }

    /*public String toString(){
        return firstName + " " + middleName + " " + lastName;
    }*/
}
