package team.backend.infrastructure.repository.quiz

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import jakarta.annotation.PostConstruct
import org.hibernate.reactive.mutiny.impl.MutinySessionFactoryImpl
import org.springframework.stereotype.Repository
import team.backend.common.entity.Job
import team.backend.common.entity.JobType
import team.backend.common.entity.Subject
import team.backend.common.entity.SubjectType
import team.backend.domain.entity.quiz.Quiz
import team.backend.domain.entity.quiz.QuizLevel
import team.backend.domain.repository.quiz.QuizStore

@Repository
class QuizStoreImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
    private val mutinySessionFactory: MutinySessionFactoryImpl
): QuizStore {

    @PostConstruct
    fun init() {
        val job = Job(type = JobType.BACKEND_ENGINEER)
        val subject = Subject(type = SubjectType.JAVA)

        val savedJob =
            mutinySessionFactory.withSession { session ->
                session.persist(job)
                    .flatMap { session.flush() } }
                .map { job }
                .subscribeAsCompletionStage()

        val savedSubject =
            mutinySessionFactory.withSession { session ->
                session.persist(subject)
                    .flatMap { session.flush() } }
                .map { subject }
                .subscribeAsCompletionStage()

        val quiz = Quiz(
            question = "Is Java a language that runs on top of the JVM?",
            active = true,
            level = QuizLevel.BEGINNER,
            job = savedJob.get(),
            subject = savedSubject.get(),
        )
        mutinySessionFactory.withSession { session -> session.persist(quiz).flatMap { session.flush() } }
            .subscribeAsCompletionStage()
    }
}