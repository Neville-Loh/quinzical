package quinzical.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import quinzical.model.QuizModel;

/**
 * Helper class for application Jeopardy, contain all helper function 
 * of the application
 * @author Neville
 *
 */
public class Helper {
	
	/**
	 * Run a bash command using process builder
	 * @param command
	 */
	public static void runBash(String command) {
		try {
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			Process process = pb.start();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			int exitStatus = process.waitFor();
			if (exitStatus == 0) {
				String line;
				while ((line = stdout.readLine()) != null) {
					System.out.println(line);
				}
			} else {
				String line;
				while ((line = stderr.readLine()) != null) {
					System.err.println(line);
				}
			}
			process.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 /**S
     * Lambda implementation of confirmClose event handler for prompting the user
     * to confirm exit without saving
     */
    public static EventHandler<WindowEvent> confirmCloseEventHandler = (event) -> {
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Do you wish to exit without saving?",
                ButtonType.YES, ButtonType.NO
        );
        
        Button noButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.NO
        );
        noButton.setText("save and exit");
        closeConfirmation.setHeaderText(null);
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        
        // save and exit
        if (ButtonType.NO.equals(closeResponse.get())) {
        	QuizModel model = QuizModel.getModel();
        	model.save();
        	Platform.exit();
        	System.exit(0);
        }
        // exit without saving
        if (ButtonType.YES.equals(closeResponse.get())) {
        	Platform.exit();
        	System.exit(0);
        } else {
            event.consume();
        }
    };
    
	/**
	 * Create a pop up javafx element for notification
	 * @param message
	 * @return pop up element
	 */
    public static Popup createPopup(final String message) {
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                popup.hide();
            }
        });
        label.getStylesheets().add("/jeopardy/view/css/Styles.css");
        label.getStyleClass().add("popup");
        popup.getContent().add(label);
        return popup;
    }
    
    /**
     * Show a pop up message on the top left cornner at a given stage
     * in javaFX stages.
     * @param message
     * @param stage
     */
    public static void showPopupMessage(final String message, final Stage stage) {
        final Popup popup = createPopup(message);
        popup.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                popup.setX(stage.getX());
                popup.setY(stage.getY()+30);
            }
        });        
        popup.show(stage);
    }
    
}

