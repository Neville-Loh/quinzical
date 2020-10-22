package quinzical.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import quinzical.Main;
import quinzical.controller.component.DrawerController;
import quinzical.model.Category;
import quinzical.model.QuizModel;

/**
 * Controller class for Category select screen, display all category
 * and selection buttons
 * @author Neville
 *
 */
public class SelectCategoryViewController implements Initializable {
	private QuizModel model;

	@FXML private BorderPane borderPane;
	@FXML private GridPane centerGridPane;
	@FXML private JFXHamburger hamburger;
	@FXML private JFXDrawer drawer;
	@FXML private Label categoryLabel;
	
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
		DrawerController.initDrawer(getClass(), drawer, hamburger);
		model = Main.getQuizModel();
		List<Category> cats = model.getAllCategorywithoutQuestion();
		centerGridPane.setGridLinesVisible(false);
		categoryLabel.setText("");
		
		// populate row constrain for each row
		if (cats.get(0) != null) {
			for (int i = 0; i < 3; i++) {
				centerGridPane.getRowConstraints()
						.add(new RowConstraints(80, 40, -1, Priority.ALWAYS, VPos.CENTER, false));
			}
			for (int j = 0; j< 3; j++) {
				centerGridPane.getColumnConstraints()
					.add(new ColumnConstraints(-1, -1, -1, Priority.ALWAYS, HPos.CENTER, false));
			}
			
		}
		
		// background image 
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

		
		
		// populate button in each row and col
		int col = 0;
		int i = 0;
		for (Category category : cats) {


			// Category button 
			Button button = new Button(category.getTitle());
			button.setPrefSize(150, 25);
			
			// ON ACTION
			button.setOnAction(new EventHandler<ActionEvent>() {
				private int catID = category.getCategoryID();
				@Override
				public void handle(ActionEvent event) {
					model.selectRandomPracticeQuestion(catID);
					ScreenController.goQuestion(getClass(), event);
				}
			});
			
			// ON HOVER change to respective background
			button.addEventHandler(MouseEvent.MOUSE_ENTERED,
			        new EventHandler<MouseEvent>() {
			          @Override
			          public void handle(MouseEvent e) {
			        	  categoryLabel.setText(category.getTitle());
			        	  
			        	  File file = new File("resource/background/"+ category.getCategoryID()+".jpg");
			        	  Image image1 = new Image(file.toURI().toString());
							
					    borderPane.setBackground(new Background(new BackgroundImage(image1,
					            BackgroundRepeat.NO_REPEAT,
					            BackgroundRepeat.NO_REPEAT,
					            BackgroundPosition.CENTER,
					            bSize)));
			           
			          }
			        });
				
			centerGridPane.add(button, col, i);
			
			col++;
			if (col >= 3) {
				i++;
				col = 0;
			}
		}

	}
}
