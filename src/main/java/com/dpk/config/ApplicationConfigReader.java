package com.dpk.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@EnableRabbit
@Configuration
//@PropertySource("classpath:application.properties")
public class ApplicationConfigReader implements RabbitListenerConfigurer {
	
	public static final String EXCHANGE_NAME = "appInsurtechExchange";
	public static final String ROUTING_KEY = "messages.key";
	public static final String QUEUE_NAME = "appInsurtechQueue";
	
	@Value("${app.exchange.name}")
	private String appExChangeName;
	
	@Value("${app.queue.name}")
	private String appQueueName;
	
	@Value("${app.routing.key}")
	private String appRoutingKey;
	
//	@Value("${app1.exchange.name}")
//	private String app1Exchange;
//	
//	@Value("${app1.queue.name}")
//	private String app1Queue;
//	
//	@Value("${app1.routing.key}")
//	private String app1RoutingKey;

//	@Value("${app2.exchange.name}")
//	private String app2Exchange;
//	
//	@Value("${app2.queue.name}")
//	private String app2Queue;
//	
//	@Value("${app2.routing.key}")
//	private String app2RoutingKey;
	@Bean
	public TopicExchange appExchange() {
		return new TopicExchange(appExChangeName);
	}
	
	@Bean
	public Queue appQueue() {
		return new Queue(appQueueName);
	}
	
	@Bean
	public Binding declareBindingApp() {
		return BindingBuilder.bind(appQueue()).to(appExchange()).with(appRoutingKey);
	}
	
//	@Bean
//	public StatefulRetryOperationsInterceptor interceptor() {
//		return RetryInterceptorBuilder.stateful()
//				.maxAttempts(1)
//				.backOffOptions(1, 1, 1)
//				.build();
//	}
	
//	@Bean(name = "rabbitListenerContainerFactory")
//    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
//            SimpleRabbitListenerContainerFactoryConfigurer configurer,
//            ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        configurer.configure(factory, connectionFactory);
//        factory.setConnectionFactory(connectionFactory());
//        factory.setMissingQueuesFatal(false);
//        factory.setReceiveTimeout(10000L);
//        
//        factory.setChannelTransacted(true);
////        BackOff recoveryBackOff = new FixedBackOff(2000, 1);
////        factory.setRecoveryBackOff(recoveryBackOff);
//        return factory;
//    }
	
//	@Bean
//	public ConnectionFactory connectionFactory() {
//		com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();
//	    connectionFactory.setConnectionTimeout(connectionTimeout);
////	    connectionFactory.setHost(rabbitHost);
////	    connectionFactory.setPort(rabbitPort);
//	    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
//	    return cachingConnectionFactory;
//	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}
	
//	@Bean
//	public RabbitTemplate rabbitTemplate() {
//		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
//		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//		return rabbitTemplate;
//	}
	
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}
	
	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
	
}
