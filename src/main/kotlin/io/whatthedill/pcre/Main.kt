package io.whatthedill.pcre

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.DialogPane
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

internal class Main : Application() {
    private var applicationContext: ConfigurableApplicationContext? = null

    override fun start(primaryStage: Stage) {
        Platform.setImplicitExit(true)
        try {
            val loading = showLoading()
            loadApplication(loading, primaryStage)

        } catch (e: RuntimeException) {
            LOGGER.error("Unable to start application, exiting...", e)
            System.exit(1)
        }
    }

    private fun loadApplication(loading: Stage, primaryStage: Stage) {
        val thread = Thread({
            applicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
            Platform.runLater {
                show(applicationContext!!, primaryStage)
                loading.close()
            }
        }, "ApplicationInitialization")
        thread.isDaemon = true
        thread.start()
    }

    private fun showLoading(): Stage {
        val modal = Stage()
        val loader = FXMLLoader()
        val loading = loader.load<Parent>(javaClass.getResourceAsStream("AppLoading.fxml")) as DialogPane
        modal.scene = Scene(loading)
        modal.initModality(Modality.APPLICATION_MODAL)
        modal.initStyle(StageStyle.UNDECORATED)
        modal.show()
        return modal
    }

    private fun show(appContext: ApplicationContext, primaryStage: Stage) {
        val rootLayout = appContext.getBean("rootLayout", BorderPane::class.java)
        val overview = appContext.getBean("telemetrySessionOverview", AnchorPane::class.java)
        rootLayout.center = overview

        primaryStage.title = "Project CARS Replay Enhancer"
        primaryStage.scene = Scene(rootLayout)
        primaryStage.show()
    }

    override fun stop() {
        super.stop()
        LOGGER.debug("Closing application context...")
        applicationContext?.stop()
        LOGGER.trace("Application context closed")
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Main::class.java)

        @JvmStatic fun main(args: Array<String>) {
            Application.launch(Main::class.java, *args)
        }
    }
}
