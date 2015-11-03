package com.yellowcong.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.http.protocol.ExecutionContext;
import org.springframework.stereotype.Service;

import com.yellowcong.dao.PassageDao;
import com.yellowcong.dto.PassageImageDto;
import com.yellowcong.model.Links;
import com.yellowcong.model.Passage;
import com.yellowcong.model.PassageImages;
import com.yellowcong.model.Summarys;
import com.yellowcong.model.SystemContext;
import com.yellowcong.service.LinksService;
import com.yellowcong.service.PassageImagesService;
import com.yellowcong.service.PassageService;
import com.yellowcong.service.SummaryService;
import com.yellowcong.service.TuikuService;

@Service("passageService")
public class PassageServiceImpl implements PassageService{
	private static int page_now =10/10 -1;
	private PassageDao passageDao;
	private TuikuService tuikuService;
	private SummaryService summaryService;
	private LinksService linksService;
	private PassageImagesService passageImagesService;

	@Resource(name="passageImagesService")
	public void setPassageImagesService(PassageImagesService passageImagesService) {
		this.passageImagesService = passageImagesService;
	}
	
	
	@Resource(name="tuikuService")
	public void setTuikuService(TuikuService tuikuService) {
		this.tuikuService = tuikuService;
	}
	
	@Resource(name="summaryService")
	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}
	@Resource(name="passageDao")
	public void setPassageDao(PassageDao passageDao) {
		this.passageDao = passageDao;
	}
	
	
	
	@Resource(name="linksService")
	public void setLinksService(LinksService linksService) {
		this.linksService = linksService;
	}

	@Override
	public Passage add(Passage psg) {
		return this.passageDao.add(psg);
	}

	@Override
	public void updatePsg() {
		// TODO Auto-generated method stub
		String url  = null;
		//去掉有一页 可能只爬取一条的问题
		int pageNow = 37500/10 -1;
		System.out.println("--------------------------当前爬取页面"+pageNow+"------------");
		System.err.println("当前爬虫:\t"+Thread.currentThread().getName());
		int i = pageNow;
		while(true){
			try {
				//System.out.println("长度"+i);
				//
				System.err.println("--------------------------当前爬取页面"+i+"------------");
				SystemContext.setPageNow(i++);
				//获取前 10 页
				List<Summarys> lists = this.summaryService.loadByPager().getData();
				for(Summarys obj:lists){
					System.err.println("------------------------------下一篇-------------------------");
					url = "http://www.tuicool.com/"+obj.getLink();
					this.addPassageAndImage(url);
				}
			} catch (Exception e) {
				System.err.println("--------------------------文章转化失败----------------------------------");
				//System.err.println(url);
			}
		}
	}
	
	private void addPassageAndImage(String url){
		PassageImageDto dto = this.tuikuService.loadPsg(url);
		Passage psg = dto.getPassage();
		if(psg != null){
			
			//System.out.println(url+"------------------"+psg.getTitle()+"------------------");
			//添加文章
			//添加 link
			Links links = new Links("1", url, new Date());
			links = this.linksService.add(links);
			//判断我们的东西是否添加过
			if(links != null){
				psg = add(psg);
				//添加图片
				List<PassageImages> imgs = dto.getPassageImages();
				//添加我们的图片
				if( imgs!= null && imgs.size() >0){
					//System.out.println(url+"------------------图片"+imgs.size()+"------------------");
					for(PassageImages img :imgs){
						img.setPassage(psg);
						this.passageImagesService.add(img);
					}
				}
			}
		}
	}


	@Override
	public void startThread() {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newFixedThreadPool(50);
		for(int i = 0;i<50;i++){
			pool.execute(new Runnable() {
				
				public synchronized void updatePsg_Thead() {
					// TODO Auto-generated method stub
					String url  = null;
					//去掉有一页 可能只爬取一条的问题
					while(true){
						
						try {
							//System.out.println("长度"+i);
							//
							System.err.println("--------------------------当前爬取页面"+page_now+"------------");
							SystemContext.setPageNow(page_now++);
							//获取前 10 页
							List<Summarys> lists = summaryService.loadByPager().getData();
							for(Summarys obj:lists){
								System.err.println("------------------------------下一篇-------------------------");
								url = "http://www.tuicool.com/"+obj.getLink();
								addPassageAndImage(url);
							}
						} catch (Exception e) {
							System.err.println("--------------------------文章转化失败----------------------------------");
							//System.err.println(url);
						}
					}
				}
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					updatePsg_Thead();
				}
			});
		}
	}
	
	
	/*public synchronized void updatePsg_Thead() {
		// TODO Auto-generated method stub
		String url  = null;
		//去掉有一页 可能只爬取一条的问题
		
		System.err.println("当前爬虫:\t"+Thread.currentThread().getName());
		while(true){
			try {
				Thread.sleep(500);
				System.err.println("--------------------------当前爬取页面"+(page_now++)+"------------");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				//System.out.println("长度"+i);
				//
				System.err.println("--------------------------当前爬取页面"+page_now+"------------");
				SystemContext.setPageNow(page_now++);
				//获取前 10 页
				List<Summarys> lists = this.summaryService.loadByPager().getData();
				for(Summarys obj:lists){
					System.err.println("------------------------------下一篇-------------------------");
					url = "http://www.tuicool.com/"+obj.getLink();
					this.addPassageAndImage(url);
				}
			} catch (Exception e) {
				System.err.println("--------------------------文章转化失败----------------------------------");
				//System.err.println(url);
			}
		}
	}
*/
}
