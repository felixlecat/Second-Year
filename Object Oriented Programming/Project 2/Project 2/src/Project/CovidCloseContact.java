package Project;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CovidCloseContact extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Stage
        primaryStage.setTitle("Covid Close Contact Application");

        //Borderpane
        BorderPane mainPage = new BorderPane();

        //Scene
        Scene scene = new Scene(mainPage,500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
