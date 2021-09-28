package Project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class Main extends Application {

    private Stage window;
    private Label appTitle1, appTitle2, appTitle3;
    private Label enterFirstName, enterLastName, enterUid, enterPhoneNumber;
    private Label contact1, contact2, contactDate, contactTime;
    private Label selectedContact;
    private Button addButton, removeButton, listButton;
    private Button loadButton, saveButton, exitButton1, exitButton2, exitButton3;
    private Button addCloseContact;
    private Button updateList, sortDate, sortName;
    private TextField firstName, lastName, uid, phoneNumber;
    private ListView<Object> contactList, closeContactList;
    private HBox row1, row2, row3, row4, row5, row6, row7;
    private HBox line1, line2, line3, line4, line5, timeBox;
    private HBox box1, box2, box3, box4;
    private HBox header1, header2, header3, footer1, footer2, footer3;
    private ComboBox<Object> dropDown1, dropDown2, dropDown3;
    private ComboBox<Object> hour, minute, day;
    private DatePicker datePicker;
    private Alert add, remove1, remove2, update, load1, load2, save1, save2, exit;
    private Alert addRecord, showCloseContact;
    private Tab tab1, tab2, tab3;
    private GridPane content1, content2, content3;
    private BorderPane page1, page2, page3;
    private TabPane appTabs;
    private ContactController contactControl = new ContactController();


    @Override
    public void start(Stage primaryStage) throws IOException {
        //Stage
        window = primaryStage;
        window.setTitle("Covid Close Contact Application");

        //Tab 1
        //HBox
        row1 = new HBox(20);
        row2 = new HBox(20);
        row3 = new HBox(20);
        row4 = new HBox(20);
        row5 = new HBox(20);
        row6 = new HBox(20);
        row7 = new HBox(20);
        header1 = new HBox(20);
        footer1 = new HBox(20);

        //Header
        appTitle1 = new Label();
        appTitle1.setText("Add New Contact");

        header1.getChildren().add(appTitle1);
        header1.setAlignment(Pos.CENTER);
        header1.setPadding(new Insets(30, 0, 30, 0));
        header1.setStyle("-fx-font: normal 32px 'Monaco' ");

        //Button
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        listButton = new Button("Update List");
        loadButton = new Button("Load");
        saveButton = new Button("Save");
        exitButton1 = new Button("Exit");

        addButton.setOnAction(e -> addContact());
        removeButton.setOnAction(e -> removeContact());
        listButton.setOnAction(e -> {
            try {
                viewContactList();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        loadButton.setOnAction((e -> {
            try {
                loadContactList();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }));
        saveButton.setOnAction(e -> {
            try {
                saveChanges();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        exitButton1.setOnAction(e -> exitProgram());

        //Alert
        add = new Alert(Alert.AlertType.NONE);
        remove1 = new Alert(Alert.AlertType.NONE);
        remove2 = new Alert(Alert.AlertType.NONE);
        update = new Alert(Alert.AlertType.NONE);
        load1 = new Alert(Alert.AlertType.NONE);
        load2 = new Alert(Alert.AlertType.NONE);
        save1 = new Alert(Alert.AlertType.NONE);
        save2 = new Alert(Alert.AlertType.NONE);
        exit = new Alert(Alert.AlertType.NONE);

        //NameLabel
        enterFirstName = new Label("Enter First Name");
        enterFirstName.setStyle("-fx-font: normal 14px 'Helvetica' ");
        firstName = new TextField();
        enterLastName = new Label("Enter Last Name");
        enterLastName.setStyle("-fx-font: normal 14px 'Helvetica' ");
        lastName = new TextField();

        row1.getChildren().addAll(enterFirstName, enterLastName);
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(65);
        row1.setPadding(new Insets(20, 30, 20, 30));

        row2.getChildren().addAll(firstName, lastName);
        row2.setAlignment(Pos.CENTER);
        row2.setPadding(new Insets(20, 30, 20, 30));

        //IDLabel,ContactLabel
        enterUid = new Label("Enter Unique ID");
        enterUid.setStyle("-fx-font: normal 14px 'Helvetica' ");
        uid = new TextField();
        enterPhoneNumber = new Label("Enter Phone Number");
        enterPhoneNumber.setStyle("-fx-font: normal 14px 'Helvetica' ");
        phoneNumber = new TextField();

        row3.getChildren().addAll(enterUid, enterPhoneNumber);
        row3.setAlignment(Pos.CENTER);
        row3.setSpacing(60);
        row3.setPadding(new Insets(0, 30, 0,40));

        row4.getChildren().addAll(uid, phoneNumber);
        row4.setAlignment(Pos.CENTER);
        row4.setPadding(new Insets(0, 30, 40, 30));

        //ButtonRow1
        row5.getChildren().addAll(addButton, removeButton, listButton);
        row5.setAlignment(Pos.CENTER);

        //ContactList
        contactList = new ListView<>();
        contactList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        contactList.setPrefSize(320, 200);
        row6.getChildren().add(contactList);
        row6.setAlignment(Pos.CENTER);

        //ButtonRow2
        row7.getChildren().addAll(loadButton,saveButton);
        row7.setAlignment(Pos.BASELINE_CENTER);
        row7.setPadding(new Insets(0, 0, 10,0));

        //Footer
        footer1.getChildren().add(exitButton1);
        footer1.setAlignment(Pos.BOTTOM_RIGHT);
        footer1.setPadding(new Insets(20, 20, 20, 20));

        //GridPane
        content1 = new GridPane();

        content1.setHgap(10);
        content1.setVgap(20);

        content1.add(row1, 1, 0, 1, 2);
        content1.add(row2, 1, 1, 1, 2);
        content1.add(row3, 1, 2, 1, 2);
        content1.add(row4, 1, 3, 1, 2);
        content1.add(row5, 1, 4, 1, 2);
        content1.add(row6, 1, 5, 1, 2);
        content1.add(row7, 1, 7, 1, 2);

        content1.setAlignment(Pos.CENTER);
        content1.setStyle("-fx-background-color: #A1FCDF");
        //#93B7BE

        //BorderPane
        page1 = new BorderPane();

        page1.setTop(header1);
        page1.setCenter(content1);
        page1.setBottom(footer1);

        page1.setStyle("-fx-background-color: #FCD29F");
        //#2D3047

        //Load file on startup
        File saveFile = new File("Contacts.txt");
        boolean exists = saveFile.exists();
        if (exists){
            contactControl.loadFile();
            viewContactListOnStartup();
        }

        //Tab 2
        //HBox
        line1 = new HBox(20);
        line2 = new HBox(20);
        line3 = new HBox(20);
        line4 = new HBox(20);
        line5 = new HBox(20);
        timeBox = new HBox(20);
        header2 = new HBox(20);
        footer2 = new HBox(20);

        //Button
        addCloseContact = new Button("Add Close Contact");
        addCloseContact.setOnAction(e -> recordCloseContact());
        exitButton2 = new Button("Exit");
        exitButton2.setOnAction(e -> exitProgram());

        //Alert
        addRecord = new Alert(Alert.AlertType.NONE);

        //Header
        appTitle2 = new Label();
        appTitle2.setText("Record Close Contacts");

        header2.getChildren().add(appTitle2);
        header2.setAlignment(Pos.CENTER);
        header2.setPadding(new Insets(30, 0, 30, 0));
        header2.setStyle("-fx-font: normal 32px 'Monaco' ");

        //ContactLabel
        contact1 = new Label("Select First Contact");
        contact1.setStyle("-fx-font: normal 14px 'Helvetica' ");
        contact2 = new Label("Select Second Contact");
        contact2.setStyle("-fx-font: normal 14px 'Helvetica' ");

        line1.getChildren().addAll(contact1, contact2);
        line1.setAlignment(Pos.CENTER);
        line1.setSpacing(60);
        line1.setPadding(new Insets(20, 35, 20, 50));

        //ComboBox
        dropDown1 = new ComboBox<>();
        dropDown2 = new ComboBox<>();

        if(contactControl.getCl().getSize() == 0){
            dropDown1.getItems().add("List is empty");
        }
        else {
            for (int i = 0; i < contactControl.getCl().getSize(); i++) {
                dropDown1.getItems().add(contactControl.getCl().getContact(i).getUid() + " - "
                        + contactControl.getCl().getContact(i).getFirstName() + " "
                        + contactControl.getCl().getContact(i).getLastName());
            }
        }

        if(contactControl.getCl().getSize() == 0){
            dropDown1.getItems().add("List is empty");
        }
        else {
            for (int i = 0; i < contactControl.getCl().getSize(); i++) {
                dropDown2.getItems().add(contactControl.getCl().getContact(i).getUid() + " - "
                        + contactControl.getCl().getContact(i).getFirstName() + " "
                        + contactControl.getCl().getContact(i).getLastName());
            }
        }

        dropDown1.getSelectionModel().selectFirst();
        dropDown2.getSelectionModel().selectFirst();

        line2.getChildren().addAll(dropDown1, dropDown2);
        line2.setAlignment(Pos.CENTER);
        line2.setSpacing(50);
        line2.setPadding(new Insets(20, 10, 20, 10));

        //DateLabel + TimeLabel
        contactDate = new Label("Select Date");
        contactDate.setStyle("-fx-font: normal 14px 'Helvetica' ");
        contactTime = new Label("Select Time");
        contactTime.setStyle("-fx-font: normal 14px 'Helvetica' ");

        line3.getChildren().addAll(contactDate, contactTime);
        line3.setAlignment(Pos.CENTER);
        line3.setSpacing(120);
        line3.setPadding(new Insets(20, 30, 20, 30));

        //Date + Time
        datePicker = new DatePicker();

        hour = new ComboBox<>();
        minute = new ComboBox<>();
        day = new ComboBox<>();

        for (int h = 1; h < 13; h++){
            hour.getItems().add(h);
        }

        minute.getItems().add("00");
        minute.getItems().add("05");
        int m = 10;
        do{
            minute.getItems().add(m);
            m = m + 5;
        }
        while(m < 60);

        day.getItems().add("AM");
        day.getItems().add("PM");

        hour.getSelectionModel().selectFirst();
        minute.getSelectionModel().selectFirst();
        day.getSelectionModel().selectFirst();

        timeBox.getChildren().addAll(hour, minute, day);
        timeBox.setSpacing(0);

        line4.getChildren().addAll(datePicker, timeBox);
        line4.setAlignment(Pos.CENTER);
        line4.setPadding(new Insets(20, 30, 20, 30));

        //Button
        line5.getChildren().add(addCloseContact);
        line5.setAlignment(Pos.CENTER);
        line5.setPadding(new Insets(40, 0, 40, 0));

        //Footer
        footer2.getChildren().add(exitButton2);
        footer2.setAlignment(Pos.BOTTOM_RIGHT);
        footer2.setPadding(new Insets(20, 20, 20, 20));

        //GridPane
        content2 = new GridPane();

        content2.setHgap(10);
        content2.setVgap(20);

        content2.add(line1, 1, 0, 1, 2);
        content2.add(line2, 1, 1, 1, 2);
        content2.add(line3, 1, 2, 1, 2);
        content2.add(line4, 1, 3, 1, 2);
        content2.add(line5, 1, 4, 1, 2);

        content2.setAlignment(Pos.CENTER);
        content2.setStyle("-fx-background-color: #A1FCDF");

        //BorderPane
        page2 = new BorderPane();

        page2.setTop(header2);
        page2.setCenter(content2);
        page2.setBottom(footer2);

        page2.setStyle("-fx-background-color: #FCD29F");

        //Tab 3
        //HBox
        box1 = new HBox(20);
        box2 = new HBox(20);
        box3 = new HBox(20);
        box4 = new HBox(20);
        header3 = new HBox(20);
        footer3 = new HBox(20);

        //Button
        updateList = new Button("Update List");
        updateList.setOnAction(e -> updateCloseContact());
        exitButton3 = new Button("Exit");
        exitButton3.setOnAction(e -> exitProgram());
        //Part 3
        sortDate = new Button("Sort by Date");
        sortDate.setOnAction(e -> sortByDate());
        sortName = new Button("Sort by Name");
        sortName.setOnAction(e -> sortByName());

        //Alert
        showCloseContact = new Alert(Alert.AlertType.NONE);

        //Header
        appTitle3 = new Label();
        appTitle3.setText("View Close Contacts");

        header3.getChildren().add(appTitle3);
        header3.setAlignment(Pos.CENTER);
        header3.setPadding(new Insets(30, 0, 30, 0));
        header3.setStyle("-fx-font: normal 32px 'Monaco' ");

        //ContactLabel
        selectedContact = new Label();
        selectedContact.setText("Select Covid Positive Contact");
        selectedContact.setStyle("-fx-font: normal 14px 'Helvetica' ");

        box1.getChildren().add(selectedContact);
        box1.setAlignment(Pos.CENTER);
        box1.setPadding(new Insets(20, 0, 30, 0));

        //ComboBox
        dropDown3 = new ComboBox<>();

        if(contactControl.getCl().getSize() == 0){
            dropDown3.getItems().add("List is empty");
        }
        else {
            for (int i = 0; i < contactControl.getCl().getSize(); i++) {
                dropDown3.getItems().add(contactControl.getCl().getContact(i).getUid() + " - "
                        + contactControl.getCl().getContact(i).getFirstName() + " "
                        + contactControl.getCl().getContact(i).getLastName());
            }
        }

        dropDown3.getSelectionModel().selectFirst();

        box2.getChildren().add(dropDown3);
        box2.setAlignment(Pos.CENTER);
        box2.setPadding(new Insets(30, 0, 40, 0));

        //ListView
        closeContactList = new ListView<>();
        closeContactList.setPrefSize(400, 200);
        box3.getChildren().add(closeContactList);
        box3.setAlignment(Pos.CENTER);

        //Button
        box4.getChildren().addAll(updateList, sortDate, sortName);

        //Footer
        footer3.getChildren().add(exitButton3);
        footer3.setAlignment(Pos.BOTTOM_RIGHT);
        footer3.setPadding(new Insets(20, 20, 20, 20));

        //GridPane
        content3 = new GridPane();

        content3.setHgap(10);
        content3.setVgap(20);

        content3.add(box1, 1, 0, 1, 2);
        content3.add(box2, 1, 1, 1, 2);
        content3.add(box3, 1, 2, 1, 2);
        content3.add(box4, 1, 4, 1, 2);

        content3.setAlignment(Pos.CENTER);
        content3.setStyle("-fx-background-color: #A1FCDF");

        //BorderPane
        page3 = new BorderPane();

        page3.setTop(header3);
        page3.setCenter(content3);
        page3.setBottom(footer3);

        page3.setStyle("-fx-background-color: #FCD29F");

        //TabPane + Tabs
        appTabs = new TabPane();

        tab1 = new Tab();
        tab1.setText("Add a Contact");
        tab1.setContent(page1);

        tab2 = new Tab();
        tab2.setText("Record a Close Contact");
        tab2.setContent(page2);

        tab3 = new Tab();
        tab3.setText("View a Covid Positive Close Contacts");
        tab3.setContent(page3);

        appTabs.getTabs().addAll(tab1, tab2, tab3);
        appTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tab2.setOnSelectionChanged(e -> refreshList1());
        tab3.setOnSelectionChanged(e -> refreshList2());

        //Scene
        Scene scene = new Scene(appTabs, 550, 700);
        window.setOnCloseRequest(e -> exitProgram());
        window.setScene(scene);
        window.show();
    }

    //Print list on startup if file exist - no alert
    private void viewContactListOnStartup() {
        for (Contact contact : contactControl.getCl().getContacts()){
            contactList.getItems().add(contact);
        }
    }

    //Button functions
    void addContact(){
        boolean result = contactControl.addContactToList(firstName.getText(),lastName.getText(),uid.getText(),phoneNumber.getText());
        add.setTitle("Add Contact");
        if(result){
            add.setHeaderText("Failed");
            add.setAlertType(Alert.AlertType.ERROR);
            add.setContentText("Contact ID already exist!");
        }
        else{
            add.setHeaderText("Success");
            add.setAlertType(Alert.AlertType.INFORMATION);
            add.setContentText("Contact added!");
        }
        add.show();

    }

    void removeContact(){ //Based on selections on listView
        remove1.setTitle("Remove Contact");
        remove1.setHeaderText("Confirm Delete");
        remove1.setAlertType(Alert.AlertType.CONFIRMATION);
        remove1.setContentText("Are you sure you want to delete the selected contact(s)?");
        remove2.setTitle("Remove Contact");

        Optional<ButtonType> result = remove1.showAndWait();
        if(result.isEmpty()){
            remove2.setHeaderText("Cancelled");
            remove2.setAlertType(Alert.AlertType.INFORMATION);
            remove2.setContentText("Action cancelled.");
            remove2.show();
        }
        else if(result.get() == ButtonType.OK){
            ObservableList<Integer> selectedIndices = contactList.getSelectionModel().getSelectedIndices();
            for (int i = selectedIndices.size() - 1; i >= 0; i--){
                contactControl.removeContactFromList(selectedIndices.get(i));
            }
            remove2.setHeaderText("Success");
            remove2.setAlertType(Alert.AlertType.INFORMATION);
            remove2.setContentText("Contact(s) deleted.");
            remove2.show();
        }
        else if(result.get() == ButtonType.CANCEL){
            remove2.setHeaderText("Cancelled");
            remove2.setAlertType(Alert.AlertType.INFORMATION);
            remove2.setContentText("Action cancelled.");
            remove2.show();
        }

    }

    void viewContactList() throws FileNotFoundException {
        contactList.getItems().clear();
        for (Contact contact : contactControl.getCl().getContacts()){
            contactList.getItems().add(contact);
        }
        update.setTitle("Update List");
        update.setHeaderText("Success");
        update.setAlertType(Alert.AlertType.INFORMATION);
        update.setContentText("List updated.");
        update.show();

    }

    void loadContactList() throws FileNotFoundException {
        load1.setTitle("Load Previous Save");
        load1.setHeaderText("Confirm Load");
        load1.setAlertType(Alert.AlertType.CONFIRMATION);
        load1.setContentText("New changes are not saved. Do you want to proceed?");
        load2.setTitle("Load Previous Save");

        Optional<ButtonType> result = load1.showAndWait();
        if(result.isEmpty()){
            load2.setHeaderText("Cancelled");
            load2.setAlertType(Alert.AlertType.INFORMATION);
            load2.setContentText("Action cancelled.");
            load2.show();
        }
        else if(result.get() == ButtonType.OK){
            contactList.getItems().clear();

            File saveFile = new File("Contacts.txt");
            boolean exists = saveFile.exists();
            if (exists){
                contactControl.loadFile();
                viewContactList();
            }

            load2.setHeaderText("Success");
            load2.setAlertType(Alert.AlertType.INFORMATION);
            load2.setContentText("Previous list loaded.");
            load2.show();
        }
        else if(result.get() == ButtonType.CANCEL){
            load2.setHeaderText("Cancelled");
            load2.setAlertType(Alert.AlertType.INFORMATION);
            load2.setContentText("Action cancelled.");
            load2.show();
        }
    }

    void saveChanges() throws FileNotFoundException {
        save1.setTitle("Save Changes");
        save1.setHeaderText("Confirm Save");
        save1.setAlertType(Alert.AlertType.CONFIRMATION);
        save1.setContentText("Old data will be overwritten. Do you want to proceed?");
        save2.setTitle("Save Changes");

        Optional<ButtonType> result = save1.showAndWait();
        if(result.isEmpty()){
            save2.setHeaderText("Cancelled");
            save2.setAlertType(Alert.AlertType.INFORMATION);
            save2.setContentText("Action cancelled.");
            save2.show();
        }
        else if(result.get() == ButtonType.OK){
            contactControl.printFile();
            save2.setHeaderText("Success");
            save2.setAlertType(Alert.AlertType.INFORMATION);
            save2.setContentText("Changes applied.");
            save2.show();
        }
        else if(result.get() == ButtonType.CANCEL){
            save2.setHeaderText("Cancelled");
            save2.setAlertType(Alert.AlertType.INFORMATION);
            save2.setContentText("Action cancelled.");
            save2.show();
        }

    }

    void exitProgram(){
        exit.setTitle("Exit Program");
        exit.setHeaderText("Confirm Exit");
        exit.setAlertType(Alert.AlertType.CONFIRMATION);
        exit.setContentText("Changes are not saved. Do you want to proceed?");

        Optional<ButtonType> result = exit.showAndWait();
        if(result.isEmpty()){
            exit.close();
        }
        else if(result.get() == ButtonType.OK){
            Platform.exit();
        }
        else if(result.get() == ButtonType.CANCEL){
            exit.close();
        }
    }

    void recordCloseContact(){
        if(datePicker.getValue() == null){
            datePicker.setValue(LocalDate.now());
        }

        Contact c1 = contactControl.matchContact(dropDown1.getValue().toString().substring(0,2));
        Contact c2 = contactControl.matchContact(dropDown2.getValue().toString().substring(0,2));

        String date = datePicker.getValue().toString();
        String h = hour.getValue().toString();
        String m = minute.getValue().toString();
        String d = day.getValue().toString();

        boolean result = contactControl.recordCloseContact(c1, c2, date, h, m, d);
        addRecord.setTitle("Add Close Contact");

        if(!result){
            addRecord.setHeaderText("Failed");
            addRecord.setAlertType(Alert.AlertType.ERROR);
            addRecord.setContentText("Please select two different contacts with different date and time.");
        }
        else{
            addRecord.setHeaderText("Success");
            addRecord.setAlertType(Alert.AlertType.INFORMATION);
            addRecord.setContentText("Close contact added!");
        }
        addRecord.show();
    }

    void updateCloseContact(){
        String contactId = dropDown3.getValue().toString().substring(0,2);
        closeContactList.getItems().clear();

        for(int i = 0; i < contactControl.getCl().getSize(); i++){
            if(contactControl.getCl().getContact(i).getUid().equals(contactId)){
                for(CloseContact contact : contactControl.getCl().getContact(i).getCloseContacts()){
                    closeContactList.getItems().add(contact);
                }
                break;
            }
        }

        showCloseContact.setTitle("Update List");
        showCloseContact.setHeaderText("Success");
        showCloseContact.setAlertType(Alert.AlertType.INFORMATION);
        showCloseContact.setContentText("List updated.");
        showCloseContact.show();
    }

    //Refresh ComboBox
    void refreshList1(){
        dropDown1.getItems().clear();
        if(contactControl.getCl().getSize() == 0){
            dropDown1.getItems().add("List is empty");
        }
        else {
            for (int i = 0; i < contactControl.getCl().getSize(); i++) {
                dropDown1.getItems().add(contactControl.getCl().getContact(i).getUid() + " - "
                        + contactControl.getCl().getContact(i).getFirstName() + " "
                        + contactControl.getCl().getContact(i).getLastName());
            }
        }

        dropDown2.getItems().clear();
        if(contactControl.getCl().getSize() == 0){
            dropDown2.getItems().add("List is empty");
        }
        else {
            for (int i = 0; i < contactControl.getCl().getSize(); i++) {
                dropDown2.getItems().add(contactControl.getCl().getContact(i).getUid() + " - "
                        + contactControl.getCl().getContact(i).getFirstName() + " "
                        + contactControl.getCl().getContact(i).getLastName());
            }
        }

        dropDown1.getSelectionModel().selectFirst();
        dropDown2.getSelectionModel().selectFirst();
    }

    void refreshList2(){
        dropDown3.getItems().clear();
        if(contactControl.getCl().getSize() == 0){
            dropDown3.getItems().add("List is empty");
        }
        else {
            for (int i = 0; i < contactControl.getCl().getSize(); i++) {
                dropDown3.getItems().add(contactControl.getCl().getContact(i).getUid() + " - "
                        + contactControl.getCl().getContact(i).getFirstName() + " "
                        + contactControl.getCl().getContact(i).getLastName());
            }
        }

        dropDown3.getSelectionModel().selectFirst();
        closeContactList.getItems().clear();
    }

    void sortByDate(){
        Integer selectedContact = dropDown3.getSelectionModel().getSelectedIndex();
        contactControl.sortListByDate(selectedContact);
        updateCloseContact();
    }

    void sortByName(){
        Integer selectedContact = dropDown3.getSelectionModel().getSelectedIndex();
        contactControl.sortListByName(selectedContact);
        updateCloseContact();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
