package com.dpk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dpk.models.Claim;
import com.dpk.services.ClaimService;

@Controller
@RequestMapping(value = "/claim")
public class ClaimController {
	@Autowired
	ClaimService claimService;

	@PostMapping(value = "/details/{id}", headers = "Accept=application/json")
	public ResponseEntity<Claim> getUserClaimDetails(@PathVariable("id") String id) {
		claimService.getUserClaimDetails();
		return new ResponseEntity<Claim>(HttpStatus.OK);
	}
}
