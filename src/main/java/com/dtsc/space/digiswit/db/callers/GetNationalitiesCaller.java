package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBResultSetCaller;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.Nationality;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221222. Gets all nationalities
 */

public class GetNationalitiesCaller extends DBResultSetCaller<Nationality> {

	public GetNationalitiesCaller(JdbcTemplate jdbctemplate) {
		super(jdbctemplate, DBResources._GETNATIONALITIES);
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
	public Nationality mapResultSet(ResultSet rs) throws SQLException {

		Nationality N = new Nationality();

		N.setCountryCode(rs.getInt(1));
		N.setAlpha2(rs.getString(2));
		N.setAlpha3(rs.getString(3));
		N.setCountryName(rs.getString(4));

		return N;
	}
}
