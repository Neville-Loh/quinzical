package jeopardy.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import jeopardy.Main;
import jeopardy.model.QuizModel;

/**
 * Controller class for question view at QuestionView.fxml.
 * Display are question and text field
 * @author Neville
 */
public class GameOverViewController implements Initializable {

	private QuizModel model;

	@FXML private Label winningLabel;
	
	/**
	 * Navigate to main menu
	 * @param event
	 */
	@FXML
	private void goMainMenu(ActionEvent event) throws IOException {
		ScreenController.goMainMenu(getClass(), event);
	}
	
	/**
	 * Raise alert for rest option, if user proceed if yes, reset the model
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
		try {
		model = Main.getQuizModel();
		String scoreStr = model.getWinningStr();
		winningLabel.setText(scoreStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
