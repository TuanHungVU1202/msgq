package com.dpk.services;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.dpk.models.Claim;
import com.dpk.models.ClaimDetails;
import com.dpk.models.ClaimList;

public interface SearchService {
	public String receiveMessage(Message message) throws IOException;

	public String getClaimList(String id);
	
	public String getClaimDetails(String id);

	public void setMapping() throws IOException;

	public HttpStatus getMappingStatus();

//	public String createClaim(Claim claimBody, String id);

	public String getAllList();
	
	public String getAllDetails();

	public String searchClaimList(String dataSearch);
	
	public String sortDate(String order);

	// Mapping data from RabbitMQ to models

	public ClaimDetails mapToClaimDetails(JSONObject json);

	public ClaimList mapToClaimList(JSONObject json);
}
