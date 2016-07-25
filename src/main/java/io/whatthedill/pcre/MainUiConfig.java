package io.whatthedill.pcre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;

import io.whatthedill.spring.fx.SpringFxmlLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@Configuration
@Lazy
public class MainUiConfig {

	@Bean
	public MainController mainController() {
		return new MainController();
	}

	@Bean
	public MainMenuController mainMenuController() {
		return new MainMenuController();
	}

	@Bean Parent mainMenu(
		@Autowired SpringFxmlLoader springFxmlLoader,
		@Value("classpath:/io/whatthedill/pcre/MainMenu.fxml") Resource resource
	){
		MenuBar menuBar = (MenuBar) springFxmlLoader.load(resource);
		menuBar.setUseSystemMenuBar(System.getProperty("os.name", "UNKNOWN").equals("Mac OS X"));
		return menuBar;
	}

	@Bean
	public BorderPane mainLayout(
		@Autowired SpringFxmlLoader springFxmlLoader,
		@Value("classpath:/io/whatthedill/pcre/Main.fxml") Resource resource,
		@Autowired @Qualifier("telemetrySessionOverview") Node telemetryLayout,
		@Autowired @Qualifier("mainMenu") Node mainMenu
	) {
		BorderPane mainLayout = (BorderPane) springFxmlLoader.load(resource);
		mainLayout.setTop(mainMenu);
		BorderPane.setAlignment(mainMenu, Pos.CENTER);
		mainLayout.setCenter(telemetryLayout);
		return mainLayout;
	}

	@Bean
	public Stage mainStage(
		@Autowired Parent mainLayout,
		@Value("Project CARS Replay Enhancer") String title
	) {
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(new Scene(mainLayout));
		return stage;
	}
}
