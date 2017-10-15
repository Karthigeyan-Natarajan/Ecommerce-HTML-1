package com.yawinsoftwares.ecommerce.html.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:project.properties")
public class ProjectProperties {
	@Autowired
    private Environment env;
	
	public String getProperty(String key) {
	     return env.getProperty(key);
	 }
	
	public String getProperty(String key, String defaultValue) {
	     return env.getProperty(key,defaultValue);
	 }
}
