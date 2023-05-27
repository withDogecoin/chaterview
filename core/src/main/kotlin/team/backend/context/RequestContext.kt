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
                val context = ctx.get<String>(key)
                Mono.just(context)
            }.awaitSingle()
    }

    /**
     * Get header by key
     * @param key header key
     * @param exchange ServerWebExchange object
     * @return header
     */
    fun getHeader(key: String, exchange: ServerWebExchange) =
        try {
            exchange.request.headers.getFirst(key) ?: ""
        } catch (e: Exception) {
            ""
        }
}