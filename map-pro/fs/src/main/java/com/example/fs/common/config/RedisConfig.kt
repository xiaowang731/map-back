package com.example.fs.common.config

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Configuration
open class RedisConfig {
    @Bean
    open fun redisTemplate(connectionFactory: RedisConnectionFactory?): RedisTemplate<String, Any> {
        val objectMapper = ObjectMapper()

        // 使用 object mapper 设置 bean 的属性,修饰符
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        objectMapper.activateDefaultTyping(objectMapper.polymorphicTypeValidator,
            ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        // 设置时间模块(格式化，不设置，则输出默认格式)
        val timeModule = JavaTimeModule()
        timeModule.addSerializer(LocalDateTime::class.java,
            LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("Asia/Shanghai"))))
        timeModule.addDeserializer(LocalDateTime::class.java,
            LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("Asia/Shanghai"))))

        objectMapper.registerModule(timeModule)

        // val serializer = Jackson2JsonRedisSerializer(objectMapper, Any::class.java)
        val serializer = FastJsonRedisSerializer(Any::class.java)
        val tmpl = RedisTemplate<String, Any>()
        tmpl.connectionFactory = connectionFactory
        tmpl.keySerializer = StringRedisSerializer()
        tmpl.valueSerializer = serializer
        tmpl.hashKeySerializer = StringRedisSerializer()
        tmpl.hashValueSerializer = serializer
        tmpl.afterPropertiesSet()
        return tmpl
    }
}

