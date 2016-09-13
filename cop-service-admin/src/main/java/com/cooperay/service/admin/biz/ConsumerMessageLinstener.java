package com.cooperay.service.admin.biz;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SessionAwareMessageListener;

import com.alibaba.fastjson.JSON;
import com.cooperay.facade.admin.entity.User;

public class ConsumerMessageLinstener implements SessionAwareMessageListener<Message> {

	@Autowired
	private JmsTemplate responseTemplate;
	
	@Autowired
	private UserBiz userBiz;
	
	@Override
	public synchronized void onMessage(Message message, Session session) throws JMSException {
		ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
		final String ms = msg.getText();
		User user = JSON.parseObject(ms, User.class);
		if(user!=null){
			user.setIsEnable(true);
			userBiz.update(user);
		}
		responseTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message response = session.createTextMessage("Server response"+message);
				response.setJMSCorrelationID(message.getJMSCorrelationID());
				return message;
			}
		});
		
		if(ms.endsWith("1"))
		throw new RuntimeException();
	}

}
