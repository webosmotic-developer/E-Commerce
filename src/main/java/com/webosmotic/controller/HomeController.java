package com.webosmotic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping(path = {"/",  "/oauth2Redirect"})
	public String index() {
		return "index.html";
	}
}
