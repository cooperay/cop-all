package com.cooperay.common.facade;

import java.util.List;
import java.util.Map;

import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;

public interface BaseFacade<T> {

	T getById(long id);
	
	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap);

	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap, String sqlId);

	List<T> listBy(Map<String, Object> paramMap);

	List<Object> listBy(Map<String, Object> paramMap, String sqlId);
	
	T getBy(Map<String, Object> paramMap);

	Object getBy(Map<String, Object> paramMap, String sqlId);

	String getSeqNextValue(String seqName);
	
}
