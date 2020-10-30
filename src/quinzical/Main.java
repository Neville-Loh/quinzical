package quinzical;


import java.io.IOException;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import quinzical.model.QuizModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Main class for the Quinical application. THe application is built with Javafx11
 * The application uses Model view controller architecture
 * @author Neville, Daniel Cutfield
 * @version 0.3.1.4
 */


public class Main extends Application {
	private Stage primaryStage;
	private static  QuizModel model = QuizModel.getModel();;
	Parent root;
   
	/**
	 * Start the current application by showing primary stage
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		model = QuizModel.getModel();
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Quinzical");
		Parent root = null;
		if (model.getDb().getAllUser().size() == 0) {
			root = FXMLLoader.load(getClass().getResource("/quinzical/view/UserSelectView.fxml"));
		} else {
			//load last user
			model.setUserAsLastUser();
			root = FXMLLoader.load(getClass().getResource("/quinzical/view/MainView.fxml"));
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		//#TODO
		//primaryStage.setOnCloseRequest(Helper.confirmCloseEventHandler);
	
	}
	
	/**
	 * Main method to start the application
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
