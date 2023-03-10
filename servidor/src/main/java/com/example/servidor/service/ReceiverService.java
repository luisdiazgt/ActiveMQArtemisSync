package com.example.servidor.service;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.ObjectMessage;
import lombok.extern.java.Log;

@Log
@Component
public class ReceiverService {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${jms.colaTestRespuesta}")
    String jmsColaTestRespuesta;
    
    @JmsListener(destination = "${jms.colaTest}")
    public void leerMensaje(ObjectMessage mensaje) throws JMSException{

        String uuid = mensaje.getJMSCorrelationID();
        final String respuesta = (String) mensaje.getObject();

        log.info(MessageFormat.format("Servidor - procesando colaTest. Mensaje: {0}. ID: {1}", respuesta,uuid));

        String respuestaModificada = respuesta + ". Agregando respuesta en servidor.";
        
        log.info(MessageFormat.format("Servidor - respondiendo colaTestRespuesta. Mensaje: {0}. ID: {1}" , respuestaModificada,uuid));

        jmsTemplate.send(jmsColaTestRespuesta, session->{
            ObjectMessage textMessage=session.createObjectMessage(respuestaModificada);
            textMessage.setJMSCorrelationID(uuid);
            return textMessage;
        });

    }

}
