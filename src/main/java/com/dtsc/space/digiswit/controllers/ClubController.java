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

import java.util.List;

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
	@PostMapping(value="/{clubId}/player", headers = { HttpHeaders.AUTHORIZATION },
			produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<Player> registerNewPlayer(
			HttpServletRequest request, @PathVariable("clubId") int clubid, @RequestBody Player player)
	{
		return clubService.registerNewPlayer(request, clubid, player);
	}

	// Club list
	@GetMapping(value="/", headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<List<Club>> getPublicClubs(HttpServletRequest request)
	{
		return clubService.getPublicClubs(request);
	}

	// Club list (overloaded with filter)
	@GetMapping(value="/filtered-by/{filterpattern}", headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<List<Club>> getFilteredPublicClubs(HttpServletRequest request, @PathVariable("filterpattern")
	String filterpattern)
	{
		return clubService.getPublicClubs(request, filterpattern);
	}

	// Club detail
	@GetMapping(value="/{clubId}", headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<Club> getClubDetail(HttpServletRequest request, @PathVariable("clubId") int clubId)
	{
		return clubService.getClubDetail(request, clubId);
	}

	// Player list
	@GetMapping(value="/{clubId}/player", headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<List<Player>> getPlayers(HttpServletRequest request, @PathVariable("clubId") int clubId)
	{
		return clubService.getPlayers(request, clubId);
	}

	// Player list (overloaded with filter)
	@GetMapping(value="/{clubId}/player/filtered-by/{filterpattern}",
			headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<List<Player>> getPlayers(HttpServletRequest request, @PathVariable("clubId") int clubId,
												   @PathVariable("filterpattern") String filterpattern)
	{
		return clubService.getPlayers(request, clubId, filterpattern);
	}

	// Player detail
	@GetMapping(value="/{clubId}/player/{playerId}", headers = { HttpHeaders.AUTHORIZATION },
			produces = {"application/json"})
	public ResponseEntity<Player> getPlayerDetail(HttpServletRequest request, @PathVariable("clubId") int clubId,
												  @PathVariable("playerId") int playerId)
	{
		return clubService.getPlayerDetail(request, clubId, playerId);
	}

	// Update club info
	@PutMapping(value="/",headers = { HttpHeaders.AUTHORIZATION },
			produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<Club> updateClub(HttpServletRequest request, @RequestBody NewClub updatedclub)
	{
		return clubService.updateClub(request, updatedclub);
	}

	// Update player info
	@PutMapping(value="/{clubId}/player/{playerId}",headers = { HttpHeaders.AUTHORIZATION },
			produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<Player> updatePlayer(HttpServletRequest request, @PathVariable("clubId") int clubId,
											   @PathVariable("playerId") int playerId, @RequestBody Player updatedplayer)
	{
		return clubService.updatePlayer(request, clubId, playerId, updatedplayer);
	}

	// Delete player info
	@DeleteMapping(value="/{clubId}/player/{playerId}", headers = { HttpHeaders.AUTHORIZATION },
			produces = {"application/json"})
	public ResponseEntity<Void> deletePlayer(HttpServletRequest request, @PathVariable("clubId") int clubId,
											 @PathVariable("playerId") int playerId)
	{
		return clubService.deletePlayer(request, clubId, playerId);
	}
}
