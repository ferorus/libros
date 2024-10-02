package uk.co.orusmorlans.libros.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@PropertySource(value = "classpath:db.properties")
@EnableConfigurationProperties
@Slf4j
public class DataSourceConfig implements ApplicationListener<ContextRefreshedEvent> {
	
	public static final String DATASOURCE_NAME                    = "dataSource";
	public static final String JDBC_TEMPLATE_NAME                 = "jdbcTemplate";
	public static final String NAMED_PARAMETER_JDBC_TEMPLATE_NAME = "namedJdbcTemplate";
	
	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void setUp() {
		log.info("New");
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		final HikariDataSource hds = applicationContext.getBean(DATASOURCE_NAME, HikariDataSource.class);
		hds.validate();
		log.info("Connected to DB: {}", hds.getJdbcUrl());
		log.info("Pool Properties: MaximumPoolSize={}, MinimumIdle={}, IdleTimeout={}",
				hds.getMaximumPoolSize(), hds.getMinimumIdle(), hds.getIdleTimeout());
	}
	
	// https://stackoverflow.com/questions/42506629/spring-boot-multiple-datasources
	@Bean(DATASOURCE_NAME)
	@ConfigurationProperties(prefix = "datasource.hikari")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

    @Bean(JDBC_TEMPLATE_NAME)
    public JdbcTemplate jdbcTemplate(@Qualifier(DATASOURCE_NAME) DataSource dataSource) {
        var jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        jdbcTemplate.setFetchSize(1000);
        return jdbcTemplate;
    }
    
    @Bean(NAMED_PARAMETER_JDBC_TEMPLATE_NAME)
    public NamedParameterJdbcTemplate namedJdbcTemplate(@Qualifier(JDBC_TEMPLATE_NAME) JdbcTemplate jdbcTemplate) {
    	return new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
