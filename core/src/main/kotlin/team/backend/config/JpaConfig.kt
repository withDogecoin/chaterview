//package team.backend.config
//
//import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
//import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
//import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
//import jakarta.persistence.EntityManagerFactory
//import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
//import org.hibernate.reactive.provider.impl.ReactiveEntityManagerFactoryBuilder
//import org.hibernate.reactive.session.ReactiveSession
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
//import org.springframework.boot.autoconfigure.domain.EntityScan
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.orm.jpa.JpaTransactionManager
//import org.springframework.orm.jpa.JpaVendorAdapter
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
//import org.springframework.transaction.PlatformTransactionManager
//import java.util.*
//import javax.sql.DataSource
//
//
//@EntityScan(value = ["team.backend"])
//@Configuration(proxyBeanMethods = false)
//@ConditionalOnClass(ReactiveSession::class)
//class JpaConfig {
//
//    @Bean
//    @ConditionalOnMissingBean(SubqueryCreator::class)
//    fun subqueryCreator(): SubqueryCreator {
//        return SubqueryCreatorImpl()
//    }
//
//    @Bean
//    fun entityManagerFactory(@Qualifier("primaryDataSource") dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
//        val emf = LocalContainerEntityManagerFactoryBean()
//        emf.dataSource = dataSource
//        emf.setPackagesToScan("team.backend.domain.entity")
//        emf.jpaVendorAdapter = jpaVendorAdapters()
//        emf.setJpaProperties(jpaProperties())
//
//        // entityManagerFactory 생성시 ReactiveEntityManagerFactoryBuilder 사용
//        ReactiveEntityManagerFactoryBuilder(emf)
//            .unwrap(SessionFactory::class.java)
//            .build()
//
//        return emf
//    }
//
//    @Bean
//    fun mutinySessionFactory(entityManagerFactory: EntityManagerFactory) =
//        entityManagerFactory.unwrap(SessionFactory::class.java)!!
//
//    @Bean
//    fun queryFactory(mutinySessionFactory: SessionFactory, subqueryCreator: SubqueryCreator): SpringDataHibernateMutinyReactiveQueryFactory {
//        return SpringDataHibernateMutinyReactiveQueryFactory(
//            sessionFactory = mutinySessionFactory,
//            subqueryCreator = subqueryCreator
//        )
//    }
//
//    @Bean
//    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
//        val transactionManager = JpaTransactionManager()
//        transactionManager.entityManagerFactory = entityManagerFactory
//        return transactionManager
//    }
//
//    private fun jpaVendorAdapters(): JpaVendorAdapter = HibernateJpaVendorAdapter().apply {
//        setDatabasePlatform("org.hibernate.dialect.MySQLDialect")
//    }
//
//    /**
//     * jpaProperties corresponding to yml
//     */
//    private fun jpaProperties(): Properties = Properties().apply {
//            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
//            setProperty("hibernate.show_sql", "true")
//            setProperty("hibernate.use_sql_comments", "true")
//            setProperty("hibernate.show_sql", "use_sql_comments")
//            setProperty("hibernate.use_sql_comments", "true")
//            setProperty("hibernate.query.in_clause_parameter_padding", "true")
//            setProperty("hibernate.query.default_batch_fetch_size", "1000")
//    }
//}