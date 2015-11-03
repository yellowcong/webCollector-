package com.yellowcong.service;

import java.util.List;

import com.yellowcong.model.Topic;

/**
 * 定义topic的service
 * @author yellowcong
 *
 */
public interface TopicService {
	/**
	 * 添加topic 不能用重复的
	 * @param topic
	 */
	public void add(Topic topic);
	
	/**
	 * 添加topic 不能用重复的
	 * @param topic
	 */
	public void adds(List<Topic> topic);
	
	/**
	 * 获取条数
	 * @return
	 */
	public int count();
	/**
	 * 更新推酷的 topics的数量
	 */
	public void updateTopics();
	/**
	 * 获取Topic来更行我们的 Summary
	 * @return
	 */
	public List<Topic> listForUpdate();
}
