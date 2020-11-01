package quinzical.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import quinzical.controller.component.DrawerController;
import quinzical.model.QuizModel;
import test.testSession;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
/**
 * Controller class for main menu view at MainView.fxml.
 * class control each button in the main menu
 * @author Neville, Daniel
 * 
 */
public class MainController implements Initializable{
	private QuizModel model;
	@FXML private AnchorPane anchorPane;
	@FXML private JFXHamburger hamburger;
	@FXML private JFXDrawer drawer;
	@FXML private Button startBtn;
	@FXML private Button practiceBtn;
	@FXML private Button LeaderboardBtn;
	@FXML private Button resetBtn;
	@FXML private Button quitBtn;
	
	/**
	 * Navigate to question select screen
	 * @param event
	 */
	@FXML
	private void showQuestionSelectView(ActionEvent event){
		ScreenController.goQuestionSelect(getClass(), event);
	}
	
	
	/**
	 * Navigate to Category select menu
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void showCategorySelectView(ActionEvent event) throws IOException{
		ScreenController.goCategorySelect(getClass(), event);
	}
	
	
	/**
	 * Navigate to leaderboard
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void showLeaderboardView(ActionEvent event) throws IOException {
		ScreenController.goLeaderboard(getClass(), event);
	}
	
	/**
	 * Send out reset Alert, if user confirm, reset the game
	 * @param event
	 */
	@FXML
	private void resetButtonClick(ActionEvent event) throws IOException{
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Are you sure you want to reset the game? Your save will be reset to its initial status. This can not be undone.",
				ButtonType.YES, ButtonType.NO
			);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.setTitle("Reset Confirmation");
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
	 * Save and exit the application
	 * @param event
	 */
	@FXML
	private void quitButtonClick(ActionEvent event) throws IOException{
		model.save();
		Platform.exit();
	}
	
	/**
	 * Method for testing purposes
	 * Sets all questions in the current session except for 1 to attempted
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void completeSession(ActionEvent event) throws IOException {
		testSession.completeSession(model.getSession());
	}
	
	/**
	 * Populate the quiz model
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DrawerController.initDrawer(getClass(), drawer, hamburger);
		model = QuizModel.getModel();
	}
	


}
