package com.dtsc.space.digiswit.requestops.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * DTordera, 20221220. Filter for request & response logs (by direct http layer, not owned by Spring)
 */

@Component
public class LoggerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		RequestLoggerFilterHelper.applyFilter(request, response, filterChain);
	}
}
