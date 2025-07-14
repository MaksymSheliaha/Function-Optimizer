package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class App extends Application {


    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("main-window.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setScene(scene);

        stage.setResizable(false);

        stage.show();

    }



    public static void main(String[] args) {
        launch();
    }

}