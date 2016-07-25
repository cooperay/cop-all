package com.cooperay.cop_service_admin;

import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.service.admin.biz.UserBiz;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
	@Autowired
	private UserBiz userBiz;
	

	@Test
	public void listPageTest(){
		PageParam pageParam = new PageParam(1,2);
		Map<String, Object> map = new HashMap<String,Object>();
		//map.put("userName", "cest");
		PageBean pageBean = userBiz.listPage(pageParam, map);
		
		System.out.println(pageBean.getPageCount());
	}
	
	@Test
	public void test1(){
		
		User user = userBiz.getById(1);
		user.setUserName("新的名字");
		userBiz.update(user);
		
	}
	

	
	
	
	
	
}
