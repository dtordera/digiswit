package com.dtsc.space.digiswit.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/*
 * DTordera, 20221220. Entity for a new club/user registration
 */

@Getter
@Setter
public class ClubRegister extends Club {

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ensure never will be rendered to json
	protected String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ensure never will be rendered to json
	protected String password;

}
