package com.dtsc.space.ci.entities.exceptions;

/*
 * DTordera, 20221221. Custom exceptions
 */

public class UserPasswordMismatchException extends SecurityException {

	private final static String _MESSAGE = "User/password mismatch";

	public UserPasswordMismatchException() { super(_MESSAGE); }
}
