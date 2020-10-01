package quinzical.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import quinzical.Main;
import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.QuizModel;

/**
 * Controller class for Category select screen, display all category
 * and selection buttons
 * @author Neville
 *
 */
public class CategorySelectViewController implements Initializable {
	private QuizModel model;

	@FXML private Label questionLabel;
	@FXML private Label remainingQuestion;
	@FXML private GridPane centerGridPane;
	
	/**
	 * Navigate to main menu
	 * @param event
	 */
	@FXML
	private void goMainMenu(ActionEvent event) {
		ScreenController.goMainMenu(getClass(), event);
	}
	
	/**
	 * initialize the current screen, population category and 
	 * question button in category.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		model = Main.getQuizModel();
		List<Category> cats = model.getAllCategorywithoutQuestion();
		remainingQuestion.setText("");
		
		
		
		// populate row constrain for each row
		if (cats.get(0) != null) {
			for (int i = 0; i < 3; i++) {
				centerGridPane.getRowConstraints()
						.add(new RowConstraints(40, 30, -1, Priority.ALWAYS, VPos.CENTER, false));
			}
			for (int j = 0; j< 3; j++) {
				centerGridPane.getColumnConstraints()
					.add(new ColumnConstraints(-1, -1, -1, Priority.ALWAYS, HPos.CENTER, false));
			}
			
		}
		
		
		// populate button in each row and col
		int col = 0;
		int i = 0;
		for (Category category : cats) {
			
//			// set constrain for the following col
//			centerGridPane.getColumnConstraints()
//					.add(new ColumnConstraints(-1, -1, -1, Priority.ALWAYS, HPos.CENTER, false));

			Button button = new Button(category.getTitle());
			button.setPrefSize(150, 25);
			
			// category button
			button.setOnAction(new EventHandler<ActionEvent>() {
				private int catID = category.getCategoryID();
				@Override
				public void handle(ActionEvent event) {
					model.selectRandomPracticeQuestion(catID);
					ScreenController.goQuestion(getClass(), event);
				}
			});
			
			//System.out.println(category.getTitle() + " i = " + i + ", j = " + col);
			centerGridPane.add(button, col, i);
			
			col++;
			if (col >= 3) {
				i++;
				col = 0;
			}
		}

	}
}
