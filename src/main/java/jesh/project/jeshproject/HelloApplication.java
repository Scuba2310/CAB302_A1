package jesh.project.jeshproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jesh.project.jeshproject.model.SqliteConnection;
import jesh.project.jeshproject.model.SqliteUserDAO;

import java.sql.Connection;
import java.io.IOException;


public class HelloApplication extends Application {
    public static final String TITLE = "SleepWell";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 490;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        String stylesheet = HelloApplication.class.getResource("CSS-Styling/HomePage.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Connection connection = SqliteConnection.getInstance();
        SqliteUserDAO sqliteUserDAO = new SqliteUserDAO();
        launch();
    }
}