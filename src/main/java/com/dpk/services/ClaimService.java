package com.dpk.services;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.dpk.models.Claim;

public interface ClaimService {
	public void getUserClaimDetails();
	
	public void setMapping() throws IOException;
	
	public HttpStatus getMappingStatus();
	
	public String createClaim(Claim claimBody, String id);
}
