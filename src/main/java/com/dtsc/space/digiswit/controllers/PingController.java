package com.dtsc.space.digiswit.controllers;

/*
 * PingController. Check if API is alive. It returns server Unix timestamp.
 * D.Tordera, 20221220
 */

import com.dtsc.space.digiswit.entities.Ping;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// DTordera, 20221220. Basic ping controller

@RestController
@RequestMapping("/ping")
public class PingController {
 		
	@RequestMapping(value="/{echo}", method= RequestMethod.GET, produces={"application/json"})
	public Ping doPing(HttpServletRequest request, @PathVariable("echo") String echo)
	{		
		return new Ping(echo);
	}
	

	@RequestMapping(value="", method=RequestMethod.GET, produces={"application/json"})
	public Ping doPing(HttpServletRequest request)
	{		
		return new Ping("pong");
	}
}
