package com.yellowcong.test;

import org.junit.Test;

import com.yellowcong.utils.HttpClientUtils;

/**
 * 测试我们的HttpClieants
 * @author yellowcong
 *
 */
public class HttpClientsTest {
	
	@Test
	public void testSendGet(){
		String content = HttpClientUtils.sendGet("http://www.tuicool.com/articles/UJreAz",true);
		System.out.println(content);
	}
}
