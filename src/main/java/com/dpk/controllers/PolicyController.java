package com.dpk.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dpk.models.Claim;

@Controller
@RequestMapping(value = "/claim")
public class PolicyController {
	@PostMapping(value = "/details/{id}", headers = "Accept=application/json")
	public ResponseEntity<Claim> getUserClaimDetails(@PathVariable String id) {
		Claim claimDetails = new Claim();
		claimDetails.setPolicyNumber("1");
		claimDetails.setPolicyHolder("a");
		claimDetails.setInsuredObject("BMI");
		claimDetails.setStartDate("2019-07-01");
		claimDetails.setEndDate("2019-12-31");
		claimDetails.setProductType(null);
		claimDetails.setInsuredSum(1000000000L);
		claimDetails.setPremium("true");
		claimDetails.setDateOfPayment(null);
		return new ResponseEntity<Claim>(claimDetails, HttpStatus.OK);
	}
}
