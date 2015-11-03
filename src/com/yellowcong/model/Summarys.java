package com.yellowcong.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * 文章的缩略数据
 * @author yellowcong
 *
 */
@Entity
@Table(name="blog_summarys")
public class Summarys {
	
	private int id;
	/**
	 * 现在的栏目
	 */
	private String channelNow;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 日期
	 */
	private Date createDate;
	/**
	 * 类容
	 */
	private String content;
	/**
	 * 来源
	 */
	private String channelSource;
	/**
	 * 首页图片 地址
	 */
	private String imgUrl;
	/**
	 * 链接地址
	 * 前缀 http://www.tuicool.com/
	 */
	private String link;
	
	public Summarys() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	//用于我们的 psg来进行便利
	public Summarys(int id, String link) {
		super();
		this.id = id;
		this.link = link;
	}



	public Summarys(int id) {
		super();
		this.id = id;
	}
	
	


	public void setChannelNow(String channelNow) {
		this.channelNow = channelNow;
	}
	
	
	

	@Column(name="channel_now")
	public String getChannelNow() {
		return channelNow;
	}


	public Summarys(String title, Date createDate, String content,
			String channelSource, String imgUrl,String link) {
		super();
		this.title = title;
		this.createDate = createDate;
		this.content = content;
		this.channelSource = channelSource;
		this.imgUrl = imgUrl;
		this.link = link;
	}


	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="title",length=100)
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
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="channel_source",length=100)
	public String getChannelSource() {
		return channelSource;
	}
	public void setChannelSource(String channelSource) {
		this.channelSource = channelSource;
	}
	
	@Column(name="img_url")
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}
}
