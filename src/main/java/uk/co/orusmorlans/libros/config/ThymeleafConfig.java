package uk.co.orusmorlans.libros.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import uk.co.orusmorlans.libros.service.PdfService;
import uk.co.orusmorlans.libros.service.PdfServiceImpl;

@Configuration
public class ThymeleafConfig {
	
//	public static final String SPRING_RESOLVER_NAME = "springResourceTemplateResolver"; 
//	public static final String SPRING_ENGINE_NAME   = "springTemplateEngine"; 
//	
//	@Bean(name = SPRING_RESOLVER_NAME)
//	@Description("Thymeleaf Spring Template Resolver")
//	public SpringResourceTemplateResolver springResourceTemplateResolver() {
//		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//		templateResolver.setPrefix("classpath:/templates/");
//	    templateResolver.setSuffix(".html");
//	    templateResolver.setTemplateMode(TemplateMode.HTML);
//	    templateResolver.setCharacterEncoding("UTF-8");
//		return templateResolver;
//	}
//		
//	@Bean(name = SPRING_ENGINE_NAME)
//	@Description("Thymeleaf Spring Template Engine")
//	public SpringTemplateEngine springTemplateEngine(final @Qualifier(SPRING_RESOLVER_NAME) SpringResourceTemplateResolver springResourceTemplateResolver) {
//		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//		templateEngine.setTemplateResolver(springResourceTemplateResolver);
//		return templateEngine;
//	}
	
  
	
	@Bean
	@Description("Spring Message Resolver")
	public ResourceBundleMessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasename("messages");
	    return messageSource;
	}
	
	@Bean PdfService pdfService() {
		PdfServiceImpl pdfService = new PdfServiceImpl();
		return pdfService;
	}
}
