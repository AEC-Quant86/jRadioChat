package com.aec.jradiochat;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class App extends Application {

    
    @Override
    public void start(Stage primaryStage) {
        try {

        	AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("MainView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            //primaryStage.setResizable(false);
            primaryStage.setTitle("jRadioChat");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        
        launch(args);

    }
}
