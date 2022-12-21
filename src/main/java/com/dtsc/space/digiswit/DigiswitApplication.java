package com.dtsc.space.digiswit;

import com.dtsc.space.digiswit.requestops.logging.RequestLogger;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.TimeZone;

// DTordera, 20221220

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@OpenAPIDefinition(
		info = @Info(
						title = "DIGISWIT CODE CHALLENGE",
						description = "Digiswit code challenge resolution by candidate D.Tordera"),
		servers = {
				@Server(url="http://dtsc.space:8080/digiswit"), // production
				@Server(url="http://localhost:9001/digiswit") // development
		}
)
public class DigiswitApplication {

	final static RequestLogger logger = new RequestLogger(DigiswitApplication.class);

	public final static String _CONTEXT_PATH = "/digiswit";


	// Setting UTC for normalized timestamp JSON rendering
	@PostConstruct
	void setUTC()
	{
		logger.info(null, "Setting Etc/UTC as timezone");
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
	}

	private static void header()
	{
		logger.info(null, "__________________________________________________________");
		logger.info(null, "");
		logger.info(null, "  COM.DTSC.SPACE.DIGISWIT: Code challenge API for DIGISWIT");
		logger.info(null, "__________________________________________________________");
		logger.info(null, "");
	}

	private static void initSpringBoot(String[] args)
	{
		System.setProperty("server.servlet.context-path", _CONTEXT_PATH);
		SpringApplication.run(DigiswitApplication.class, args);
	}


	public static void main(String[] args) {

		header();
		initSpringBoot(args);
	}
}
