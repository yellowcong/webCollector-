package com.yellowcong.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.yellowcong.utils.StringUtil;

public class StringTest {
	
	@Test
	public void testRemoveEMoje(){
		
		long start = 0l ;
		long end = (4400+300)*1000l;
		
		String str = StringUtil.countTime(start,end);
		System.out.println(str);
		/*//计算天数
		long day = date/(24*60*60);
		System.out.println(day+"天");
		
		long hh = date%(24*60*60)/(60*60);
		System.out.println(hh+"时");
		long mm = date%(24*60*60)%(60*60)/60;
		System.out.println(mm+"分");
		long ss = date%(24*60*60)%(60*60)%60;
		System.out.println(ss+"秒");*/
		
	}
	
	
}
