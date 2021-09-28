package Project;

import java.io.Serializable;

public class Person implements Serializable {

    private Name name;
    private String phone;
    private String email;
    private int id;


    public Person(String fn, String mn, String ln, String phone, String email, int id){
        this.name = new Name(fn, mn, ln);
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    public void setName(Name name){
        this.name = name;
    }

    public Name getName(){
        return name;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    /*public String toString(){
        return name + ", " + phone + ", " + email + ", " + id;
    }*/

}
