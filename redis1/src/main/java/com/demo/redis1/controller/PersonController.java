package com.demo.redis1.controller;

import com.demo.redis1.service.RedisListCacheService;
import com.demo.redis1.service.RedisValueCacheService;
import org.example.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/redis1")
public class PersonController {
    private final RedisValueCacheService valueCache;
    private final RedisListCacheService listCache;

    @Autowired
    public PersonController(final RedisValueCacheService valueCache, final RedisListCacheService listCache) {
        this.valueCache = valueCache;
        this.listCache = listCache;
    }

    @PostMapping
    public void cachePerson(@RequestBody final PersonDTO dto) {
        valueCache.cache(dto.getId(), dto);
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable final String id) {
        return (PersonDTO) valueCache.getCachedValue(id);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable final String id) {
        valueCache.deleteCachedValue(id);
    }

    @PostMapping("/list/{key}")
    public void cachePersons(@PathVariable final String key, @RequestBody final List<PersonDTO> persons) {
        listCache.cachePersons(key, persons);
    }

    @GetMapping("/list/{key}")
    public List<PersonDTO> getPersonsInRange(@PathVariable final String key,
                                             @RequestParam(defaultValue = "1") int from,  //0 is the Redis index of the first element
                                             @RequestParam(defaultValue = "0") int to) {  //-1 is the Redis last element including
        return listCache.getPersonsInRange(key,
                from - 1, //0 is the Redis index of the first element
                to - 1); //-1 is the Redis last element including
    }

    @GetMapping("/list/last/{key}")
    public PersonDTO getLastElementByRemovingIt(@PathVariable final String key) {
        return listCache.getLastElement(key);
    }

    @DeleteMapping("/list/{key}")
    public void trim(@PathVariable final String key,
                     @RequestParam(defaultValue = "1") int from,  //0 is the Redis index of the first element
                     @RequestParam(defaultValue = "0") int to) {  //-1 is the Redis last element including
        listCache.trim(key,
                from - 1, //0 is the Redis index of the first element
                to - 1); //-1 is the Redis last element including
    }

    @DeleteMapping("/list/deep/{key}")
    public void trim(@PathVariable final String key) {
        listCache.deleteCachedList(key);
    }
}
