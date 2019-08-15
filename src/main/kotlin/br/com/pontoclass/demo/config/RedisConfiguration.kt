package br.com.pontoclass.demo.config

import br.com.pontoclass.demo.model.Hello
import com.github.caryyu.spring.embedded.redisserver.RedisServerConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class RedisConfiguration {

    @Bean
    fun redisServerConfiguration(): RedisServerConfiguration {
        return RedisServerConfiguration()
    }

    @Bean
    fun redisOperations(factory: ReactiveRedisConnectionFactory): ReactiveRedisOperations<String, Hello> =
            Jackson2JsonRedisSerializer(Hello::class.java).let { serializer ->
                RedisSerializationContext
                        .newSerializationContext<String, Hello>(StringRedisSerializer())
                        .let { ReactiveRedisTemplate(factory, it.value(serializer).build()) }
            }
}