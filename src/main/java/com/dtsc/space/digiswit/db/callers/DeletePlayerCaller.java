package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.ci.entities.BaseEntity;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.Player;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221222. Caller to DB for deleting player
 */

public class DeletePlayerCaller extends DBCaller<DeletePlayerCaller> {

	// input
	private final int clubId;
	private final int playerId;

	public DeletePlayerCaller(JdbcTemplate jdbctemplate, int clubId, int playerId) {
		super(jdbctemplate, DBResources._DELETEPLAYER);
		this.clubId = clubId;
		this.playerId = playerId;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {
		cs.setInt(1, clubId);
		cs.setInt(2, playerId);

		cs.registerOutParameter(3, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));
	}

	@Override // Not required at all on this procedure
	public <Q extends BaseEntity> Q getResultObject() {
		return null;
	}
}
