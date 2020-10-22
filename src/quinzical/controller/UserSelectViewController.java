package quinzical.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import quinzical.Main;
import quinzical.db.QuinzicalDB;
import quinzical.model.QuizModel;
import quinzical.model.User;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
/**
 * Controller class for main menu view at MainView.fxml.
 * class control each button in the main menu
 * @author Neville
 * 
 */
public class UserSelectViewController implements Initializable{
	private QuizModel model;
	@FXML TextField userName;
	@FXML VBox vbox;
	/**
	 * Navigate to question select screen
	 * @param event
	 */
	@FXML
	private void createUserButton(ActionEvent event){
		if (userName.getText() != null || userName.getText().strip() != "") {
			System.out.println(userName.getText());
			model.createNewUser(userName.getText());
			ScreenController.goMainMenu(this.getClass(), event);
		}
		//ScreenController.goQuestionSelect(getClass(), event);
	}
	
	
	/**
	 * submit the text field as user answer
	 * @param event
	 */
	@FXML
	public void onEnter(ActionEvent event) throws IOException {
		if (userName.getText() != null || userName.getText().strip() != "") {
			int resultid = model.createNewUser(userName.getText());
			model.setUser(resultid);
			ScreenController.goMainMenu(this.getClass(), event);
		}
	}
	

	/**
	 * Populate the quiz model
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		model = QuizModel.getModel();
		QuinzicalDB db = model.getDb();
		List<User> users = db.getAllUser();
		
		for (User user : users) {
			Button btn = new Button();
			btn.setText(user.getName());
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					model.setUser(user);
					ScreenController.goMainMenu(this.getClass(), event);
				}
			});
			vbox.getChildren().add(btn);
		}
	}
	


}
