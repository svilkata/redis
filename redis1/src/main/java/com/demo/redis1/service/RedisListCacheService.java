package com.demo.redis1.service;

import jakarta.annotation.PostConstruct;
import org.example.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedisListCacheService {
    private ListOperations<String, Object> listOperations;

    @Autowired
    public RedisListCacheService(final RedisTemplate<String, Object> redisTemplate) {
        listOperations = redisTemplate.opsForList();
    }

    public void cachePersons(final String key, final List<PersonDTO> persons) {
        for (final PersonDTO person : persons) {
            listOperations.leftPush(key, person);
        }
    }

    public List<PersonDTO> getPersonsInRange(final String key, int from, int to) {
        final List<Object> objects = listOperations.range(key, from, to);
        if (CollectionUtils.isEmpty(objects)) {
            return Collections.emptyList();
        }

        return objects.stream()
                .map(x -> (PersonDTO) x)
                .collect(Collectors.toList());
    }

    public PersonDTO getLastElement(final String key) {
        final Object o = listOperations.rightPop(key);
        if (o == null) {
            return null;
        }

        return (PersonDTO) o;
    }

    public void trim(final String key, int from, int to) {
        listOperations.trim(key, from, to);
    }

    public void deleteCachedList(final String key) {
        listOperations.getOperations().delete(key); //could be also redisTemplate.delete(key) regardless of kind of pair
    }

    @PostConstruct
    public void setup() {
        listOperations.leftPush("xx", "Redis list cache started there");

        System.out.println(listOperations.rightPop("xx"));
    }
}
