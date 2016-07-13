package com.cooperay.service.admin.facade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;
import com.cooperay.facade.admin.entity.BillNumber;
import com.cooperay.facade.admin.service.BillNumberFacade;
import com.cooperay.service.admin.biz.BillNumberBiz;

@Service
public class BillNumberFacadeImpl implements BillNumberFacade {

	@Autowired
	private BillNumberBiz billNumberBiz;
	
	public BillNumber getById(long id) {
		return billNumberBiz.getById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return billNumberBiz.listPage(pageParam, paramMap);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap, String sqlId) {
		return billNumberBiz.listPage(pageParam, paramMap, sqlId);
	}

	public List<BillNumber> listBy(Map<String, Object> paramMap) {
		return billNumberBiz.listBy(paramMap);
	}

	public List<Object> listBy(Map<String, Object> paramMap, String sqlId) {
		return billNumberBiz.listBy(paramMap, sqlId);
	}

	public BillNumber getBy(Map<String, Object> paramMap) {
		return billNumberBiz.getBy(paramMap);
	}

	public Object getBy(Map<String, Object> paramMap, String sqlId) {
		return billNumberBiz.getBy(paramMap, sqlId);
	}

	public String getSeqNextValue(String seqName) {
		return billNumberBiz.getSeqNextValue(seqName);
	}

}
