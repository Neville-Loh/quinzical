package quinzical.controller;
import javafx.fxml.FXML;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import quinzical.Main;
import quinzical.controller.component.DrawerController;
import quinzical.model.Question;
import quinzical.model.QuizModel;

/**
 * Controller class for answer view after a question
 * Display the solution to user if incorrect
 * @author Neville
 */
public class AnswerResultViewController{
	
	private QuizModel model;
	
	@FXML private Label winningLabel;
	@FXML private Label isCorrectLabel;
	@FXML private Label correctAnsLabel;
	@FXML private HBox bottomHBox;
	@FXML private JFXHamburger hamburger;
	@FXML private JFXDrawer drawer;
	@FXML private Label winningText;
	
	/**
	 * Navigate to main menu
	 * @param event
	 */
	@FXML
	private void goMainMenu(ActionEvent event){
		ScreenController.goMainMenu(getClass(), event);
	}
	
	/**
	 * Navigate to question select screen
	 * @param event
	 */
	@FXML
	private void goQuestionSelect(ActionEvent event) {
		ScreenController.goQuestionSelect(getClass(), event);
	}
	
	/**
	 * Initialize the current page if the user answer is correct
	 * @param question
	 */
	public void validAnswerInit(Question question) {
		DrawerController.initDrawer(getClass(), drawer, hamburger);
		model = Main.getQuizModel();
		isCorrectLabel.setText("Correct");
		model.textToSpeech("Correct");
		winningText.setText("");
		winningLabel.setText("");
		
		if (!question.isPractice()) {
			String scoreStr = model.getWinningStr();
			winningText.setText("Your current Winning is");
			winningLabel.setText(scoreStr);
			checkGameOverStatus();
		}
	}
	
	/**
	 * Initialize the current page if the user answer is wrong
	 * @param question
	 */
	public void invalidAnswerInit(Question question) {
		DrawerController.initDrawer(getClass(), drawer, hamburger);
		model = Main.getQuizModel();
		isCorrectLabel.setText("Incorrect");
		winningText.setText("");
		winningLabel.setText("");
		
		String correctAnsStr = "The correct answer is " + question.getAnswer() + ".";
		correctAnsLabel.setText(correctAnsStr);
		model.textToSpeech("Incorrect. " + correctAnsStr);
		if (!question.isPractice()) {
			String scoreStr = model.getWinningStr();
			winningText.setText("Your current Winning is");
			winningLabel.setText(scoreStr);
			checkGameOverStatus();
		}
	}
	
	/**
	 * Check the number of remaining question, if 0 remains,
	 * set bottom bar to a next button which brings user to the game over
	 * screen.
	 */
	private void checkGameOverStatus() {
		if (model.getRemainingQuestionCount() == 0) {
			bottomHBox.getChildren().clear();
			Button button = new Button("Next");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event){
					ScreenController.goGameOver(getClass(), event);
				}
			});
			bottomHBox.getChildren().add(button);
		}
	}
	
	
	
}
