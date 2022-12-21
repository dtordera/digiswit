package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.NewClub;
import com.dtsc.space.digiswit.entities.Player;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221220. Caller to DB for new club insertion
 */

public class InsertNewPlayerCaller extends DBCaller<InsertNewPlayerCaller> {

	@Getter
	private final Player player;

	public InsertNewPlayerCaller(JdbcTemplate jdbctemplate, int clubId, Player player) {
		super(jdbctemplate, DBResources._INSERTNEWPLAYER);
		this.player = player;
		this.player.setClubId(clubId);
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {
		cs.setString(1, player.getGivenName());
		cs.setString(2, player.getFamilyName());
		cs.setString(3, player.getNationality().toUpperCase());
		cs.setString(4, player.getEmail());
		cs.setDate(5, player.getDateOfBirth());
		cs.setInt(6, player.getClubId());

		cs.registerOutParameter(7, Types.INTEGER);
		cs.registerOutParameter(8, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));

		if (getRc() == 0)
			player.setId(cs.getInt("p_playerId"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Player getResultObject() {
		return player;
	}
}
