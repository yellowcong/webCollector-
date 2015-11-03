package com.yellowcong.service;

import java.util.List;

import com.yellowcong.model.ProxyHttps;


/**
 * Proxy的使用方法
 * @author yellowcong
 *
 */
public interface ProxyHttpsService{
	/**
	 * 添加一条数据
	 * @param proxy
	 */
	public void add(ProxyHttps proxy);
	/**
	 * 添加多条数据
	 * @param proxys
	 */
	public void adds(List<ProxyHttps> proxys);
	/**
	 * 更新网站索引
	 * http://www.xicidaili.com/ 所有的索引
	 * @return
	 */
	public void updateAll();
	/**
	 * 更行网站最近的索引 最近的10条数据
	 */
	public void updateLatest();
}
