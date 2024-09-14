package com.demo.redis2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

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
//        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));

        return redisTemplate;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new RedisMessageSubscriber(), "onMessage");
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("mytopicname");
    }

    //Injecting the LettuceConnectionFactory bean, MessageListenerAdapter bean and ChannelTopic bean
    @Bean
    public RedisMessageListenerContainer container(LettuceConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter, ChannelTopic channelTopic) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, channelTopic);

        return redisMessageListenerContainer;
    }
}
