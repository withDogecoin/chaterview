package team.backend.context

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.util.context.ContextView

object RequestContext {

    /**
     * Get Value in Context
     * @param key key of Context
     * @return value in Context
     */
    suspend fun getValue(key: String): String {
        return Mono
            .deferContextual { ctx: ContextView ->
                val authorization = ctx.get<String>(key)
                Mono.just(authorization)
            }.awaitSingle()
    }

    /**
     * Get header by key
     * @param key header key
     * @param exchange ServerWebExchange object
     * @return header
     */
    fun getHeader(key: String, exchange: ServerWebExchange): String {
        val request = exchange.request
        return if (key == "Authorization") {
            try {
                request.headers.getFirst(key) ?: ""
            } catch (e: Exception) {
                ""
            }
        } else {
            request.headers.getFirst(key) ?: ""
        }
    }
}