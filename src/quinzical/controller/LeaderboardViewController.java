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
import quinzical.model.Session;
import quinzical.model.User;

/**
 * Controller class for the leaderboard view. Displays a table with all the scores 
 * and users in the database in order of the scores.
 * @author Daniel Cutfield
 *
 */

public class LeaderboardViewController implements Initializable {
	private QuizModel model;
	@FXML private TableView<Session> tableView;
	@FXML private TableColumn<Session, Integer> rankColumn;
	@FXML private TableColumn<Session, String> userNameColumn;
	@FXML private TableColumn<Session, Integer> scoreColumn;
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
			
			userNameColumn.setCellValueFactory(new PropertyValueFactory<Session, String>("user"));
			scoreColumn.setCellValueFactory(new PropertyValueFactory<Session, Integer>("winnings"));
			//TODO add function that gets all sessions
			//List<Session> sessions = model.getDb();
			// Populate table
			ObservableList<Session> sessions = getSessions();
			tableView.setItems(sessions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * populates a list of sessions for testing purposes
	 * @return
	 */
	private ObservableList<Session> getSessions() {
		ObservableList<Session> sessions = FXCollections.observableArrayList();
		sessions.add(new Session(new User("Daniel")));
		Session session = new Session(new User("Neville"));
		session.setWinnings(500);
		sessions.add(session);
		session = new Session(new User("Random"));
		session.setWinnings(300);
		sessions.add(session);
		return sessions;
	}

}
