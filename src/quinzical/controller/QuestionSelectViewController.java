package quinzical.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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
 * Controller class for Question select screen, display all category
 * and selection buttons
 * @author Neville
 *
 */
public class QuestionSelectViewController implements Initializable {
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
		try {
		model = Main.getQuizModel();
		List<Category> cats = model.getCategoryList();
		remainingQuestion.setText(Integer.toString(model.getRemainingQuestionCount()));
		
		// populate row constrain for each row
		if (cats.get(0) != null) {
			for (int i = 0; i <= cats.get(0).getQuestionCount(); i++) {
				centerGridPane.getRowConstraints()
						.add(new RowConstraints(40, 30, -1, Priority.ALWAYS, VPos.CENTER, false));
			}
		}
		
		// populate button in each row and column
		int col = 0;
		int row;
		for (Category category : cats) {
			row = 1;
			
			// set constraints for the following column
			centerGridPane.getColumnConstraints()
					.add(new ColumnConstraints(-1, -1, -1, Priority.ALWAYS, HPos.CENTER, false));
			
			// setting button for category
			Label label = new Label(category.getTitle());
			centerGridPane.add(label, col, 0);
			Button button = null;
			
			// setting button for each question
			ArrayList<Question> questionList = category.getQuestions();
			for (int i=0; i < questionList.size(); i++){
			    Question question = questionList.get(i);
			    
			    // Display the button if the question is not attempt
				if (!question.isAttempted()) {
					button = new Button(Integer.toString(question.getScore()));
					button.setPrefSize(150, 25);
					button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							model.setActiveQuestion(question);
							ScreenController.goQuestion(getClass(), event);
						}
					});
					
					// Set active question if it is the non-attempted question with the lower score
					if (i == questionList.size() - 1 || questionList.get(i+1).isAttempted()) {
						button.setDisable(false);
					} else {
						button.setDisable(true);
					}
					
					centerGridPane.add(button, col, row);
				}
				
				row++;
			}

			col++;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
