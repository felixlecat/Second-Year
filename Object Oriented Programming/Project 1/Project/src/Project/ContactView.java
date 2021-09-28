package Project;

//Part 3 - contact display from contact list

public class ContactView {

    public void printContactDetails(String firstName, String lastName, String uid, String phoneNumber){
        System.out.println("- Contact -");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("ID: " + uid);
        System.out.println("Phone Number: " + phoneNumber);
    }

}
