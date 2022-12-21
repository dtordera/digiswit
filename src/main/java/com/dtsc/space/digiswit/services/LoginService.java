package com.dtsc.space.digiswit.services;

import com.dtsc.space.digiswit.entities.Login;
import com.dtsc.space.digiswit.entities.Session;
import com.dtsc.space.digiswit.requestops.logging.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/*
 * DTordera, 20221221. Login processes
 */

@Service
public class LoginService {

	final static RequestLogger logger = new RequestLogger(LoginService.class);

	@Autowired
	DBService dbService;

	public ResponseEntity<Session> getToken(HttpServletRequest request, Login login)
	{
		logger.info(request, "Get new token for user " + login.getUsername());

		try {
			// Call db to retrieve active token (or generate new one)
			return new ResponseEntity<>(dbService.getToken(request, login), HttpStatus.OK);
		}
		catch(SecurityException S)
		{
			logger.exception(request, S);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		catch(Exception E) // default exception
		{
			logger.exception(request, E);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
