package com.dtsc.space.digiswit.controllers;

/*
 * D.Tordera, 20221220. PingController. Check if API is alive. It returns server Unix timestamp.
 */

import com.dtsc.space.digiswit.entities.Nationality;
import com.dtsc.space.digiswit.entities.Ping;
import com.dtsc.space.digiswit.services.ListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ping")
public class PingController {

	@Autowired
	ListService listService;

	// API Status check
	@GetMapping(value="", produces={"application/json"})
	public Ping doPing(HttpServletRequest request)
	{
		return new Ping("pong");
	}

	// API Status check + parameter echo
	@GetMapping(value="/{echo}", produces={"application/json"})
	public Ping doPing(HttpServletRequest request, @PathVariable("echo") String echo)
	{		
		return new Ping(echo);
	}

	// Nationalities list
	@GetMapping(value="/nationalities", produces={"application/json"})
	public ResponseEntity<List<Nationality>> getNationalities(HttpServletRequest request)
	{
		return listService.getNationalities(request);
	}
}
