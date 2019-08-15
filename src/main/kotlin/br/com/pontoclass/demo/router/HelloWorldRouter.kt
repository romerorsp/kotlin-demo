package br.com.pontoclass.demo.router

import br.com.pontoclass.demo.handler.HelloWorldHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class HelloWorldRouter(val handler: HelloWorldHandler) {

    @Bean
    fun route(): RouterFunction<ServerResponse> =
            router {
                accept(MediaType.ALL).nest {
                    GET("/hello/{callerName}", handler::getCarStatus)
                }
            }
}