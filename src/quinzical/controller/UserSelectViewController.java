package quinzical.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import quinzical.Main;
import quinzical.model.QuizModel;
import javafx.scene.control.Button;
/**
 * Controller class for main menu view at MainView.fxml.
 * class control each button in the main menu
 * @author Neville
 * 
 */
public class UserSelectViewController implements Initializable{
	private QuizModel model;
	@FXML Button btn1;
	@FXML Button btn2;
	
	/**
	 * Navigate to question select screen
	 * @param event
	 */
	@FXML
	private void createUserButton(ActionEvent event){
		ScreenController.goQuestionSelect(getClass(), event);
	}
	


	/**
	 * Populate the quiz model
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("asdfas");
		
		model = Main.getQuizModel();
	}
	


}
