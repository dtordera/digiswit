package com.dtsc.space.digiswit.entities;

import lombok.Getter;
import lombok.Setter;

/*
 * DTordera, 20221220. Ping Basic ping entity
 */

@Getter
@Setter
final public class Ping {
	
	String echo;
	long ts;
	
	public Ping(String echo)
	{
		this.echo = echo; 
		this.ts = System.currentTimeMillis()/1000; // return unix timestamp, seconds
	}
}
