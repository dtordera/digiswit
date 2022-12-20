package com.dtsc.space.digiswit.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/*
 * DTordera, 2017
 * General logging filter (outside Spring): gets request and prints it without consuming it; same with response
 */

public class RequestLoggerFilterHelper {

	final static RequestLogger logger = new RequestLogger(RequestLoggerFilterHelper.class); // general tweaked logger
	final static Random rnd = new Random(); // required for uid
	final static int maxPayloadLength = 1000; // Not log too long responses...
	final static boolean includeResponsePayload = true; //

	private static String getContentAsString(byte[] buf, String charsetName) {
		
	    if (buf == null || buf.length == 0) return "";

		int length = Math.min(buf.length, maxPayloadLength);


	    try {
	    	return new String(buf, 0, length, charsetName);

	    } catch (UnsupportedEncodingException ex) {
	    	return "Unsupported Encoding";
	    }
	}

	public static void applyFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
	{
		long startTime = System.currentTimeMillis();		
		long uid = rnd.nextLong() % 1000000000L;
		uid = uid < 0 ? -uid : uid;

		// Setting a random grepable uid as new request attribute
		request.setAttribute("uid", uid);

		// In case of X-Real-IP established (will be set on front nginx) we show it
		String throughProxy = request.getHeader("X-Real-IP");
		logger.info(request, "-> Received new request from " + request.getLocalAddr() + (throughProxy != null? " (X-Real-IP: " + throughProxy + ")":""));

		// Retrieve method & URL triggered
		StringBuffer reqInfo = new StringBuffer()			
			.append(request.getMethod())
			.append(" to ")
			.append(request.getRequestURL());
	
		String queryString = request.getQueryString();
		if (queryString != null)
			reqInfo.append("?").append(queryString);

		// Logging info
		logger.info(request, "-> " + reqInfo);


		// Cloning request & response
		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

		// Send them to workflow chain
		filterChain.doFilter(wrappedRequest, wrappedResponse);

		// Get request payload
		String requestBody =
				getContentAsString(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding());

		if (requestBody.length() > 0)
			logger.info(request, "(request payload) -> " + requestBody);

		// Calculate total request & answer time
		long duration = System.currentTimeMillis() - startTime;

		logger.info(request, "<- Answered with http status code " + response.getStatus() + " (" + duration + "ms)");
		
		byte[] r = wrappedResponse.getContentAsByteArray();
		if (includeResponsePayload && r.length > 0)
			logger.info(request, "<- " +
					getContentAsString(r, response.getCharacterEncoding()));
	
	    wrappedResponse.copyBodyToResponse();  
	}
}
