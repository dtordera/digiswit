package com.dtsc.space.digiswit.controllers;

/*
 * D.Tordera, 20221220. PingController. Check if API is alive. It returns server Unix timestamp.
 */

import com.dtsc.space.digiswit.entities.Nationality;
import com.dtsc.space.digiswit.entities.Ping;
import com.dtsc.space.digiswit.services.ListService;
import com.dtsc.space.digiswit.services.UpdateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utility")
public class UtilityController {

	@Autowired
	ListService listService;

	@Autowired
	UpdateService updateService;

	// API Status check
	@GetMapping(value="/ping", produces={"application/json"})
	public Ping doPing(HttpServletRequest request)
	{
		return new Ping("pong");
	}

	// API Status check + parameter echo
	@GetMapping(value="/ping/{echo}", produces={"application/json"})
	public Ping doPing(HttpServletRequest request, @PathVariable("echo") String echo)
	{		
		return new Ping(echo);
	}

	// Nationalities list
	@GetMapping(value="/nationality", produces={"application/json"})
	public ResponseEntity<List<Nationality>> getNationalities(HttpServletRequest request)
	{
		return listService.getNationalities(request);
	}

	// Prune system (delete clubs + players) required for correct tests cases
	@DeleteMapping(value="/prune-system")
	public ResponseEntity<Void> pruneSystem(HttpServletRequest request)
	{
		return updateService.pruneSystem(request);
	}
}
