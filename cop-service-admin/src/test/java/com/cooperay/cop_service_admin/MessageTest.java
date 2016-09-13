package com.cooperay.cop_service_admin;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageTest {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Test
	public void sendTets(){
		
	
		for (int i = 0; i < 10; i++) {
			System.out.println("thread1---"+i);
			final int j = i;
			jmsTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					// TODO Auto-generated method stub
					return session.createTextMessage("test message"+j);
				}
			});
		}
		
		
		
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				
				for (int i = 0; i < 100000; i++) {
					System.out.println("thread2---"+i);
					jmsTemplate.send(new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							// TODO Auto-generated method stub
							return session.createTextMessage("test message");
						}
					});
				}
			}
		}).start();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				for (int i = 0; i < 100000; i++) {
					System.out.println("thread3---"+i);
					jmsTemplate.send(new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							// TODO Auto-generated method stub
							return session.createTextMessage("test message");
						}
					});
				}
			}
		}).start();*/
		
		
		
		
	}
	
	
}
