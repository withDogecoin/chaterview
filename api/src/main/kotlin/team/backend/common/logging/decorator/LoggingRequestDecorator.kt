package team.backend.common.logging.decorator

import org.slf4j.Logger
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.util.StringUtils
import reactor.core.publisher.Flux
import team.backend.support.asString
import java.io.ByteArrayOutputStream
import java.nio.channels.Channels
import java.util.*

class LoggingRequestDecorator internal constructor(log: Logger, delegate: ServerHttpRequest): ServerHttpRequestDecorator(delegate) {

    private val body: Flux<DataBuffer>?

    override fun getBody(): Flux<DataBuffer> {
        return body!!
    }

    init {
        if (log.isInfoEnabled) {
            val path = delegate.uri.path
            val query = delegate.uri.query
            val method = Optional.ofNullable(delegate.method)
                .map { it.name() }
                .orElse("NONE")
            val headers = delegate.headers.asString()
            log.info(
                "{} {}\n {}", method, path + (if (StringUtils.hasText(query)) "?$query" else ""), headers
            )
            body = super.getBody().doOnNext { buffer: DataBuffer ->
                val bodyStream = ByteArrayOutputStream()
                Channels.newChannel(bodyStream).write(buffer.asByteBuffer().asReadOnlyBuffer())
                log.info("{}: {}", ">>> Request", String(bodyStream.toByteArray()))
            }
        } else {
            body = super.getBody()
        }
    }
}