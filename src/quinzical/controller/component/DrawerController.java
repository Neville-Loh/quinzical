package quinzical.controller.component;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

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
import javafx.scene.layout.VBox;

public class DrawerController implements Initializable {
	
	@FXML private Label userName;
	@FXML private Button settingButton;
	@FXML private JFXSlider speechSpeedSlider;
	
	public static void initDrawer(Class<?> controllerClass, JFXDrawer drawer, JFXHamburger hamburger) {
		try {
			VBox toolbar = FXMLLoader.load(controllerClass.getResource("/quinzical/view/component/ToolBar.fxml"));
			drawer.setSidePane(toolbar);
			
			HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
			drawer.setDirection(DrawerDirection.RIGHT);
			drawer.setOverLayVisible(false);
			drawer.setDefaultDrawerSize(200);
			drawer.setPrefWidth(0);;
			
			task.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					System.out.println("drawingasdf cfallfefffdffff");
					task.setRate(task.getRate() * -1);
					task.play();
					if(drawer.isOpened()) {
						drawer.close();
					} else {
						drawer.open();
					}
				}
				
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@FXML
	private void goSetting(ActionEvent event) {
		System.out.println("go setting");
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
