package com.yellowcong.dao.impl;

import org.springframework.stereotype.Repository;

import com.yellowcong.dao.PassageImagesDao;
import com.yellowcong.model.PassageImages;

@Repository("passageImagesDao")
public class PassageImagesDaoImpl  extends BaseDaoImpl<PassageImages> implements PassageImagesDao{

}
