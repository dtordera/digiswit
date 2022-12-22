package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBResultSetCaller;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.Player;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221222. Gets all clubs, filtered by pattern
 */

public class GetClubPlayersCaller extends DBResultSetCaller<Player> {

	// Input
	String pattern;
	int clubId;

	public GetClubPlayersCaller(JdbcTemplate jdbctemplate, int clubId, String pattern) {
		super(jdbctemplate, DBResources._GETCLUBPLAYERS);
		this.clubId = clubId;
		this.pattern = pattern;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {
		cs.setInt(1, clubId);
		cs.setString(2, pattern);

		cs.registerOutParameter(3, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {
		setRc(cs.getInt("rc"));
	}

	@Override
	public Player mapResultSet(ResultSet rs) throws SQLException {

		Player P = new Player();

		P.setId(rs.getInt(1));
		P.setGivenName(rs.getString(2));
		P.setFamilyName(rs.getString(3));

		return P;
	}
}
