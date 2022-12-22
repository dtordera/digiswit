package com.dtsc.space.digiswit.entities;

import com.dtsc.space.ci.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/*
 * DTordera, 20221222. Returns list of available nationality values parameter
 */

@Getter
@Setter
public class Nationality extends BaseEntity {

	protected int countryCode;
	protected String alpha2;
	protected String alpha3;
	protected String countryName;
}
