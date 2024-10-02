package uk.co.orusmorlans.libros.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

// https://www.baeldung.com/spring-yaml-propertysource
public class YamlPropertySourceFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
		YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
		factory.setResources(encodedResource.getResource());

		Properties properties = factory.getObject();
		if(properties == null) {
			throw new IllegalStateException("properties is null");
		}
		String fileName = encodedResource.getResource().getFilename();
		if(fileName == null) {
			throw new IllegalStateException("fileName is null");
		}
		
		return new PropertiesPropertySource(fileName, properties);
	}

}
