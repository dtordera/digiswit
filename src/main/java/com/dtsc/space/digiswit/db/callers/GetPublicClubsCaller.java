package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBResultSetCaller;
import com.dtsc.space.ci.db.IDBResource;
import com.dtsc.space.ci.entities.BaseEntity;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.Club;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221222. Gets all clubs, filtered by pattern
 */

public class GetPublicClubsCaller extends DBResultSetCaller<Club> {

	// Input
	String pattern;

	public GetPublicClubsCaller(JdbcTemplate jdbctemplate, String pattern) {
		super(jdbctemplate, DBResources._GETPUBLICCLUBS);
		this.pattern = pattern;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {
		cs.setString(1, pattern);
		cs.registerOutParameter(2, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {
		setRc(cs.getInt("rc"));
	}

	@Override
	public Club mapResultSet(ResultSet rs) throws SQLException {

		Club C = new Club();

		C.setId(rs.getInt(1));
		C.setOfficialName(rs.getString(2));
		C.setPopularName(rs.getString(3));
		C.setFederation(rs.getString(4));

		return C;
	}
}
