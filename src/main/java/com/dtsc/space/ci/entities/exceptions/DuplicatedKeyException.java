package com.dtsc.space.ci.entities.exceptions;

/*
 * DTordera, 20221221. Custom exceptions
 */


import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;

public class DuplicatedKeyException extends DataIntegrityViolationException {

	private final static String _MESSAGE = "resource already exists";

	public DuplicatedKeyException() { super(_MESSAGE); }
}
