package com.yellowcong.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.model.Pager;
import com.yellowcong.model.PassageImages;
import com.yellowcong.model.SystemContext;
import com.yellowcong.service.PassageImagesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class PassageImagesServiceTest {
	
	private PassageImagesService passageImagesService;

	@Resource(name="passageImagesService")
	public void setPassageImagesService(PassageImagesService passageImagesService) {
		this.passageImagesService = passageImagesService;
	}
	
	
	@Test
	public void testAdd(){
		PassageImages img = new PassageImages();
		img.setUrl("xxx");
		this.passageImagesService.add(img);
	}
	
	@Test
	public void testLoadQueryByPager(){
		int index = 0;
		while(true){
			System.err.println("------------当前页面"+index+"-------");
			SystemContext.setPageNow(index++);
			Pager<PassageImages> pager = this.passageImagesService.loadByPager();
			List<PassageImages> list = pager.getData();
			for(PassageImages img  :list){
				System.out.println(img.getUrl());
			}
		}
	}
	
	@Test
	public void testLoadThead(){
		this.passageImagesService.startThread(10);
	}
	
}
