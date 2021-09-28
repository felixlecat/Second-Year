package Project;

import java.io.*;
import java.util.Collections;
import java.util.Scanner; // Part 1, 2

//Part 3 - I couldn't get serialisation work - (updated 16/3) it works

public class ContactController implements Serializable {

    private ContactList cl;
    //Part 3
    private Contact contact;
    private ContactView view;

    public ContactController(){
        cl = new ContactList();
    }

    //Part 3
    public ContactController(Contact contact, ContactView view){
        this.contact = contact;
        this.view = view;
    }

    public ContactList getCl(){
        return cl;
    }

    //Add new contact
    public boolean addContactToList(String fName, String lName, String uid, String phoneNo){
        Contact c = new Contact(fName, lName, uid, phoneNo);
        boolean present = false;
        for(int i = 0; i < cl.getSize(); i++){
            if(cl.getContact(i).getUid().equals(uid)){
                present = true;
            }
        }
        if(!present){
            cl.addContact(c);
        }
        return present;
    }

    //Remove contact
    public void removeContactFromList(int index){
        for (int i = cl.getSize() -1 ; i >= 0; i--){
            if(i == index){
                cl.removeContact(i);
            }
        }
    }

    //Write to text file
    public void printFile() throws FileNotFoundException {

        /*Part 1, 2 - without serialization
        PrintWriter outputFile = new PrintWriter("Contacts.txt");
        for (Contact c : cl.getContacts()){
            outputFile.println(c);
        }
        outputFile.close();*/

        //Part 3 - with serialization
        try{
            FileOutputStream fos = new FileOutputStream("Contacts.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cl);
            oos.close();
            fos.close();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    //Load from text file
    public void loadFile() throws FileNotFoundException {
        cl.getContacts().clear();

        /*Part 1, 2 - without serialization
        Scanner sc = new Scanner(new File("Contacts.txt"));
        while(sc.hasNextLine()){
            String s = sc.nextLine();
            String[] c = s.split(",");
            String fname = c[0];
            String lname = c[1];
            String uid = c[2];
            String phoneno = c[3];
            Contact contact = new Contact(fname, lname, uid, phoneno);
            boolean present = false;
            for(int i = 0; i < cl.getSize(); i++){
                if(cl.getContact(i).getUid().equals(uid)){
                    present = true;
                }
            }
            if(!present){
                cl.addContact(contact);
            }
        }
        sc.close();*/

        //Part 3 - with serialization
        try{
            FileInputStream fis = new FileInputStream("Contacts.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            cl = (ContactList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
        }

        for (Contact c : cl.getContacts()){
            System.out.println(c);
        }
    }

    //Record a close contact
    public boolean recordCloseContact(Contact c1, Contact c2, String date, String hour, String minute, String day){
        boolean result = true;
        String time = hour + ":" + minute + " " + day;
        if(c1.equals(c2)){
            result = false;
        }
        else{
            for (int i = 0; i < c1.getSize(); i++){
                if(c1.getCContact(i).getDate().equals(date) && c1.getCContact(i).getTime().equals(time)
                        && c1.getCContact(i).getContact().equals(c2)){
                    result = false;
                }
            }
            if(result){
                c1.addCloseContact(c2, date, time);
                c2.addCloseContact(c1, date, time);
                result = true;
            }
        }
        return result;
    }

    //Find contact with matching id
    public Contact matchContact(String contactId){
        Contact c = null;
        for(int i = 0; i < cl.getSize(); i++){
            if(cl.getContact(i).getUid().equals(contactId)){
                c = cl.getContact(i);
            }
        }
        return c;
    }

    //Part 3
    //MVCDemo
    public void updateView(){
        view.printContactDetails(contact.getFirstName(), contact.getLastName(), contact.getUid(), contact.getPhoneNumber());
    }

    public void setContactFirstName(String fn){
        contact.setFirstName(fn);
    }

    public String getContactFirstName(){
        return contact.getFirstName();
    }

    public void setContactLastName(String ln){
        contact.setLastName(ln);
    }

    public String getContactLastName(){
        return contact.getLastName();
    }

    public void setContactId(String id){
        contact.setUid(id);
    }

    public String getContactId(){
        return contact.getUid();
    }

    public void setContactPhoneNumber(String pn){
        contact.setPhoneNumber(pn);
    }

    public String getContactPhoneNumber(){
        return contact.getPhoneNumber();
    }

    //Sort list based on date
    public void sortListByDate(Integer selectedContact){
        Collections.sort(cl.getContact(selectedContact).getCloseContacts(), new DateComparator());
    }

    //Sort list based on name
    public void sortListByName(Integer selectedContact){
        Collections.sort(cl.getContact(selectedContact).getCloseContacts(), new NameComparator());
    }

}
