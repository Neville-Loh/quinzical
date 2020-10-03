package quinzical;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXRippler.RipplerMask;
import com.jfoenix.controls.JFXRippler.RipplerPos;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import quinzical.model.QuizModel;
import quinzical.util.Helper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;



import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;
import javafx.util.Duration;

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
		model = QuizModel.getModel();
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Quinzical");
//		this.primaryStage.setHeight(1024);
//		this.primaryStage.setWidth(768);
		Parent root = FXMLLoader.load(getClass().getResource("/quinzical/view/MainView.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//////////////// test
		


		
		/////////////////////////////////
		
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
		launch(args);
		//System.out.print("ass");
		
		//new test.testSQLiteDB();
	}
}
