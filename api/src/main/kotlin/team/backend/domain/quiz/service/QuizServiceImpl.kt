package team.backend.domain.quiz.service

import org.springframework.stereotype.Service
import team.backend.client.OpenAiClient
import team.backend.domain.quiz.command.QuizCommand
import team.backend.domain.member.repository.MemberQuizAnswerStore
import team.backend.domain.member.repository.MemberReader
import team.backend.domain.prompt.repository.PromptReader
import team.backend.domain.quiz.repository.QuizReader
import team.backend.dto.ChatDto
import team.backend.exception.InvalidAnswerException
import team.backend.domain.member.entity.MemberQuizAnswer
import team.backend.model.ChatModel
import team.backend.model.ChatRole
import team.backend.domain.prompt.entity.PositionType
import team.backend.domain.prompt.entity.PromptType
import team.backend.domain.quiz.entity.Quiz
import team.backend.domain.quiz.policy.RANDOM_COUNT
import team.backend.domain.quiz.query.QuizQuery
import kotlin.random.Random

@Service
class QuizServiceImpl(
    private val quizReader: QuizReader,
    private val promptReader: PromptReader,
    private val memberReader: MemberReader,
    private val memberQuizAnswerStore: MemberQuizAnswerStore,
    private val openAiClient: OpenAiClient
): QuizService {
    override suspend fun random(authorization: String): QuizQuery.RandomResponse {
        val member = memberReader.get(authorization.toLong())
        val quizzes = quizReader.getRandomly(member.job, member.tier, getRandomIds())
            .asSequence()
            .map { QuizQuery.Base(it.question, it.level.name, it.getJobName(), it.getSubjectName()) }
            .toList()

        return QuizQuery.RandomResponse(quizzes)
    }

    // TODO Caching
    private suspend fun getRandomIds(): List<Long> {
        val quizIds = quizReader.getIds()
        val randomIds = mutableListOf<Long>()

        for (i in 1..RANDOM_COUNT) {
            val randomIndex = Random.nextInt(quizIds.size)
            val randomId = quizIds[randomIndex]
            randomIds.add(randomId)
        }

        return randomIds.toList()
    }

    override suspend fun answer(command: QuizCommand.AnswerRequest): QuizCommand.AnswerResponse {
        val quiz = quizReader.get(command.quizId)
        val aiAnswer = openAiClient.chat(
            ChatDto.Request.of(
                model = ChatModel.GPT35TURBO,
                role = ChatRole.USER,
                content = generateMessage(quiz, command.answer)
            )
        )
        var feedback = aiAnswer.choices[0].message.content
        val isCorrect = isCorrect(feedback)
        feedback = feedback.substringAfter(".").trim()

        memberQuizAnswerStore.save(
            MemberQuizAnswer(
            correct = isCorrect,
            answer = command.answer,
            feedback = feedback,
            quiz = quiz,
            member = memberReader.get(1L)
            )
        )

        return QuizCommand.AnswerResponse(
            isCorrect = isCorrect,
            answer = feedback
        )
    }

    private suspend fun generateMessage(quiz: Quiz, answer: String): String {
        val prompts = promptReader.find(PromptType.INTERVIEW_ANSWER)
        val interviewPrefixPrompt = prompts.first { it.positionType == PositionType.PREFIX }
        val interviewSuffixPrompt = prompts.first { it.positionType == PositionType.SUFFIX }
        return quiz.question + interviewPrefixPrompt.command + answer + interviewSuffixPrompt.command
    }

    private suspend fun isCorrect(answer: String): Boolean {
        return when (answer.split(".")[0].trim()) {
            "Correct" -> true
            "Incorrect" -> false
            else -> throw InvalidAnswerException()
        }
    }
}