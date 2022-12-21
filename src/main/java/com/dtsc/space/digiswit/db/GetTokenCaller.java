package com.dtsc.space.digiswit.db;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.ci.entities.BaseEntity;
import com.dtsc.space.digiswit.entities.Login;
import com.dtsc.space.digiswit.entities.Token;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221221. General token retriever from db
 */

public class GetTokenCaller extends DBCaller<GetTokenCaller> {

	// Input object
	private final Login login;

	// Resulting object (if all had been ok, null if not)
	@Getter
	private Token token;

	public GetTokenCaller(JdbcTemplate jdbctemplate, Login login) {
		super(jdbctemplate, DBResources._GETTOKEN);
		this.login = login;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {

		cs.setString(1,login.getUsername());
		cs.setString(2, login.getPassword());

		cs.registerOutParameter(3, Types.VARCHAR);
		cs.registerOutParameter(4, Types.INTEGER);
		cs.registerOutParameter(5, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));

		if (getRc() != 0) return;

		token = new Token();
		token.setToken(cs.getString("p_token"));
		token.setCreatedOn(cs.getLong("p_insertionTimestamp"));
		token.setExpiresOn(cs.getLong("p_expirationTimestamp"));
	}

	@Override
	public Token getResultObject() { return token; }
}
