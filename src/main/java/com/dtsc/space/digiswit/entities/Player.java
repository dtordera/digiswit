package com.dtsc.space.digiswit.entities;

/*
 * DTordera, 20221221. Player entity
 */

import com.dtsc.space.ci.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Player extends BaseEntity {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected int id = -1;

	protected String givenName;
	protected String familyName;
	protected String nationality;
	protected String email;
	protected Date dateOfBirth;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected int clubId=-1;
}
