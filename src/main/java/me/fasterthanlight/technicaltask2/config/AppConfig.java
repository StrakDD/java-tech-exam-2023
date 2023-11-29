package me.fasterthanlight.technicaltask2.config;

import me.fasterthanlight.technicaltask2.service.UserCrud;
import me.fasterthanlight.technicaltask2.service.UserCrudStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class AppConfig {

    @Bean
    LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    @RequestScope
    protected UserCrud userCrud(UserCrudStrategy userCrudStrategy) {
        return userCrudStrategy.getUserCrud();
    }
}
