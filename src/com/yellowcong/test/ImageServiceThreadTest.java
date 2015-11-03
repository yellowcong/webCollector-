package com.yellowcong.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yellowcong.service.PassageImagesService;
import com.yellowcong.service.PassageService;

public class ImageServiceThreadTest {

	
	public static void main(String[] args) {
		AbstractApplicationContext context = 	new ClassPathXmlApplicationContext("ApplicationContext.xml");
		PassageImagesService service = (PassageImagesService) context.getBean("passageImagesService");
		/**
		 * 开启100个线程来爬取数据
		 */
		service.startThread(100);
	}
}
