package com.yellowcong.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;







import com.yellowcong.model.Passage;
import com.yellowcong.model.Summarys;
import com.yellowcong.model.Topic;
import com.yellowcong.service.PassageService;
import com.yellowcong.service.SummaryService;
import com.yellowcong.service.TopicService;
import com.yellowcong.service.TuikuService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class PassageServiceTest {
	
	private PassageService service;
	private TuikuService tuikuService;
	private SummaryService summaryService;
	private TopicService topicService;
	
	@Resource(name="topicService")
	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}

	@Resource(name="summaryService")
	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}

	@Resource(name="passageService")
	public void setService(PassageService service) {
		this.service = service;
	}

	@Resource(name="tuikuService")
	public void setTuikuService(TuikuService tuikuService) {
		this.tuikuService = tuikuService;
	}

	@Test
	public void testAddSum(){
		Summarys sum = new Summarys();
		sum.setChannelSource("xxx");
		this.summaryService.add(sum);
	}
	@Test
	public void testInit(){
		System.out.println("系统初始化");
		for(int i=0;i<=20;i++){
			//System.out.println(i);
			//热门 lang =2 表示英文  lang = 1 表示中文
			//http://www.tuicool.com/ah/1/2?lang=1
			//科技
			//http://www.tuicool.com/ah/101000000/2?lang=1
			//创投
			//http://www.tuicool.com/ah/101040000/1?lang=1
			//数码
			//http://www.tuicool.com/ah/101050000/"+i+"?lang=1"
			//技术
			//http://www.tuicool.com/ah/20/1?lang=1
			//技术便利 有点问题 ，文字切了一半
			//设计
			//http://www.tuicool.com/ah/108000000/1?lang=1
			//营销
			//http://www.tuicool.com/ah/114000000/1?lang=1

			List<Summarys> sums = tuikuService.loadSummarys("http://www.tuicool.com/ah/0/"+i+"?lang=1","热门");
			this.summaryService.adds(sums);
		}
		
		
		//添加推酷
		/*List<Summarys> sums = 
		tuikuService.loadSummarys("http://www.tuicool.com/ah/0?lang=1");
		
		this.summaryService.adds(sums);*/
	}
	@Test
	public void testUpdatexx(){
		//lnag 1 中文， lang 2 英文
		//http://www.tuicool.com/topics/11080009?st=0&lang=0&pn=1
		//http://www.tuicool.com/topics/11080009?st=0&lang=1&pn=2
		//http://www.tuicool.com/topics/11080009
		
		// 0 开始    就10 页 
		//http://www.tuicool.com/topics/11080009?st=0&lang=0&pn=10 
		List<Topic> topics = this.topicService.listForUpdate();
		for(Topic topic :topics){
			
			//http://www.tuicool.com/topics/11000077?st=0&lang=0&pn=10
			
			String title = topic.getTitle();
			String url = topic.getUrl();
			System.out.println("____________________________"+title+"____________________________________");
			System.out.println("____________________________"+url+"____________________________________");
			//中文
			for(int i=0;i<=10;i++){
				///topics/10050109
				List<Summarys> sums = tuikuService.loadSummarys("http://www.tuicool.com"+url+"?st=0&lang=1&pn="+i,title);
				this.summaryService.adds(sums);
			}
			
			//英文
			for(int i=0;i<=10;i++){
				///topics/10050109
				List<Summarys> sums = tuikuService.loadSummarys("http://www.tuicool.com"+url+"?st=0&lang=2&pn="+i,title);
				this.summaryService.adds(sums);
			}
			
		}
		
		
	}
	@Test
	public void testUpdateIndex(){
		int count = this.tuikuService.updateIndex();
		System.out.println("更新记录条数:\t"+count);
	}
	
	@Test
	public void testCount(){
		int count = this.summaryService.conunt();
		System.out.println("记录条数:\t"+count);
	}
	
	@Test
	public void testLoadPsg(){
		//Passage psg = tuikuService.loadPsg("http://www.tuicool.com/articles/7FbmQzU");
//		System.out.println(psg.getTitle());
		//this.service.add(psg);
	}
}
