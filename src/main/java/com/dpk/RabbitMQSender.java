package com.dpk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

/**
 * Message sender to send message to queue using exchange.
 * 
 * @author deepak.af.kumar
 *
 */

@Service
@EnableScheduling
public class RabbitMQSender {
	@Value("${app.exchange.name}")
	private String appExChangeName;

	@Value("${app.queue.name}")
	private String appQueueName;

	@Value("${app.routing.key}")
	private String appRoutingKey;

	private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);

	private final RabbitTemplate rabbitTemplate;

	public RabbitMQSender(final RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(String exchange, String routingKey, Object data) {
		log.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
		rabbitTemplate.convertAndSend(exchange, routingKey, data);
		log.info("The message has been sent to the queue.");
	}

//	public void sendMessagePublicCorpus(RabbitTemplate rabbitTemplate, String exchange, String routingKey, String url, Object data) {
//		log.info("Sending message to the queue using routingKey {}.Url={}. Message= {}", routingKey,url, data);
//		rabbitTemplate.convertAndSend(exchange, routingKey, data);
//		log.info("The message has been sent to the queue.");
//	}
}
