
package view;

import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.util.Pair;
import javafx.application.Platform;
import javafx.geometry.*;

public class PaymentDialog {
	public Dialog getDialog() {
	// Create the custom dialog.
	Dialog<Pair<String, String>> dialog = new Dialog<>();
	dialog.setTitle("Neue Einzahlung");
	dialog.setHeaderText("Neue Einzahlung:");

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
	TextField beschreibung = new TextField();
	beschreibung.setPromptText("Beschreibung");

	grid.add(new Label("Betrag:"), 0, 0);
	grid.add(betrag, 1, 0);
	grid.add(new Label("Beschreibung:"), 0, 1);
	grid.add(beschreibung, 1, 1);

	dialog.getDialogPane().setContent(grid);

	// Request focus on the username field by default.
	Platform.runLater(() -> betrag.requestFocus());

	// Convert the result to a username-password-pair when the login button is clicked.
	dialog.setResultConverter(dialogButton -> {
	    if (dialogButton == loginButtonType) {
	        return new Pair<>(betrag.getText(), beschreibung.getText());
	    }
	    return null;
	});
	return dialog;
	}
}
