/**
 * 
 */
package view;

import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.*;


public class TransactionDialog {
	public Dialog getDialog() {
	// Create the custom dialog.
	Dialog<ArrayList<String>> dialog = new Dialog<>();
	dialog.setTitle("Neue Überweisung");
	dialog.setHeaderText("Neue Überweisung:");

	// Set the button types.
	ButtonType loginButtonType = new ButtonType("Speichern", ButtonData.OK_DONE);
	dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

	// Create the username and password labels and fields.
	GridPane grid = new GridPane();
	grid.setHgap(10);
	grid.setVgap(10);
	grid.setPadding(new Insets(20, 150, 10, 10));

	TextField betrag = new TextField();
	betrag.setPromptText("Betrag");
	TextField sender = new TextField();
	sender.setPromptText("Sender/Empfänger");
	TextField beschreibung = new TextField();
	beschreibung.setPromptText("Beschreibung");

	grid.add(new Label("Betrag:"), 0, 0);
	grid.add(betrag, 1, 0);
	grid.add(new Label("Sender/Empfägner:"), 0, 1);
	grid.add(sender, 1, 1);
	grid.add(new Label("Beschreibung"),0,2);
	grid.add(beschreibung,1,2);
	
	dialog.getDialogPane().setContent(grid);

	// Request focus on the username field by default.
	Platform.runLater(() -> betrag.requestFocus());

	// Convert the result to a username-password-pair when the login button is clicked.
	dialog.setResultConverter(dialogButton -> {
	    if (dialogButton == loginButtonType) {
	    	ArrayList<String> list = new ArrayList<>();
	    	list.add(betrag.getText());
	    	list.add(sender.getText());
	    	list.add(beschreibung.getText());
	    	return list;
	    }
	    return null;
	});
	return dialog;
	}
}
