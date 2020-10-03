package quinzical;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import quinzical.model.QuizModel;
import quinzical.util.Helper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Main class for the Jeopardy application. THe application is built with Javafx
 * The application uses Model view controller architecture
 * @author Neville
 */


public class Main extends Application {
	private Stage primaryStage;
	private static  QuizModel model;
   
	/**
	 * Start the current application by showing primary stage
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		// to ensure singleton assignment
		if (model == null) {
			model = new QuizModel();
		}
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Quinzical");
//		this.primaryStage.setHeight(1024);
//		this.primaryStage.setWidth(768);
		Parent root = FXMLLoader.load(getClass().getResource("/quinzical/view/MainView.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		System.out.print("ass");
		
		////////////////////////////////// TESTING //////////////////////////////////
//		primaryStage.setTitle("Hello World");
//	    StackPane root = new StackPane();
//	    Scene scene = new Scene(root, 300, 250);
//
//	    File file = new File("src/quinzical/view/resource/background/place.jpg");
//	    Image image = new Image(file.toURI().toString());
//	    ImageView iv = new ImageView(image);
//
//	    root.getChildren().add(iv);
//	    primaryStage.setScene(scene);
//	    primaryStage.show();
		
//		BorderPane borderPane = new BorderPane();
//		
//	    File file = new File("src/quinzical/view/resource/background/place.jpg");
//	    Image image1 = new Image(file.toURI().toString());
//	    //Image image1 = new Image("src/quinzical/view/resource/background/place.jpg");
//	    Image image2 = new Image("https://cdn.sstatic.net/Sites/stackoverflow/company/img/logos/so/so-logo.png?v=9c558ec15d8a");
//
//	    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
//
//	    Background background2 = new Background(new BackgroundImage(image2,
//	            BackgroundRepeat.NO_REPEAT,
//	            BackgroundRepeat.NO_REPEAT,
//	            BackgroundPosition.CENTER,
//	            bSize));
//
//	    borderPane.setBackground(new Background(new BackgroundImage(image1,
//	            BackgroundRepeat.NO_REPEAT,
//	            BackgroundRepeat.NO_REPEAT,
//	            BackgroundPosition.CENTER,
//	            bSize)));
//
//	    Button btn = new Button("Change Background");
//	    btn.setOnAction((ActionEvent event) -> {
//	        borderPane.setBackground(background2);
//	    });
//
//	    borderPane.setCenter(btn);
//
//	    Scene scene = new Scene(borderPane, 600, 400);
//
//	    primaryStage.setScene(scene);
//	    primaryStage.show();
		////////////////////////////////////////////////////////////////////////////////
		
		//#TODO
		//primaryStage.setOnCloseRequest(Helper.confirmCloseEventHandler);
	}
	
	
	/**
	 * Static method to pass model to controller
	 * @return the current quiz model of the game
	 */
	public static QuizModel getQuizModel() {
		return model;
	}
	
	public static void main(String[] args) {
		System.out.print("ass");
		launch(args);
		//System.out.print("ass");
		
		//new test.testSQLiteDB();
	}
}
