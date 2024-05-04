package com.shahidfoy.rediscachedemo.repository;

import com.shahidfoy.rediscachedemo.entity.Pokedex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PokedexRepository extends JpaRepository<Pokedex, Integer> {

    String HASH_KEY_POKEDEX = "Pokedex";

    @Transactional(readOnly = true)
    List<Pokedex> findByType1(String type);

    @Transactional(readOnly = true)
    List<Pokedex> findByType2(String type);
}
