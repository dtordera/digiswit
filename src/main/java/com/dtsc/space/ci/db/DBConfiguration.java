package com.dtsc.space.ci.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/*
 * DBControllerConfig. DBController bean factory
 * D.Tordera, 20200221. Refactor  
 */

public class DBConfiguration {

	private static Logger logger = LogManager.getLogger(DBConfiguration.class);
	
	public static Properties loadProperties(String propFileName) throws IOException {
		Properties p = new Properties();
		FileInputStream is = null;

		try {
			is = new FileInputStream(propFileName);
			if (is != null)
				p.load(is);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}

		return p;
	}		
		
	private static void checkconnect(HikariDataSource ds) throws SQLException
	{
		Connection cn = null;
    
        cn = ds.getConnection();
        if (cn != null) cn.close();
	}	
	
	private static HikariConfig hikariconfig(String propFileName) throws IOException
	{
		Properties p = loadProperties(propFileName);
		
		HikariConfig hc = new HikariConfig();
						
		String user = p.getProperty("database.username","");
		String pass = p.getProperty("database.password","");
		String jdbcurl = p.getProperty("database.jdbcurl","");
		
		logger.info("Connection string : " + jdbcurl);
		
		String driverclassname = p.getProperty("database.driverclassname", "");
		
		hc.setDriverClassName(driverclassname);
		hc.setJdbcUrl(jdbcurl);
		hc.setUsername(user);
		hc.setPassword(pass);
		
		hc.addDataSourceProperty("cacheMetadata", false);		
		hc.addDataSourceProperty("cachePrepStmts", "true");
		hc.addDataSourceProperty("prepStmtCacheSize","256");
		hc.addDataSourceProperty("prepStmtCacheSqlLimit","2048");
				
		return hc; 
	}
	
	public static HikariDataSource getDataSource(String propFileName) throws Exception
	{		
	    logger.info("Setting up Hikari DBCP connection pool.");
		logger.info("Getting config from file: " + propFileName);
	    
        HikariDataSource ds = new HikariDataSource(hikariconfig(propFileName));
       
        logger.info("Checking connection ...");
        checkconnect(ds);
                
		logger.info("All ok!");
		return ds;		
	}	
}
