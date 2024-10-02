package uk.co.orusmorlans.libros.controller;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import uk.co.orusmorlans.libros.service.PdfService;

@Controller
@Slf4j
@RequestMapping(PrintCssController.PRINTCSS_ENDPOINT)
@RequiredArgsConstructor
public class PrintCssController {
	
	public static final String PRINTCSS_ENDPOINT = "/printcss";
	
	@Autowired
	private PdfService pdfService;
	@Autowired
	WebApplicationContext webCtxt;
	
	@GetMapping()
	public  String home() {
		return "printcss";
	}
	
	@GetMapping("/pdf")
	public ResponseEntity<String> pdf(HttpServletRequest request, HttpServletResponse response) {
		final ServletContext servletContext = webCtxt.getServletContext() ;
        final IWebExchange webExchange =
                JakartaServletWebApplication.
                        buildApplication(servletContext).buildExchange(request, response);
		pdfService.generatePdfFile("printcss","printcss.pdf",webExchange,null);
		return ResponseEntity //
				.ok() //
//				.contentType(MediaType.APPLICATION_OCTET_STREAM) //
//				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + filename + "\"") //
//				.body(baos::writeTo);
				.body("ok");
		
	}

}




