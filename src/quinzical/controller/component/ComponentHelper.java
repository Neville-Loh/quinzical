package quinzical.controller.component;

import java.io.IOException;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComponentHelper {
	
	public static void initDrawer(Class<?> controllerClass, JFXDrawer drawer, JFXHamburger hamburger) {
		try {
			VBox toolbar = FXMLLoader.load(controllerClass.getResource("/quinzical/view/component/ToolBar.fxml"));
			drawer.setSidePane(toolbar);
			
			HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
			
			task.setRate(-1);
			hamburger.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					System.out.println("ass");
				}
				
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
