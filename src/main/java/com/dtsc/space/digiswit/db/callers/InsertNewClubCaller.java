package com.dtsc.space.digiswit.db.callers;

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.digiswit.db.DBResources;
import com.dtsc.space.digiswit.entities.NewClub;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/*
 * DTordera, 20221220. Caller to DB for new club insertion
 */

public class InsertNewClubCaller extends DBCaller<InsertNewClubCaller> {

	@Getter
	private final NewClub newclub;


	public InsertNewClubCaller(JdbcTemplate jdbctemplate, NewClub newclub) {
		super(jdbctemplate, DBResources._INSERTNEWCLUB);
		this.newclub = newclub;
	}

	@Override
	public void mapParameters(CallableStatement cs) throws SQLException {
		cs.setString(1,newclub.getUsername());
		cs.setString(2, newclub.getPassword());
		cs.setString(3, newclub.getOfficialName());
		cs.setString(4, newclub.getPopularName());
		cs.setString(5, newclub.getFederation());
		cs.setBoolean(6, newclub.isShown());

		cs.registerOutParameter(7, Types.INTEGER);
		cs.registerOutParameter(8, Types.INTEGER);
	}

	@Override
	public void mapResponse(CallableStatement cs) throws SQLException {

		setRc(cs.getInt("rc"));

		if (getRc() == 0)
			newclub.setId(cs.getInt("p_clubId"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public NewClub getResultObject() {
		return newclub;
	}
}
