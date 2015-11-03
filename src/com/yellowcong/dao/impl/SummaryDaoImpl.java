package com.yellowcong.dao.impl;

import org.springframework.stereotype.Repository;

import com.yellowcong.dao.SummaryDao;
import com.yellowcong.model.Summarys;

@Repository("summaryDao")
public class SummaryDaoImpl extends BaseDaoImpl<Summarys> implements SummaryDao{

	@Override
	public Summarys add(Summarys t) {
		Object obj = super.getSession().createQuery("select new Summarys(id) from Summarys where title = ?").setParameter(0, t.getTitle()).uniqueResult();
		if(obj != null){
			return null;
		}
		return super.add(t);
	}

	@Override
	public int count() {
		return Integer.parseInt(super.getSession().createQuery("select count(*) from Summarys").uniqueResult().toString());
	}

	
	
}
