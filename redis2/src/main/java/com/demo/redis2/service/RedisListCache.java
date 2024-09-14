package com.demo.redis2.service;

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
public class RedisListCache {
    private ListOperations<String, Object> listOps;

    @Autowired
    public RedisListCache(final RedisTemplate<String, Object> redisTemplate) {
        listOps = redisTemplate.opsForList();
    }

    public void cachePersons(final String key, final List<PersonDTO> persons) {
        for (final PersonDTO person : persons) {
            listOps.leftPush(key, person);
        }
    }

    public List<PersonDTO> getPersonsInRange(final String key, int from, int to) {
        final List<Object> objects = listOps.range(key, from, to);
        if (CollectionUtils.isEmpty(objects)) {
            return Collections.emptyList();
        }

        return objects.stream()
                .map(x -> (PersonDTO) x)
                .collect(Collectors.toList());
    }

    public PersonDTO getLastElement(final String key) {
        final Object o = listOps.rightPop(key);
        if (o == null) {
            return null;
        }

        return (PersonDTO) o;
    }

    public void trim(final String key, int from, int to) {
        listOps.trim(key, from, to);
    }

    public void deleteCachedList(String key) {
        listOps.getOperations().delete(key); //could be also redisTemplate.delete(key) regardless of kind of pair
    }
}
