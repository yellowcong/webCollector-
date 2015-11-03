package com.yellowcong.service;

import java.util.List;

import com.yellowcong.dto.PassageImageDto;
import com.yellowcong.model.Summarys;

/**
 * 推酷的方法接口
 * @author yellowcong
 *
 */
public interface TuikuService {
	
	/**
	 * 通过 URL来获取 推酷里面的文章数据
	 * @param url
	 * @return PassageImageDto  里面存储了图片和 文章信息
	 */
	public PassageImageDto loadPsg(String url);
	
	/**
	 * 根据 一个URL 获取文章里面的数据
	 * @param url
	 * @param channelNow 现在的栏目
	 * @return  List<Summarys> 
	 */ 
	public List<Summarys> loadSummarys(String url,String channelNow);
	
	/**
	 * 更新热门文章索引
	 * 通过这个方法来更新索引 可以启动多线程来 更新索引 
	 * 获取到更新的数据条数
	 * @return
	 */
	public int updateIndex();
	
	
}
