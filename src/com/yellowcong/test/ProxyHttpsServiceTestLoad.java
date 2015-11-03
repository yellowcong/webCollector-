package com.yellowcong.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.model.ProxyHttps;
import com.yellowcong.service.ProxyHttpsService;
import com.yellowcong.utils.ProxyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class ProxyHttpsServiceTestLoad {
	
	private List<String []> list ;
	private ProxyHttpsService  service;
	@Resource(name="proxyHttpsService")
	public void setService(ProxyHttpsService service) {
		this.service = service;
	}
	
	//添加我们的测试数据
	@Test
	public void add() throws Exception{
		String [][] dates = new String[][]{
				/*{"国内高匿代理", "http://www.xicidaili.com/nn/","224"},
				{"国内普通代理", "http://www.xicidaili.com/nt/","90"},
				{"国外高匿代理", "http://www.xicidaili.com/wn/","139"},
				{"国外普通代理", "http://www.xicidaili.com/wt/","248"},*/
				{"SOCKS代理", "http://www.xicidaili.com/qq/","424"}};
		List<ProxyHttps> proxys = null;
		for(String [] data:dates){
			int page = Integer.parseInt(data[2]);
			for(int i=1;i<=page;i++){
				proxys = ProxyUtils.loadChannelProxy(data[1]+"/"+i, data[0]);
				this.service.adds(proxys);
			}
		}
	}
	
	@Test
	public void testUpdate(){
		this.service.updateLatest();
		
	}
}

