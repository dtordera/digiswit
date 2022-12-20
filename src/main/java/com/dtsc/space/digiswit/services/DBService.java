package com.dtsc.space.digiswit.services;

import com.dtsc.space.digiswit.db.InsertNewClubCaller;
import com.dtsc.space.digiswit.entities.NewClub;
import com.dtsc.space.digiswit.logging.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class DBService {

	final static RequestLogger logger = new RequestLogger(DBService.class);

	@Autowired
	JdbcTemplate jdbctemplate;

	public NewClub insertNewClub(HttpServletRequest request, NewClub newclub) throws SQLException, IllegalArgumentException
	{
		logger.info(request, "Committing to database");

		InsertNewClubCaller C = new InsertNewClubCaller(jdbctemplate, newclub);
		C.doCall();

		// All ok? just return
		if (C.getRc() == 0)
		{
			logger.info(request, "Commit OK");
			return C.getNewclub();
		}

		// Check some procedure error codes...
		switch(C.getRc())
		{
			case -1 : throw new IllegalArgumentException("Duplicated register by username/officialName");
			default : throw new SQLException("Unknown result from database");
		}
	}
}
