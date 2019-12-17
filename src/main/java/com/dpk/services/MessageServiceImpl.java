package com.dpk.services;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.amqp.core.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dpk.mapper.Mapper;

@Service
public class MessageServiceImpl implements MessageService {
	String URL = "http://localhost:9600/claim/details/1";
	String URL_SET_MAPPING = "http://localhost:9600/claim";
	String URL_CHECK_STATUS = "http://localhost:9600/claim/details/_mapping";

	@Override
	public void receiveMessage(Message message) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
//		Claim claimDetails = new Claim();

		Mapper byteToObj = new Mapper();

		String dataToSend = byteToObj.byteToObject(message.getBody(), String.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(dataToSend, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);
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
}
