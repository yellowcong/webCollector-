package com.yellowcong.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.service.SummaryService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class SummaryServiceTest {
	
	private SummaryService summaryService;

	@Resource(name="summaryService")
	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}
	
	@Test
	public void testUpdateSummary(){
		//更新Topic栏目的所有数据
		this.summaryService.updateAll();
	}
}
