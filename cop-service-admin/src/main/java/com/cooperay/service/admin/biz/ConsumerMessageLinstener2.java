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
import com.cooperay.facade.admin.entity.User.State;

public class ConsumerMessageLinstener2 implements SessionAwareMessageListener<Message> {

	@Autowired
	private UserBiz userBiz;
	
	@Override
	public synchronized void onMessage(Message message, Session session) throws JMSException {
		ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
		final String ms = msg.getText();
		User user = JSON.parseObject(ms, User.class);
		if(user!=null){
			user.setState(State.WAITPAYMENT);
			userBiz.updateSelective(user);
		}
	}

}
