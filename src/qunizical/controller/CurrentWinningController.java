package qunizical.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import quinzical.Main;
import qunizical.model.QuizModel;

/**
 * Controller class for current winning view at CurrentWinningView.fxml.
 * Display the current winning of the user
 * @author Neville
 */
public class CurrentWinningController implements Initializable{
	
	private QuizModel model;
	
	@FXML private Label winningLabel;
	
	/**
	 * Navigate to the main menu
	 * @param event
	 */
	@FXML
	private void goMainMenu(ActionEvent event) {
		ScreenController.goMainMenu(getClass(), event);
	}
	
	/**
	 * Initialize screen and populate model, and current winning of user.
	 * @param event
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		model = Main.getQuizModel();
		String scoreStr = model.getWinningStr();
		winningLabel.setText(scoreStr);
		
	}
	
	
	
}
