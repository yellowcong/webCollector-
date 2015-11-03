package com.yellowcong.Crawler;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.yellowcong.model.Topic;
import com.yellowcong.service.TopicService;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * Crawl news from yahoo news
 *
 */
public class ProxyCrawler extends BreadthCrawler {
	public static List<Topic> topics = new ArrayList<Topic>();
	
	public ProxyCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		String [][] dates = new String[][]{
				{"国内高匿代理", "http://www.xicidaili.com/nn/","224"},
				{"国内普通代理", "http://www.xicidaili.com/nt/","90"},
				{"国外高匿代理", "http://www.xicidaili.com/wn/","139"},
				{"国外普通代理", "http://www.xicidaili.com/wt/","248"},
				{"SOCKS代理", "http://www.xicidaili.com/qq/","424"}};
		List<String[]> list = new ArrayList<String[]>();
		for(int i=0;i<dates.length;i++){
			int page =Integer.parseInt(dates[i][2]);
			for(int j=1;j<=page;j++){
				list.add(new String[]{dates[i][0],dates[i][1],j+""});
			}
		}
		for(String [] str:list){
			//,str[0] 栏目
			String date= str[1]+"/"+str[2];
			this.addSeed(date);
		}
		/* start page */
		// 获取主题
		for (int i = 1; i < 9; i++) {
			this.addSeed("http://www.tuicool.com/topics?tab=1&id=" + i);
		}
	}

	@Override
	public void visit(Page page, Links nextLinks) {
		
	}

	/**
	 * 获取所有的topic
	 * @return 
	 */
	public static List<Topic> getTopics(){
		try {
			ProxyCrawler crawler = new ProxyCrawler("crawl", true);
			// 设定线程
			crawler.setThreads(1);
			// crawler.setTopN(100);
			// crawler.setResumable(true);
			/* start crawl with depth of 4 */
			// 设定深度
			crawler.start(1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//返回所有 获取的topic
		return topics;
	}

}
