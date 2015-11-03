package com.yellowcong.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.model.ProxyHttps;
import com.yellowcong.service.ProxyHttpsService;
import com.yellowcong.utils.ProxyUtils;
/**
 * 带多线程的爬虫 爬 SQL线程问题
 * @author yellowcong
 *
 */
public class ProxyHttpsServiceTest {
	private static List<String []> list ;
	private static ProxyHttpsService service;
	
	
	
	
	public static void main(String [] args){
		
		//
		setUp();
		System.out.println("总记录条数\t"+list.size());
		ExecutorService pool = Executors.newFixedThreadPool(5);
		//创建内部类
		pool.execute(new ProxyHttpsServiceTest().new GetUrl());
		//立马关闭
		pool.shutdownNow();
	}
	/**
	 * 初始化数据 获取我们的栏目
	 */
	public static void setUp(){
			
		String [][] dates = new String[][]{
				/*{"国内高匿代理", "http://www.xicidaili.com/nn/","224"},
				{"国内普通代理", "http://www.xicidaili.com/nt/","90"},
				{"国外高匿代理", "http://www.xicidaili.com/wn/","139"},*/
				{"国外普通代理", "http://www.xicidaili.com/wt/","248"},
				{"SOCKS代理", "http://www.xicidaili.com/qq/","424"}};
		list = new ArrayList<String[]>();
		for(int i=0;i<dates.length;i++){
			int page =Integer.parseInt(dates[i][2]);
			for(int j=1;j<=page;j++){
				list.add(new String[]{dates[i][0],dates[i][1],j+""});
			}
		}
		
		ApplicationContext app = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		service = (ProxyHttpsService) app.getBean("proxyHttpsService");
		
		
		
		
	}
	
	/**
	 * 定义多线程类
	 * @author yellowcong
	 *
	 */
	class GetUrl implements Runnable{
		

		/**
		 * 调用一次就获取一个数据，
		 * @return
		 */
		public synchronized String [] getUrl(){
			String []  reuslt  = null;
			System.out.println("----------------------剩余页面数"+list.size()+"----------------------");
			if(list.size()>0){
				reuslt =list.get(0);
				list.remove(0);
			}
			return reuslt;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			try {
					
						while(list.size()>0){
							String [] str = getUrl();
							if(str != null){
								System.out.println("--------------------------"+Thread.currentThread().getName()+"------------------------");
								//[国外普通代理, http://www.xicidaili.com/wt/, 16]
								List<ProxyHttps> proxys = ProxyUtils.loadChannelProxy(str[1]+"/"+str[2],str[0]);
								System.out.println(Arrays.toString(str));
								service.adds(proxys);
							}
						}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

