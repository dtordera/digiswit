package com.dtsc.space.digiswit.requestops.validation;

import com.dtsc.space.digiswit.InterceptorConfig;
import com.dtsc.space.digiswit.entities.Session;
import com.dtsc.space.digiswit.requestops.logging.RequestLogger;
import com.dtsc.space.digiswit.services.DBService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/*
 * DTordera, 20221221. Interceptor to check for authorization: bearer <token> header
 */

@Component
public class AuthorizeInterceptor implements HandlerInterceptor {

	private final static RequestLogger logger = new RequestLogger(AuthorizeInterceptor.class);

	@Autowired
	DBService dbService;

	private Session checkForAuthorization(HttpServletRequest request, String token) throws Exception {
		logger.info(request, "Retrieving info for token / " + token + " /");
		return dbService.checkToken(request, token);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
	{
		try {

			// Call DB for token check
			logger.info(request, "Checking authorization token");
			Session S = checkForAuthorization(request, request.getHeader(HttpHeaders.AUTHORIZATION).
					toLowerCase().replace("bearer ", ""));

			// Store clubId related to this token, for further use (validation and so on)
			request.setAttribute("clubId", S.getClubId());

			logger.info(request, "Authorized. ClubId " + S.getClubId());
			return true;
		}
		catch(NullPointerException N) // will happen if header doesn't appear ...
		{
			logger.exception(request, N);
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return false;
		}
		catch(SecurityException S) // will happen if rejected by db ...
		{
			logger.exception(request, S);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}
		catch(Exception E) // default
		{
			logger.exception(request, E);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return false;
		}
	}
}
