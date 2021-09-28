package Project;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;

import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;

public class CovidApplicationDisplay extends JFrame{

    private Person currentEntry;
    private PersonQueries personQueries;
    private List<Person> result1;
    private List<Contact> result2;
    private int numberOfEntries = 0;
    private int currentEntryIndex;
    private Contact currentEntry2;
    private int numberOfEntries2 = 0;
    private int currentEntryIndex2;

    private JLabel firstNameLabel, middleNameLabel, lastNameLabel, phoneLabel, emailLabel, idLabel;
    private JTextField firstNameTextField, middleNameTextField, lastNameTextField, phoneTextField, emailTextField, idTextField;

    private JLabel idLabel2;
    private JTextField idTextField2;
    private JLabel dateLabel;

    private SqlDateModel model;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private JButton browseButton1, browseButton2, insertButton1, insertButton2, deleteButton1, deleteButton2, exitButton, findButton1, findButton2, saveButton;
    private JPanel queryPanel, navigatePanel, displayPanel;

    private JButton previousButton, nextButton;
    private JTextField indexTextField, maxTextField;
    private JLabel ofLabel;

    private JButton previousButton2, nextButton2;
    private JTextField indexTextField2, maxTextField2;
    private JLabel ofLabel2;

    private JPanel queryPanel2, queryPanel3, queryPanel4, queryPanel5;
    private JPanel displayPanel2, navigatePanel2;
    private JTextField firstPersonId, secondPersonId, dateTextField;
    private JLabel firstPersonLabel, secondPersonLabel, dateLabel2;
    private JLabel queryLabel2, queryLabel3, queryLabel4, queryLabel5;
    private JTextField queryTextField2, queryTextField3;

    public CovidApplicationDisplay(){
        super("Covid Close Contact Application");

        //Initialize
        personQueries = new PersonQueries();

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

        idLabel2 = new JLabel();
        idTextField2 = new JTextField(10);
        dateLabel = new JLabel();

        model = new SqlDateModel();
        datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);

        browseButton1 = new JButton();
        browseButton2 = new JButton();
        insertButton1 = new JButton();
        insertButton2 = new JButton();
        deleteButton1 = new JButton();
        deleteButton2 = new JButton();
        exitButton = new JButton();
        findButton1 = new JButton();
        findButton2 = new JButton();
        saveButton = new JButton();

        queryPanel = new JPanel();
        navigatePanel = new JPanel();
        displayPanel = new JPanel();

        previousButton = new JButton();
        nextButton = new JButton();
        indexTextField = new JTextField(2);
        maxTextField = new JTextField(2);
        ofLabel = new JLabel();

        navigatePanel2 = new JPanel();
        previousButton2 = new JButton();
        nextButton2 = new JButton();
        indexTextField2 = new JTextField(2);
        maxTextField2 = new JTextField(2);
        ofLabel2 = new JLabel();

        queryPanel2 = new JPanel();
        queryPanel3 = new JPanel();
        queryPanel4 = new JPanel();
        queryPanel5 = new JPanel();
        displayPanel2 = new JPanel();

        firstPersonId = new JTextField(4);
        secondPersonId = new JTextField(4);
        dateTextField = new JTextField(6);

        firstPersonLabel = new JLabel();
        secondPersonLabel = new JLabel();
        dateLabel2 = new JLabel();

        queryLabel2 = new JLabel();
        queryLabel3 = new JLabel();
        queryLabel4 = new JLabel();
        queryLabel5 = new JLabel();

        queryTextField2 = new JTextField(10);
        queryTextField3 = new JTextField(10);

        //Start
        setLayout(new FlowLayout(FlowLayout.CENTER,20,8));
        setSize(570, 830);
        setResizable(false);

        //Navigation Panel 1
        navigatePanel.setLayout(new BoxLayout(navigatePanel, BoxLayout.X_AXIS));

