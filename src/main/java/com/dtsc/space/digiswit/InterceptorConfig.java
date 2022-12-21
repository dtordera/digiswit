package com.dtsc.space.digiswit;

import com.dtsc.space.digiswit.requestops.logging.RequestLogger;
import com.dtsc.space.digiswit.requestops.validation.AuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * DTordera, 20221221. Setting up interceptors to our required endpoints
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer  {

	@Autowired
	AuthorizeInterceptor authorizeInterceptor;

	private final static RequestLogger logger = new RequestLogger(InterceptorConfig.class);

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		logger.info(null, "Adding Authorize interceptor");
		registry.addInterceptor(authorizeInterceptor)
				.excludePathPatterns("/ping")
				.excludePathPatterns("/ping/**")
				.excludePathPatterns("/login")
				.excludePathPatterns("/club");
	}
}
