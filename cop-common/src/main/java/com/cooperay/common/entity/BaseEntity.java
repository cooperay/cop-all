package com.cooperay.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @描述:基础实体类，包含各实体公用属性  
* @时间: 2016年5月25日 下午3:33:02 
* @作者：李阳
* @版本：V1.0.0 
*
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer version = 0;
	/**
	 * 创建时间
	 */
	protected Date createTime = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
