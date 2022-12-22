package com.dtsc.space.digiswit.services;

import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.Nationality;
import com.dtsc.space.digiswit.entities.Player;
import com.dtsc.space.digiswit.requestops.logging.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * DTordera, 20221222. Process service class for list operations
 */

@Service
public class ListService {

	final static RequestLogger logger = new RequestLogger(ListService.class);

	@Autowired
	ValidationService validationService;

	@Autowired
	DBService dbService;

	// Retrieves all nationalities available
	public ResponseEntity<List<Nationality>> getNationalities(HttpServletRequest request)
	{
		logger.info(request, "Retrieving valid nationalities");

		try {
			return new ResponseEntity<>(dbService.getNationalities(request), HttpStatus.OK);
		}
		catch(Exception E)
		{
			logger.exception(request, E);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Retrieves public clubs
	public ResponseEntity<List<Club>> getPublicClubs(HttpServletRequest request)
	{
		return getPublicClubs(request, "");
	}

	// Same as before, but with filter pattern
	public ResponseEntity<List<Club>> getPublicClubs(HttpServletRequest request, String pattern)
	{
		logger.info(request, "Retrieving public clubs" + (!pattern.isEmpty()? " (filtered by " + pattern + ")":""));

		try {
			return new ResponseEntity<>(dbService.getPublicClubs(request, pattern), HttpStatus.OK);
		}
		catch(Exception E) // default exception response
		{
			logger.exception(request, E);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get club extra info (+number of players). NOTE! any user can check for any club info, not only their one.
	// This can be enabled on validateGetClubDetail
	public ResponseEntity<Club> getClubDetail(HttpServletRequest request, int clubId)
	{
		logger.info(request, "Retrieving club " + clubId + " detail info");

		try {
			// Validate arguments
			validationService.validateGetClubDetail(request, clubId);

			// All ok, retrieve from db
			return new ResponseEntity<Club>(dbService.getClubDetail(request, clubId), HttpStatus.OK);
		}
		catch(SecurityException S) // will occur when a valid token attempts to work on a not-owned club id (currently disabled)
		{
			logger.exception(request, S);
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		catch(Exception E) {// default exception response

				logger.exception(request, E);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Retrieve player list of a club
	public ResponseEntity<List<Player>> getClubPlayers(HttpServletRequest request, int clubId)
	{
		return getClubPlayers(request, clubId, "");
	}


	// Retrieve player list of a club, with filter
	public ResponseEntity<List<Player>> getClubPlayers(HttpServletRequest request, int clubId, String pattern)
	{
		logger.info(request, "Retrieving club " + clubId + " player list" + (!pattern.isEmpty()? " (filtered by " + pattern + ")":""));

		try {
			// Validate arguments
			validationService.validateGetClubPlayers(request, clubId);

			// All ok, retrieve from db
			return new ResponseEntity<>(dbService.getClubPlayers(request, clubId, pattern), HttpStatus.OK);
		}
		catch(SecurityException S) // will occur when a valid token attempts to work on a not-owned club id (currently disabled)
		{
			logger.exception(request, S);
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		catch(Exception E) {// default exception response

			logger.exception(request, E);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Player> getPlayerDetail(HttpServletRequest request, int clubId, int playerId)
	{
		logger.info(request, "Retrieving player id " + playerId + " from club id " + clubId + " detail info");
		try {
			// Validate arguments
			validationService.validateGetPlayerDetail(request, clubId, playerId);

			// All ok, retrieve from db
			return new ResponseEntity<>(dbService.getPlayerDetail(request, clubId, playerId), HttpStatus.OK );
		}
		catch(SecurityException S) // will occur when a valid token attempts to work on a not-owned club id (currently disabled)
		{
			logger.exception(request, S);
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		catch(Exception E) {// default exception response

			logger.exception(request, E);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
