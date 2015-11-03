package com.yellowcong.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yellowcong.dao.SummaryDao;
import com.yellowcong.model.Links;
import com.yellowcong.model.Pager;
import com.yellowcong.model.Summarys;
import com.yellowcong.model.SystemContext;
import com.yellowcong.model.Topic;
import com.yellowcong.service.LinksService;
import com.yellowcong.service.SummaryService;
import com.yellowcong.service.TopicService;
import com.yellowcong.service.TuikuService;

@Service("summaryService")
public class SummaryServiceImpl implements SummaryService {
	private SummaryDao summaryDao;
	private TuikuService tuikuService;
	private TopicService topicService;
	private LinksService linksService;
	
	@Resource(name="linksService")
	public void setLinksService(LinksService linksService) {
		this.linksService = linksService;
	}
	@Resource(name="topicService")
	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}
	@Resource(name="tuikuService")
	public void setTuikuService(TuikuService tuikuService) {
		this.tuikuService = tuikuService;
	}

	@Resource(name="summaryDao")
	public void setSummaryDao(SummaryDao summaryDao) {
		this.summaryDao = summaryDao;
	}

	@Override
	public Summarys add(Summarys summary) {
		return this.summaryDao.add(summary);
	}

	@Override
	public void adds(List<Summarys> summarys) {
		for(Summarys sum:summarys){
			this.summaryDao.add(sum);
		}
	}

	@Override
	public int conunt() {
		return this.summaryDao.count();
	}

	@Override
	public void updateAll() {
		//获取到所有的标题
		List<Topic> topics = this.topicService.listForUpdate();
		//通过标题来获取到数据 url
		for(Topic topic :topics){
			
			//http://www.tuicool.com/topics/11000077?st=0&lang=0&pn=10
			
			String title = topic.getTitle();
			String url = topic.getUrl();
			//添加中英文的数据
			this.addSummary(title, url);
		}
	}
	/**
	 * 添加summary中的数据
	 * @param title
	 * @param url
	 */
	private void addSummary(String title ,String url){
		System.out.println("____________________________"+title+"____________________________________");
		System.out.println("____________________________"+url+"____________________________________");
		//通过URL来获取中文和
		//中文
		for(int i=0;i<=10;i++){
			///topics/10050109
			String href = "http://www.tuicool.com"+url+"?st=0&lang=1&pn="+i;
			Links links = new Links("推酷网",href,new Date());
			links = this.linksService.add(links);
			//访问的数据
			if(links != null){
				List<Summarys> sums = tuikuService.loadSummarys("http://www.tuicool.com"+url+"?st=0&lang=1&pn="+i,title);
				this.adds(sums);
			}
		}
		
		//英文
		for(int i=0;i<=10;i++){
			///topics/10050109
			String href = "http://www.tuicool.com"+url+"?st=0&lang=2&pn="+i;
			Links links = new Links("推酷网",href,new Date());
			links = this.linksService.add(links);
			//访问的数据
			if(links != null){
				List<Summarys> sums = tuikuService.loadSummarys("http://www.tuicool.com"+url+"?st=0&lang=1&pn="+i,title);
				this.adds(sums);
			}
		}
	}
	@Override
	public Pager<Summarys> loadByPager() {
		// TODO Auto-generated method stub
		SystemContext.setPageSize(10);
		return this.summaryDao.queryByPager("select new Summarys(id,link) from Summarys");
	}

}
