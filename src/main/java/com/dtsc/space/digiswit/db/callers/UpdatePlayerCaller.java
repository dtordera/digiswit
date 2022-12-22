package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.Player;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221222. Caller to DB for update existing player
 */

public class UpdatePlayerCaller extends DBCaller<UpdatePlayerCaller> {

	// Input / output object
	private final Player player;

	public UpdatePlayerCaller(JdbcTemplate jdbctemplate, Player player) {
		super(jdbctemplate, DBResources._UPDATEPLAYER);
		this.player = player;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {
		cs.setInt(1, player.getId());
		cs.setInt(2, player.getClubId());

		cs.setString(3, player.getGivenName());
		cs.setString(4, player.getFamilyName());
		cs.setString(5, player.getNationality());
		cs.setString(6, player.getEmail());
		cs.setDate(7, player.getDateOfBirth());

		cs.registerOutParameter(8, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Player getResultObject() {
		return player;
	}
}
