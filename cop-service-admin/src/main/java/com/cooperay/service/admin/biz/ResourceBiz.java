package com.cooperay.service.admin.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.core.biz.BaseBizImpl;
import com.cooperay.common.core.dao.BaseDao;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.service.admin.dao.ResourceDao;

@Service
public class ResourceBiz extends BaseBizImpl<Resource> {

	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	protected BaseDao<Resource> getDao() {
		return resourceDao;
	}

}
