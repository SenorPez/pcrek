package io.whatthedill.pcre.telemetry

import io.whatthedill.pcre.telemetry.capture.Session
import io.whatthedill.pcre.telemetry.capture.jpa.SessionRepository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ListView
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Controller
@Lazy
open class OverviewController {
    @FXML
    private lateinit var captureButton: Button

    @FXML
    private lateinit var sessionList: ListView<Session>

    @Autowired
    private lateinit var sessionRepository: SessionRepository

    private lateinit var sessionData: ObservableList<Session>

    private val random: Random = Random(Date().time)

    @FXML
    private fun initialize() {
        sessionData = FXCollections.observableArrayList()

        sessionData.addAll(sessionRepository.findAll())

        sessionList.items = sessionData
    }

    @FXML
    @Transactional(propagation = Propagation.REQUIRED)
    private fun handleCaptureClicked() {

        val session = sessionRepository.save(
                Session().apply {
                    id = UUID.randomUUID()
                    name = "Telemetry Session - ${random.nextInt(10000)}"
                    created = Date()
                }
        )

        LOGGER.debug("Inserted 1 record; {}", session)

        sessionData.add(session)
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(OverviewController::class.java)
    }
}
