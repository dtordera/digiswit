package com.dtsc.space.digiswit.db;

/*
 * DTordera, 20221221. Static class to convert DB calls results to its correct Exception
 */

import com.dtsc.space.ci.db.DBCaller;
import com.dtsc.space.ci.entities.BaseEntity;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DBErrorConverter {

	// ResultCode : Just a numeric code + log message container
	@Getter
	enum RC {

		_OK(0,""),
		_DUPLICATED_KEY(-1, "Duplicated key"),
		_USERPASSWORDMISMATCH(-2, "User/password mismatch"),
		_UNKNOWN_ERROR(-999, "Unknown error");

		RC(int value, String msg)
		{
			this.value = value;
			this.msg = msg;
		}

		final int value;
		final String msg;
	}

	// Error code to exception converter
	final static Map<Integer, Exception> _dbErrors = new HashMap<>();
	static {
		_dbErrors.put(RC._OK.getValue(), null);
		_dbErrors.put(RC._USERPASSWORDMISMATCH.getValue(), new SecurityException(RC._USERPASSWORDMISMATCH.getMsg()));
		_dbErrors.put(RC._DUPLICATED_KEY.getValue(), new IllegalArgumentException(RC._DUPLICATED_KEY.getMsg()));
	};


	final static String _undefined_error_msg = "Undefined database error result code";

	@SuppressWarnings({"unchecked","rawtypes"})
	public <T extends DBCaller, Q extends BaseEntity> Q checkCaller(T caller) throws Exception {

		if (caller.getRc() == 0)  // All OK? just return required object from caller
			return (Q) caller.getResultObject();

		// All other cases
		Exception E = _dbErrors.get(caller.getRc());
		throw E == null? new NullPointerException(_undefined_error_msg) : E;
	}
}
