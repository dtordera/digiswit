package com.dtsc.space.digiswit.requestops.logging;

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
 * DTordera, 20221220. General custom logging filter (outside Spring): gets req & response and prints it without consuming it
 * NOTE: could be used as well SpringBoot CommonsRequestLoggingFilter.
 */

public class RequestLoggerFilterHelper {

	final static RequestLogger logger = new RequestLogger(RequestLoggerFilterHelper.class);

	final static Random rnd = new Random(); // required for uid

	final static int maxPayloadLength = 1000; // Not log too much long responses...
	final static boolean includeResponsePayload = true; //

	private static String getContentAsString(byte[] buf, String charsetName) {
		
	    if (buf == null || buf.length == 0) return "";

		// max limit
		int length = Math.min(buf.length, maxPayloadLength);

	    try {
	    	return new String(buf, 0, length, charsetName) +
					(buf.length > maxPayloadLength ? " ...(" + (buf.length - maxPayloadLength) + " bytes more)":"");

	    } catch (UnsupportedEncodingException ex) {
			return "Unsupported Encoding"; // should never arrive here
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

		// Calculate total request & answer time
		long duration = System.currentTimeMillis() - startTime;

		// Get request payload. It must be shown *after* doFilter, as it has been parsed there from the request,
		// and not before.

		String requestBody =
				getContentAsString(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding());

		if (requestBody.length() > 0)
			logger.info(request, "-> Request payload was: " + requestBody.replace("\n", "").replace("\t",""));

		logger.info(request, "<- Answered with http status code " + response.getStatus() + " (" + duration + " ms)");
		
		byte[] r = wrappedResponse.getContentAsByteArray();
		if (includeResponsePayload && r.length > 0)
			logger.info(request, "<- " +
					getContentAsString(r, response.getCharacterEncoding()));
	
	    wrappedResponse.copyBodyToResponse();  
	}
}
