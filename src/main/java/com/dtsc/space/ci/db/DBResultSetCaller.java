package com.dtsc.space.ci.db;

import com.dtsc.space.ci.entities.BaseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * DTordera, 20221220. DBResultSetCaller: main caller object, that, if exists after db call, collects it's resultSet
 */
public abstract class DBResultSetCaller<T extends BaseEntity> extends DBCaller<DBResultSetCaller<T>> {

	private final List<T> _items;

	public DBResultSetCaller(JdbcTemplate jdbctemplate, IDBResource sqlcommand)
	{
		super(jdbctemplate, sqlcommand);
		setRc(0);
		_items = new ArrayList<>();
	}

	public abstract T mapResultSet(ResultSet rs) throws SQLException;

	public void addItems(ResultSet rs) throws SQLException
	{
		if (rs!=null)
			while (rs.next())
				addItem(mapResultSet(rs));
	}

	public void addItem(T C)
	{
		_items.add(C);
	}

	public List<T> getItems()
	{
		return _items;
	}
}