        previousButton.setText("Previous");
        previousButton.setEnabled(false);
        previousButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        previousButtonActionPerformed(evt);
                    }
                }
        );

        navigatePanel.add(previousButton);
        navigatePanel.add(Box.createHorizontalStrut(10));

        indexTextField.setHorizontalAlignment(JTextField.CENTER);
        indexTextField.setEditable(false);
        /*indexTextField.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        indexTextFieldActionPerformed(evt);
                    }
                }
        );*/

        navigatePanel.add(indexTextField);
        navigatePanel.add(Box.createHorizontalStrut(10));

        ofLabel.setText("of");
        navigatePanel.add(ofLabel);
        navigatePanel.add(Box.createHorizontalStrut(10));

        maxTextField.setHorizontalAlignment(JTextField.CENTER);
        maxTextField.setEditable(false);
        navigatePanel.add(maxTextField);
        navigatePanel.add(Box.createHorizontalStrut(10));

        nextButton.setText("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        nextButtonActionPerformed(evt);
                    }
                }
        );

        navigatePanel.add(nextButton);
        add(navigatePanel);

        //Display Panel 1
        displayPanel.setLayout(new GridLayout(6,2,4,4));

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

        //Query Panel 1
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.X_AXIS));
        queryPanel.setBorder(BorderFactory.createTitledBorder("Find a person with id"));
        idLabel2.setText("Person Id: ");
        queryPanel.add(Box.createHorizontalStrut(5));
        queryPanel.add(idLabel2);
        queryPanel.add(Box.createHorizontalStrut(10));
        queryPanel.add(idTextField2);
        queryPanel.add(Box.createHorizontalStrut(10));

        findButton1.setText("Find person");
        findButton1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        findButton1ActionPerformed(evt);
                    }
                }
        );

        queryPanel.add(findButton1);
        queryPanel.add(Box.createHorizontalStrut(5));
        add(queryPanel);

        //Insert Button
        insertButton1.setText("Insert new person");
        insertButton1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        insertButton1ActionPerformed(evt);
                    }
                }
        );
        add(insertButton1);

        //Query Panel 2
        queryPanel2.setLayout(new BoxLayout(queryPanel2, BoxLayout.X_AXIS));
        queryPanel2.setBorder(BorderFactory.createTitledBorder("Delete a person with id"));
        queryLabel2.setText("Person Id: ");
        queryPanel2.add(Box.createHorizontalStrut(5));
        queryPanel2.add(queryLabel2);
        queryPanel2.add(Box.createHorizontalStrut(10));
        queryPanel2.add(queryTextField2);
        queryPanel2.add(Box.createHorizontalStrut(10));
        
        deleteButton1.setText("Delete Person");
        deleteButton1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        deleteButton1ActionPerformed(evt);
                    }
                }
        );
        queryPanel2.add(deleteButton1);
        add(queryPanel2);

        //Browse All People
        browseButton1.setText("Browse all people");
        browseButton1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        browseButton1ActionPerformed(evt);
                    }
                }
        );
        add(browseButton1);

        //Navigation Panel 2
        navigatePanel2.setLayout(new BoxLayout(navigatePanel2, BoxLayout.X_AXIS));

        previousButton2.setText("Previous");
        previousButton2.setEnabled(false);
        previousButton2.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        previousButton2ActionPerformed(evt);
                    }
                }
        );

        navigatePanel2.add(previousButton2);
        navigatePanel2.add(Box.createHorizontalStrut(10));

        indexTextField2.setHorizontalAlignment(JTextField.CENTER);
        indexTextField2.setEditable(false);
        /*indexTextField.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        indexTextFieldActionPerformed(evt);
                    }
                }
        );*/

        navigatePanel2.add(indexTextField2);
        navigatePanel2.add(Box.createHorizontalStrut(10));

        ofLabel2.setText("of");
        navigatePanel2.add(ofLabel2);
        navigatePanel2.add(Box.createHorizontalStrut(10));

        maxTextField2.setHorizontalAlignment(JTextField.CENTER);
        maxTextField2.setEditable(false);
        navigatePanel2.add(maxTextField2);
        navigatePanel2.add(Box.createHorizontalStrut(10));

        nextButton2.setText("Next");
        nextButton2.setEnabled(false);
        nextButton2.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        nextButton2ActionPerformed(evt);
                    }
                }
        );

        navigatePanel2.add(nextButton2);
        add(navigatePanel2);
        
        //Display Panel 2
        displayPanel2.setLayout(new GridLayout(4,2,4,4));

        firstPersonLabel.setText("Person Id 1: ");
        displayPanel2.add(firstPersonLabel);
        displayPanel2.add(firstPersonId);

        secondPersonLabel.setText("Person Id 2: ");
        displayPanel2.add(secondPersonLabel);
        displayPanel2.add(secondPersonId);

        dateLabel2.setText("Contact Date: ");
        displayPanel2.add(dateLabel2);
        dateTextField.setEditable(false);
        displayPanel2.add(dateTextField);

        dateLabel.setText("DatePicker");
        displayPanel2.add(dateLabel);
        displayPanel2.add(datePicker);
        add(displayPanel2);

        //Query Panel 3
        queryPanel3.setLayout(new BoxLayout(queryPanel3, BoxLayout.X_AXIS));
        queryPanel3.setBorder(BorderFactory.createTitledBorder("Find a person's close contact record"));
        queryLabel3.setText("Person Id: ");
        queryPanel3.add(Box.createHorizontalStrut(5));
        queryPanel3.add(queryLabel3);
        queryPanel3.add(Box.createHorizontalStrut(10));
        queryPanel3.add(queryTextField3);
        queryPanel3.add(Box.createHorizontalStrut(10));

        browseButton2.setText("Browse record");
        browseButton2.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        browseButton2ActionPerformed(evt);
                    }
                }
        );
        queryPanel3.add(browseButton2);
        add(queryPanel3);

        //Insert New Record
        insertButton2.setText("Insert New Record");
        insertButton2.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        insertButton2ActionPerformed(evt);
                    }
                }
        );
        add(insertButton2);

        //Query Panel 4
        queryPanel4.setLayout(new BoxLayout(queryPanel4, BoxLayout.X_AXIS));
        queryPanel4.setBorder(BorderFactory.createTitledBorder("Get records before or equal to date"));
        queryLabel4.setText("Select date in DatePicker");
        queryPanel4.add(Box.createHorizontalStrut(5));
        queryPanel4.add(queryLabel4);
        queryPanel4.add(Box.createHorizontalStrut(10));

        findButton2.setText("Get records");
        findButton2.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        findButton2ActionPerformed(evt);
                    }
                }
        );
        queryPanel4.add(findButton2);
        add(queryPanel4);

        //Query Panel 5
        queryPanel5.setLayout(new BoxLayout(queryPanel5, BoxLayout.X_AXIS));
        queryPanel5.setBorder(BorderFactory.createTitledBorder("Delete a specific record"));
        queryLabel5.setText("Insert data at text field and select date in DatePicker");
        queryPanel5.add(Box.createHorizontalStrut(5));
        queryPanel5.add(queryLabel5);
        queryPanel5.add(Box.createHorizontalStrut(10));

        deleteButton2.setText("Delete a record");
        deleteButton2.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        deleteButton2ActionPerformed(evt);
                    }
                }
        );
        queryPanel5.add(deleteButton2);
        add(queryPanel5);
        
        //Exit from window
        addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent evt){
                        personQueries.close();
                        System.exit(0);
                    }
                }
        );
        setVisible(true);

        //Save to file button
        saveButton.setText("Save to File");
        saveButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result1 = personQueries.getAllPeople();
                        result2 = personQueries.getAllRecord();
                        try{
                            FileOutputStream fos = new FileOutputStream("SerializeData.txt");
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(result1);
                            oos.writeObject(result2);
                            oos.close();
                            fos.close();
                        }
                        catch(IOException ioe){
                            ioe.printStackTrace();
                        }
                        //personQueries.saveUpdate(); //refer to 'PersonQueries.java'
                        JOptionPane.showMessageDialog(saveButton,"File Created!", "File Created", JOptionPane.PLAIN_MESSAGE);
                    }
                }
        );
        add(saveButton);

        //Exit Button
        exitButton.setText("Exit");
        exitButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        personQueries.close();
                        System.exit(0);
                    }
                }
        );
        add(exitButton);
    }

    private void insertButton2ActionPerformed(ActionEvent evt) {
        int result = personQueries.addCloseContactRecord(Integer.parseInt(firstPersonId.getText()),Integer.parseInt(secondPersonId.getText()),
                (Date) datePicker.getModel().getValue());

        if(result == 1){
            JOptionPane.showMessageDialog(this,"Record Added!", "Record Added", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this,"Record Not Added!", "Record Not Added", JOptionPane.PLAIN_MESSAGE);
        }
        browseButton2ActionPerformed(evt);
    }

    private void nextButton2ActionPerformed(ActionEvent evt) {
        currentEntryIndex2++;

        if (currentEntryIndex2 >= numberOfEntries2)
            currentEntryIndex2 = 0;

        indexTextField2.setText("" + ( currentEntryIndex2 + 1 ));
        indexTextField2ActionPerformed(evt);
    }

    private void previousButton2ActionPerformed(ActionEvent evt) {
        currentEntryIndex2--;

        if(currentEntryIndex2 < 0){
            currentEntryIndex2 = numberOfEntries2 - 1;
        }
        indexTextField2.setText("" + (currentEntryIndex2 + 1));
        indexTextField2ActionPerformed(evt);
    }

    private void indexTextField2ActionPerformed(ActionEvent evt) {
        currentEntryIndex2 = (Integer.parseInt(indexTextField2.getText()) - 1);

        if(numberOfEntries2 != 0 && currentEntryIndex2 < numberOfEntries2){
            currentEntry2 = result2.get(currentEntryIndex2);

            firstPersonId.setText("" + currentEntry2.getPersonId1());
            secondPersonId.setText("" + currentEntry2.getPersonId2());
            dateTextField.setText("" + currentEntry2.getContactDate());

            maxTextField2.setText("" + numberOfEntries2);
            indexTextField2.setText("" + (currentEntryIndex2 + 1));
        }
    }

    private void findButton2ActionPerformed(ActionEvent evt) {
        java.sql.Date selectedDate = (java.sql.Date) datePicker.getModel().getValue();
        result2 = personQueries.displaySelectedDateRecord(selectedDate);
        numberOfEntries2 = result2.size();

        if(numberOfEntries2 != 0){
            currentEntryIndex2 = 0;
            currentEntry2 = result2.get(currentEntryIndex2);

            firstPersonId.setText("" + currentEntry2.getPersonId1());
            secondPersonId.setText("" + currentEntry2.getPersonId2());
            dateTextField.setText("" + currentEntry2.getContactDate());

            maxTextField2.setText("" + numberOfEntries2);
            indexTextField2.setText("" + (currentEntryIndex2 + 1));
            nextButton2.setEnabled(true);
            previousButton2.setEnabled(true);
        }
        else{
            browseButton2ActionPerformed(evt);
        }
    }

    private void deleteButton2ActionPerformed(ActionEvent evt) {
        int result = personQueries.deleteCloseContactRecord(Integer.parseInt(firstPersonId.getText()),Integer.parseInt(secondPersonId.getText()),
                (java.sql.Date) datePicker.getModel().getValue());

        if(result == 1){
            JOptionPane.showMessageDialog(this,"Record Deleted!", "Record Deleted", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this,"Record Not Deleted!", "Record Not Deleted", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void browseButton2ActionPerformed(ActionEvent evt) {
        try{
            result2 = personQueries.findCloseContactRecord(Integer.parseInt(queryTextField3.getText()));
            numberOfEntries2 = result2.size();

            if(numberOfEntries2 != 0){
                currentEntryIndex2 = 0;
                currentEntry2 = result2.get(currentEntryIndex2);

                firstPersonId.setText("" + currentEntry2.getPersonId1());
                secondPersonId.setText("" + currentEntry2.getPersonId2());
                dateTextField.setText("" + currentEntry2.getContactDate());

                maxTextField2.setText("" + numberOfEntries2);
                indexTextField2.setText("" + (currentEntryIndex2 + 1));
                nextButton2.setEnabled(true);
                previousButton2.setEnabled(true);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void deleteButton1ActionPerformed(ActionEvent evt) {
        int result = personQueries.deleteContact(Integer.parseInt(queryTextField2.getText()));

        if(result == 1){
            JOptionPane.showMessageDialog(this,"Person Deleted!", "Person Deleted", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this,"Person Not Deleted!", "Person Not Deleted", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void previousButtonActionPerformed(ActionEvent evt){
        currentEntryIndex--;

        if(currentEntryIndex < 0){
            currentEntryIndex = numberOfEntries - 1;
        }
        indexTextField.setText("" + (currentEntryIndex + 1));
        indexTextFieldActionPerformed(evt);
    }

    private void nextButtonActionPerformed(ActionEvent evt) {
        currentEntryIndex++;

        if (currentEntryIndex >= numberOfEntries)
            currentEntryIndex = 0;

        indexTextField.setText("" + ( currentEntryIndex + 1 ));
        indexTextFieldActionPerformed(evt);
    }

    private void findButton1ActionPerformed(ActionEvent evt){
        result1 = personQueries.findSelectedPerson(Integer.parseInt(idTextField2.getText()));
        numberOfEntries = result1.size();

        if(numberOfEntries != 0){
            currentEntryIndex = 0;
            currentEntry = result1.get(currentEntryIndex);

            firstNameTextField.setText(currentEntry.getName().getFirstName());
            middleNameTextField.setText(currentEntry.getName().getMiddleName());
            lastNameTextField.setText(currentEntry.getName().getLastName());
            phoneTextField.setText(currentEntry.getPhone());
            emailTextField.setText(currentEntry.getEmail());
            idTextField.setText("" + currentEntry.getId());

            maxTextField.setText("" + numberOfEntries);
            indexTextField.setText("" + (currentEntryIndex + 1));
            nextButton.setEnabled(true);
            previousButton.setEnabled(true);
        }
        else{
            JOptionPane.showMessageDialog(this,"Contact Not Found!", "Contact Not Found", JOptionPane.PLAIN_MESSAGE);
            browseButton1ActionPerformed(evt);
        }
    }

    private void indexTextFieldActionPerformed(ActionEvent evt){
        currentEntryIndex = (Integer.parseInt(indexTextField.getText()) - 1);

        if(numberOfEntries != 0 && currentEntryIndex < numberOfEntries){
            currentEntry = result1.get(currentEntryIndex);

            firstNameTextField.setText(currentEntry.getName().getFirstName());
            middleNameTextField.setText(currentEntry.getName().getMiddleName());
            lastNameTextField.setText(currentEntry.getName().getLastName());
            phoneTextField.setText(currentEntry.getPhone());
            emailTextField.setText(currentEntry.getEmail());
            idTextField.setText("" + currentEntry.getId());

            maxTextField.setText("" + numberOfEntries);
            indexTextField.setText("" + (currentEntryIndex + 1));
        }
    }

    private void browseButton1ActionPerformed(ActionEvent evt){
        try{
            result1 = personQueries.getAllPeople();
            numberOfEntries = result1.size();

            if(numberOfEntries != 0){
                currentEntryIndex = 0;
                currentEntry = result1.get(currentEntryIndex);

                firstNameTextField.setText(currentEntry.getName().getFirstName());
                middleNameTextField.setText(currentEntry.getName().getMiddleName());
                lastNameTextField.setText(currentEntry.getName().getLastName());
                phoneTextField.setText(currentEntry.getPhone());
                emailTextField.setText(currentEntry.getEmail());
                idTextField.setText("" + currentEntry.getId());

                maxTextField.setText("" + numberOfEntries);
                indexTextField.setText("" + (currentEntryIndex + 1));
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void insertButton1ActionPerformed(ActionEvent evt){
        int result = personQueries.addPerson(firstNameTextField.getText(), middleNameTextField.getText(),
                lastNameTextField.getText(), phoneTextField.getText(), emailTextField.getText(),
                Integer.parseInt(idTextField.getText()));

        if(result == 1){
            JOptionPane.showMessageDialog(this,"Person Added!", "Person Added", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this,"Person Not Added!", "Person Not Added", JOptionPane.PLAIN_MESSAGE);
        }
        browseButton1ActionPerformed(evt);
    }

    public static void main(String args[]){
        new CovidApplicationDisplay();
    }

}
