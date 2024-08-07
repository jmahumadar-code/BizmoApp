package com.bizmobiz.bizmoweb.config;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.bizmobiz.bizmoweb.util.ClassTool;

@Configuration
public class RestConfig extends RepositoryRestConfigurerAdapter {
	
	Logger logger = Logger.getLogger(this.getClass());

    @SuppressWarnings("rawtypes")
    @Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    	List<Class> classNameSet;
		try {
			classNameSet = ClassTool.getClassName("com.bizmobiz.bizmoweb.domain");
			
			for (Class className : classNameSet) {
	            config.exposeIdsFor(className);
	        }

	        logger.info("exposeIdsFor: " + classNameSet);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
}
