package com.yellowcong.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 爬虫的文章
 * @author yellowcong
 *
 */
@Entity
@Table(name="crawler_links_4")
public class Links {
	private int id;
	/**
	 * 网站名称
	 * 1 表示爬成功了
	 * 0表示没有爬成功
	 */
	private String webSite;
	/**
	 * 链接地址
	 */
	private String href;
	
	/**
	 * 爬取时间
	 */
	private Date crawlerDate;

	public Links(int id) {
		super();
		this.id = id;
	}

	public Links() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Links(String webSite, String href, Date crawlerDate) {
		super();
		this.webSite = webSite;
		this.href = href;
		this.crawlerDate = crawlerDate;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="web_site_name",length=100)
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	@Column(name="crawler_date")
	public Date getCrawlerDate() {
		return crawlerDate;
	}

	public void setCrawlerDate(Date crawlerDate) {
		this.crawlerDate = crawlerDate;
	}
}
