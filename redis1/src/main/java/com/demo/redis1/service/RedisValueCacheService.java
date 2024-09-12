package com.demo.redis1.service;

import com.demo.redis1.dto.PersonDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisValueCacheService {
    private final ValueOperations<String, Object> valueOps;

    public RedisValueCacheService(final RedisTemplate<String, Object> redisTemplate) {
        valueOps = redisTemplate.opsForValue();
    }

    public void cache(final String key, final Object data) {
        valueOps.set(key, data); //inserts or replace with a new data value

//        valueOps.set(key, data, 4000, TimeUnit.MILLISECONDS); //expiration timeout.
    }

    public Object getCachedValue(final String key) {
        return valueOps.get(key);
    }

    public void deleteCachedValue(final String key) {
        valueOps.getOperations().delete(key);
    }

    @PostConstruct
    public void setup() {
        PersonDTO personDTO = new PersonDTO().setId("123").setName("Svilen").setAge(17);
        cache(personDTO.getId(), personDTO);
    }
}
