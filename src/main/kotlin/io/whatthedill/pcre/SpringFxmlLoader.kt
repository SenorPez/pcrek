package io.whatthedill.pcre

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import java.io.IOException

internal class SpringFxmlLoader {
    fun load(url: String, applicationContext: ApplicationContext): Parent {
        LOGGER.info("Loading fxml: [{}]", url)
        try {
            SpringFxmlLoader::class.java.getResourceAsStream(url).use { fxmlStream ->
                val loader = FXMLLoader()
                loader.setControllerFactory { clazz ->
                    LOGGER.debug("Getting bean [{}]", clazz)
                    val bean = applicationContext.getBean(clazz)
                    LOGGER.debug("Found [{}]->[{}]", clazz, bean)
                    bean
                }
                return loader.load<Parent>(fxmlStream)
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(SpringFxmlLoader::class.java)
    }
}
