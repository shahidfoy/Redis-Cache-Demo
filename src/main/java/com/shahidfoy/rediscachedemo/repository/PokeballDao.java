package com.shahidfoy.rediscachedemo.repository;

import com.shahidfoy.rediscachedemo.entity.Pokeball;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PokeballDao {

    public static final String HASH_KEY_POKEBALL = "Pokeball";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Pokeball save(Pokeball pokeball) {
        redisTemplate.opsForHash().put(HASH_KEY_POKEBALL, pokeball.getId(), pokeball);
        return pokeball;
    }

    public List<Pokeball> findAll() {
        System.out.println("called findAll() from db");
        return redisTemplate.opsForHash().values(HASH_KEY_POKEBALL)
                .stream()
                .filter(Pokeball.class::isInstance)
                .map(Pokeball.class::cast)
                .collect(Collectors.toList());
    }

    public Pokeball findPokeballById(int id) {
        System.out.println("called findPokeballById() from db");
        return (Pokeball) redisTemplate.opsForHash().get(HASH_KEY_POKEBALL, id);
    }

    public String deletePokeball(int id) {
        redisTemplate.opsForHash().delete(HASH_KEY_POKEBALL, id);
        return "pokeball removed!!";
    }
}
