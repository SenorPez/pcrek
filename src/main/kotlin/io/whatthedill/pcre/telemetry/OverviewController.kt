package io.whatthedill.pcre.telemetry

import io.whatthedill.pcre.telemetry.capture.Session
import io.whatthedill.pcre.telemetry.capture.jpa.SessionRepository
import javafx.fxml.FXML
import javafx.scene.control.Button
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Controller
import java.util.*

@Controller
@Lazy
open class OverviewController {
    @FXML
    private lateinit var captureButton: Button

    @Autowired
    private lateinit var sessionRepository: SessionRepository

    private val random: Random = Random(Date().time)

    @FXML
    private fun handleCaptureClicked() {

        val session = sessionRepository.save(
                Session().apply {
                    id = UUID.randomUUID()
                    name = "Telemetry Session - ${random.nextInt(10000)}"
                    created = Date()
                }
        )

        LOGGER.debug("Inserted 1 record; {}", session)
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(OverviewController::class.java)
    }
}
