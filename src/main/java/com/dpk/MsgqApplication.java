package com.dpk;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.dpk.services.SearchService;

@EnableRabbit
@SpringBootApplication
public class MsgqApplication extends SpringBootServletInitializer {
	private static final Logger log = LoggerFactory.getLogger(MsgqApplication.class);

	@Autowired
	SearchService searchService;

	public static void main(String[] args) {
		SpringApplication.run(MsgqApplication.class, args);
	}

	@PostConstruct
	public void AppStartUp() throws IOException {
		log.info("Initializing Mapping for Elasticsearch");
		try {
			if (!HttpStatus.OK.equals(searchService.getMappingStatus())) {
				searchService.setMapping();
			}
		} catch (Exception e) {
			searchService.setMapping();
		}
	}

	@Bean(name = "restClient")
	public RestTemplate getRestClient() {
		RestTemplate restClient = new RestTemplate();
		restClient.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restClient;
	}

//	@Autowired
//	private ApplicationConfigReader applicationConfig;
//	
//	public ApplicationConfigReader getApplicationConfig() {
//		return applicationConfig;
//	}
//	
//	public void setApplicationConfig(ApplicationConfigReader applicationConfig) {
//		this.applicationConfig = applicationConfig;
//	}
//	
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(MsgqApplication.class);
//	}
//	
//	/* This bean is to read the properties file configs */	
//	@Bean
//	public ApplicationConfigReader applicationConfig() {
//		return new ApplicationConfigReader();
//	}
//	
//	/* Creating a bean for the Message queue Exchange */
//	@Bean
//	public TopicExchange getApp1Exchange() {
//		return new TopicExchange(getApplicationConfig().getApp1Exchange());
//	}
//
//	/* Creating a bean for the Message queue */
//	@Bean
//	public Queue getApp1Queue() {
//		return new Queue(getApplicationConfig().getApp1Queue());
//	}
//	
//	/* Binding between Exchange and Queue using routing key */
//	@Bean
//	public Binding declareBindingApp1() {
//		return BindingBuilder.bind(getApp1Queue()).to(getApp1Exchange()).with(getApplicationConfig().getApp1RoutingKey());
//	}
//	
//	/* Creating a bean for the Message queue Exchange */
//	@Bean
//	public TopicExchange getApp2Exchange() {
//		return new TopicExchange(getApplicationConfig().getApp2Exchange());
//	}
//
//	/* Creating a bean for the Message queue */
//	@Bean
//	public Queue getApp2Queue() {
//		return new Queue(getApplicationConfig().getApp2Queue());
//	}
//	
//	/* Binding between Exchange and Queue using routing key */
//	@Bean
//	public Binding declareBindingApp2() {
//		return BindingBuilder.bind(getApp2Queue()).to(getApp2Exchange()).with(getApplicationConfig().getApp2RoutingKey());
//	}
//
//	/* Bean for rabbitTemplate */
//	@Bean
//	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//		return rabbitTemplate;
//	}
//
//	@Bean
//	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//		return new Jackson2JsonMessageConverter();
//	}
//
//	@Bean
//	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
//		return new MappingJackson2MessageConverter();
//	}
//	
//	@Bean
//	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
//		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
//		factory.setMessageConverter(consumerJackson2MessageConverter());
//		return factory;
//	}
//
//	@Override
//	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
//		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
//	}
}
