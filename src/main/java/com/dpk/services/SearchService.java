package com.dpk.services;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.http.HttpStatus;

import com.dpk.models.Claim;

public interface SearchService {
	public String receiveMessage(Message message) throws IOException;
	
	public void getUserClaimDetails();

	public void setMapping() throws IOException;

	public HttpStatus getMappingStatus();

	public String createClaim(Claim claimBody, String id);

	public void getAll();
	
	// Mapping data from Rabbitmq to models
	public void sendToClaimDetails(String message);
	
	public void sendToClaimList(String message);
}
