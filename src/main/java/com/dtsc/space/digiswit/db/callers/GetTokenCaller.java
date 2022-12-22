package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.Login;
import com.dtsc.space.digiswit.entities.Session;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221221. General token retriever from db
 */

public class GetTokenCaller extends DBCaller<GetTokenCaller> {

	// Input
	private final Login login;

	// Output
	private Session session;

	public GetTokenCaller(JdbcTemplate jdbctemplate, Login login) {
		super(jdbctemplate, DBResources._GETTOKEN);
		this.login = login;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {

		cs.setString(1,login.getUsername());
		cs.setString(2, login.getPassword());

		cs.registerOutParameter(3, Types.VARCHAR);
		cs.registerOutParameter(4, Types.BIGINT);
		cs.registerOutParameter(5, Types.BIGINT);
		cs.registerOutParameter(6, Types.INTEGER);
		cs.registerOutParameter(7, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));

		if (getRc() != 0) return;

		session = new Session();
		session.setClubId(cs.getInt("p_clubId"));
		session.setToken(cs.getString("p_token"));
		session.setCreatedOn(cs.getLong("p_insertionTimestamp"));
		session.setExpiresOn(cs.getLong("p_expirationTimestamp"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Session getResultObject() { return session; }
}
