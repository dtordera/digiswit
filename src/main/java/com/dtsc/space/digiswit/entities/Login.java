package com.dtsc.space.digiswit.entities;

import com.dtsc.space.ci.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login extends BaseEntity {

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ensure never will be rendered to json
	protected String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ensure never will be rendered to json
	protected String password;
}
