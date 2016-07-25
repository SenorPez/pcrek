package io.whatthedill.spring.fx

import org.springframework.core.io.Resource

import javafx.scene.Parent

interface SpringFxmlLoader {
    fun load(resource: Resource): Parent
}
