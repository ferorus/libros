package org.orusmorlans.libros.config;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Properties;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;

import lombok.extern.slf4j.Slf4j;

import org.orusmorlans.libros.jpa.LibrosRepository;
import org.orusmorlans.libros.jpa.LibrosEntity;

@Configuration
@Import({DataSourceConfig.class})
//https://github.com/Cosium/spring-data-jpa-entity-graph/blob/master/doc/MAIN.md
@EnableJpaRepositories(
		repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class,
		basePackageClasses = {LibrosRepository.class},
		entityManagerFactoryRef = JpaConfig.ENTITY_MANAGER_FACTORY,
		transactionManagerRef = JpaConfig.TRANSACTION_MANAGER_NAME
)

@EnableTransactionManagement
@Slf4j
public class JpaConfig {

	@Autowired
	@Qualifier(DataSourceConfig.DATASOURCE_NAME)
	private DataSource dataSource;

	public static final ZoneId TIMEZONE_ID = ZoneId.systemDefault();
	
	public static final String PERSISTENCE_UNIT = "libros";
	public static final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";
	public static final String TRANSACTION_MANAGER_NAME = "entityTransactionManager";
	public static final String TRANSACTION_TEMPLATE_NAME = "transactionTemplate";

	public JpaConfig() {
		super();
		//https://github.com/vladmihalcea/hibernate-types#how-to-remove-the-hypersistence-optimizer-banner-from-the-log
		System.setProperty("hibernate.types.print.banner", "false");
		TimeZone.setDefault(TimeZone.getTimeZone(TIMEZONE_ID));
		System.setProperty("user.timezone", TimeZone.getTimeZone(TIMEZONE_ID).getDisplayName());
	}

	private Properties hibernateProperties() throws IOException {
		Resource resource = new ClassPathResource("/hibernate.properties");
		var properties = PropertiesLoaderUtils.loadProperties(resource);
		
		log.info("Using Hibernate Properties: {}", properties);
		return properties;
	}

	@Bean(ENTITY_MANAGER_FACTORY)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IOException {
		var vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setGenerateDdl(false);

		var factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setDataSource(dataSource);

		factory.setPackagesToScan(LibrosEntity.class.getPackage().getName());
		factory.setJpaProperties(hibernateProperties());
		factory.setPersistenceUnitName(PERSISTENCE_UNIT);

		return factory;
	}

	@Bean(TRANSACTION_MANAGER_NAME)
	public PlatformTransactionManager transactionManager() throws IOException {
		var txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		txManager.setDataSource(dataSource);
		txManager.setPersistenceUnitName(PERSISTENCE_UNIT);

		log.debug("Created to PlatformTransactionManager: {}", txManager);

		return txManager;
	}

	@Bean(TRANSACTION_TEMPLATE_NAME)
	public TransactionTemplate transactionTemplate() throws IOException {
		return new TransactionTemplate(transactionManager());
	}


}
