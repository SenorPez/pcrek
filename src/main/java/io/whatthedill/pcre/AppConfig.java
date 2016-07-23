package io.whatthedill.pcre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;

import io.whatthedill.spring.fx.ApplicationContextFxmlLoader;
import io.whatthedill.spring.fx.SpringFxmlLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

@Configuration
@ComponentScan({"io.whatthedill.pcre"})
public class AppConfig {

	@Bean
	@Scope("prototype")
	public SpringFxmlLoader getSpringFxmlLoader() {
		return new ApplicationContextFxmlLoader();
	}

	@Bean
	public BorderPane rootLayout(
		@Autowired SpringFxmlLoader springFxmlLoader,
		@Value("classpath:/io/whatthedill/pcre/Main.fxml") Resource resource
	) {
		return (BorderPane) springFxmlLoader.load(resource);
	}

	@Bean
	public AnchorPane telemetrySessionOverview(
		@Autowired SpringFxmlLoader springFxmlLoader,
		@Value("classpath:/io/whatthedill/pcre/telemetry/Overview.fxml") Resource resource
	) {
		return (AnchorPane) springFxmlLoader.load(resource);
	}
}
