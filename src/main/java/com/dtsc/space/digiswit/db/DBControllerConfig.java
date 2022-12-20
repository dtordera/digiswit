package com.dtsc.space.digiswit.db;


import com.dtsc.space.ci.db.DBConfiguration;
import com.dtsc.space.digiswit.logging.RequestLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/*
 * DBControllerConfig. DBController bean factory
 * D.Tordera, 202102201 (refactor)
 */

@Configuration
public class DBControllerConfig {

	final static RequestLogger logger = new RequestLogger(DBControllerConfig.class);


	@Value("${database.configfile}")
	String databasefile;

	@Bean
	public JdbcTemplate jdbctemplate() throws Exception
	{
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConfiguration.getDataSource(databasefile));
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        
        return jdbcTemplate;		
	}	
}
