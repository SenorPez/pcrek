package io.whatthedill.pcre

import javafx.animation.FadeTransition
import javafx.application.Application
import javafx.application.Platform
import javafx.concurrent.Task
import javafx.concurrent.Worker
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration
import org.slf4j.LoggerFactory
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.core.io.ClassPathResource

internal class Main : Application() {
    private lateinit var applicationContext: ConfigurableApplicationContext

    override fun start(primaryStage: Stage) {
        Platform.setImplicitExit(true)

        val loading: Parent = showLoadingDialog(primaryStage)

        getApplicationContextTask().run {
            stateProperty().addListener { observableValue, oldState, newState ->
                when (newState!!) {
                    Worker.State.SUCCEEDED -> {
                        showMainWindow(loading, primaryStage)
                    }
                    Worker.State.FAILED -> {
                        LOGGER.error("error loading application context, exiting", exception)
                        Platform.exit()
                    }
                    Worker.State.READY -> {
                        LOGGER.trace("ready to load application context")
                    }
                    Worker.State.SCHEDULED -> {
                        LOGGER.trace("application context scheduled for loading")
                    }
                    Worker.State.RUNNING -> {
                        LOGGER.trace("application context is being loaded")
                    }
                    Worker.State.CANCELLED -> {
                        LOGGER.trace("application context loading was cancelled, exiting", exception)
                        Platform.exit()
                    }
                }
            }

            Thread(this).run {
                name = "ApplicationContextStartup"
                start()
            }
        }
    }

    private fun showMainWindow(loading: Parent, primaryStage: Stage) {
        LOGGER.trace("application context loaded")
        primaryStage.toFront()

        FadeTransition(Duration.seconds(1.2), loading).apply {
            this.fromValue = 1.toDouble()
            this.toValue = 0.toDouble()
            this.onFinished = EventHandler { primaryStage.close() }
            this.play()
        }

        val mainStage = applicationContext.getBean("mainStage", Stage::class.java)
        mainStage.show()
    }

    private fun getApplicationContextTask(): Task<ConfigurableApplicationContext> {
        return object : Task<ConfigurableApplicationContext>() {
            override fun call(): ConfigurableApplicationContext {
                return AnnotationConfigApplicationContext().apply {
                    applicationContext = this
                    register(ApplicationConfig::class.java)
                    refresh()
                }
            }
        }
    }

    private fun showLoadingDialog(primaryStage: Stage): Parent {
        return ClassPathResource("/io/whatthedill/pcre/Loading.fxml").inputStream.use { fxml ->
            val fxmlLoader = FXMLLoader()
            fxmlLoader.load<Parent>(fxml).apply {
                primaryStage.scene = Scene(this)
                primaryStage.initStyle(StageStyle.UNDECORATED)
                primaryStage.show()
            }
        }
    }

    override fun stop() {
        super.stop()
        applicationContext.apply {
            LOGGER.debug("Closing application context...")
            if (this.isActive) {
                this.stop()
            }
            LOGGER.trace("Application context closed")
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Main::class.java)

        @JvmStatic fun main(args: Array<String>) {
            Application.launch(Main::class.java, *args)
        }
    }
}
