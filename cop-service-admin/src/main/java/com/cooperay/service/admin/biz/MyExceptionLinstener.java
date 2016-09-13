package com.cooperay.service.admin.biz;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

public class MyExceptionLinstener implements ExceptionListener {

	@Override
	public void onException(JMSException exception) {
		System.out.println("jms exception ");

	}

}
