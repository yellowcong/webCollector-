package com.yellowcong.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yellowcong.dao.LinksDao;
import com.yellowcong.model.Links;
import com.yellowcong.service.LinksService;


@Service("linksService")
public class LinksServiceImpl implements LinksService{
	private LinksDao linksDao;
	
	@Resource(name="linksDao")
	public void setLinksDao(LinksDao linksDao) {
		this.linksDao = linksDao;
	}

	@Override
	public Links add(Links links) {
		return this.linksDao.add(links);
	}

	@Override
	public void adds(List<Links> links) {
		for(Links link:links){
			this.add(link);
		}
	}

	@Override
	public int countPsgs() {
		// TODO Auto-generated method stub
		return this.linksDao.countPsgs();
	}
	
}
