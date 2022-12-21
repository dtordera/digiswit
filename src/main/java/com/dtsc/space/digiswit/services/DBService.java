package com.dtsc.space.digiswit.services;

import com.dtsc.space.digiswit.db.DBErrorConverter;
import com.dtsc.space.digiswit.db.GetTokenCaller;
import com.dtsc.space.digiswit.db.InsertNewClubCaller;
import com.dtsc.space.digiswit.entities.Login;
import com.dtsc.space.digiswit.entities.NewClub;
import com.dtsc.space.digiswit.entities.Token;
import com.dtsc.space.digiswit.logging.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@Service
public class DBService {

	final static RequestLogger logger = new RequestLogger(DBService.class);

	@Autowired
	DBErrorConverter errorConverter;

	@Autowired
	JdbcTemplate jdbctemplate;

	public NewClub insertNewClub(HttpServletRequest request, NewClub newclub) throws Exception {

		return errorConverter.checkCaller(new InsertNewClubCaller(jdbctemplate, newclub).doCall(request));
	}

	public Token getToken(HttpServletRequest request, Login login) throws Exception
	{
		return errorConverter.checkCaller(new GetTokenCaller(jdbctemplate, login).doCall(request));
	}
}
