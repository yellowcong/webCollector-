package com.yellowcong.dao.impl;

import org.springframework.stereotype.Repository;

import com.yellowcong.dao.ProxyHttpsDao;
import com.yellowcong.model.ProxyHttps;

@Repository("proxyHttpsDao")
public class ProxyHttpsDaoImpl extends BaseDaoImpl<ProxyHttps> implements ProxyHttpsDao{

	@Override
	public ProxyHttps add(ProxyHttps t) {
		// TODO Auto-generated method stub
		
		Object obj = this.getSession().createQuery("select new ProxyHttps(id) from ProxyHttps where ip = ?").setParameter(0, t.getIp()).uniqueResult();
		if(obj != null){
			return null;
		}
		
		return super.add(t);
	}

}
