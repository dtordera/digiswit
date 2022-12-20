package com.dtsc.space.digiswit.db;

import com.dtsc.space.ci.db.IDBResource;

public enum DBResources implements IDBResource {

	_INSERTNEWCLUB("{call InsertNewClub(?,?,?,?,?,?,?,?)}"),

	_NOOP("");

	final String text;

	private DBResources(final String text) {
		this.text = text;
	}

	@Override
	public String getString() {
		return text;
	}
}
