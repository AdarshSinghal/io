package com.adarsh.io.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adarsh.io.model.AddPlayerRequest;
import com.adarsh.io.model.PlayerResponse;
import com.adarsh.io.model.UpdatePlayerRequest;
import com.adarsh.io.model.dto.Player;
import com.adarsh.io.service.PlayerService;
import com.adarsh.io.utils.ErrorTemplate;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private ErrorTemplate error;

	@GetMapping
	public ResponseEntity<PlayerResponse> getPlayers(@RequestParam(required = false) String username,
			@RequestParam(required = false) Long pid) throws Exception {

		if (pid != null) {
			return ResponseEntity.ok(playerService.findById(pid));
		} else if (username != null) {
			return ResponseEntity.ok(playerService.getByUsername(username));
		}
		throw error.illegalArgs("Expecting only 1 filter");
	}

	@PostMapping
	public ResponseEntity<Player> addplayer(@Valid @RequestBody AddPlayerRequest player) throws Exception {
		return ResponseEntity.ok(playerService.addplayer(player));
	}

	@PutMapping
	public ResponseEntity<Player> updatePlayer(@Valid @RequestBody UpdatePlayerRequest player) throws Exception {
		return ResponseEntity.ok(playerService.updatePlayer(player));
	}

}
