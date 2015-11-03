package com.yellowcong.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="xicidaili_com_proxy")
public class ProxyHttps {
	private int id;
	/**
	 * 代理的国家
	 */
	private String country;
	/**
	 * 代理的ip地址
	 */
	private String ip;
	/**
	 * 代理的端口号
	 */
	private String port;
	/**
	 * ip地址位置
	 */
	private String localtion;
	/**
	 * 是否透明
	 */
	private String toumin;
	/**
	 * 代理类型
	 * 
	 */
	private String type;
	/**
	 * 代理的创建日期
	 */
	private Date createDate;
	/**
	 * 栏目名称
	 */
	private String channel;
	/**
	 * 速度
	 */
	private float quickly;
	/**
	 * 需要时间
	 */
	private float time;
	
	
	public ProxyHttps() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProxyHttps(int id) {
		super();
		this.id = id;
	}

	public ProxyHttps(String country, String ip, String port, String localtion,
			String toumin, String type, Date createDate, String channel,
			float quickly, float time) {
		super();
		this.country = country;
		this.ip = ip;
		this.port = port;
		this.localtion = localtion;
		this.toumin = toumin;
		this.type = type;
		this.createDate = createDate;
		this.channel = channel;
		this.quickly = quickly;
		this.time = time;
	}
	
	

	public ProxyHttps(String ip, String port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="country",length=60)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Column(name="ip",length=60)
	public String getIp() {
		return ip;
	}
	
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(name="port",length=8)
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	@Column(name="localtion",length=32)
	public String getLocaltion() {
		return localtion;
	}
	public void setLocaltion(String localtion) {
		this.localtion = localtion;
	}
	
	@Column(name="toumin",length=32)
	public String getToumin() {
		return toumin;
	}

	public void setToumin(String toumin) {
		this.toumin = toumin;
	}

	
	@Column(length=10)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="channel")
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public float getQuickly() {
		return quickly;
	}

	public void setQuickly(float quickly) {
		this.quickly = quickly;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}
	
}
