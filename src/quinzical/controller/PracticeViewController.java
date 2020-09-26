package quinzical.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import quinzical.Main;
import quinzical.model.Question;
import quinzical.model.PracticeModel;

public class PracticeViewController implements Initializable {

	private PracticeModel model;
	private Question question;
	private int attemptNumber = 1;
	
	@FXML private Label questionLabel;
	@FXML private TextField textfield;
	
	/**
	 * Navigate to main menu
	 * @param event
	 */
	@FXML
	private void goMainMenu(ActionEvent event) {
		ScreenController.goMainMenu(getClass(), event);

	}

	/**
	 * submit the text field as user answer
	 * @param event
	 */
	@FXML
	public void centerButton(ActionEvent event) {
		submitAnswer(event);
	}

	/**
	 * submit the text field as user answer
	 * @param event
	 */
	@FXML
	public void onEnter(ActionEvent event) throws IOException {
		submitAnswer(event);
	}

	/**
	 *  Initialize the current page, using the active question from the model as
	 *  a label.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//odel = Main.getPracticeModel();
		question = model.getActiveQuestion();
		model.textToSpeech(question.toString());
		questionLabel.setText(question.toString());

	}
	
	/**
	 * Submit answer in the text field to check for correctness within model.
	 * depending on if the answer is correct or not, the next screen initialize
	 * will be different.
	 * @param event
	 */
	private void submitAnswer(ActionEvent event) {
		String input = textfield.getText();
		boolean isCorrect = model.answerQuestion(question, input);

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/quinzical/view/AnswerResultView.fxml"));
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			AnswerResultViewController controller = loader.getController();

			// Initialize different page according to the user answer
			if (isCorrect) {
				controller.validAnswerInit(question);
				nextQuestion();
			} else if (attemptNumber == 1) {
				questionLabel.setText(questionLabel.getText() + "\nTry again");
				attemptNumber++;
			} else if (attemptNumber == 2) {
				questionLabel.setText(questionLabel.getText() + "\nTry again" + "\nThe correct answer begins with " + question.getAnswer().substring(0,1));
				attemptNumber++;
			} else {
				controller.invalidAnswerInit(question);
				attemptNumber = 1;
				nextQuestion();
			}
			
			// switch to next screen
			//Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			//window.setScene(scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void nextQuestion() {
		model.nextQuestion();
		Question newQuestion = model.getActiveQuestion();
		question = newQuestion;
		questionLabel.setText(newQuestion.toString());
	}

}
