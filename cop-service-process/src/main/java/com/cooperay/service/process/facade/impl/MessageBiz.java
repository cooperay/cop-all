package com.cooperay.service.process.facade.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class MessageBiz {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(String jsonMessage){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
			}
		}).start();
		
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage(jsonMessage);
			}
		});
	}
	
	
}
