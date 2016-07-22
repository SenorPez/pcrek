package io.whatthedill.pcre

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

internal class Main : Application() {
    private var applicationContext: ConfigurableApplicationContext? = null

    override fun start(primaryStage: Stage) {
        Platform.setImplicitExit(true)
        try {
            show(applicationContext!!, primaryStage)
        } catch (e: RuntimeException) {
            LOGGER.error("Unable to start application, exiting...", e)
            System.exit(1)
        }
    }

    private fun show(appContext: ApplicationContext, primaryStage: Stage) {
        val fxmlLoader = appContext.getBean(SpringFxmlLoader::class.java)
        val root = fxmlLoader.load("/pcre.fxml", appContext)
        primaryStage.title = "Project CARS Replay Enhancer"
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun init() {
        super.init()
        applicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
    }

    override fun stop() {
        super.stop()
        applicationContext!!.stop()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Main::class.java)

        @JvmStatic fun main(args: Array<String>) {
            Application.launch(Main::class.java, *args)
        }
    }
}
