package com.cooperay.cop_service_admin;

import java.util.HashMap;
import java.util.Map;

import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;
import com.cooperay.service.admin.biz.UserBiz;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class UserTest {
	//@Autowired
	private UserBiz userBiz;
	

	//@Test
	public void listPageTest(){
		PageParam pageParam = new PageParam(1,2);
		Map<String, Object> map = new HashMap<String,Object>();
		//map.put("userName", "cest");
		PageBean pageBean = userBiz.listPage(pageParam, map);
		System.out.println(pageBean.getPageCount());
	}
}
