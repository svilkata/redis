package com.demo.redis1.controller;

import com.demo.redis1.config.RedisMessagePublisher;
import com.demo.redis1.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis1")
public class PublisherController {
    private static Logger logger = LoggerFactory.getLogger(PublisherController.class);
    private RedisMessagePublisher redisMessagePublisher;

    @Autowired
    public PublisherController(RedisMessagePublisher redisMessagePublisher) {
        this.redisMessagePublisher = redisMessagePublisher;
    }

    @PostMapping("publish")
    public void publish(@RequestBody MessageDTO messageDTO) {
        logger.info(">> publishing: {}", messageDTO);
        redisMessagePublisher.publish(messageDTO.toString());
    }
}
