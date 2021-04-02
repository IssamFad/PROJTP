package com.example.demo;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.servlet.ServletProperties;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

@Component
@ApplicationPath("api")
@Configuration
public class JerseyConfiguration extends ResourceConfig{
	public JerseyConfiguration() {
		register(PersonneResource.class);
 		register(LivreResource.class);
 		register(BibliothequeResource.class);
 		property(ServletProperties.FILTER_FORWARD_ON_404, true);
	}
}