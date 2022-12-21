package com.dtsc.space.digiswit.requestops.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/*
 * DTordera, 20221220. General logger object derivation with some extra info & extra grepable uid
 */

public class RequestLogger {
	
	private Logger _logger;
	
	private String uid(HttpServletRequest  request)
	{
		return request != null ? "[" + request.getAttribute("uid") + "] (" + (request).getServletPath() + ") " : ""; 		
	}
	
	public RequestLogger(@SuppressWarnings("rawtypes") Class C)
	{
		_logger = LogManager.getLogger(C);
	}
	
	public void info(HttpServletRequest r, String m)
	{
		_logger.info(uid(r) + m);
	}
	
	public void warn(HttpServletRequest r, String m)
	{
		_logger.warn(uid(r) + m);
	}
	
	public void error(HttpServletRequest r, String m)
	{
		_logger.error(uid(r) + m);
	}

	public void exception(HttpServletRequest r, Exception E)
	{
		error(r, "** EXCEPTION ERROR ** " + E.getClass().getSimpleName() + ": " + E.getMessage());
	}
}
