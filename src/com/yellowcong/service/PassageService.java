package com.yellowcong.service;

import com.yellowcong.model.Passage;

/**
 * passage的服务
 * @author yellowcong
 *
 */
public interface PassageService {
	/**
	 * 添加文章
	 * @param psg
	 */
	public Passage add(Passage psg);
	/**
	 * 更新文章
	 */
	public void updatePsg();
	
	/**
	 * 多线程
	 */
	public void startThread();
}
