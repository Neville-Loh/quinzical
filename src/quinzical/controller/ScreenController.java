package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Utility Controller for switch different scene.
 * @author Neville
 */
public class ScreenController {

	/**
	 * Main Menu Utility Method to go to the main menu
	 * @param controllerClass
	 * @param event of button
	 */
	public static void goMainMenu(Class<?> controllerClass, ActionEvent event) {
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/MainView.fxml"));
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(new Scene(parent));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Question Select Utility Method to go to the Question Select View
	 * @param controllerClass
	 * @param event of button
	 */
	public static void goQuestionSelect(Class<?> controllerClass, ActionEvent event) {
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/QuestionSelectView.fxml"));
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(new Scene(parent));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Question Page Utility Method to go to the Question View
	 * @param controllerClass
	 * @param event of button
	 */
	public static void goQuestion(Class<?> controllerClass, ActionEvent event) {
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/QuestionView.fxml"));
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(new Scene(parent));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Current Winning Utility Method to go to the Current Winning View
	 * @param controllerClass
	 * @param event of button
	 */
	public static void goCurrentWinning(Class<?> controllerClass, ActionEvent event) {
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/CurrentWinningView.fxml"));
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(new Scene(parent));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Game Over Utility Method to go to the GameOver View
	 * @param controllerClass
	 * @param event of button
	 */
	public static void goGameOver(Class<?> controllerClass, ActionEvent event){
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/GameOverView.fxml"));
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(new Scene(parent));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Category Select  Utility Method to go to the GameOver View
	 * @param controllerClass
	 * @param event of button
	 */
	public static void goCategorySelect(Class<?> controllerClass, ActionEvent event){
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/CategorySelectView.fxml"));
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(new Scene(parent));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param controllerClass
	 * @param event of button
	 */
	public static BorderPane getLoadingScreen(Class<?> controllerClass){
		try {
			BorderPane pane = FXMLLoader.load(controllerClass.getResource("/quinzical/view/component/Spinner.fxml"));
			return pane;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param controllerClass
	 * @param event of button
	 */
	public static void goLoadingScreen(Class<?> controllerClass, ActionEvent event){
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/component/Spinner.fxml"));
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(new Scene(parent));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
