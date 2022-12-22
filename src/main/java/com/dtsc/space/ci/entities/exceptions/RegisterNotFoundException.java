package com.dtsc.space.ci.entities.exceptions;

import java.sql.SQLException;

/*
 * DTordera, 20221221. Custom exceptions
 */

public class RegisterNotFoundException extends SQLException {

	private final static String _MESSAGE = "resource not found";

	public RegisterNotFoundException() { super(_MESSAGE); }
}
