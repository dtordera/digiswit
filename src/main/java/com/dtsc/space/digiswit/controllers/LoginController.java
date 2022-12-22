package com.dtsc.space.digiswit.controllers;

import com.dtsc.space.digiswit.entities.Login;
import com.dtsc.space.digiswit.entities.Session;
import com.dtsc.space.digiswit.services.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * DTordera, 20221221. Main login-by-token controller
 */

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	RegisterService registerService;

	// Registering new club/user
	@PostMapping(value="", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<Session> registerNewClub(HttpServletRequest request, @RequestBody Login login)
	{
		return registerService.getToken(request, login);
	}
}
