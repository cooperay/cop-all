package com.cooperay.service.admin.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.core.biz.BaseBizImpl;
import com.cooperay.common.core.dao.BaseDao;
import com.cooperay.facade.admin.entity.Group;
import com.cooperay.service.admin.dao.GroupDao;

@Service
public class GroupBiz extends BaseBizImpl<Group> {

	@Autowired
	private GroupDao groupDao;
	
	@Override
	protected BaseDao<Group> getDao() {
		return groupDao;
	}

}
