package com.dtsc.space.digiswit.entities;

import com.dtsc.space.ci.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/*
 * DTordera, 20221221. Return class for Login method
 */

@Getter
@Setter
public class Token extends BaseEntity {

	protected String token;
	protected long createdOn;
	protected long expiresOn;
}
