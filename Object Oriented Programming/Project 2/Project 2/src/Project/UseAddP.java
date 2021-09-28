package Project; //tested and worked

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;

public class UseAddP extends JFrame{

    private Person currentEntry;
    private AddP addP;
    private List <Person> results;
    private JLabel firstNameLabel, middleNameLabel, lastNameLabel, phoneLabel, emailLabel, idLabel;
    private JTextField firstNameTextField, middleNameTextField, lastNameTextField, phoneTextField, emailTextField, idTextField;
    private JPanel displayPanel;
    private JButton browseButton, insertButton;

    public UseAddP(){
        super("Covid Close Contact Application");

        addP = new AddP();

        firstNameLabel = new JLabel();
        middleNameLabel = new JLabel();
        lastNameLabel = new JLabel();
        phoneLabel = new JLabel();
        emailLabel = new JLabel();
        idLabel = new JLabel();

        firstNameTextField = new JTextField(10);
        middleNameTextField = new JTextField(10);
        lastNameTextField = new JTextField(10);
        phoneTextField = new JTextField(10);
        emailTextField = new JTextField(10);
        idTextField = new JTextField(10);

        browseButton = new JButton();
        insertButton = new JButton();

        setLayout( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );
        setSize( 400, 400 );
        setResizable( false );

        displayPanel = new JPanel();

        displayPanel.setLayout( new GridLayout( 6, 2, 4, 4 ) );

        firstNameLabel.setText("First Name: ");
        displayPanel.add(firstNameLabel);
        displayPanel.add(firstNameTextField);

        middleNameLabel.setText("Middle Name: ");
        displayPanel.add(middleNameLabel);
        displayPanel.add(middleNameTextField);

        lastNameLabel.setText("Last Name: ");
        displayPanel.add(lastNameLabel);
        displayPanel.add(lastNameTextField);

        phoneLabel.setText("Phone Number (10 digits): ");
        displayPanel.add(phoneLabel);
        displayPanel.add(phoneTextField);

        emailLabel.setText("Email address: ");
        displayPanel.add(emailLabel);
        displayPanel.add(emailTextField);

        idLabel.setText("Enter Unique Id: ");
        displayPanel.add(idLabel);
        displayPanel.add(idTextField);

        add(displayPanel);

        insertButton.setText( "Insert New Entry" );
        insertButton.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent evt )
                    {
                        insertButtonActionPerformed(evt);
                    } // end method actionPerformed
                } // end anonymous inner class
        ); // end call to addActionListener

        add(insertButton);

        addWindowListener(
                new WindowAdapter()
                {
                    public void windowClosing( WindowEvent evt )
                    {
                        addP.close(); // close database connection
                        System.exit( 0 );
                    }
                }
        );

        setVisible(true);

    }

    private void insertButtonActionPerformed(ActionEvent evt){
        int result = addP.addPerson(firstNameTextField.getText(), middleNameTextField.getText(), lastNameTextField.getText(),
                phoneTextField.getText(), emailTextField.getText(), Integer.parseInt(idTextField.getText()));
        if(result == 1){
            JOptionPane.showMessageDialog(this, "Person Added!", "Person Added", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this, "Person not added.", "Person Not Added", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String args[]){
        new UseAddP();
    }

}
