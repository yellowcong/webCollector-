package com.yellowcong.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yellowcong.dao.PassageImagesDao;
import com.yellowcong.model.Links;
import com.yellowcong.model.Pager;
import com.yellowcong.model.PassageImages;
import com.yellowcong.model.SystemContext;
import com.yellowcong.service.LinksService;
import com.yellowcong.service.PassageImagesService;
import com.yellowcong.utils.HttpImageUtils;


@Service("passageImagesService")
public class PassageImagesServiceImpl implements PassageImagesService{
	/**
	 * 爬去的页面
	 */
	//11501
	//3553
	//3500页面
	//4107
	//5760
	//142000 张了
	//19718
	//20607
	//24451
	//24906
	private static int pageNow = 24850;
	/**
	 * 注入对象
	 */
	private PassageImagesDao passageImagesDao;
	private LinksService linksService;
	
	

	@Resource(name="linksService")
	public void setLinksService(LinksService linksService) {
		this.linksService = linksService;
	}
	
	
	@Resource(name="passageImagesDao")
	public void setPassageImagesDao(PassageImagesDao passageImagesDao) {
		this.passageImagesDao = passageImagesDao;
	}

	@Override
	public PassageImages add(PassageImages imgs) {
		return this.passageImagesDao.add(imgs);
	}

	@Override
	public void adds(List<PassageImages> imgs) {
		for(PassageImages img :imgs){
			this.passageImagesDao.add(img);
		}
	}

	@Override
	public Pager<PassageImages> loadByPager() {
		//select PassageImages(id,url) 
		return this.passageImagesDao.queryByPager("from PassageImages");
	}

	@Override
	public void startThread(int num) {
		// TODO Auto-generated method stub
		System.out.println("启动线程数:\t"+num);
		ExecutorService pool = Executors.newFixedThreadPool(num);
		
		for(int i =0;i<num;i++){
			
			pool.execute(new Runnable() {
				
				public synchronized void downloadImage(){
					while(true){
						System.err.println("-------------------------当前页面"+pageNow+"-------------------------");
						System.out.println("-------------------------当前线程:\t"+Thread.currentThread().getName());
						SystemContext.setPageNow(pageNow++);
						Pager<PassageImages> pager =loadByPager();
						List<PassageImages> list = pager.getData();
						for(PassageImages img:list){
							System.out.println(img.getUrl());
							//然后插入数据到服务器中
							Links links = new Links();
							links.setHref(img.getUrl());
							links.setCrawlerDate(new Date());
							links.setWebSite("1");
						    links = linksService.add(links);
							if(links != null){
								boolean flag = HttpImageUtils.downloadImage(img.getUrl());
								if(!flag){
									System.out.println("没有数据");
								}
							}
						}
					}
				}
				@Override
				public void run() {
					// TODO Auto-generated method stub
					downloadImage();
				}
			});
		}
	}

}
