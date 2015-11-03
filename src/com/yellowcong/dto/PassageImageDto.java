package com.yellowcong.dto;

import java.util.List;

import com.yellowcong.model.Passage;
import com.yellowcong.model.PassageImages;

/**
 * 获取到Passage中的文章和 图片
 * @author yellowcong
 * @createDate 2015年10月18日
 *
 */
public class PassageImageDto {
	private Passage  passage ;
	private List<PassageImages> passageImages;
	
	
	public PassageImageDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PassageImageDto(Passage passage, List<PassageImages> passageImages) {
		super();
		this.passage = passage;
		this.passageImages = passageImages;
	}
	public Passage getPassage() {
		return passage;
	}
	public void setPassage(Passage passage) {
		this.passage = passage;
	}
	public List<PassageImages> getPassageImages() {
		return passageImages;
	}
	public void setPassageImages(List<PassageImages> passageImages) {
		this.passageImages = passageImages;
	}
	
	
}
