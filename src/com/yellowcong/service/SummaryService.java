package com.yellowcong.service;

import java.util.List;

import com.yellowcong.model.Pager;
import com.yellowcong.model.Summarys;

/**
 * 添加数据
 * @author yellowcong
 *
 */
public interface SummaryService {
	/**
	 * 添加评论
	 * @param summary
	 */
	public Summarys add(Summarys summary);
	/**
	 * 添加多个Summarys
	 * @param summarys
	 */
	public void adds(List<Summarys> summarys);
	/**
	 * 获取添加条数
	 * @return
	 */
	public int conunt();
	
	/**
	 * 更细topic栏目中的所有数据
	 */
	public void updateAll();
	
	/**
	 * 通过分页的方法来获取我们的数据
	 * @return
	 */
	public Pager<Summarys> loadByPager();
	
}
