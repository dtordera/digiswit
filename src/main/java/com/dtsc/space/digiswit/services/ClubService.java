package com.dtsc.space.digiswit.services;

import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.NewClub;
import com.dtsc.space.digiswit.entities.Player;
import com.dtsc.space.ci.entities.exceptions.DuplicatedKeyException;
import com.dtsc.space.digiswit.requestops.logging.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

			// All ok, attempt to insert db
			return new ResponseEntity<>(dbService.insertNewClub(request, newclub), HttpStatus.OK);
		}
		catch(DuplicatedKeyException D) // case of existing entity on server (could be used 400 bad request as well)
		{
			logger.exception(request, D);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		catch(IllegalArgumentException IA) // bad arguments by semantic (could be used 400 bad request as well)
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

	//
	// New player section
	//

	public ResponseEntity<Player> registerNewPlayer(HttpServletRequest request, int clubId, Player player)
	{
		logger.info(request, "Creating new player " + player.getGivenName() + " for club with id " + clubId);

		try {
			// Validate arguments
			validatorService.validateNewPlayerRegister(request, clubId, player);

			// All ok, attempt to insert db
			return new ResponseEntity<>(dbService.insertNewPlayer(request, clubId, player), HttpStatus.OK);
		}
		catch(DuplicatedKeyException D) // case of existing entity on server (could be used 400 bad request as well)
		{
			logger.exception(request, D);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		catch(IllegalArgumentException IA) // bad arguments by semantic (could be used 400 bad request as well)
		{
			logger.exception(request, IA);
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		catch(SecurityException S) // will occur when a valid token attempts to work on a not-owned club id
		{
			logger.exception(request, S);
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		catch (Exception E) {	// All other
			logger.exception(request, E);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//
	// Get clubs
	//
}
