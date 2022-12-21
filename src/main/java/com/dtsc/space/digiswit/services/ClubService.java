package com.dtsc.space.digiswit.services;

import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.NewClub;
import com.dtsc.space.digiswit.logging.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ClubService {

	final static RequestLogger logger = new RequestLogger(ClubService.class);

	@Autowired
	ValidationService validatorService;

	@Autowired
	DBService dbService;

	//
	// New club/user register section
	//

	public ResponseEntity<Club> registerNewClub(HttpServletRequest request, NewClub newclub)
	{
		logger.info(request, "Creating new club register for \"" + newclub.getOfficialName() + "\" for user " + newclub.getUsername());

		try {

			// Validate arguments
			validatorService.validateNewClubRegister(request, newclub);

			// All ok, return with echo
			return new ResponseEntity<>(dbService.insertNewClub(request, newclub), HttpStatus.OK);
		}
		catch(IllegalArgumentException IA) // bad arguments: returning, instead of 400 bad request, a more suitable 422
		{
			logger.exception(request, IA);
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		catch(Exception E) // default exception response
		{
			logger.exception(request, E);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
