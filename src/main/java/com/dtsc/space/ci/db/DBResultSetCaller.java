package com.dtsc.space.ci.db;

import com.dtsc.space.ci.entities.BaseEntity;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * DTordera, 20221220. DBResultSetCaller: main caller object, that, if exists after db call, collects it's resultSet
 */
public abstract class DBResultSetCaller<T extends BaseEntity> extends DBCaller<DBResultSetCaller<T>> {

	@Getter
	private final List<T> items;

	public DBResultSetCaller(JdbcTemplate jdbctemplate, IDBResource sqlcommand)
	{
		super(jdbctemplate, sqlcommand);
		setRc(0);
		items = new ArrayList<>();
	}

	public abstract T mapResultSet(ResultSet rs) throws SQLException; // To implement

	public void addItems(ResultSet rs) throws SQLException // go through resultSet and generate list
	{
		if (rs!=null)
			while (rs.next())
				items.add(mapResultSet(rs));
	}

	public <Q extends BaseEntity> Q getResultObject() { return null; } // Should not be required on resultSet operations

}
