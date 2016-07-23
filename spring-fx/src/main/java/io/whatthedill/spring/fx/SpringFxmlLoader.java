package io.whatthedill.spring.fx;

import org.springframework.core.io.Resource;

import javafx.scene.Parent;

public interface SpringFxmlLoader {
	Parent load(Resource resource);
}
