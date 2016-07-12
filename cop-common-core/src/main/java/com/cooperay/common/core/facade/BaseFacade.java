package com.cooperay.common.core.facade;

/**
 * 
* @描述: 服务借口基类 
* @时间: 2016年5月26日 下午4:55:17 
* @作者：李阳
* @版本：V1.0.0 
*
 */
public interface BaseFacade<T> {

	/**
	 * 
	 * @作者：李阳
	 * @时间：2016年5月26日 下午4:56:08
	 * @描述：保存并返回受影响行数
	 */
	Long save(T record);
	
	/**
	 * 
	 * @作者：李阳
	 * @时间：2016年5月26日 下午4:58:47
	 * @描述：根据id删除并返回受影响行数
	 */
	Long del(Long id);
	
	/**
	 * 
	 * @作者：李阳
	 * @时间：2016年5月26日 下午4:57:32
	 * @描述：修改并返回受影响行数
	 */
	Long update(T record);
	
	/**
	 * 
	 * @作者：李阳
	 * @时间：2016年5月26日 下午4:59:39
	 * @描述：根据id查询一条记录
	 */
	T getById(Long id);
	
	
}
