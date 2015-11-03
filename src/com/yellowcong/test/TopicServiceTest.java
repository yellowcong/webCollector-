package com.yellowcong.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.model.Topic;
import com.yellowcong.service.TopicService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class TopicServiceTest {
		
	private TopicService topicService;
	
	@Resource(name="topicService")
	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}


	@Test
	public void testAdd(){
		List<Topic> topcis = com.yellowcong.Crawler.TuikuCrawler.getTopics();
		this.topicService.adds(topcis);
		
	}
	
	@Test
	public void testCount(){
		int count = this.topicService.count();
		System.out.println("条数\t"+count);
	}
}
