package quinzical.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import quinzical.controller.component.DrawerController;
import quinzical.model.QuizModel;
import quinzical.model.User;

/**
 * Controller class for Game over view at GameOverView.fxml.
 * Display a message saying that the game is over and must be reset
 * to play again
 * @author Neville
 */
public class GameOverViewController implements Initializable {

	private QuizModel model;

	@FXML private Label winningLabel;
	@FXML private JFXHamburger hamburger;
	@FXML private JFXDrawer drawer;
	
	/**
	 * Navigate to main menu
	 * @param event
	 */
	@FXML
	private void goMainMenu(ActionEvent event) throws IOException {
		ScreenController.goMainMenu(getClass(), event);
	}
	
	/**
	 * Raise alert for reset option, if user proceed by clicking yes, reset the model
	 * to its initial state.
	 * @param event
	 */
	@FXML
	private void resetButtonClick(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Are you sure you want to reset the game? Your save will be reset to its initial status. This can not be undone.",
				ButtonType.YES, ButtonType.NO
			);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.setTitle("Rest Confirmation");
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			model.reset();
			ScreenController.goMainMenu(getClass(), event);
		}
		else {
			event.consume();
		}
	}
	
	/**
	 * Initialize the current scene
	 * populate label of current winning.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DrawerController.initDrawer(getClass(), drawer, hamburger);
		model = QuizModel.getModel();
		User user = model.getUser();
		
		// Set final winning label
		String scoreStr = model.getWinningStr();
		winningLabel.setText(scoreStr);
		
		if (user.getHighestScore() < model.getWinning()) {
			System.out.println("THis is avativatewasiadsfsdkfjlsda");
			user.setHighestScore(model.getWinning());
			model.getDb().updateUser(user);
		}
		model.finishCurrentSession();
	}

}
