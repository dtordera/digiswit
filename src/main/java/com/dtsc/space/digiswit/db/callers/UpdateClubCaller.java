package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.ClubRegister;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221222. Caller to DB for club update
 */

public class UpdateClubCaller extends DBCaller<UpdateClubCaller> {

	// Input / output object
	private final ClubRegister updatedClub;


	public UpdateClubCaller(JdbcTemplate jdbctemplate, ClubRegister updatedClub) {
		super(jdbctemplate, DBResources._UPDATECLUB);
		this.updatedClub = updatedClub;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {
		cs.setInt(1, updatedClub.getId());
		cs.setString(2,updatedClub.getUsername());
		cs.setString(3, updatedClub.getPassword());
		cs.setString(4, updatedClub.getOfficialName());
		cs.setString(5, updatedClub.getPopularName());
		cs.setString(6, updatedClub.getFederation());
		cs.setBoolean(7, updatedClub.isShown());

		cs.registerOutParameter(8, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public ClubRegister getResultObject() {
		return updatedClub;
	}
}
