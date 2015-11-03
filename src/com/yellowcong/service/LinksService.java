package com.yellowcong.service;

import java.util.List;

import com.yellowcong.model.Links;

	
/**
 * 定义我们的Links对象的操作
 * @author yellowcong
 *
 */
public interface LinksService {
	/**
	 * 添加一条数据
	 * @param links
	 * @return
	 */
	public Links add(Links links);
	/**
	 * 添加多条
	 * @param links
	 */
	public void adds(List<Links> links);
	/**
	 * 计算passage的长度
	 * @return
	 */
	public int countPsgs();
}
