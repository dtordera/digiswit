package com.dtsc.space.digiswit.db.callers;

/*
 * DTordera, 20221221. Retrieves, from a token, club/user session
 */

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.ci.entities.BaseEntity;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.Session;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class CheckTokenCaller extends DBCaller<CheckTokenCaller> {

	String token;

	Session session;

	public CheckTokenCaller(JdbcTemplate jdbctemplate, String token) {
		super(jdbctemplate, DBResources._CHECKTOKEN);
		this.token = token;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {

		cs.setString(1, token);

		cs.registerOutParameter(2, Types.INTEGER);
		cs.registerOutParameter(3, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {
		setRc(cs.getInt("rc"));

		if (getRc() != 0) return;

		session = new Session();
		session.setClubId(cs.getInt("p_clubId"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Session getResultObject() {
		return session;
	}
}
