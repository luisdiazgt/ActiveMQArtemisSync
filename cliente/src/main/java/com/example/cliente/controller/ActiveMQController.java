package com.example.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cliente.model.MensajeActiveMQ;
import com.example.cliente.service.SenderService;

@RestController
@RequestMapping(value = "/activemq")
public class ActiveMQController {

    @Autowired
	SenderService senderService;

    @PostMapping(value = "/text")
	public ResponseEntity<?> sendMessageToKafkaTopic(@RequestBody String message) {
		String respuesta = this.senderService.enviaColaSincrona(message);
		return ResponseEntity.ok().body(respuesta);
	}

	@PostMapping(value = "/entity")
	public ResponseEntity<?> sendMessageToKafkaTopicEntity(@RequestBody MensajeActiveMQ message) {
		String respuesta = this.senderService.enviaColaSincrona(message.getMensaje());
		return ResponseEntity.ok().body(respuesta);
	}
    
}
