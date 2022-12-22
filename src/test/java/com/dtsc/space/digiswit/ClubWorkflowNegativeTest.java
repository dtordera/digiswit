package com.dtsc.space.digiswit;

import com.dtsc.space.digiswit.entities.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Test cases for general Club workflow (negative conditions)
 */

@SpringBootTest
@AutoConfigureMockMvc
public class ClubWorkflowNegativeTest {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void duplicatedClub(@Autowired MockMvc mvc) throws Exception
	{
		System.out.println("** Prunning all");
		mvc.perform(delete("/utility/prune-system")).andExpect(status().isOk());

		// Create a new club/user (direct JSON to avoid Jackson non-write annotations)
		String club1 = "{ \"username\":\"testing@mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
				"\"officialName\":\"Testing F.C.\", \"popularName\":\"the Final Test\", \"federation\":\"TESTALL\"}";

		System.out.println("** Inserting first club");
		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club1)).andExpect(status().isOk());

		// Create another club/user, but reusing same officialName
		String club2 = "{ \"username\":\"anotherone@mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
				"\"officialName\":\"Testing F.C.\", \"popularName\":\"Another Test\", \"federation\":\"TESTALL\"}";

		System.out.println("** Inserting second club: must fail");
		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club2)).andExpect(status().isConflict());

		// Create another club/user, but reusing username
		String club3 = "{ \"username\":\"testing@mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
				"\"officialName\":\"Best F.C.\", \"popularName\":\"The Best\", \"federation\":\"TESTALL\"}";

		System.out.println("** Inserting third club: must fail");
		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club3)).andExpect(status().isConflict());
	}

	@Test
	public void illegalArgumentsOnNewClub(@Autowired MockMvc mvc) throws Exception
	{
		System.out.println("** Prunning all");
		mvc.perform(delete("/utility/prune-system")).andExpect(status().isOk());

		// Create a new club/user
		System.out.println("** Invalid email");
		String club1 = "{ \"username\":\"testing_my_mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
				"\"officialName\":\"Testing F.C.\", \"popularName\":\"the Final Test\", \"federation\":\"TESTALL\"}";

		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club1)).andExpect(status().isUnprocessableEntity());

		System.out.println("** Not all fields");
		String club2 = "{ \"username\":\"testing@mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
				"\"officialName\":\"Testing F.C.\", \"popularName\":\"the Final Test\"}";

		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club2)).andExpect(status().isUnprocessableEntity());

		System.out.println("** Fields empty");
		String club3 = "{ \"username\":\"testing@mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
				"\"officialName\":\"\", \"popularName\":\"the Final Test\", \"federation\":\"TESTALL\"}";

		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club3)).andExpect(status().isUnprocessableEntity());

		System.out.println("** Fields length (password)");
		String club4 = "{ \"username\":\"testing@mail.com\", \"password\":\"test1\", \"shown\":true, "+
				"\"officialName\":\"\", \"popularName\":\"the Final Test\", \"federation\":\"TESTALL\"}";
		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club4)).andExpect(status().isUnprocessableEntity());

		System.out.println("** Fields length (federation)");
		String club5 = "{ \"username\":\"testing@mail.com\", \"password\":\"test1\", \"shown\":true, "+
				"\"officialName\":\"\", \"popularName\":\"the Final Test\", \"federation\":\"TEST\"}";
		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club5)).andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void failsOnUpdate(@Autowired MockMvc mvc) throws Exception
	{
		System.out.println("** Prunning all");
		mvc.perform(delete("/utility/prune-system")).andExpect(status().isOk());

		// Create a new club/user (direct JSON to avoid Jackson non-write annotations)
		String club1 = "{ \"username\":\"testing@mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
				"\"officialName\":\"Testing F.C.\", \"popularName\":\"the Final Test\", \"federation\":\"TESTALL\"}";

		System.out.println("** Inserting first club");
		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club1)).andExpect(status().isOk());

		// Create a second club/user
		String club2 = "{ \"username\":\"more@mail.com\", \"password\":\"testing1234\", \"shown\":true, "+
				"\"officialName\":\"More F.C.\", \"popularName\":\"MORE\", \"federation\":\"TESTALL\"}";

		System.out.println("** Inserting second club");
		mvc.perform(post("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(club2)).andExpect(status().isOk());

		// do login for first user...
		System.out.println("** Retrieve token");
		String login = "{\"username\":\"testing@mail.com\", \"password\":\"testing1234\"}";

		System.out.println("** Do login");
		MvcResult resultDoLogin = mvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				content(login)).andExpect(status().isOk()).andReturn();

		String token = objectMapper.readValue(resultDoLogin.getResponse().getContentAsString(), Session.class).getToken();
		System.out.println("** Retrieved token : " + token);

		System.out.println("** Attempt to modify first club with officialName of the second one...");
		String updateclub1 = "{ \"officialName\":\"More F.C.\"}";

		mvc.perform(put("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				header("authorization", "Bearer " + token).
				content(updateclub1)
		).andExpect(status().isConflict());

		// check for email
		System.out.println("** Attempt to modify username...");
		String updateclub2 = "{ \"username\":\"test_my_mail\"}";
		mvc.perform(put("/club").
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
				header("authorization", "Bearer " + token).
				content(updateclub2)
		).andExpect(status().isUnprocessableEntity());


	}
}
