package com.dpk.services;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dpk.models.Claim;

@Service
public class ClaimServiceImpl implements ClaimService {
	@Override
	public void getUserClaimDetails() {
		String url = "http://192.168.19.30:9600/claim/details/1";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		Claim claim = new Claim("1", "a", "BMI", "2019-07-01", "2019-12-31", null, 1000000000L, "true", null);

		HttpEntity<Claim> httpEntity = new HttpEntity<>(claim, httpHeaders);

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
	}
}
