package com.cooperay.service.admin.dao;

import java.util.List;

import com.cooperay.common.core.dao.BaseDao;
import com.cooperay.facade.admin.entity.Resource;

public interface ResourceDao extends BaseDao<Resource> {

	List<Resource> getAllUserResources(Long userId);
}
