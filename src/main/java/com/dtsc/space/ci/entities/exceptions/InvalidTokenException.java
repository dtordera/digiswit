package com.dtsc.space.ci.entities.exceptions;

/*
 * DTordera, 20221221. Custom exceptions
 */

public class InvalidTokenException extends SecurityException {

	private final static String _MESSAGE = "Non existent or expired token";

	public InvalidTokenException() { super(_MESSAGE); }
}
