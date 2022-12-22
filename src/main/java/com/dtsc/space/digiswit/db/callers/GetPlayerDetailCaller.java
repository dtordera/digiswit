package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.Player;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221222. Gets player detail by its id
 */

public class GetPlayerDetailCaller extends DBCaller<GetPlayerDetailCaller> {

	// Input
	final private int clubId;
	final private int playerId;

	// Output
	private Player player;

	public GetPlayerDetailCaller(JdbcTemplate jdbctemplate, int clubId, int playerId) {
		super(jdbctemplate, DBResources._GETPLAYERDETAIL);
		this.clubId = clubId;
		this.playerId = playerId;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {

		cs.setInt(1, clubId);
		cs.setInt(2, playerId);

		cs.registerOutParameter(3, Types.VARCHAR);
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.registerOutParameter(6, Types.VARCHAR);
		cs.registerOutParameter(7, Types.DATE);
		cs.registerOutParameter(8, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));

		if (getRc()!=0) return;

		player = new Player();
		player.setId(playerId);
		player.setClubId(clubId);
		player.setGivenName(cs.getString("p_givenName"));
		player.setFamilyName(cs.getString("p_familyName"));
		player.setNationality(cs.getString("p_nationality"));
		player.setEmail(cs.getString("p_email"));
		player.setDateOfBirth(cs.getDate("p_dateOfBirth"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Player getResultObject() { return player; }
}
