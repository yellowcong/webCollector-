package com.yellowcong.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;

@Entity
@Table(name = "blog_passage")
public class Passage {
	private int id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 关键字
	 */
	private String keyWords;
	/**
	 * 文章来源地址
	 */
	private String sourceUrl;
	/**
	 * 原始栏目
	 */
	private String sourceChannel;
	/**
	 * 文章的第一张图片地址
	 */
	private String indexImage;
	/**
	 * 内容
	 */
	private String content;
	
	public Passage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Passage(int id) {
		super();
		this.id = id;
	}




	public Passage(String title, Date createDate, String keyWords,
			String sourceUrl, String sourceChannel, String indexImage,
			String content) {
		super();
		this.title = title;
		this.createDate = createDate;
		this.keyWords = keyWords;
		this.sourceUrl = sourceUrl;
		this.sourceChannel = sourceChannel;
		this.indexImage = indexImage;
		this.content = content;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	


	public void setId(int id) {
		this.id = id;
	}
	
	@Column(length=100)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="key_words",length=100)
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	
	@Column(name="sources_url")
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	
	@Column(name="sources_channel",length=50)
	public String getSourceChannel() {
		return sourceChannel;
	}
	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}
	
	public String getIndexImage() {
		return indexImage;
	}
	public void setIndexImage(String indexImage) {
		this.indexImage = indexImage;
	}
	@Column(columnDefinition="text")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
