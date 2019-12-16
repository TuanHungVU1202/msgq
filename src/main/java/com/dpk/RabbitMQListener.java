package com.dpk;

import java.net.URL;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dpk.config.ApplicationConfigReader;
import com.dpk.mapper.Mapper;
import com.dpk.models.Policy;
import com.google.gson.Gson;

/**
 * Message Listener for RabbitMQ
 * 
 * @author deepak.af.kumar
 *
 */

@Service
public class RabbitMQListener {
	private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

	@Autowired
	ApplicationConfigReader applicationConfigReader;

	String URL = "http://localhost:9200/claim/details/1";

	@RabbitListener(queues = "${app.queue.name}")
	public void receiveMessage(Message message) {
		log.info("Received message: {} from app queue.", message);

		try {
			RestTemplate restTemplate = new RestTemplate();
			Policy policyDetails = new Policy();

			Mapper byteToObj = new Mapper();

			String dataToSend = byteToObj.byteToObject(message.getBody(), String.class);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			log.info("here we go the received message " + dataToSend);
			HttpEntity<String> requestEntity = new HttpEntity<String>(dataToSend, headers);
			ResponseEntity<String> exchange = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);

			log.info("Making API call");
			log.info("Process exiting after API call");
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				log.info("Delay...");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					log.info("Throwing exception so that message will be requed in the queue");
					throw new RuntimeException();
				}
			} else {
				throw new AmqpRejectAndDontRequeueException(ex);
			}

		} catch (Exception e) {
			log.error("Internal server error occured in API call. {}", e);
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
}
