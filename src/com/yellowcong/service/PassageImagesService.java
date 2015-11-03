package com.yellowcong.service;

import java.util.List;

import com.yellowcong.model.Pager;
import com.yellowcong.model.PassageImages;
import com.yellowcong.model.Summarys;

/**
 * 建立图片的方法
 * @author yellowcong
 * @createDate 2015年10月18日
 *
 */
public interface PassageImagesService {
	/**
	 * 添加一条数据
	 * @param imgs
	 */
	public PassageImages add(PassageImages imgs);
	/**
	 * 添加多条数据
	 * @param imgs
	 */
	public void adds(List<PassageImages> imgs);
	
	/**
	 * 分页获取数据
	 * @return
	 */
	public Pager<PassageImages> loadByPager();
	/**
	 * 开启多线程来搞
	 * @param num 这个是启动的线程数目
	 */
	public void startThread(int num);
}
