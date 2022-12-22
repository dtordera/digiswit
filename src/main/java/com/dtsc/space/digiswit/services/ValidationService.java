package com.dtsc.space.digiswit.services;

import com.dtsc.space.ci.utility.U;
import com.dtsc.space.digiswit.entities.ClubRegister;
import com.dtsc.space.digiswit.entities.Player;
import com.dtsc.space.digiswit.requestops.logging.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/*
 * General argument validation class
 */

@Service
public class ValidationService {

	final static RequestLogger logger = new RequestLogger(ValidationService.class);

	// NOTE: fields appearing & filled in are checked here in a simple way, and not on jackson deserialization,
	// but it is possible to do so as well in a more generic way.

	public void validateNewPlayerRegister(HttpServletRequest request, int clubId, Player player)
	{
		logger.info(request, "Validating new player register arguments");

		// Ensure all required fields are present
		if (player.getGivenName() == null || player.getFamilyName() == null || player.getEmail() == null ||
		    player.getDateOfBirth() == null || player.getNationality() == null)
			throw new IllegalArgumentException("All required fields must be present");

		// All fields required are filled in
		if (player.getGivenName().isEmpty() || player.getFamilyName().isEmpty() || player.getEmail().isEmpty() ||
				player.getNationality().length() != 2)
			throw new IllegalArgumentException("All fields must be filled in");

		// Check for valid email
		if (!U.isValidEmail(player.getEmail()))
			throw new IllegalArgumentException("Invalid email: Must be a valid email account");

		// Token is related to a club/user. So there's no possibility parameter clubId could be different
		if (clubId != Integer.parseInt(request.getAttribute("clubId").toString()))
			throw new SecurityException("Club not owned by token user");

		logger.info(request, "Validation OK");
	}

	public void validateNewClubRegister(HttpServletRequest request, ClubRegister newclub) throws IllegalArgumentException
	{
		logger.info(request, "Validating club register arguments");

		// Ensure all required fields are present
		if (
				newclub.getOfficialName() == null || newclub.getPopularName() == null || newclub.getUsername() == null
			 || newclub.getPassword() == null || newclub.getFederation() == null
		)
			throw new IllegalArgumentException("All required fields must be present");

		// All fields required are filled in
		if (
				newclub.getOfficialName().isEmpty() || newclub.getPopularName().isEmpty() || newclub.getUsername().isEmpty()
             || newclub.getPassword().isEmpty()  || newclub.getFederation().isEmpty()
		   )
			throw new IllegalArgumentException("All fields must be filled in");

		// Federation 8 char max length
		if (newclub.getFederation().length()>8)
			throw new IllegalArgumentException("Federation must be 8 char length max");


		// Valid email on username
		if (!U.isValidEmail(newclub.getUsername()))
			throw new IllegalArgumentException("Invalid username: Must be a valid email account");

		// Password
		if (newclub.getPassword().length() < 8)
			throw new IllegalArgumentException("Invalid password: too short");

		logger.info(request, "Validation OK");
	}

	public void validateGetClubDetail(HttpServletRequest request, int clubId) throws IllegalArgumentException
	{
		logger.info(request, "Validating for club detail info");

		// Token is related to a club/user. So there's no possibility parameter clubId could be different
		// NOTE: deactivated
		/*if (clubId != Integer.parseInt(request.getAttribute("clubId").toString()))
			throw new SecurityException("Club not owned by token user");*/

		logger.info(request, "Validation OK");
	}

	public void validateGetClubPlayers(HttpServletRequest request, int clubId) throws IllegalArgumentException
	{
		logger.info(request, "Validation for club players list");

		// Token is related to a club/user. So there's no possibility parameter clubId could be different
		// NOTE: deactivated
		/*if (clubId != Integer.parseInt(request.getAttribute("clubId").toString()))
			throw new SecurityException("Club not owned by token user");*/

		logger.info(request, "Validation OK");
	}

	public void validateGetPlayerDetail(HttpServletRequest request, int clubId, int playerId) throws IllegalArgumentException
	{
		logger.info(request, "Validation for player detail");

		// Token is related to a club/user. So there's no possibility parameter clubId could be different
		// NOTE: deactivated
		/*if (clubId != Integer.parseInt(request.getAttribute("clubId").toString()))
			throw new SecurityException("Club not owned by token user");*/

		logger.info(request, "Validation OK");
	}


	public void validateUpdateClubRegister(HttpServletRequest request, ClubRegister updatedclub) throws IllegalArgumentException
	{
		logger.info(request, "Validating club register arguments for update");

		// Not all fields required, as will update only appearing fields...
		// ...but if a field appears then it must have a value
		if (
				(updatedclub.getOfficialName() != null && updatedclub.getOfficialName().isEmpty()) ||
				(updatedclub.getPopularName() != null && updatedclub.getPopularName().isEmpty()) ||
				(updatedclub.getFederation() != null && updatedclub.getFederation().isEmpty()) ||
				(updatedclub.getUsername() != null && updatedclub.getUsername().isEmpty()) ||
				(updatedclub.getPassword() != null && updatedclub.getPassword().isEmpty())
		)
			throw new IllegalArgumentException("All appearing fields must be filled in");

		// Federation 8 char max length
		if (updatedclub.getFederation()!= null && updatedclub.getFederation().length()>8)
			throw new IllegalArgumentException("Federation must be 8 char length max");

		// Valid email on username
		if (updatedclub.getUsername() != null && !U.isValidEmail(updatedclub.getUsername()))
			throw new IllegalArgumentException("Invalid username: Must be a valid email account");

		// Password
		if (updatedclub.getPassword() != null && updatedclub.getPassword().length() < 8)
			throw new IllegalArgumentException("Invalid password: too short");

		logger.info(request, "Validation OK");
	}

	public void validateUpdatePlayerRegister(HttpServletRequest request, int clubId, Player player)
	{
		logger.info(request, "Validating updated player register arguments");

		// Not all fields required, as will update only appearing fields...
		// ...but if a field appears then it must have a value

		if (
				(player.getGivenName() != null && player.getGivenName().isEmpty()) ||
				(player.getFamilyName() != null && player.getFamilyName().isEmpty()) ||
				(player.getNationality() != null && player.getNationality().length() != 2) ||
				(player.getEmail() != null && player.getEmail().isEmpty())
		)
			throw new IllegalArgumentException("All appearing fields must be filled in");

		// Check for valid email
		if (player.getEmail() != null && !U.isValidEmail(player.getEmail()))
			throw new IllegalArgumentException("Invalid email: Must be a valid email account");

		// Token is related to a club/user. So there's no possibility parameter clubId could be different
		if (clubId != Integer.parseInt(request.getAttribute("clubId").toString()))
			throw new SecurityException("Club not owned by token user");

		logger.info(request, "Validation OK");
	}

	public void validateDeletePlayer(HttpServletRequest request, int clubId)
	{
		logger.info(request, "Validating club id " + clubId + " for player deletion");

		// Token is related to a club/user. So there's no possibility parameter clubId could be different
		if (clubId != Integer.parseInt(request.getAttribute("clubId").toString()))
			throw new SecurityException("Club not owned by token user");

		logger.info(request, "Validation OK");
	}
}
