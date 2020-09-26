package quinzical.exception;	/**
 * 
 */

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Runtime Exception class for the application, created in order to distinguish
 * exception from other classes
 * @author Neville Loh
 * 
 */


public class QuinzicalRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public QuinzicalRuntimeException(String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("A runtime exception occured");
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
