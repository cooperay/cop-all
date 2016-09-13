package com.cooperay.service.admin.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import com.cooperay.common.core.dao.BaseDaoImpl;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.service.admin.dao.ResourceDao;
import com.sun.tools.corba.se.idl.constExpr.NotEqual;

@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Resource> implements ResourceDao {

	@Override
	public List<Resource> getAllUserResources(Long userId) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		List<Object> list = listBy(paramMap, "listAllUserResourceBy");
		if(list == null)
			return null;
		List<Resource> rList = new ArrayList<>();
		for (Object object : list) {
			rList.add((Resource)object);
		}
		return rList;
	}

}
