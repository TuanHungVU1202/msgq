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
	String url = "http://localhost:9600/claim/details/1";

	@Override
	public void receiveMessage(Message message) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
//		Claim claimDetails = new Claim();

		Mapper byteToObj = new Mapper();

		String dataToSend = byteToObj.byteToObject(message.getBody(), String.class);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(dataToSend, httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
	}
}
