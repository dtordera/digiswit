package com.dtsc.space.ci.entities.exceptions;

/*
 * DTordera, 20221221. Custom exceptions
 */

import java.sql.SQLException;

public class UnknownErrorException extends SQLException {

	private final static String _MESSAGE = "Unknown error";

	public UnknownErrorException() { super(_MESSAGE); }
}
