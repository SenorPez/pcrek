package io.whatthedill.pcre.telemetry.capture.jpa

import io.whatthedill.pcre.telemetry.capture.Session
import org.hibernate.annotations.SQLInsert
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.Repository
import org.springframework.data.repository.RepositoryDefinition
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@RepositoryDefinition(domainClass = Session::class, idClass = UUID::class)
@Transactional
interface SessionRepository : CrudRepository<Session, UUID> {
    fun <S : Session> findAll(pageRequest: Pageable): Page<S>
}