package com.kbt.tech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
	
	@GetMapping("/home")
	public String home() {
		return "<h1>Hi..Kingshuk</h1>";
	}

}
