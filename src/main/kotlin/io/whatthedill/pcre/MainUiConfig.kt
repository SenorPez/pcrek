package io.whatthedill.pcre

import io.whatthedill.spring.fx.SpringFxmlLoader
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.MenuBar
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.core.io.Resource

@Configuration
@Lazy
open class MainUiConfig {

    @Bean
    open fun mainController(): MainController {
        return MainController()
    }

    @Bean
    open fun mainMenuController(): MainMenuController {
        return MainMenuController()
    }

    @Bean
    open fun mainMenu(
            @Autowired springFxmlLoader: SpringFxmlLoader,
            @Value("classpath:/io/whatthedill/pcre/MainMenu.fxml") resource: Resource): Parent {
        val menuBar = springFxmlLoader.load(resource) as MenuBar
        menuBar.isUseSystemMenuBar = useMacOsMenuBar()
        return menuBar
    }

    private fun useMacOsMenuBar() = System.getProperty("os.name", "UNKNOWN") == "Mac OS X" &&
            System.getProperty("apple.laf.useScreenMenuBar", "false") == "true"

    @Bean
    open fun mainLayout(
            @Autowired springFxmlLoader: SpringFxmlLoader,
            @Value("classpath:/io/whatthedill/pcre/Main.fxml") resource: Resource,
            @Autowired @Qualifier("telemetrySessionOverview") telemetryLayout: Node,
            @Autowired @Qualifier("mainMenu") mainMenu: Node): BorderPane {
        val mainLayout = springFxmlLoader.load(resource) as BorderPane
        mainLayout.top = mainMenu
        BorderPane.setAlignment(mainMenu, Pos.CENTER)
        mainLayout.center = telemetryLayout
        return mainLayout
    }

    @Bean
    open fun mainStage(
            @Autowired mainLayout: Parent,
            @Value("Project CARS Replay Enhancer") title: String): Stage {
        val stage = Stage()
        stage.title = title
        stage.scene = Scene(mainLayout)
        return stage
    }
}
