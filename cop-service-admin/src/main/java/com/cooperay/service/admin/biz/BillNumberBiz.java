package com.cooperay.service.admin.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.core.biz.BaseBizImpl;
import com.cooperay.common.core.dao.BaseDao;
import com.cooperay.facade.admin.entity.BillNumber;
import com.cooperay.service.admin.dao.BillNumberDao;

@Service
public class BillNumberBiz extends BaseBizImpl<BillNumber> {
	
	@Autowired
	private BillNumberDao billNumberDao;
	
	@Override
	protected BaseDao<BillNumber> getDao() {
		return billNumberDao;
	}

}
