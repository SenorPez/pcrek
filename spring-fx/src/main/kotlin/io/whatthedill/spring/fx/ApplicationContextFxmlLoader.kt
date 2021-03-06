package io.whatthedill.spring.fx

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.core.io.Resource

class ApplicationContextFxmlLoader : SpringFxmlLoader, ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext?) {
        this.applicationContext = applicationContext
    }

    private var applicationContext: ApplicationContext? = null

    override fun load(resource: Resource): Parent {
        LOGGER.debug("Loading fxml: [{}]", resource)
        resource.inputStream.use { fxmlStream ->
            val loader = FXMLLoader()
            loader.setControllerFactory { clazz ->
                LOGGER.debug("Getting bean of type [{}]", clazz)
                val bean = applicationContext!!.getBean(clazz)
                LOGGER.trace("Found bean [{}] as the type [{}]", bean, clazz)
                bean
            }
            return loader.load<Parent>(fxmlStream)
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ApplicationContextFxmlLoader::class.java)
    }
}
