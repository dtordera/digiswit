package com.dtsc.space.digiswit.db;

/*
 * DTordera, 20221221. Static class to convert DB calls results to its correct Exception
 */

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.ci.entities.BaseEntity;
import com.dtsc.space.ci.entities.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DBErrorConverter {

	// Error code to exception converter
	final static Map<Integer, Exception> _dbErrors = new HashMap<>();
	static {
		_dbErrors.put( 0, null);
		_dbErrors.put(-1, new DuplicatedKeyException());
		_dbErrors.put(-2, new UserPasswordMismatchException());
		_dbErrors.put(-3, new InvalidTokenException());
		_dbErrors.put(-999, new UnknownErrorException());
	};

	@SuppressWarnings({"unchecked","rawtypes"})
	public <T extends DBCaller, Q extends BaseEntity, R extends Exception> Q checkCaller(T caller) throws R {

		if (caller.getRc() == 0)  // All OK? just return required object from caller
			return (Q) caller.getResultObject();

		// All other cases
		throw (R)_dbErrors.get(caller.getRc());
	}
}
