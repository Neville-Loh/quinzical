package quinzical.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import quinzical.Main;
import quinzical.controller.component.DrawerController;
import quinzical.model.PracticeQuestion;
import quinzical.model.Question;
import quinzical.model.QuizModel;

/**
 * Controller class for question view at QuestionView.fxml. Display are question
 * and text field
 * 
 * @author Neville
 *
 */
public class QuestionViewController implements Initializable {

	private QuizModel model;
	private Question question;

	@FXML private Label questionLabel;
	@FXML private TextField textfield;
	@FXML private Label attempLabel;
	@FXML private JFXHamburger hamburger;
	@FXML private JFXDrawer drawer;

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
		if (question.isPractice()) {
			submitPracticeAnswer(event);
		} else {
			submitAnswer(event);
		}
	}

	/**
	 * submit the text field as user answer
	 * @param event
	 */
	@FXML
	public void onEnter(ActionEvent event) throws IOException {
		if (question.isPractice()) {
			submitPracticeAnswer(event);
		} else {
			submitAnswer(event);
		}
	}

	/**
	 * play back button for the text to speech voice
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void playBackButton(ActionEvent event) throws IOException {
		model.textToSpeech(question.toString());
	}

	/**
	 * Don't know button which bring user to answer screen directly
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void dontKnowButton(ActionEvent event) throws IOException {
		question.setAttempted(true);
		if (!question.isPractice()){
			model.getSession().incrementRemainingQuestion(-1);
		}
		goAnswerPage(false, event);
	}

	/**
	 * Initialize the current page, using the active question from the model as a
	 * label.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DrawerController.initDrawer(getClass(), drawer, hamburger);
		try {
			model = Main.getQuizModel();
			question = model.getActiveQuestion();
			model.textToSpeech(question.toString());
		
			if (question.isPractice()) {
				questionLabel.setText(question.getPrompt());
				attempLabel.setText("Attempt Left: " + ((PracticeQuestion) question).getAttemptLeft());
			} else {
				questionLabel.setText("");
				attempLabel.setText("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Submit answer in the text field to check for correctness within model.
	 * depending on if the answer is correct or not, the next screen initialize will
	 * be different.
	 * 
	 * @param event
	 */
	private void submitAnswer(ActionEvent event) {
		String input = textfield.getText();
		boolean isCorrect = model.answerQuestion(question.getID(), input); // #TODO change this
		goAnswerPage(isCorrect, event);
	}

	/**
	 * Submit answer for practice in the text field to check for correctness within model.
	 * depending on if the answer is correct or not, the next screen initialize will
	 * be different. If the attempted left is 1, show hint for the quetsion
	 * @param event
	 */
	private void submitPracticeAnswer(ActionEvent event) {
		PracticeQuestion pq = (PracticeQuestion) question;
		String input = textfield.getText();
		boolean isCorrect = model.answerPracticeQuestion(input); // #TODO change this
		attempLabel.setText("Attempt Left: " + pq.getAttemptLeft());
		
		if (pq.getAttemptLeft() == 1) {
			// show hint
			attempLabel.setText("Attempt Left: " + pq.getAttemptLeft() + ", The answer starts with '"
					+ pq.getAnswer().charAt(0) + "'");
		}

		// Initialize next page if attempt left = 0 or answer is correct
		if (pq.getAttemptLeft() == 0 || isCorrect) {
			goAnswerPage(isCorrect, event);
		}
	}
	
	
	/**
	 * Go to the answer page depending on if the passed in boolean is right or wrong
	 * The initialized screen will be different depending on the boolean
	 * @param isCorrect
	 * @param event
	 */
	private void goAnswerPage(boolean isCorrect, ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/quinzical/view/AnswerResultView.fxml"));
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			AnswerResultViewController controller = loader.getController();

			// Initialize different page according to the user answer
			if (isCorrect) {
				controller.validAnswerInit(question);
			} else {
				controller.invalidAnswerInit(question);
			}

			// switch to next screen
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}