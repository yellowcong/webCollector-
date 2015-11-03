package com.yellowcong.dao.impl;

import org.springframework.stereotype.Repository;

import com.yellowcong.dao.TopicDao;
import com.yellowcong.model.Topic;

@Repository("topicDao")
public class TopicDaoImpl extends BaseDaoImpl<Topic> implements TopicDao{
	@Override
	public Topic add(Topic t) {
		Object obj = super.getSession().createQuery("select new Topic(id) from Topic where title = ?").setParameter(0, t.getTitle()).uniqueResult();
		if(obj != null){
			return null;
		}
		return super.add(t);
	}
}
