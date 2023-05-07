package team.backend.config

import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.zaxxer.hikari.HikariDataSource
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.provider.ReactivePersistenceProvider
import org.hibernate.reactive.provider.Settings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import java.util.Properties
import jakarta.persistence.spi.PersistenceUnitInfo
import org.hibernate.reactive.session.ReactiveSession
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ReactiveSession::class)
class ReactiveQueryConfig {

    @Bean
    @ConditionalOnMissingBean(SubqueryCreator::class)
    fun subqueryCreator(): SubqueryCreator {
        return SubqueryCreatorImpl()
    }

    @Bean
    fun entityManagerFactory(@Qualifier("primaryDataSource") dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = dataSource
        emf.setPersistenceProviderClass(ReactivePersistenceProvider::class.java)
        emf.setPackagesToScan("team.backend.domain.entity")
        emf.jpaVendorAdapter = jpaVendorAdapters()
        emf.setJpaProperties(jpaProperties())

        return emf
    }

    @Bean
    fun mutinySessionFactory(@Qualifier("primaryDataSource") dataSource: DataSource, localSessionFactoryBean: LocalContainerEntityManagerFactoryBean): Mutiny.SessionFactory {
        val reactivePersistenceInfo = ReactivePersistenceInfo(
            localSessionFactoryBean.persistenceUnitInfo!!,
            localSessionFactoryBean.jpaPropertyMap,
            dataSource
        )

        return ReactivePersistenceProvider()
            .createContainerEntityManagerFactory(reactivePersistenceInfo, reactivePersistenceInfo.properties)
            .unwrap(Mutiny.SessionFactory::class.java)
    }

    class ReactivePersistenceInfo(
        persistenceUnitInfo: PersistenceUnitInfo,
        jpaPropertyMap: Map<String, Any>,
        dataSource: DataSource
    ):
        PersistenceUnitInfo by persistenceUnitInfo {

        private val internalProps = Properties(persistenceUnitInfo.properties).apply {
            putAll(jpaPropertyMap)
            setProperty(Settings.SQL_CLIENT_POOL, MySQLConnectionPool::class.qualifiedName)
            setProperty(Settings.SQL_CLIENT_POOL_CONFIG, VertxMySQLDBConnectionPoolConfiguration::class.qualifiedName)
            setProperty(Settings.URL, (dataSource.unwrap(HikariDataSource::class.java) as HikariDataSource).jdbcUrl)
            setProperty(Settings.USER, (dataSource.unwrap(HikariDataSource::class.java) as HikariDataSource).username)
            setProperty(Settings.PASS, (dataSource.unwrap(HikariDataSource::class.java) as HikariDataSource).password)
            setProperty(Settings.HBM2DDL_AUTO, "create")
        }

        override fun getProperties(): Properties = internalProps

        override fun getPersistenceProviderClassName(): String = ReactivePersistenceProvider::class.qualifiedName!!
    }

    @Bean
    fun queryFactory(
        sessionFactory: Mutiny.SessionFactory,
        subqueryCreator: SubqueryCreator
    ): SpringDataHibernateMutinyReactiveQueryFactory {
        return SpringDataHibernateMutinyReactiveQueryFactory(
            sessionFactory = sessionFactory,
            subqueryCreator = subqueryCreator
        )
    }

    private fun jpaVendorAdapters(): JpaVendorAdapter = HibernateJpaVendorAdapter().apply {
        setDatabasePlatform("org.hibernate.dialect.MySQLDialect")
    }

    private fun jpaProperties(): Properties = Properties().apply {
            setProperty("hibernate.vertx.pool.connect_timeout", "30000")
            setProperty("hibernate.vertx.pool.class", "team.backend.config.MySQLConnectionPool")
            setProperty("hibernate.vertx.pool.configuration_class", "team.backend.config.VertxMySQLDBConnectionPoolConfiguration")
            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
            setProperty("hibernate.show_sql", "true")
            setProperty("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/chaterview")
            setProperty("jakarta.persistence.jdbc.user", "doge")
            setProperty("jakarta.persistence.jdbc.password", "1234")
            setProperty("hibernate.use_sql_comments", "true")
            setProperty("hibernate.show_sql", "true")
            setProperty("hibernate.use_sql_comments", "true")
            setProperty("hibernate.query.in_clause_parameter_padding", "true")
            setProperty("hibernate.query.default_batch_fetch_size", "1000")
    }
}