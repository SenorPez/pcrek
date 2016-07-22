package io.whatthedill.pcre;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public SpringFxmlLoader getSpringFxmlLoader() {
		return new SpringFxmlLoader();
	}

	@Bean
	public Controller getController() {
		return new Controller();
	}
}
