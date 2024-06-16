package io.sabitovka.donntu;

import io.sabitovka.donntu.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Application extends javafx.application.Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        MainController.createView(stage).show();
    }

    public static void main(String[] args) {
        launch();
    }
}
