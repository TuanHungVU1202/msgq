package com.dpk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.dpk.config.ApplicationConfigReader;

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

	@RabbitListener(queues = "${app.queue.name}")
	public void receiveMessage(Message message) {
		log.info("Received message: {} from app queue.", message);

		try {
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
