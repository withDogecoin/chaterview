package team.backend.domain.quiz.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import team.backend.domain.quiz.command.QuizCommand
import team.backend.presentation.quiz.dto.QuizDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
interface QuizMapper {

    fun from(
        request: QuizDto.AnswerRequest,
        authorization: String,
    ): QuizCommand.AnswerRequest

    @Mapping(source = "correct", target = "isCorrect")
    fun from(
        request: QuizCommand.AnswerResponse
    ): QuizDto.AnswerResponse
}