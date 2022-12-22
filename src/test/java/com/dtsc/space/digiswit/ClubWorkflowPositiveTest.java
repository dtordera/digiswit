package com.dtsc.space.digiswit;

import com.dtsc.space.digiswit.entities.Club;
import com.dtsc.space.digiswit.entities.ClubRegister;
import com.dtsc.space.digiswit.entities.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Test cases for general Club workflow (positive conditions)
 */

@SpringBootTest
@AutoConfigureMockMvc
public class ClubWorkflowPositiveTest {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void CompleteClubPositiveWorkflow(@Autowired MockMvc mvc) throws Exception
	{
		// clear all db
		System.out.println("** Prunning all");
		mvc.perform(delete("/utility/prune-system")).andExpect(status().isOk());

		// Create a new club/user (direct JSON to avoid Jackson non-write annotations)
		String club1 = "{ \"username\":\"testing@mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
		"\"officialName\":\"Testing F.C.\", \"popularName\":\"the Final Test\", \"federation\":\"TESTALL\"}";

		System.out.println("** Inserting first club");
		MvcResult resultNewClub = mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club1)).andExpect(status().isOk()).andReturn();

		int clubId = objectMapper.readValue(resultNewClub.getResponse().getContentAsString(), ClubRegister.class).getId();
		System.out.println("Retrieved club id for first club: " + clubId);

		// Create another club
		String club2 = "{ \"username\":\"moreclub@mail.com\", \"password\":\"more43212\", \"shown\":true, "+
				"\"officialName\":\"More F.C.\", \"popularName\":\"More Club\", \"federation\":\"TESTALL\"}";

		System.out.println("** Inserting second club");
		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club2)).andExpect(status().isOk());

		// And another, but this one not public...
		String club3 = "{ \"username\":\"notshown@mail.com\", \"password\":\"secret189\", \"shown\":false, "+
				"\"officialName\":\"NOTSHOWN F.C.\", \"popularName\":\"The Secret\", \"federation\":\"SECRET\"}";

		System.out.println("** Inserting third club");
		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club3)).andExpect(status().isOk());

		// do login for first user...
		String login = "{\"username\":\"testing@mail.com\", \"password\":\"testing1234\"}";

		System.out.println("** Do login");
		MvcResult resultDoLogin = mvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(login)).andExpect(status().isOk()).andReturn();

		String token = objectMapper.readValue(resultDoLogin.getResponse().getContentAsString(), Session.class).getToken();
		System.out.println("** Retrieved token : " + token);

		// List all clubs. Should only appear 2 of them
		System.out.println("** Get list (with authorization)");
		MvcResult resultGetAll = mvc.perform(get("/club").
				accept(MediaType.APPLICATION_JSON).
				header("authorization", "Bearer " + token)
		).andExpect(status().isOk()).andReturn();

		Club[] twoclubs = objectMapper.readValue(resultGetAll.getResponse().getContentAsString(), Club[].class);
		assertTrue(twoclubs.length == 2);

		// List filtered. Should only appear 1 of them
		// NOTE! this will not work on hanged API unless ' ' -> '%20;'
		System.out.println("** Get filtered list (with authorization)");
		MvcResult resultGetOne = mvc.perform(get("/club/filtered-by/The Final Test").
				accept(MediaType.APPLICATION_JSON).
				header("authorization", "Bearer " + token)
		).andExpect(status().isOk()).andReturn();

		Club[] oneclub = objectMapper.readValue(resultGetOne.getResponse().getContentAsString(),Club[].class);
		assertTrue(oneclub.length == 1);

		// Update existing values
		System.out.println("** Update values (password & popularName only)");
		String updateclub = "{\"password\":\"1234Testing\",\"popularName\":\"The Real Test\"}";
		mvc.perform(put("/club").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				header("authorization", "Bearer " + token).
				content(updateclub)
		).andExpect(status().isOk());

		// Get detail & check modifications
		System.out.println("** Get club detail & check for modification");
		mvc.perform(get("/club/" + clubId).
				accept(MediaType.APPLICATION_JSON).
				header("authorization", "Bearer " + token)
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.popularName").value("The Real Test"));

		System.out.println("** All OK");
	}
}
