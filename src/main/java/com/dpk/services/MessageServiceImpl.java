package com.dpk.services;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.amqp.core.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dpk.mapper.Mapper;

@Service
public class MessageServiceImpl implements MessageService {
	String URL = "http://localhost:9200/claim/details/1";

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
}
