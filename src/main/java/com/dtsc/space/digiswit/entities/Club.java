package com.dtsc.space.digiswit.entities;

import com.dtsc.space.ci.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/*
 * DTordera, 20221220. Club base information
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Club extends BaseEntity {

	protected int id = -1;

	protected String officialName;
	protected String popularName;
	protected String federation;
	protected boolean shown = true;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected int numberOfPlayers;
}
