package com.yellowcong.dao.impl;

import org.springframework.stereotype.Repository;

import com.yellowcong.dao.LinksDao;
import com.yellowcong.model.Links;

@Repository("linksDao")
public class LinksDaoImpl extends BaseDaoImpl<Links> implements LinksDao {

	@Override
	public Links add(Links t) {
		// TODO Auto-generated method stub
		//and webSite = ?
		Object obj = this.getSession().createQuery("select new Links(id) from Links where href = ?").setParameter(0, t.getHref()).uniqueResult();
		if(obj != null){
			return null;
		}
		return super.add(t);
	}

	@Override
	public int countPsgs() {
		// TODO Auto-generated method stub
		String result = this.getSession().createQuery("select count(*) from Links where webSite = ? or webSite = ?").setParameter(0, "1").setParameter(1, "0").uniqueResult().toString();
		return Integer.parseInt(result);
	}
}
