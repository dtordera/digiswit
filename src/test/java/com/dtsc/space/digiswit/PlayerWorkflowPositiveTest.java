package com.dtsc.space.digiswit;

/*
 * Test cases for general player workflow (positive conditions)
 */

import com.dtsc.space.digiswit.entities.ClubRegister;
import com.dtsc.space.digiswit.entities.Player;
import com.dtsc.space.digiswit.entities.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerWorkflowPositiveTest {

	private final ObjectMapper objectMapper = new ObjectMapper();


	@Test
	public void CompletePlayerPositiveWorkflow(@Autowired MockMvc mvc) throws Exception {

		// clear all db
		System.out.println("** Prunning all");
		mvc.perform(delete("/utility/prune-system")).andExpect(status().isOk());

		// Create a new club/user
		String club1 = "{ \"username\":\"testing@mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
				"\"officialName\":\"Testing F.C.\", \"popularName\":\"the Final Test\", \"federation\":\"TESTALL\"}";

		System.out.println("** Inserting first club");
		MvcResult resultNewClub = mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club1)).andExpect(status().isOk()).andReturn();

		int clubId = JsonPath.parse(resultNewClub.getResponse().getContentAsString()).read("$.id");
		System.out.println("Retrieved club id for first club: " + clubId);

		// do login for first user...
		String login = "{\"username\":\"testing@mail.com\", \"password\":\"testing1234\"}";

		System.out.println("** Do login");
		MvcResult resultDoLogin = mvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(login)).andExpect(status().isOk()).andReturn();

		String token = objectMapper.readValue(resultDoLogin.getResponse().getContentAsString(), Session.class).getToken();
		System.out.println("** Retrieved token : " + token);

		// Adding a player
		System.out.println("** Adding player 1");
		String player1 = "{\"givenName\":\"Leo\", \"familyName\":\"Messi\", \"email\":\"leo.messi@mail.com\", \"dateOfBirth\":\"1988-08-09\" "+
				",\"nationality\":\"AG\"}";

		MvcResult resultMessi = mvc.perform(post("/club/" + clubId + "/player").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(player1).
				header("authorization", "Bearer " + token)
		).andExpect(status().isOk()).andReturn();

		int messiid = JsonPath.parse(resultMessi.getResponse().getContentAsString()).read("$.id");
		System.out.println("** Player 1 id : " + messiid);

		// Adding another one...
		System.out.println("** Adding player 2");
		String player2 = "{\"givenName\":\"Cristiano\", \"familyName\":\"Ronaldo\", \"email\":\"cronaldo@mail.com\", \"dateOfBirth\":\"1987-02-10\" "+
				",\"nationality\":\"PT\"}";

		MvcResult resultCristiano = mvc.perform(post("/club/" + clubId + "/player").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(player2).
				header("authorization", "Bearer " + token)
		).andExpect(status().isOk()).andReturn();

		int cristianoid = JsonPath.parse(resultCristiano.getResponse().getContentAsString()).read("$.id");
		System.out.println("** Player 2 id : " + cristianoid);

		// Update player 2
		System.out.println("** Update player 2 email");
		String updateplayer2 = "{\"email\":\"cristiano.ronaldo@mail.com\"}";
		mvc.perform(put("/club/" + clubId + "/player/" + cristianoid).
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(updateplayer2).
				header("authorization", "Bearer " + token)
		).andExpect(status().isOk());

		// Delete all
		System.out.println("** Delete player 2");
		mvc.perform(delete("/club/" + clubId + "/player/" + cristianoid).
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				header("authorization", "Bearer " + token)
		).andExpect(status().isOk()).andReturn();

		System.out.println("** Delete player 1");
		mvc.perform(delete("/club/" + clubId + "/player/" + messiid).
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				header("authorization", "Bearer " + token)
		).andExpect(status().isOk()).andReturn();
	}
}
