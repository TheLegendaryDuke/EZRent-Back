package com.aoranzhang.ezrentback.spring.session

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@Configuration
@EnableRedisHttpSession
class SessionConfig {
    @Bean
    fun connectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory()
    }
}
