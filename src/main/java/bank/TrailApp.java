package bank;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TrailApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField textField = new TextField("Howdy");
        Scene scene = new Scene(textField);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
