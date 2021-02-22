package com.adarsh.io.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.adarsh.io.model.dto.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {

	Optional<Player> findByUsername(String username);

}
