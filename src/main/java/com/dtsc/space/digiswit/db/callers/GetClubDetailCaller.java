package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.ci.entities.BaseEntity;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.Club;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221222. Gets club detail by its id
 */

public class GetClubDetailCaller extends DBCaller<GetClubDetailCaller> {

	// Input
	final private int clubId;

	// Output
	private Club club;

	public GetClubDetailCaller(JdbcTemplate jdbctemplate, int clubId) {
		super(jdbctemplate, DBResources._GETCLUBDETAIL);
		this.clubId = clubId;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {

		cs.setInt(1, clubId);

		cs.registerOutParameter(2, Types.VARCHAR);
		cs.registerOutParameter(3, Types.VARCHAR);
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.registerOutParameter(5, Types.INTEGER);
		cs.registerOutParameter(6, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));

		if (getRc()!=0) return;

		club = new Club();
		club.setId(clubId);
		club.setOfficialName(cs.getString("p_officialName"));
		club.setPopularName(cs.getString("p_popularName"));
		club.setFederation(cs.getString("p_federation"));
		club.setNumberOfPlayers(cs.getInt("p_numberOfPlayers"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Club getResultObject() { return club; }
}
