package team.backend.common.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebExchangeDecorator
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import team.backend.common.logging.decorator.LoggingRequestDecorator
import team.backend.common.logging.decorator.LoggingResponseDecorator

@Component
class LoggingFilter: WebFilter {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        chain.filter(LoggingWebExchange(log, exchange))
}

class LoggingWebExchange(log: Logger, delegate: ServerWebExchange) : ServerWebExchangeDecorator(delegate) {
    // Encapsulate the logic of logging by Decorators
    private val requestDecorator: LoggingRequestDecorator = LoggingRequestDecorator(log, delegate.request)
    private val responseDecorator: LoggingResponseDecorator = LoggingResponseDecorator(log, delegate.response)

    override fun getRequest(): ServerHttpRequest {
        return requestDecorator
    }

    override fun getResponse(): ServerHttpResponse {
        return responseDecorator
    }
}