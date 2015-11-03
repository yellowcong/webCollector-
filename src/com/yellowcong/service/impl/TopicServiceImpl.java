package com.yellowcong.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yellowcong.dao.TopicDao;
import com.yellowcong.model.Topic;
import com.yellowcong.service.TopicService;

@Service("topicService")
public class TopicServiceImpl implements TopicService{
	
	private TopicDao topicDao;
	
	@Resource(name="topicDao")
	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	@Override
	public void add(Topic topic) {
		topicDao.add(topic);
	}

	@Override
	public void adds(List<Topic> topics) {
		int strat = this.topicDao.count();
		for(Topic obj:topics){
			this.topicDao.add(obj);
		}
		int end = this.topicDao.count();
		System.out.println("更新topic条数"+(end-strat));
	}

	@Override
	public int count() {
		return this.topicDao.count();
	}

	@Override
	public void updateTopics() {
		List<Topic> topcis = com.yellowcong.Crawler.TuikuCrawler.getTopics();
		this.adds(topcis);
	}

	@Override
	public List<Topic> listForUpdate() {
		return this.topicDao.list("select new Topic(title,url) from Topic");
	}

}
