package com.dtsc.space.digiswit.services;

import com.dtsc.space.ci.utility.U;
import com.dtsc.space.digiswit.entities.NewClub;
import com.dtsc.space.digiswit.logging.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/*
 * General argument validation class
 */

@Service
public class ValidationService {

	final static RequestLogger logger = new RequestLogger(ValidationService.class);


	public void validateNewClubRegister(HttpServletRequest request, NewClub newclub) throws IllegalArgumentException
	{
		logger.info(request, "Validating new club register arguments");

		// NOTE: fields appearing & filled in are checked here in a simple way, and not on jackson deserialization,
		// but it is possible to do so as well in a more generic way.

		// All fields required are present (shown got default value = true, so it is not required)
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
}
