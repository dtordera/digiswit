package com.dtsc.space.digiswit.services;

import com.dtsc.space.ci.entities.exceptions.DuplicatedKeyException;
import com.dtsc.space.ci.entities.exceptions.RegisterNotFoundException;
import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.ClubRegister;
import com.dtsc.space.digiswit.entities.Player;
import com.dtsc.space.digiswit.requestops.logging.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

/*
 * DTordera, 20221222. Process service class for update/delete operations
 */

@Service
public class UpdateService {

	final static RequestLogger logger = new RequestLogger(UpdateService.class);

	@Autowired
	ValidationService validationService;

	@Autowired
	DBService dbService;

	public ResponseEntity<Club> updateClub(HttpServletRequest request, ClubRegister updatedclub)
	{
		logger.info(request, "Updating club info");
		try {
			// validate params
			validationService.validateUpdateClubRegister(request, updatedclub);

			// set updated club id as the one related to authorized token, so no requiring an extra location param
			updatedclub.setId(Integer.parseInt(request.getAttribute("clubId").toString()));

			// All ok, attempt to insert db
			return new ResponseEntity<>(dbService.updateClub(request, updatedclub), HttpStatus.OK);
		}
		catch(DuplicatedKeyException D) // case of existing entity on server (could be used 400 bad request as well)
		{
			logger.exception(request, D);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		catch(IllegalArgumentException
			   | SQLIntegrityConstraintViolationException IA) // bad arguments by semantic (could be used 400 bad request as well)
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

	public ResponseEntity<Player> updatePlayer(HttpServletRequest request,int clubId,int playerId,Player updatedplayer)
	{
		logger.info(request, "Update player " + playerId + " from club " + clubId);
		try {
			// Validate arguments
			validationService.validateUpdatePlayerRegister(request, clubId, updatedplayer);

			// Set all required info on updatedplayer object
			updatedplayer.setId(playerId);
			updatedplayer.setClubId(clubId);

			// All ok, attempt to insert db
			return new ResponseEntity<>(dbService.updatePlayer(request, updatedplayer), HttpStatus.OK);
		}
		catch(DuplicatedKeyException D) // case of existing entity on server (could be used 400 bad request as well)
		{
			logger.exception(request, D);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		catch(IllegalArgumentException
			  | SQLIntegrityConstraintViolationException IA) // bad arguments by semantic (could be used 400 bad request as well)
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

	public ResponseEntity<Void> deletePlayer(HttpServletRequest request, int clubId,int playerId)
	{
		logger.info(request, "Delete request for player " + playerId + " from club " + clubId);
		try {
			// Check if required clubid belongs to user...
			validationService.validateDeletePlayer(request, clubId);

			// Call db for direct deletion (not required any object response)
			dbService.deletePlayer(request, clubId, playerId);

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(RegisterNotFoundException F)
		{
			logger.exception(request, F);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
}
