package com.yellowcong.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.model.Passage;
import com.yellowcong.model.Summarys;
import com.yellowcong.service.PassageService;
import com.yellowcong.service.SummaryService;
import com.yellowcong.service.TuikuService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class PassageServiceTest2 {
	
	private TuikuService tuikuService;
	private SummaryService summaryService;
	private PassageService passageService;
	
	@Resource(name="tuikuService")
	public void setTuikuService(TuikuService tuikuService) {
		this.tuikuService = tuikuService;
	}
	
	@Resource(name="passageService")
	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}


	@Resource(name="summaryService")
	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}

	@Test
	public void testUpdate(){
//		passageService.updatePsg();
		passageService.startThread();
	}
	
	@Test
	public void testLoadByPager(){
		List<Summarys> lists = this.summaryService.loadByPager().getData();
		
		for(Summarys obj:lists){
			String url = "http://www.tuicool.com/"+obj.getLink();
			Passage psg = this.tuikuService.loadPsg(url).getPassage();
			if(psg != null){
				System.out.println(url+"------------------"+psg.getTitle()+"------------------");
			}
		}
	}
}
