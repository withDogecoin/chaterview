package team.backend.quiz.domain.service

import org.springframework.stereotype.Service
import team.backend.client.OpenAiClient
import team.backend.quiz.domain.command.QuizCommand
import team.backend.member.domain.repository.MemberQuizAnswerStore
import team.backend.member.domain.repository.MemberReader
import team.backend.prompt.domain.repository.PromptReader
import team.backend.quiz.domain.repository.QuizReader
import team.backend.dto.ChatDto
import team.backend.exception.InvalidAnswerException
import team.backend.member.domain.entity.MemberQuizAnswer
import team.backend.model.ChatModel
import team.backend.model.ChatRole
import team.backend.prompt.domain.entity.PositionType
import team.backend.prompt.domain.entity.PromptType
import team.backend.quiz.domain.entity.Quiz

@Service
class QuizServiceImpl(
    private val quizReader: QuizReader,
    private val promptReader: PromptReader,
    private val memberReader: MemberReader,

    private val memberQuizAnswerStore: MemberQuizAnswerStore,

    private val openAiClient: OpenAiClient
): QuizService {

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
        val interviewPrefixPrompt = promptReader.get(PromptType.INTERVIEW_ANSWER, PositionType.PREFIX)
        val interviewSuffixPrompt = promptReader.get(PromptType.INTERVIEW_ANSWER, PositionType.SUFFIX)
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