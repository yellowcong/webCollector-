package com.yellowcong.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yellowcong.dao.ProxyHttpsDao;
import com.yellowcong.model.ProxyHttps;
import com.yellowcong.service.ProxyHttpsService;
import com.yellowcong.utils.ProxyUtils;
import com.yellowcong.utils.StringUtil;


/**
 * Proxy的使用方法
 * @author yellowcong
 *
 */
@Service("proxyHttpsService")
public class ProxyHttpsServiceImpl implements ProxyHttpsService{
	private ProxyHttpsDao proxyHttpsDao;
	
	@Resource(name="proxyHttpsDao")
	public void setProxyHttpsDao(ProxyHttpsDao proxyHttpsDao) {
		this.proxyHttpsDao = proxyHttpsDao;
	}

	@Override
	public void add(ProxyHttps proxy) {
		// TODO Auto-generated method stub
		this.proxyHttpsDao.add(proxy);
	}

	@Override
	public void adds(List<ProxyHttps> proxys) {
		for(ProxyHttps proxy:proxys){
			this.proxyHttpsDao.add(proxy);
		}
	}

	@Override
	public void updateAll() {
		
		List<String  []> dates = getIndex();
		
		try {
			List<ProxyHttps> proxys = null;
			for(String [] data:dates){
				int page = Integer.parseInt(data[2]);
				for(int i=1;i<=page;i++){
					proxys = ProxyUtils.loadChannelProxy(data[1]+"/"+i, data[0]);
					this.adds(proxys);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	private List<String[]> getIndex(){
		String [][] dates = new String[][]{
				{"国内高匿代理", "http://www.xicidaili.com/nn/","224"},
				{"国内普通代理", "http://www.xicidaili.com/nt/","90"},
				{"国外高匿代理", "http://www.xicidaili.com/wn/","139"},
				{"国外普通代理", "http://www.xicidaili.com/wt/","248"},
				{"SOCKS代理", "http://www.xicidaili.com/qq/","424"}};
		List<String[]> list = new ArrayList<String[]>();
		for(int i=0;i<dates.length;i++){
			int page =Integer.parseInt(dates[i][2]);
			for(int j=1;j<=page;j++){
				list.add(new String[]{dates[i][0],dates[i][1],j+""});
			}
		}
		return list;
	}
	@Override
	public void updateLatest() {
		int startCount = this.proxyHttpsDao.count();
		long startTime = System.currentTimeMillis();
		
		String [][] dates = new String[][]{
				{"国内高匿代理", "http://www.xicidaili.com/nn/","10"},
				{"国内普通代理", "http://www.xicidaili.com/nt/","10"},
				{"国外高匿代理", "http://www.xicidaili.com/wn/","10"},
				{"国外普通代理", "http://www.xicidaili.com/wt/","10"},
				{"SOCKS代理", "http://www.xicidaili.com/qq/","10"}};
		
		List<String[]> list = new ArrayList<String[]>();
		for(int i=0;i<dates.length;i++){
			int page =Integer.parseInt(dates[i][2]);
			for(int j=1;j<=page;j++){
				list.add(new String[]{dates[i][0],dates[i][1],j+""});
			}
		}
		
		try {
			List<ProxyHttps> proxys = null;
			for(String [] data:dates){
				int page = Integer.parseInt(data[2]);
				for(int i=1;i<=page;i++){
					proxys = ProxyUtils.loadChannelProxy(data[1]+"/"+i, data[0]);
					this.adds(proxys);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int endCount = this.proxyHttpsDao.count();
		long endTime = System.currentTimeMillis();
		
		System.out.println("-------------------更新条数"+(endCount-startCount)+"-------------------------");
		System.out.println("-------------------开始时间"+new Date(startTime)+"-------------------------");
		System.out.println("-------------------结束时间"+new Date(endTime)+"-------------------------");
		System.out.println("-------------------更新所用时间"+StringUtil.countTime(startTime, endTime)+"-------------------------");
	}
}
