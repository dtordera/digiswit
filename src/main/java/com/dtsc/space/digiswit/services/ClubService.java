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
	ValidationService validator;

	@Autowired
	DBService dbservice;

	//
	// New club/user register section
	//

	public ResponseEntity<Club> registerNewClub(HttpServletRequest request, NewClub newclub)
	{
		logger.info(request, "Creating new club register for \"" + newclub.getOfficialName() + "\" for user " + newclub.getUsername());

		try {

			// Validate arguments
			validator.validateNewClubRegister(request, newclub);

			// Attempting to insert on db
			dbservice.insertNewClub(request, newclub);

			// All ok, return with echo
			return new ResponseEntity<>(newclub, HttpStatus.OK);
		}
		catch(IllegalArgumentException IA) // bad arguments: returning, instead of 400 bad request, a more suitable 422
		{
			logger.exception(request, IA);
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		catch(SQLException sql)
		{
			logger.exception(request, sql);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
