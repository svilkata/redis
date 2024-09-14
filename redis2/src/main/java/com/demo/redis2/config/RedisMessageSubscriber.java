package com.demo.redis2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisMessageSubscriber implements MessageListener {
    private static List<String> messageList = new ArrayList<>();
    private static Logger logger = LoggerFactory.getLogger(RedisMessageSubscriber.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageList.add(message.toString());
        logger.info("Message received: {}", message);
    }

    //TODO - a controller method could be implemented getting all current messages received so far
    public List<String> getMessages() {
        return RedisMessageSubscriber.messageList;
    }
}
