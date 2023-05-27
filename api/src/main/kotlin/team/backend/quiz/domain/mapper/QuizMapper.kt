package team.backend.quiz.domain.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import team.backend.quiz.domain.command.QuizCommand
import team.backend.quiz.presentation.dto.QuizDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
interface QuizMapper {

    fun from(
        request: QuizDto.AnswerRequest
    ): QuizCommand.AnswerRequest

    @Mapping(source = "correct", target = "isCorrect")
    fun from(
        request: QuizCommand.AnswerResponse
    ): QuizDto.AnswerResponse
}