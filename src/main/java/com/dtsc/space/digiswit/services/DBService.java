package com.dtsc.space.digiswit.services;

import com.dtsc.space.digiswit.db.DBErrorConverter;
import com.dtsc.space.digiswit.db.callers.CheckTokenCaller;
import com.dtsc.space.digiswit.db.callers.GetTokenCaller;
import com.dtsc.space.digiswit.db.callers.InsertNewClubCaller;
import com.dtsc.space.digiswit.db.callers.InsertNewPlayerCaller;
import com.dtsc.space.digiswit.entities.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DBService {

	@Autowired
	DBErrorConverter errorConverter;

	@Autowired
	JdbcTemplate jdbctemplate;

	public NewClub insertNewClub(HttpServletRequest request, NewClub newclub) throws Exception {

		return errorConverter.checkCaller(new InsertNewClubCaller(jdbctemplate, newclub).doCall(request));
	}

	public Session getToken(HttpServletRequest request, Login login) throws Exception
	{
		return errorConverter.checkCaller(new GetTokenCaller(jdbctemplate, login).doCall(request));
	}

	public Session checkToken(HttpServletRequest request, String token) throws Exception
	{
		return errorConverter.checkCaller(new CheckTokenCaller(jdbctemplate, token).doCall(request));
	}

	public Player insertNewPlayer(HttpServletRequest request, int clubId, Player player) throws Exception
	{
		return errorConverter.checkCaller(new InsertNewPlayerCaller(jdbctemplate, clubId, player).doCall(request));
	}
}
