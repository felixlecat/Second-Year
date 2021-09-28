package Project;

//Part 3 - demo on the update of contact list (add function in Tab 1)

public class MVCDemo {

    public static void main(String[] args){
        Contact contact = retrieveContactFromFile();
        ContactView view = new ContactView();
        ContactController control = new ContactController(contact, view);

        control.updateView();

        control.setContactFirstName("John");
        control.setContactLastName("Doe");
        control.setContactId("02");
        control.setContactPhoneNumber("1234");

        System.out.println("New entry after update >>");

        control.updateView();
    }


    private static Contact retrieveContactFromFile() {
        Contact contact = new Contact();
        contact.setFirstName("Megan");
        contact.setLastName("Harvey");
        contact.setUid("01");
        contact.setPhoneNumber("8765");
        return contact;
    }

}
