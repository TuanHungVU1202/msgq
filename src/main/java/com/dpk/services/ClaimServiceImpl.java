package com.dpk.services;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dpk.models.Claim;
import com.google.gson.Gson;

@Service
public class ClaimServiceImpl implements ClaimService {
	String URL_SET_MAPPING = "http://localhost:9600/claim";
	String URL_CHECK_STATUS = "http://localhost:9600/claim/details/_mapping";
	String URL_SEARCH = "http://localhost:9600/claim/details/_search";

	String idToCreate;

	@Override
	public void getUserClaimDetails() {
//		String url = "http://192.168.19.30:9600/claim/details/1";
//
//		RestTemplate restTemplate = new RestTemplate();
//
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//		Claim claim = new Claim("1", "a", "BMI", "2019-07-01", "2019-12-31", null, 1000000000L, "true", null);
//
//		HttpEntity<Claim> httpEntity = new HttpEntity<>(claim, httpHeaders);
//
//		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
	}

	@Override
	public void setMapping() throws IOException {
		String mapping = "{\r\n" + "    \"mappings\": {\r\n" + "        \"details\": {\r\n"
				+ "            \"properties\": {\r\n" + "                \"policyNumber\": {\r\n"
				+ "                    \"type\": \"keyword\"\r\n" + "                },\r\n"
				+ "                \"policyHolder\": {\r\n" + "                    \"type\": \"keyword\"\r\n"
				+ "                },\r\n" + "                \"insuredObject\": {\r\n"
				+ "                    \"type\": \"nested\"\r\n" + "                },\r\n"
				+ "                \"startDate\": {\r\n" + "                    \"type\": \"date\"\r\n"
				+ "                },\r\n" + "                \"endDate\": {\r\n"
				+ "                    \"type\": \"date\"\r\n" + "                },\r\n"
				+ "                \"productType\": {\r\n" + "                    \"type\": \"text\"\r\n"
				+ "                },\r\n" + "                \"sumInsured\": {\r\n"
				+ "                    \"type\": \"text\"\r\n" + "                },\r\n"
				+ "                \"premium\": {\r\n" + "                    \"type\": \"text\"\r\n"
				+ "                },\r\n" + "                \"paymentDate\": {\r\n"
				+ "                    \"type\": \"date\"\r\n" + "                }\r\n" + "            }\r\n"
				+ "        }\r\n" + "    }\r\n" + "}";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(mapping, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL_SET_MAPPING, HttpMethod.PUT, httpEntity,
				String.class);
	}

	@Override
	public HttpStatus getMappingStatus() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL_CHECK_STATUS, HttpMethod.GET, httpEntity,
				String.class);

		return responseEntity.getStatusCode();
	}

	// Create claim by claim object receiving from Rabbitmq
	@Override
	public String createClaim(Claim claimBody, String id) {
		this.idToCreate = id;
		String URL_CREATE = String.format("http://localhost:9600/claim/details/%s", idToCreate);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		String jsonInString = new Gson().toJson(claimBody);
		HttpEntity<String> httpEntity = new HttpEntity<String>(jsonInString, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL_CREATE, HttpMethod.PUT, httpEntity,
				String.class);

		return jsonInString;
	}

	@Override
	public void getAll() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		ResponseEntity<String> responseEntity = restTemplate.exchange(URL_SEARCH, HttpMethod.GET, httpEntity,
				String.class);
	}
}
