package quinzical.controller.component;

import java.io.IOException;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import quinzical.controller.ScreenController;
import quinzical.model.QuizModel;
import quinzical.model.ScoreHandler;
import quinzical.util.TextToSpeech;

/**
 * Drawer Controller for the Quinzical application
 * 
 * @author Neville
 *
 */
public class DrawerController implements Initializable {

	@FXML private Label userName;
	@FXML private Label winningLabel;
	@FXML private JFXSlider speechSpeedSlider;
	@FXML private JFXSlider volumeSlider;
	@FXML private JFXToggleButton toggleButton;
	@FXML private JFXToggleButton highContrastToggle;
	@FXML private Label rank;
	
	private static QuizModel model = QuizModel.getModel();

	/**
	 * Method to initialize the drawer
	 * 
	 * @param controllerClass
	 * @param drawer
	 * @param hamburger
	 */
	public static void initDrawer(Class<?> controllerClass, JFXDrawer drawer, JFXHamburger hamburger) {
		try {
			// Loading Drawer
			VBox toolbar = FXMLLoader.load(controllerClass.getResource("/quinzical/view/component/ToolBar.fxml"));
			drawer.setSidePane(toolbar);

			/*
			 * The following code block is used to offset the drawer when the drawer is
			 * closed this is done to prevent blockage of elements under the drawer.
			 */
			drawer.setOnDrawerOpening(event -> {
				AnchorPane.setRightAnchor(drawer, 0.0);
				// AnchorPane.setLeftAnchor(drawer, 0.0);
				AnchorPane.setTopAnchor(drawer, 0.0);
				AnchorPane.setBottomAnchor(drawer, 0.0);
			});

			drawer.setOnDrawerClosed(event -> {
				// AnchorPane.clearConstraints(drawer);
				AnchorPane.setRightAnchor(drawer, -900.0);
				AnchorPane.setTopAnchor(drawer, 0.0);
				AnchorPane.setBottomAnchor(drawer, 0.0);
			});

			// Drawer Animation
			HamburgerBasicCloseTransition task = new HamburgerBasicCloseTransition(hamburger);
			drawer.setDirection(DrawerDirection.RIGHT);
			drawer.setOverLayVisible(false);
			drawer.setDefaultDrawerSize(300);
			drawer.setPrefWidth(0);
			;

			task.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							task.setRate(task.getRate() * -1);
							task.play();
							if (drawer.isOpened()) {
								drawer.close();
							} else {
								drawer.open();
							}

						}

					}).start();
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Go to the main menu menu
	 * @param event
	 */
	@FXML
	private void goMainMenu(ActionEvent event) {
		ScreenController.goMainMenu(getClass(), event);
	}
	
	/**
	 * Change to a different user
	 * @param event
	 */
	@FXML
	private void changeUser(ActionEvent event) {
		ScreenController.goUserSelect2(getClass(), event);
	}

	/**
	 * reset the game if confirmed , then Go to the main menu
	 * @param event
	 */
	@FXML
	private void goReset(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Are you sure you want to reset the game? Your save will be reset to its initial status. This can not be undone.",
				ButtonType.YES, ButtonType.NO);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.setTitle("Reset Confirmation");
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			model.reset();
		} else {
			event.consume();
		}
		goMainMenu(event);
	}

	/**
	 * Initialize the class with model and data. Set event listener to slider bar
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		winningLabel.setText(model.getWinningStr());
		userName.setText(model.getUser().getName());
		toggleButton.setSelected(model.getConfig().isEnableSpeech());
		rank.setText(ScoreHandler.geTitle(model.getWinning()));

		
		TextToSpeech tts = model.getTextToSpeechObject();
		speechSpeedSlider.setValue(tts.getSpeed());
		volumeSlider.setValue(tts.getVolume());
		
		// set event handler for toggle button enable speech
		toggleButton.setOnAction(e -> {
			if (toggleButton.isSelected()) {
				model.getConfig().setEnableSpeech(true);
			} else {
				model.getConfig().setEnableSpeech(false);
				
			}
			e.consume();
		});
		
		
		highContrastToggle.setOnAction(e -> {
			javafx.scene.Parent p = ((Node) e.getSource()).getScene().getRoot();
			if (highContrastToggle.isSelected()) {
				p.getStylesheets().remove(0);
				p.getStylesheets().add(0, getClass().getResource("/quinzical/view/css/HighContrast.css").toExternalForm());
				model.getConfig().setEnableHighContrast(true);
			} else {
				p.getStylesheets().remove(0);
				p.getStylesheets().add(0, getClass().getResource("/quinzical/view/css/Styles.css").toExternalForm());
				model.getConfig().setEnableHighContrast(false);
			}
			e.consume();
		});
		
		
		// Set event handler for scroll bar text speed
		speechSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (old_val != new_val) {
					// Change scale of map based on slider position.
					model.setSpeechSpeed(new_val.intValue());

				} else {
					speechSpeedSlider.setValue(1);
				}
			}
		});
		
		// Set event handler for scroll bar text volume
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (old_val != new_val) {
					// Change scale of map based on slider position.
					model.setSpeechVolume(new_val.intValue());

				} else {
					volumeSlider.setValue(1);
				}
			}
		});

	}
}
