package com.dpk.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/policy")
public class PolicyViewController {
	@PostMapping(value = "/search/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> postToEs(@RequestBody String body, @PathVariable String id) throws Exception {
		return null;
	}
}
