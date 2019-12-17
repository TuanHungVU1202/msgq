package com.dpk.services;

import java.io.IOException;

import org.springframework.amqp.core.Message;

public interface MessageService {
	public void receiveMessage(Message message) throws IOException;
	
	public void setMapping() throws IOException;
}
