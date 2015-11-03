package com.yellowcong.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.service.LinksService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class LinksServiceTest {
	
	private LinksService linksService;
	
	@Resource
	public void setLinksService(LinksService linksService) {
		this.linksService = linksService;
	}

	@Test
	public  void test(){
		System.out.println("文章 长度"+this.linksService.countPsgs());
	}
}
