package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.ci.entities.BaseEntity;
import com.dtsc.space.digiswit.db.DBResources;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221221. Prune all Clubs + Players data on db, and flushes cache
 */
public class PruneSystemCaller extends DBCaller<PruneSystemCaller> {


	public PruneSystemCaller(JdbcTemplate jdbctemplate) {
		super(jdbctemplate, DBResources._PRUNESYSTEM);
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {
		cs.registerOutParameter(1, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));
	}

	@Override
	public <Q extends BaseEntity> Q getResultObject() { return null; }
}
