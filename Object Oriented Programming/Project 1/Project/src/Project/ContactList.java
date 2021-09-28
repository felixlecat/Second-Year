package Project;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactList implements Serializable {

    private Contact closeContact;
    private ArrayList <Contact> contacts;

    public ContactList(){
        contacts = new ArrayList<Contact>();
    }

    public void setContacts(ArrayList<Contact> cl){
        this.contacts = cl;
    }

    public ArrayList<Contact> getContacts(){
        return contacts;
    }

    public int getSize(){
        return contacts.size();
    }

    public int getCloseContactSize(){
        return closeContact.getSize();
    }

    public void getCloseContact(int i){
        closeContact.getCContact(i);
    }

    public void addContact(Contact c){
        contacts.add(c);
    }

    //Get contact from contact list
    public Contact getContact(int i){
        if ((i > -1) && (i < contacts.size())){
            return contacts.get(i);
        }
        else{
            return null;
        }
    }

    /*Remove contact based on ID - never used
    public void removeContactById(String s){
        for (int i = 0; i < contacts.size(); i++){
            if (getContact(i).getUid().equals(s)){
                contacts.remove(i);
            }
        }
    }*/

    //Delete contact from contact list
    public void removeContact(int i){
       for(int j = contacts.size() - 1; j >= 0; j--){
            if(j == i){
                contacts.remove(j);
            }
        }
    }

}
