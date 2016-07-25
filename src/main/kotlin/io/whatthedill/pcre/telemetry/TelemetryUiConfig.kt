package io.whatthedill.pcre.telemetry

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.core.io.Resource

import io.whatthedill.pcre.telemetry.OverviewController
import io.whatthedill.spring.fx.SpringFxmlLoader
import javafx.scene.layout.AnchorPane

@Configuration
@Lazy
open class TelemetryUiConfig {

    @Bean
    open fun overviewController(): OverviewController {
        return OverviewController()
    }

    @Bean
    open fun telemetrySessionOverview(
            @Autowired springFxmlLoader: SpringFxmlLoader,
            @Value("classpath:/io/whatthedill/pcre/telemetry/Overview.fxml") resource: Resource): AnchorPane {
        return springFxmlLoader.load(resource) as AnchorPane
    }
}
