package org.orusmorlans.libros.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(LibrosController.LIBROS_ENDPOINT)
@RequiredArgsConstructor
public class LibrosController {
	
	public static final String LIBROS_ENDPOINT = "/libros";
	
	@GetMapping("")
	public  String home() {
		return "index";
	}
	


}





