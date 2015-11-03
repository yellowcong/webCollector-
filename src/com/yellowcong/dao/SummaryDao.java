package com.yellowcong.dao;

import com.yellowcong.model.Summarys;

public interface SummaryDao extends BaseDao<Summarys>{
	
	/**
	 * 获取插入的数据条数
	 * @return
	 */
	public int count();
}
