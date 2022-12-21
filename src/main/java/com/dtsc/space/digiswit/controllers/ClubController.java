package com.dtsc.space.digiswit.controllers;

import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.Login;
import com.dtsc.space.digiswit.entities.NewClub;
import com.dtsc.space.digiswit.entities.Player;
import com.dtsc.space.digiswit.services.ClubService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * DTordera, 20221220. Club main controller
 */

@RestController
@RequestMapping("/club")
public class ClubController {

	@Autowired
	ClubService clubService;

	// Registering new club/user
	@PostMapping(value="", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<Club> registerNewClub(HttpServletRequest request, @RequestBody NewClub newclub)
	{
		return clubService.registerNewClub(request, newclub);
	}

	// New player on club
	@PostMapping(value="/{clubId}/player", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<Player> registerNewPlayer(
			HttpServletRequest request, @PathVariable("clubId") int clubid, @RequestBody Player player)
	{
		return clubService.registerNewPlayer(request, clubid, player);
	}
}
