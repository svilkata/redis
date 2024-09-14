package com.demo.redis1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class RedisConfiguration {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setDatabase(0);
        config.setHostName("localhost");
        config.setPort(6379);
        config.setPassword("mypass");

        return new LettuceConnectionFactory(config);
    }

    //Injecting the LettuceConnectionFactory bean
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("mytopicname");
    }

    //Injecting the RedisTemplate<String, Object> bean and ChannelTopic bean
    @Bean
    MessagePublisher messagePublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic channelTopic) {
        return new RedisMessagePublisher(redisTemplate, channelTopic);
    }
}
