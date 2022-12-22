package com.dtsc.space.digiswit.services;

import com.dtsc.space.digiswit.db.DBErrorConverter;
import com.dtsc.space.digiswit.db.callers.*;
import com.dtsc.space.digiswit.entities.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {

	@Autowired
	DBErrorConverter errorConverter;

	@Autowired
	JdbcTemplate jdbctemplate;

	// General
	public List<Nationality> getNationalities(HttpServletRequest request) throws Exception
	{
		return errorConverter.checkCaller(new GetNationalitiesCaller(jdbctemplate).doCall(request));
	}

	// Token
	public Session getToken(HttpServletRequest request, Login login) throws Exception
	{
		return errorConverter.checkCaller(new GetTokenCaller(jdbctemplate, login).doCall(request));
	}

	public Session checkToken(HttpServletRequest request, String token) throws Exception
	{
		return errorConverter.checkCaller(new CheckTokenCaller(jdbctemplate, token).doCall(request));
	}

	// Club
	public ClubRegister insertNewClub(HttpServletRequest request, ClubRegister newclub) throws Exception {

		return errorConverter.checkCaller(new InsertNewClubCaller(jdbctemplate, newclub).doCall(request));
	}

	public ClubRegister updateClub(HttpServletRequest request, ClubRegister updatedClub) throws Exception {
		return errorConverter.checkCaller(new UpdateClubCaller(jdbctemplate, updatedClub).doCall(request));
	}

	public List<Club> getPublicClubs(HttpServletRequest request, String pattern) throws Exception
	{
		return errorConverter.checkCaller(new GetPublicClubsCaller(jdbctemplate, pattern).doCall(request));
	}

	public Club getClubDetail(HttpServletRequest request, int clubId) throws Exception
	{
		return errorConverter.checkCaller(new GetClubDetailCaller(jdbctemplate, clubId).doCall(request));
	}

	// Player
	public Player insertNewPlayer(HttpServletRequest request, int clubId, Player player) throws Exception
	{
		return errorConverter.checkCaller(new InsertNewPlayerCaller(jdbctemplate, clubId, player).doCall(request));
	}

	public Player updatePlayer(HttpServletRequest request, Player player) throws Exception
	{
		return errorConverter.checkCaller(new UpdatePlayerCaller(jdbctemplate, player).doCall(request));
	}

	public List<Player> getClubPlayers(HttpServletRequest request, int clubId, String pattern) throws Exception
	{
		return errorConverter.checkCaller(new GetClubPlayersCaller(jdbctemplate, clubId, pattern).doCall(request));
	}

	public Player getPlayerDetail(HttpServletRequest request, int clubId, int playerId) throws Exception
	{
		return errorConverter.checkCaller(new GetPlayerDetailCaller(jdbctemplate, clubId, playerId).doCall(request));
	}

	public void deletePlayer(HttpServletRequest request, int clubId, int playerId) throws Exception
	{
		errorConverter.checkCaller(new DeletePlayerCaller(jdbctemplate, clubId, playerId).doCall(request));
	}
}
