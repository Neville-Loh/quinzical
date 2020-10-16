package quinzical.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility Controller for switch different scene.
 * 
 * @author Neville
 */
public class ScreenController {

	/**
	 * Main Menu Utility Method to go to the main menu
	 * @param controllerClass
	 * @param event          
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
	 * @param event    
	 */
	public static void goQuestionSelect(Class<?> controllerClass, ActionEvent event) {
		try {
			// Set loading screen
			Scene Loading = getLoadingScene(controllerClass);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(Loading);			
			// Load the next screen concurrently using worker thread
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws IOException {
					Parent parent = FXMLLoader
							.load(controllerClass.getResource("/quinzical/view/QuestionSelectView.fxml"));
					
					Platform.runLater(new Runnable() {
		                @Override public void run() {
		                	window.setScene(new Scene(parent));
		                }
		            });
					return null;
				}
			};
			new Thread(task).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Category Select Utility Method to go to the GameOver View
	 * @param controllerClass
	 * @param event  
	 */
	public static void goCategorySelect(Class<?> controllerClass, ActionEvent event) {
		try {
			// Set loading screen
			Scene Loading = getLoadingScene(controllerClass);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(Loading);			
			// Load the next screen concurrently using worker thread
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws IOException {
					Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/CategorySelectView.fxml"));
					Platform.runLater(new Runnable() {
		                @Override public void run() {
		                	window.setScene(new Scene(parent));
		                }
		            });
					return null;
				}
			};
			new Thread(task).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Go to the user select screen
	 * @param controllerClass
	 * @param event
	 */
	public static void goUserSelect(Class<?> controllerClass, ActionEvent event) {
		try {
			// Set loading screen
			Scene Loading = getLoadingScene(controllerClass);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(Loading);			
			// Load the next screen concurrently using worker thread
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws IOException {
					Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/UserSelectView.fxml"));
					Platform.runLater(new Runnable() {
		                @Override public void run() {
		                	window.setScene(new Scene(parent));
		                }
		            });
					return null;
				}
			};
			new Thread(task).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void goUserSelect2(Class<?> controllerClass, ActionEvent event) {
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/UserSelectView.fxml"));
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
	 * Game Over Utility Method to go to the GameOver View
	 * @param controllerClass
	 * @param event     
	 */
	public static void goGameOver(Class<?> controllerClass, ActionEvent event) {
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/GameOverView.fxml"));
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(new Scene(parent));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * =======================================================================================================
	 * Utility Method
	 * 
	 * =======================================================================================================
	 */
	/**
	 * Get Loading screen as a Scene
	 * @param controllerClass
	 * @param event
	 */
	public static Scene getLoadingScene(Class<?> controllerClass) {
		try {
			Parent parent = FXMLLoader.load(controllerClass.getResource("/quinzical/view/component/Spinner.fxml"));
			return new Scene(parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
