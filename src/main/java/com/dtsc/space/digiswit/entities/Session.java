package com.dtsc.space.digiswit.entities;

import com.dtsc.space.ci.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/*
 * DTordera, 20221221. Return class for Login method
 */

@Getter
@Setter
public class Session extends BaseEntity {

	protected int clubId;
	protected String token;
	protected long createdOn;
	protected long expiresOn;
}
