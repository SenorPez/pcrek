package io.whatthedill.pcre

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

internal class Main : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("/pcre.fxml"))
        primaryStage.title = "Project CARS Replay Enhancer"
        primaryStage.scene = Scene(root, 300.0, 275.0)
        primaryStage.show()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Application.launch(Main::class.java, *args)
        }
    }
}
