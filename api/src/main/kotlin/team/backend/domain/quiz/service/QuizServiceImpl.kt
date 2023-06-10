package team.backend.domain.quiz.service

import org.springframework.stereotype.Service
import team.backend.client.OpenAiClient
import team.backend.domain.member.entity.Member
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
import team.backend.exception.NotFoundMemberException

@Service
class QuizServiceImpl(
    private val quizReader: QuizReader,
    private val promptReader: PromptReader,
    private val memberReader: MemberReader,
    private val memberQuizAnswerStore: MemberQuizAnswerStore,
    private val openAiClient: OpenAiClient
): QuizService {
    override suspend fun random(authorization: String): QuizQuery.RandomResponse {
        val member = memberReader.find(authorization.toLong()) ?: throw NotFoundMemberException()

        val quizzes = quizReader.getQuizByIds(getRandomlyQuizIds(member))
            .asSequence()
            .map { QuizQuery.Base(it.question, it.level.name, it.getJobName(), it.getSubjectName()) }
            .toList()

        return QuizQuery.RandomResponse(quizzes)
    }

    private suspend fun getRandomlyQuizIds(member: Member): List<Long> {
        val quizIds = getQuizIdsThatCanBeSolvedByMember(member)

        if (quizIds.size <= RANDOM_COUNT) {
            return quizIds
        }

        return quizIds.shuffled().take(RANDOM_COUNT)
    }

    private suspend fun getQuizIdsThatCanBeSolvedByMember(member: Member): List<Long> {
        return quizReader.getQuizIds(member.job, member.tier)
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
            member = memberReader.find(1L) ?: throw NotFoundMemberException()
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