package com.dtsc.space.digiswit.db;

import com.dtsc.space.ci.db.IDBResource;

public enum DBResources implements IDBResource {

	_GETNATIONALITIES("{call GetNationalities(?)}"),
	_INSERTNEWCLUB("{call InsertNewClub(?,?,?,?,?,?,?,?)}"),
	_INSERTNEWPLAYER("{call InsertNewPlayer(?,?,?,?,?,?,?,?)}"),
	_GETTOKEN("{call GetToken(?,?,?,?,?,?,?)}"),
	_CHECKTOKEN("{call CheckToken(?,?,?)}"),
	_GETPUBLICCLUBS("{call GetPublicClubs(?,?)}"),
	_GETCLUBPLAYERS("{call GetClubPlayers(?,?,?)}"),
	_GETCLUBDETAIL("{call GetClubDetail(?,?,?,?,?,?)}"),
	_GETPLAYERDETAIL("{call GetPlayerDetail(?,?,?,?,?,?,?,?)}"),
	_UPDATECLUB("{call UpdateClub(?,?,?,?,?,?,?,?)}"),
	_UPDATEPLAYER("{call UpdatePlayer(?,?,?,?,?,?,?,?)}"),
	_DELETEPLAYER("{call DeletePlayer(?,?,?)}"),
	_PRUNESYSTEM("{call PruneSystem(?)}"),


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
