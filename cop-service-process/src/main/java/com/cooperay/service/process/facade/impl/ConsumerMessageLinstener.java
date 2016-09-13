package com.cooperay.service.process.facade.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.listener.SessionAwareMessageListener;

public class ConsumerMessageLinstener implements SessionAwareMessageListener<Message> {

	@Override
	public synchronized void onMessage(Message message, Session session) throws JMSException {
		ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
		final String ms = msg.getText();
		System.out.println("======>"+ms);
		if(ms.endsWith("1"))
		throw new RuntimeException();
	}

}
