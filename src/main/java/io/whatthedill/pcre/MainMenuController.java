package io.whatthedill.pcre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public final class MainMenuController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuController.class);

	@FXML
	private MenuItem exitMenuItem;

	@FXML
	private MenuItem aboutMenuItem;

	@FXML
	private void handleClickExit() {
		LOGGER.trace("Exiting the application...");
		exitMenuItem.setDisable(true);
		Platform.exit();
	}
}
