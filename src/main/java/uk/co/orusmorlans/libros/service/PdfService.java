package uk.co.orusmorlans.libros.service;

import java.util.Map;

import org.thymeleaf.web.IWebExchange;

public interface PdfService {
	void generatePdfFile(String templateName, String pdfFile, IWebExchange webExchange, Map<String,Object> data);
}
