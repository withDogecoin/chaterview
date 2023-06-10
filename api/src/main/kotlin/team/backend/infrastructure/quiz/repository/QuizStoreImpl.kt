package team.backend.infrastructure.quiz.repository

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import jakarta.annotation.PostConstruct
import org.hibernate.reactive.mutiny.impl.MutinySessionFactoryImpl
import org.springframework.stereotype.Repository
import team.backend.domain.job.entity.Job
import team.backend.domain.job.entity.JobType
import team.backend.domain.subject.entity.Subject
import team.backend.domain.subject.entity.SubjectType
import team.backend.domain.quiz.entity.Quiz
import team.backend.domain.quiz.entity.QuizLevel
import team.backend.domain.quiz.repository.QuizStore

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
            level = QuizLevel.EASY,
            job = savedJob.get(),
            subject = savedSubject.get(),
        )
        mutinySessionFactory.withSession { session -> session.persist(quiz).flatMap { session.flush() } }
            .subscribeAsCompletionStage()
    }
}