package Project; // tested, need change person info

public class DBNoGUI {

    private AddP addP;

    public DBNoGUI(){
        addP = new AddP();
        int result = addP.addPerson("Ball", "Guy", "Guilly", "0836437716", "ballguyguilly@live.com", 03);
        if(result == 1){
            System.out.println("Person added!");
        }
        else{
            System.out.println("Person not added");
        }
    }

    public static void main(String args[]){
        new DBNoGUI();
    }
}
