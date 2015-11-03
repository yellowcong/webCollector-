package com.yellowcong.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blog_topic")
public class Topic {
	private int id;
	/**
	 * 栏目
	 */
	private String channel;

	/**
	 * 图片地址
	 */
	private String imgUrl;
	/**
	 * topic名次
	 */
	private String title;
	/**
	 * 链接地址
	 */
	private String url;

	public Topic(int id) {
		super();
		this.id = id;
	}

	public Topic() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Topic(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}

	public Topic(String channel, String imgUrl, String title, String url) {
		super();
		this.channel = channel;
		this.imgUrl = imgUrl;
		this.title = title;
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

	@Column(length = 50, name = "topic_channel")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(length = 200, name = "img_url")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Column(length = 200, name = "topic_url")
	public String getUrl() {
		return url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(length = 50, name = "topic_title")
	public String getTitle() {
		return title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
