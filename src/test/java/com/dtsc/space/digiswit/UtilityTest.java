package com.dtsc.space.digiswit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UtilityTest {

	@Test
	public void isAPIup(@Autowired MockMvc mvc) throws Exception
	{
		mvc.perform(get("/ping").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void isEchoing(@Autowired MockMvc mvc) throws Exception
	{
		mvc.perform(get("/ping/TESTING_ECHO").accept(MediaType.APPLICATION_JSON)).
				andExpect(status().isOk()).
				andExpect(content().json("{\"echo\":\"TESTING_ECHO\"}"));
	}

	@Test
	public void getNationalities(@Autowired MockMvc mvc) throws Exception
	{
		mvc.perform(get("/ping/nationalities").accept(MediaType.APPLICATION_JSON)).
				andExpect(status().isOk()).
				andExpect(jsonPath("$[0].countryCode").value(20)).
				andExpect(jsonPath("$[0].alpha2").value("AD")).
				andExpect(jsonPath("$[0].alpha3").value("AND")).
				andExpect(jsonPath("$[0].countryName").value("Andorra"));
	}
}
