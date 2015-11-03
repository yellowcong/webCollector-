package com.yellowcong.dao.impl;

import org.springframework.stereotype.Repository;

import com.yellowcong.dao.PassageDao;
import com.yellowcong.model.Passage;

@Repository("passageDao")
public class PassageDaoImpl extends BaseDaoImpl<Passage> implements PassageDao{

	@Override
	public Passage add(Passage t) {
		// TODO Auto-generated method stub
		//Passage(int id)
		/*Object obj = this.getSession().createQuery("select new Passage(id) from Passage where title = ?").setParameter(0, t.getTitle()).uniqueResult();
		if(obj != null){
			throw new RuntimeException("文章已存在");
		}*/
		
		return super.add(t);
	}
	
}
