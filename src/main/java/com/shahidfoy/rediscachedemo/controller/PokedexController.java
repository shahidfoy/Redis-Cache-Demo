package com.shahidfoy.rediscachedemo.controller;

import com.shahidfoy.rediscachedemo.entity.Pokedex;
import com.shahidfoy.rediscachedemo.repository.PokedexRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.shahidfoy.rediscachedemo.repository.PokedexRepository.HASH_KEY_POKEDEX;

@Slf4j
@RestController
@RequestMapping("pokedex")
public class PokedexController {

    private final PokedexRepository pokedexRepository;

    @Autowired
    public PokedexController(PokedexRepository pokedexRepository) {
        this.pokedexRepository = pokedexRepository;
    }

    @PostMapping
    @CacheEvict(value="pokeList", allEntries = true)
    public Pokedex save(@RequestBody Pokedex pokedex) {
        return pokedexRepository.save(pokedex);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "pokeList", allEntries = true)
    @CachePut(key="#id", value = HASH_KEY_POKEDEX, condition = "#id!=null")
    public Pokedex update(@PathVariable int id, @RequestBody Pokedex pokedex) {
        Pokedex updatedPokedex = pokedexRepository.findById(id).orElse(null);
        if (updatedPokedex != null && id == pokedex.getId()) {
            BeanUtils.copyProperties(pokedex, updatedPokedex);
            pokedexRepository.save(updatedPokedex);
        }
        return updatedPokedex;
    }

    @GetMapping
    @Cacheable(value="pokeList")
    public List<Pokedex> getAllPokemon() {
        log.info("retrieving getAllPokemon() from db");
        return pokedexRepository.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key="#id", value=HASH_KEY_POKEDEX)
    public Pokedex findPokemon(@PathVariable int id) {
        Optional<Pokedex> optionalPokedex = pokedexRepository.findById(id);
        return optionalPokedex.orElse(null);
    }

    @DeleteMapping("/{id}")
    @Caching(evict = {
            @CacheEvict(key="#id", value=HASH_KEY_POKEDEX),
            @CacheEvict(value="pokeList", allEntries = true)
    })
    public void remove(@PathVariable int id) {
        pokedexRepository.deleteById(id);
        log.info("pokemon removed by id");
    }
}
