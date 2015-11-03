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
public class TuikuCrawler extends BreadthCrawler {
	public static List<Topic> topics = new ArrayList<Topic>();
	
	public TuikuCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		/* start page */
		// 获取主题
		for (int i = 1; i < 9; i++) {
			this.addSeed("http://www.tuicool.com/topics?tab=1&id=" + i);
		}
	}

	@Override
	public void visit(Page page, Links nextLinks) {
		String url = page.getUrl();
		//ttp://www.tuicool.com/topics?tab=1&id=8
		int id = Integer.parseInt(url.substring(url.indexOf("id=")+3));
		String [] channels = {"","推荐主题 ","互联移动","科技世界","手机数码","产品设计","编程语言","架构存储","技术纵横"};
		//System.out.println(channels[id]+"\t"+url);
		Document doc = page.getDoc();
		Elements elements = doc.select("div[class=topic_hot_list span5]");
		//System.out.println("条数"+elements.size());
		//获取数据
		Iterator<Element> el = elements.iterator();

		while (el.hasNext()) {
			Element element = el.next();
			String title = element.text();
			String imgurl = element.getElementsByTag("img").attr("src");
			String link = element.getElementsByAttribute("href").attr("href");
			// System.out.println(element.getElementsByTag("img").text());
			Topic topic = new Topic(channels[id], imgurl, title, link);
			this.topics.add(topic);
		}
	}

	/**
	 * 获取所有的topic
	 * @return 
	 */
	public static List<Topic> getTopics(){
		try {
			TuikuCrawler crawler = new TuikuCrawler("crawl", true);
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
