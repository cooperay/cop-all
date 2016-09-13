package com.cooperay.service.admin.biz;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class MessageBiz implements MessageListener {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(String jsonMessage){
		
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					jmsTemplate.send(new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							Message message = session.createTextMessage(jsonMessage);
							Destination tempDest = session.createTemporaryQueue();
							MessageConsumer responseConsumer = session.createConsumer(tempDest);
							
							message.setJMSReplyTo(tempDest);
							message.setJMSCorrelationID(UUID.randomUUID().toString());
							responseConsumer.setMessageListener(MessageBiz.this);
							// TODO Auto-generated method stub
							return message;
						}
					});
				}
			});
			thread.start();
	}

	@Override
	public void onMessage(Message message) {
		System.out.println("response " + message);
	}
	
	
}
