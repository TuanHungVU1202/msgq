package com.dpk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dpk.models.Claim;
import com.dpk.services.SearchService;

@Controller
@RequestMapping(value = "/claim")
public class SearchController {
	@Autowired
	SearchService claimService;

	@GetMapping(value = "/get-all", headers = "Accept=application/json")
	public ResponseEntity<String> getAll() {
		claimService.getAll();
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@GetMapping(value = "/get/{id}", headers = "Accept=application/json")
	public ResponseEntity<Claim> getUserClaimDetails(@PathVariable String id) {
		claimService.getUserClaimDetails();
		return new ResponseEntity<Claim>(HttpStatus.OK);
	}

	// Creating claim request
	@PostMapping(value = "/add/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> addClaimDetails(@PathVariable String id, @RequestBody Claim claimRequest) {
		String claimJson = claimService.createClaim(claimRequest, id);
		return new ResponseEntity<String>(claimJson, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/search", headers = "Accept=application/json")
	public ResponseEntity<String> searchClaimList(@RequestBody String dataSearch) {
		return new ResponseEntity<String>(HttpStatus.FOUND);
	}
}
