package io.whatthedill.pcre;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import io.whatthedill.pcre.telemetry.TelemetryUiConfig;
import io.whatthedill.spring.fx.ApplicationContextFxmlLoader;
import io.whatthedill.spring.fx.SpringFxmlLoader;

@Configuration
@Import({
	MainUiConfig.class,
	TelemetryUiConfig.class
})
public class ApplicationConfig {
	@Bean
	@Scope("prototype")
	public SpringFxmlLoader getSpringFxmlLoader() {
		return new ApplicationContextFxmlLoader();
	}
}
