package quinzical.controller.component;


import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import quinzical.model.QuizModel;

/**
 * Drawer Controller for the Quinzical applications
 * @author Neville
 *
 */
public class DrawerController implements Initializable {
	
	
	@FXML private Label userName;
	@FXML private Label winningLabel;
	@FXML private Button settingButton;
	@FXML private JFXSlider speechSpeedSlider;
	@FXML private JFXSlider volumeSlider;
	private static QuizModel model = QuizModel.getModel();
	
	/**
	 * Method to initialize the drawer
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
			 * The following code block is used to offset the drawer when the drawer is closed
			 * this is done to prevent blockage of element under the drawer.
			 */
			drawer.setOnDrawerOpening(event ->
			{
			    AnchorPane.setRightAnchor(drawer, 0.0);
			    //AnchorPane.setLeftAnchor(drawer, 0.0);
			    AnchorPane.setTopAnchor(drawer, 0.0);
			    AnchorPane.setBottomAnchor(drawer, 0.0);
			});

			drawer.setOnDrawerClosed(event ->
			{
			    //AnchorPane.clearConstraints(drawer);
			    AnchorPane.setRightAnchor(drawer, -500.0);
			    AnchorPane.setTopAnchor(drawer, 0.0);
			    AnchorPane.setBottomAnchor(drawer, 0.0);
			});
			
			
			
			// Drawer Animation
			HamburgerBasicCloseTransition task = new  HamburgerBasicCloseTransition(hamburger);
			drawer.setDirection(DrawerDirection.RIGHT);
			drawer.setOverLayVisible(false);
			drawer.setDefaultDrawerSize(200);
			drawer.setPrefWidth(0);;
			
			task.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							task.setRate(task.getRate() * -1);
							task.play();
							if(drawer.isOpened()) {
								drawer.close();
							} else {
								drawer.open();
							}
							
						}
						
					}).start();
//					if(drawer.isOpened()) {
//						drawer.close();
//					} else {
//						drawer.open();
//					}
//					task.setRate(task.getRate() * -1fdsf);
//					task.play();
				}
				
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Go to the setting menu
	 * @param event
	 */
	@FXML
	private void goSetting(ActionEvent event) {
		System.out.println("go settisnggsadffff");
	}

	
	/**
	 * Initialize the class with model and data.
	 * Set event listener to slilder bar
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		winningLabel.setText(model.getWinningStr());
		userName.setText(model.getUser().getName());
		
		// detect dragging on zoom scroll bar
				speechSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
						if (old_val != new_val) {
							// Change scale of map based on slider position.
							System.out.println(new_val.intValue());
							// double d = SettlementMapPanel.DEFAULT_SCALE;
							
						}else {
								speechSpeedSlider.setValue(1);
						}
					}
				});
		
	}
}
