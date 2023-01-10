
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import bank.*;
import bank.exceptions.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import view.DeleteConfirmationDialog;

public class MainViewController extends Application implements Initializable{

    PrivateBank pB = null;
    @FXML ListView<String> accountList;
    @FXML MenuItem loeschenKontext;
    @FXML MenuItem auswaehlenKontext;

    @Override
    public void start(Stage stage) throws IOException {
        Parent mainView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainView.fxml")));
        //Load Scenes from FXML
        Scene mainScene = new Scene(mainView);

        //Save stage
        StageHolder stageHolder = StageHolder.getInstance();
        stageHolder.setStage(stage);
        stage.setScene(mainScene);
        stage.setTitle("Hauptfenster");
        stage.show();
    }


    public void changeScene(ActionEvent event) throws IOException {
        //Save selected User
        UserHolder userInstance = UserHolder.getInstance();
        String userName = accountList.getSelectionModel().getSelectedItem();
        userInstance.setUserName(userName);


        Parent accountView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/accountView.fxml")));
        Scene accountScene = new Scene(accountView);

        Stage app_stage = StageHolder.getInstance().getStage();
        app_stage.setScene(accountScene);
        app_stage.setTitle("Accountansicht");
        app_stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


    @FXML
    public void neuerAccountDialog(){
        TextInputDialog dialog = new TextInputDialog("Atassi");
        dialog.setTitle("Neuen Account anlegen");
        dialog.setHeaderText("Account anlegen");
        dialog.setContentText("Bitte Account Name angeben:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            try {
                pB.createAccount(name);
                updateList();
            } catch (AccountAlreadyExistsException e) {
                System.err.println(e.getMessage());
            }
        });
    }
    @FXML
    public void loescheAccount(Event event) {
        Alert alert = DeleteConfirmationDialog.getAlert();
        Optional<ButtonType> result = alert.showAndWait();
        //Check if user confirmed deletion
        if (!(result.get() == ButtonType.OK)){
            return;
        }
        try {
            pB.deleteAccount(accountList.getSelectionModel().getSelectedItem());
        } catch (AccountDoesNotExistException | IOException e) {
            e.printStackTrace();
        }
        updateList();
    }

    @FXML
    public void auswaehlen(Event event) {
        System.out.println("Auswaehlen");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Prefill list view
        try {
            pB = new PrivateBank("Privatbank Muster", 0.1,0.2,"json/");
        } catch (IOException | TransactionAlreadyExistException | AccountDoesNotExistException | AccountAlreadyExistsException | TransactionAttributeException | AmountNotValidException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        List<String> allAccounts = pB.getAllAccounts();
        accountList.setItems(FXCollections.observableArrayList(allAccounts));

    }

    private void updateList() {
        accountList.setItems(FXCollections.observableArrayList(pB.getAllAccounts()));
    }

}
