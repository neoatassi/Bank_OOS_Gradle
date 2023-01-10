/**
 * 
 */
package view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class DeleteConfirmationDialog {
	public static Alert getAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Bestätigen");
		alert.setHeaderText("Bitte bestätigen:");
		alert.setContentText("Wollen Sie den Eintrag wirklich löschen?");
		return alert;
	}
}
