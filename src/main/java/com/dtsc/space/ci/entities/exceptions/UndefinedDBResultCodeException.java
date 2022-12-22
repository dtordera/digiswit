package com.dtsc.space.ci.entities.exceptions;

import java.sql.SQLException;

/*
 * DTordera, 20221221. Custom exceptions
 */


public class UndefinedDBResultCodeException extends SQLException {

	private final static String _MESSAGE = "DB result code not defined";

	public UndefinedDBResultCodeException() { super(_MESSAGE); }
}
