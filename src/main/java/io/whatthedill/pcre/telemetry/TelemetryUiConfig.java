package io.whatthedill.pcre.telemetry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;

import io.whatthedill.spring.fx.SpringFxmlLoader;
import javafx.scene.layout.AnchorPane;

@Configuration
@Lazy
public class TelemetryUiConfig {

	@Bean
	public OverviewController overviewController() {
		return new OverviewController();
	}

	@Bean
	public AnchorPane telemetrySessionOverview(
		@Autowired SpringFxmlLoader springFxmlLoader,
		@Value("classpath:/io/whatthedill/pcre/telemetry/Overview.fxml") Resource resource
	) {
		return (AnchorPane) springFxmlLoader.load(resource);
	}
}
