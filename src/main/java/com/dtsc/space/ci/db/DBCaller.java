package com.dtsc.space.ci.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;

/*
 * DTordera, 20221220. DBCaller: main class for DBCaller objects, i.e, objects that store parameters & response for a procedure call.
 */

public abstract class DBCaller<T extends DBCaller<T>> {

	private JdbcTemplate jdbctemplate;

	@Getter
	@Setter
	private int rc = -1; // inner result code. Will store extra info about db call. 0 will be OK.

	public DBCaller(JdbcTemplate jdbctemplate, IDBResource sqlcommand)
	{
		this.jdbctemplate = jdbctemplate;
		this.sqlcommand = sqlcommand;
	}

	// returns DBResource (sql command) defined for caller
	protected IDBResource sqlcommand;
	public IDBResource getSQLCommand()
	{
		return sqlcommand;
	};

	// Maps IN/OUT parameters for procedure call
	public abstract void mapParameters(CallableStatement cs) throws SQLException;

	// Maps response parameters
	public abstract void mapResponse(CallableStatement cs) throws SQLException;

	// Inner call to db
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T doCall() throws SQLException
	{
		CallableStatement cs = null;
		Connection cn = null;

		try
		{
			// Prepare connection & procedure
			cn = jdbctemplate.getDataSource().getConnection();
			cs = cn.prepareCall(this.getSQLCommand().getString());
			cn.setAutoCommit(isautomaticCommit());

			// Map parameters in case are required
			this.mapParameters(cs);

			// Makes the call
			cs.execute();

			// In case caller was a dbresultsetcaller, then means db call returns a resultset, so retrieve it...
			if (DBResultSetCaller.class.isAssignableFrom(this.getClass()))
			{
				ResultSet rs = cs.getResultSet();

				// call to implemented dbresultset retriever...
				if (rs != null)
					((DBResultSetCaller)this).addItems(rs);

				// Ignore other possible resultsets returned by db call (can be extended, although not really needed)
				while (cs.getMoreResults());

				if (rs != null) rs.close();
			}

			// Map response params
			this.mapResponse(cs);

			if (!isautomaticCommit())
				cn.commit();
		}
		finally
		{
			try
			{
				if (cs != null) cs.close();
				if (cn != null) cn.close();
			}
			catch(SQLException e){
				// do nothing: we cannot do nothing on statement neither connection at this point, in case cannot
				// be closed correctly (ex, comm failure)
			}
		}

		return (T)this;
	}

	protected boolean isautomaticCommit() {
		return true;
	}
}