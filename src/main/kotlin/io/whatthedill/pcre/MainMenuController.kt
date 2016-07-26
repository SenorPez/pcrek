package io.whatthedill.pcre

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.MenuItem
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Controller

@Controller
@Lazy
open class MainMenuController {

    @FXML
    private lateinit var exitMenuItem: MenuItem

    @FXML
    private lateinit var aboutMenuItem: MenuItem

    @FXML
    private fun handleClickExit() {
        LOGGER.trace("Exiting the application...")
        exitMenuItem.isDisable = true
        Platform.exit()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MainMenuController::class.java)
    }
}
