package team.backend.config

import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence
import org.hibernate.reactive.mutiny.Mutiny.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryFactoryConfig {
    @Bean
    fun entityManagerFactory(): EntityManagerFactory = Persistence.createEntityManagerFactory("chaterview")

    @Bean
    fun mutinySessionFactory(entityManagerFactory: EntityManagerFactory): SessionFactory =
        entityManagerFactory.unwrap(SessionFactory::class.java)

    @Bean
    fun queryFactory(sessionFactory: SessionFactory): SpringDataHibernateMutinyReactiveQueryFactory {
        return SpringDataHibernateMutinyReactiveQueryFactory(
            sessionFactory = sessionFactory,
            subqueryCreator = SubqueryCreatorImpl()
        )
    }
}