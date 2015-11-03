package com.yellowcong.test;


import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yellowcong.service.PassageService;

public class TestDemo {
	public static void main(String[] args) {
		
		AbstractApplicationContext context = 	new ClassPathXmlApplicationContext("ApplicationContext.xml");
		final PassageService service = (PassageService) context.getBean("passageService");
		service.startThread();
	}
	
	
}
