package quinzical.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import quinzical.model.QuizModel;
import quinzical.model.User;

/**
 * Controller class for the leaderboard view. Displays a table with all the scores 
 * and users in the database in order of the scores.
 * @author Daniel Cutfield
 *
 */

public class LeaderboardViewController implements Initializable {
	private QuizModel model;
	@FXML private TableView<User> tableView;
	@FXML private TableColumn<User, String> userNameColumn;
	@FXML private TableColumn<User, Integer> scoreColumn;
	@FXML private BorderPane borderPane;
	@FXML private Button mainMenuButton;
	
	/**
	 * Navigate to main menu
	 * @param event
	 */
	@FXML
	private void goMainMenu(ActionEvent event) {
		ScreenController.goMainMenu(getClass(), event);
	}

	
	/**
	 * Initialise the leaderboard screen. Populate the leaderboard table
	 * with scores.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			model = QuizModel.getModel();
			
			userNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
			scoreColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("highestScore"));
			
			//Populate list
			List<User> users = model.getDb().getAllUser();
			ObservableList<User> usersObservable = FXCollections.observableArrayList();
			for (int i = 0; i < users.size(); i++) {
				usersObservable.add(users.get(i));
			}
			
			//Put list into table
			tableView.setItems(usersObservable);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
