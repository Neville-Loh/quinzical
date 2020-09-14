package quinzical.util;	/**
 * 
 */

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Runtime Exception class for the application, created in order to distinguish
 * exception from other classe
 * @author Neville Loh
 * 
 */


public class JeopardyRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public JeopardyRuntimeException(String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("A runtime exception occured");
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
