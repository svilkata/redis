package com.demo.redis1.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;


//This util bean actually not needed
@Component
@Scope(org.springframework.web.context.WebApplicationContext.SCOPE_APPLICATION)
public class UtilParsing {
    private final ObjectMapper objectMapper;

    public UtilParsing(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public byte[] serializeIntoRedisRecord(Object javaObject) {
        byte[] serializedObject;
        try {
            serializedObject = objectMapper.writeValueAsBytes(javaObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return serializedObject;
    }

    public <Т> Т deserializeFromRedisRecord(byte[] redisObject, Class<Т> classType) {
        Т deserializedObject;
        try {
            deserializedObject = objectMapper.readValue(redisObject, classType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return deserializedObject;
    }

}
