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
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.service.admin.biz.DeptBiz;
import com.cooperay.service.admin.biz.UserBiz;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeptTest {
	@Autowired
	private DeptBiz deptBiz;
	

	@Test
	public void listPageTest(){
		PageParam pageParam = new PageParam(1,2);
		Map<String, Object> map = new HashMap<String,Object>();
		//map.put("userName", "cest");
		PageBean pageBean = deptBiz.listPage(pageParam, map);
		
		System.out.println(pageBean.getPageCount());
	}
	
	@Test
	public void test1(){
		
		Department department = new Department();
		department.setDeptName("tets");
		deptBiz.create(department);
		
	}
	

	
	
	
	
	
}
