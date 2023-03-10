package com.example.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@EnableJms
public class ClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);
	}

	@Bean
	public JmsTemplate jmsTemplate(jakarta.jms.ConnectionFactory connectionFactory)
	{
		JmsTemplate template=new JmsTemplate();
		template.setConnectionFactory(connectionFactory);
		return template;

	}

}
