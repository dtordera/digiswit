package com.dtsc.space.digiswit.controllers;

import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.ClubRegister;
import com.dtsc.space.digiswit.entities.Player;
import com.dtsc.space.digiswit.services.ListService;
import com.dtsc.space.digiswit.services.RegisterService;
import com.dtsc.space.digiswit.services.UpdateService;
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
	RegisterService registerService;

	@Autowired
	ListService listService;

	@Autowired
	UpdateService updateService;

	// Registering new club/user
	@PostMapping(value="", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<Club> registerNewClub(HttpServletRequest request, @RequestBody ClubRegister newclub)
	{
		return registerService.registerNewClub(request, newclub);
	}

	// New player on club
	@PostMapping(value="/{clubId}/player", headers = { HttpHeaders.AUTHORIZATION },
			produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<Player> registerNewPlayer(HttpServletRequest request, @PathVariable("clubId") int clubid,
													@RequestBody Player player)
	{
		return registerService.registerNewPlayer(request, clubid, player);
	}

	// Club list
	@GetMapping(value="", headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<List<Club>> getPublicClubs(HttpServletRequest request)
	{
		return listService.getPublicClubs(request);
	}

	// Club list (overloaded with filter)
	@GetMapping(value="/filtered-by/{pattern}", headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<List<Club>> getFilteredPublicClubs(HttpServletRequest request, @PathVariable("pattern")
															 String pattern)
	{
		return listService.getPublicClubs(request, pattern);
	}

	// Club detail
	@GetMapping(value="/{clubId}", headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<Club> getClubDetail(HttpServletRequest request, @PathVariable("clubId") int clubId)
	{
		return listService.getClubDetail(request, clubId);
	}

	// Player list
	@GetMapping(value="/{clubId}/player", headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<List<Player>> getPlayers(HttpServletRequest request, @PathVariable("clubId") int clubId)
	{
		return listService.getClubPlayers(request, clubId);
	}

	// Player list (overloaded with filter)
	@GetMapping(value="/{clubId}/player/filtered-by/{pattern}",
			headers = { HttpHeaders.AUTHORIZATION }, produces = {"application/json"})
	public ResponseEntity<List<Player>> getPlayers(HttpServletRequest request, @PathVariable("clubId") int clubId,
												   @PathVariable("pattern") String pattern)
	{
		return listService.getClubPlayers(request, clubId, pattern);
	}

	// Player detail
	@GetMapping(value="/{clubId}/player/{playerId}", headers = { HttpHeaders.AUTHORIZATION },
			produces = {"application/json"})
	public ResponseEntity<Player> getPlayerDetail(HttpServletRequest request, @PathVariable("clubId") int clubId,
												  @PathVariable("playerId") int playerId)
	{
		return listService.getPlayerDetail(request, clubId, playerId);
	}

	// Update club info
	@PutMapping(value="",headers = { HttpHeaders.AUTHORIZATION },
			produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<Club> updateClub(HttpServletRequest request, @RequestBody ClubRegister updatedclub)
	{
		return updateService.updateClub(request, updatedclub);
	}

	// Update player info
	@PutMapping(value="/{clubId}/player/{playerId}",headers = { HttpHeaders.AUTHORIZATION },
			produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<Player> updatePlayer(HttpServletRequest request, @PathVariable("clubId") int clubId,
											   @PathVariable("playerId") int playerId, @RequestBody Player updatedplayer)
	{
		return updateService.updatePlayer(request, clubId, playerId, updatedplayer);
	}

	// Delete player info
	@DeleteMapping(value="/{clubId}/player/{playerId}", headers = { HttpHeaders.AUTHORIZATION },
			produces = {"application/json"})
	public ResponseEntity<Void> deletePlayer(HttpServletRequest request, @PathVariable("clubId") int clubId,
											 @PathVariable("playerId") int playerId)
	{
		return updateService.deletePlayer(request, clubId, playerId);
	}
}
