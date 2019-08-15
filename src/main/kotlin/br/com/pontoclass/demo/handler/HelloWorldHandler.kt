package br.com.pontoclass.demo.handler

import br.com.pontoclass.demo.model.Hello
import org.springframework.data.domain.Example
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.core.publisher.switchIfEmpty


@Component
class HelloWorldHandler(val redis: ReactiveRedisOperations<String, Hello>) {

    fun sayHello(request: ServerRequest): Mono<ServerResponse> =
            redis.keys(request.pathVariable("callerName"))
                    .flatMap { redis.opsForValue().get(it) }
                    .reduce(ArrayList<Hello>()) { list, hello ->
                        list.apply {
                            add(hello)
                        }
                    }.flatMap {
                         ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(it))
                    }.switchIfEmpty { ServerResponse.notFound().build() }
}