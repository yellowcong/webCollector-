package com.yellowcong.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 文章的图片，用于以后图片的爬取
 * @author yellowcong
 * @createDate 2015年10月18日
 *
 */
@Entity
@Table(name="blog_passage_imgs")
public class PassageImages {
	private int id;
	/**
	 * 图片的地址
	 */
	private String url;
	/**
	 * 添加的日期
	 */
	private Date createDate;
	/**
	 * 外键  图片
	 */
	private Passage passage;
	
	public PassageImages() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public PassageImages(String url, Date createDate) {
		super();
		this.url = url;
		this.createDate = createDate;
	}


	public PassageImages(int id, String url) {
		super();
		this.id = id;
		this.url = url;
	}


	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="img_url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@ManyToOne
	@JoinColumn(name="passage_id")
	public Passage getPassage() {
		return passage;
	}
	public void setPassage(Passage passage) {
		this.passage = passage;
	}
}
