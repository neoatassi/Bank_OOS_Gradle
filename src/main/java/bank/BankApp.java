package bank;

import javafx.fxml.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BankApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent anwendung = FXMLLoader.load(Objects.requireNonNull(getClass().
                getResource("/mainView.fxml")));

        Scene scene = new Scene(anwendung);
        stage.setScene(scene);
        stage.setTitle("Uebung");
        stage.show();

    }
}
