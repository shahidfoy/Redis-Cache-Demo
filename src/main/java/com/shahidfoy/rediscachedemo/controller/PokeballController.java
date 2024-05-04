package com.shahidfoy.rediscachedemo.controller;

import com.shahidfoy.rediscachedemo.entity.Pokeball;
import com.shahidfoy.rediscachedemo.repository.PokeballDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shahidfoy.rediscachedemo.repository.PokeballDao.HASH_KEY_POKEBALL;

@Slf4j
@RestController
@RequestMapping("pokeball")
public class PokeballController {

    private final PokeballDao pokeballDao;

    @Autowired
    public PokeballController(PokeballDao pokeballDao) {
        this.pokeballDao = pokeballDao;
    }

    @PostMapping
    public Pokeball save(@RequestBody Pokeball pokeball) {
        return pokeballDao.save(pokeball);
    }

    @PutMapping("/{id}")
    @CachePut(key="#id", value=HASH_KEY_POKEBALL, condition ="#id!=null")
    public Pokeball update(@PathVariable int id, @RequestBody Pokeball pokeball) {
        Pokeball updatePokeball = pokeballDao.findPokeballById(id);
        if (id == pokeball.getId()) {
            BeanUtils.copyProperties(pokeball, updatePokeball);
            pokeballDao.save(updatePokeball);
        }
        return updatePokeball;
    }

    @GetMapping
    public List<Pokeball> getAllPokeballs() {
        return pokeballDao.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key="#id", value = HASH_KEY_POKEBALL, unless = "#result.power > 1000")
    public Pokeball findPokeball(@PathVariable int id) {
        return pokeballDao.findPokeballById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key="#id", value = HASH_KEY_POKEBALL)
    public String remove(@PathVariable int id) {
        return pokeballDao.deletePokeball(id);
    }
}
