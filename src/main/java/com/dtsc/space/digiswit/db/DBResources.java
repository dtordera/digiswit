package com.dtsc.space.digiswit.db;

import com.dtsc.space.ci.db.IDBResource;

public enum DBResources implements IDBResource {

	_INSERTNEWCLUB("{call InsertNewClub(?,?,?,?,?,?,?,?)}"),
	_GETTOKEN("{call GetToken(?,?,?,?,?,?)}"),

	_NOOP("");

	final String text;

	DBResources(final String text) {
		this.text = text;
	}

	@Override
	public String getString() {
		return text;
	}
}
