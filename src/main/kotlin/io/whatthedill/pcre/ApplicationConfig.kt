package io.whatthedill.pcre

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.whatthedill.pcre.telemetry.TelemetryConfig
import io.whatthedill.spring.fx.ApplicationContextFxmlLoader
import io.whatthedill.spring.fx.SpringFxmlLoader
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*
import org.springframework.core.io.Resource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.io.File
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = arrayOf("io.whatthedill.pcre"))
@ComponentScan(basePackages = arrayOf("io.whatthedill.pcre"))
@Import(MainUiConfig::class, TelemetryConfig::class)
open class ApplicationConfig {
    @Bean
    @Scope("prototype")
    open fun springFxmlLoader(): SpringFxmlLoader {
        return ApplicationContextFxmlLoader()
    }

    @Bean
    open fun homeDirectory(): File {
        return File("${System.getProperty("user.home")}/.pcre/").apply {
            if (!exists()) {
                if (!mkdirs()) {
                    throw IllegalStateException("Unable to find/create application home directory at [${toURI()}]")
                } else {
                    LOGGER.info("Created application home directory at [${toURI()}]")
                }
            }
        }
    }

    @Bean(destroyMethod = "close")
    open fun datasource(
            @Value("classpath:/io/whatthedill/pcre/telemetry/telemetry-session.sql") telemetry: Resource
    ): HikariDataSource {
        val hikariDataSource = HikariDataSource(
                HikariConfig().apply {
                    jdbcUrl = "jdbc:h2:file:~/.pcre/db/pcrek"
                    minimumIdle = 3
                    maximumPoolSize = 3
                }
        )

        ResourceDatabasePopulator().run {
            this.setContinueOnError(true)
            addScript(telemetry)
            DatabasePopulatorUtils.execute(this, hikariDataSource)
        }

        return hikariDataSource
    }

    @Bean
    open fun transactionManager(@Autowired entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager().apply {
            this.entityManagerFactory = entityManagerFactory
        }
    }

    @Bean
    open fun jdbcOperations(@Autowired dataSource: DataSource): JdbcOperations {
        return JdbcTemplate(dataSource)
    }

    @Bean
    open fun namedParameterJdbcOperations(@Autowired jdbcOperations: JdbcOperations): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(jdbcOperations)
    }

    @Bean
    open fun entityManagerFactory(@Autowired dataSource: DataSource): EntityManagerFactory {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setDatabase(Database.H2)
        vendorAdapter.setGenerateDdl(true)

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan("io.whatthedill.pcre")
        factory.dataSource = dataSource
        factory.afterPropertiesSet()

        return factory.`object`
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ApplicationConfig::class.java)
    }
}
