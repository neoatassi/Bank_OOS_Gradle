
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import bank.*;
import bank.exceptions.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Pair;


public class AccountViewController implements Initializable {

    PrivateBank pB = null;
    List<Transaction> allTransactions = null;

    /**
     * 0 = alle anzeigen, 1 = auf, 2= ab, 3= pos, 4 = neg
     */
    int sortierung = 0;
    @FXML ListView<String> transactionList;
    @FXML Label accountName;
    @FXML Label kontostandLabel;
    public void changeScene(ActionEvent event) {
        Parent accountView = null;
        try {
            accountView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainView.fxml")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Scene accountScene = new Scene(accountView);
        //Access the stage via Singleton
        Stage app_stage = StageHolder.getInstance().getStage();

        app_stage.setScene(accountScene);
        app_stage.setTitle("Hauptansicht");
        app_stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Get Username
        UserHolder userHolder = UserHolder.getInstance();
        String userName = userHolder.getUserName();
        accountName.setText(userName);
        //Prefill list view
        try {
            pB = new PrivateBank("Privatbank Hansen", 0.1,0.2,"json/");
        } catch (IOException | TransactionAlreadyExistException | AccountDoesNotExistException | AccountAlreadyExistsException | TransactionAttributeException | AmountNotValidException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        update();
    }

    public void update() {
        //Set kontostand
        String userName = UserHolder.getInstance().getUserName();
        kontostandLabel.setText("Kontostand: " + pB.getAccountBalance(userName) + "Euro");

        //Update ListView
        switch(sortierung) {
            case 0:
                allTransactions = pB.getTransactions(userName);
                break;
            case 1:
                allTransactions = pB.getTransactionsSorted(userName, true);
                break;
            case 2:
                allTransactions = pB.getTransactionsSorted(userName, false);
                break;
            case 3:
                allTransactions = pB.getTransactionsByType(userName, true);
                break;
            case 4:
                allTransactions = pB.getTransactionsByType(userName, false);
                break;
            default:
                throw new IllegalArgumentException();


        }
        List<String> transactions = new ArrayList<String>();
        for(Transaction t : allTransactions)
            transactions.add(t.toString());

        transactionList.setItems(FXCollections.observableArrayList(transactions));

    }
    @FXML
    public void alle(){ sortierung = 1; update(); }
    @FXML
    public void aufsteigend() { sortierung = 1; update(); }
    @FXML
    public void absteigend() {sortierung = 2; update(); }
    @FXML
    public void positiv() {sortierung = 3; update();}
    @FXML
    public void negativ() { sortierung = 4; update(); }

    public void fehlerDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText("Eingabefehler");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void neueEinzahlung() {

        Dialog dialog = new view.PaymentDialog().getDialog();
        Optional<Pair<String, String>> result = dialog.showAndWait();
        if(result.isEmpty())
            fehlerDialog("Keine Werte");
        else {
            try {
                //Hole Werte aus Optional<String, String>
                result.ifPresent(betragBeschreibung -> {
                    double betrag = Double.parseDouble(betragBeschreibung.getKey());
                    String beschreibung = betragBeschreibung.getValue();
                    //Checke ob Beschreibung leer
                    if(beschreibung == "")
                        throw new NumberFormatException();
                    Payment p = null;
                    try {
                        p = new Payment(java.time.LocalDate.now().toString(), betrag, beschreibung);
                    } catch (AmountNotValidException e) {
                        e.printStackTrace();
                    }
                    try {
                        pB.addTransaction(UserHolder.getInstance().getUserName(), p);
                    } catch (TransactionAlreadyExistException | AccountDoesNotExistException | IOException | TransactionAttributeException | AmountNotValidException e) {
                        dialog.close();
                        fehlerDialog(e.getMessage());

                    }
                });
            }
            catch(IllegalArgumentException e) {
                dialog.close();
                fehlerDialog("Falsche oder fehlende Eingaben");
            }
        }
        update();

    }

    @FXML
    public void neueUeberweisung() throws AmountNotValidException {
        Dialog dialog = new view.TransactionDialog().getDialog();
        Optional<ArrayList<String>> inputValues = dialog.showAndWait();
        ArrayList<String> values = null;
        //Eingaben prüfen
        try {
            values = inputValues.get();
        }
        catch(NoSuchElementException e) {
            fehlerDialog("Keine Werte angegeben");
            return;
        }
        //Alle Werte belegt?
        if(values.size() != 3 || values.get(0) == "" || values.get(1) == "" || values.get(2) == "") {
            fehlerDialog("Werte fehlen");
            return;
        }
        //Betrag korrekt angegeben?
        double betrag = 0;
        String senderEmpf = values.get(1);
        String beschreibung = values.get(2);
        try {
            betrag = Double.parseDouble(values.get(0));
        }
        catch(NumberFormatException e) {
            fehlerDialog("Falscher Betrag");
            return;
        }
        //betrag > 0: Outgoing Transfer
        Transaction t = null;
        if(betrag >= 0) {
            OutgoingTransfer oT = new OutgoingTransfer(java.time.LocalDate.now().toString(),betrag, beschreibung, UserHolder.getInstance().getUserName(),
                    senderEmpf);
            t = (Transaction) oT;
        }
        else {
            IncomingTransfer iT = new IncomingTransfer(java.time.LocalDate.now().toString(),betrag * (-1),beschreibung,
                    senderEmpf, UserHolder.getInstance().getUserName());
            t = (Transaction) iT;
        }
        try {
            pB.addTransaction(UserHolder.getInstance().getUserName(), t);
        } catch (TransactionAlreadyExistException | AccountDoesNotExistException | IOException | TransactionAttributeException e) {
            fehlerDialog("Überweisung konnte nicht hinzugefügt werden");
            e.printStackTrace();
        }


        update();
    }

    @FXML
    public void loescheTrans() {
        Alert alert = view.DeleteConfirmationDialog.getAlert();
        Optional<ButtonType> result = alert.showAndWait();
        //Check if user confirmed deletion
        if (!(result.get() == ButtonType.OK)){
            return;
        }
        Transaction toRemove = allTransactions.get(transactionList.getSelectionModel().getSelectedIndex());
        try {
            pB.removeTransaction(UserHolder.getInstance().getUserName(), toRemove);
        } catch (TransactionDoesNotExistException | IOException | AccountDoesNotExistException e) {
            fehlerDialog("Transaktion konnte nicht gelöscht werden");
            e.printStackTrace();
        }
        update();
    }


}
