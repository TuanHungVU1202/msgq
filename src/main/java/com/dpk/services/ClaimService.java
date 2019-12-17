package com.dpk.services;

import java.io.IOException;

import org.springframework.http.HttpStatus;

public interface ClaimService {
	public void getUserClaimDetails();
	
	public void setMapping() throws IOException;
	
	public HttpStatus getMappingStatus();
}
