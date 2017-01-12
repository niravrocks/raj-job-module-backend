package com.niit.backend.configuration;

import java.nio.charset.StandardCharsets;

import javax.servlet.ServletRegistration;
import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//configuration of DispatcherServlet
public class WebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {
	protected void customizeRegistration(
			ServletRegistration.Dynamic registration) {
		registration.setInitParameter("dispatchOptionsRequest", "true");
		registration.setAsyncSupported(true);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { WebAppConfig.class, WebSocketConfig.class };
	}

	// DispatcherServlet - receives all the request (any URL)
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	// //////////////////////////////////////////////////
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebAppConfig.class };
	}

	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
		return new Filter[] { characterEncodingFilter };
	}

	// /////////////////////////////////////////////////////

}