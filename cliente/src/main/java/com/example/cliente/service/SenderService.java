package com.example.cliente.service;

import org.springframework.jms.core.JmsTemplate;

import java.text.MessageFormat;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.jms.ObjectMessage;
import lombok.extern.java.Log;

@Log
@Service
public class SenderService {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${jms.colaTest}")
    String jmsColaTest;

    @Value("${jms.colaTestRespuesta}")
    String jmsColaTestRespuesta;

    private static final String JMSCORR="JMSCorrelationID = '";

    public String enviaColaSincrona(String request){

        // Enviar Cola con id único
        String uuid=UUID.randomUUID().toString();
        log.info(MessageFormat.format("Cliente - mensaje enviado. Mensaje: {0}. ID: {1}", request,uuid));

        jmsTemplate.send(jmsColaTest, session->{
                ObjectMessage textMessage=session.createObjectMessage(request);
                textMessage.setJMSCorrelationID(uuid);
                return textMessage;
        });

        // Leer Respuesta en cola
        jmsTemplate.setReceiveTimeout(10000); //Tiempo de espera máximo del mensaje 10 segundos
        String respuesta = (String)jmsTemplate.receiveSelectedAndConvert(jmsColaTestRespuesta,JMSCORR+uuid+"'");
        // String respuesta = (String)jmsTemplate.receiveSelectedAndConvert(jmsColaTest,JMSCORR+uuid+"'");
        log.info(MessageFormat.format("Cliente - mensaje leido. Mensaje: {0}. ID: {1}", respuesta,uuid));

        return respuesta;
    }
    
}
