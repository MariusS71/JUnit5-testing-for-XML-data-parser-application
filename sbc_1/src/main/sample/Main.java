package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        /*
       FXMLLoader fxmlLoader = new FXMLLoader(sample.Main.class.getResource("page1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
        primaryStage.setTitle("Tema 2 SBC");
        primaryStage.setScene(scene);
        primaryStage.show();
    */
        FXMLLoader fxmlLoader = new FXMLLoader(sample.Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}