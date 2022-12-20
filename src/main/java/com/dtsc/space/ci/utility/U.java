package com.dtsc.space.ci.utility;

import java.util.regex.Pattern;

/*
 * DTordera, 20221220. General Utility functions static class
 */
public class U {

	// regex delivered by https://owasp.org/www-community/OWASP_Validation_Regex_Repository
	private final static Pattern _EMAILREGEX = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$",2);

	public static boolean isValidEmail(String email)
	{
		return _EMAILREGEX.matcher(email).matches();
	}
}
