package com.yellowcong.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yellowcong.model.ProxyHttps;

public class ProxyUtils {
	
	
	/**
	 * 获取http://www.xicidaili.com/ 所有的网站的proxy
	 * 这个方法不好用， 会导致数据过多 程序死掉
	 * @throws Exception 
	 */
	@Deprecated
	public static List<ProxyHttps> getAllProxy() throws Exception{
		//"http://www.xicidaili.com/nn/" 带分页的 224
				//http://www.xicidaili.com/nt/  国内  90 
				//http://www.xicidaili.com/wn/ 139 页
				//http://www.xicidaili.com/wt/ 国外普通代理 248 
				//http://www.xicidaili.com/qq/ socks 代理 424
		String [][] dates = new String[][]{
				{"国内高匿代理", "http://www.xicidaili.com/nn/","1"},
				{"国内普通代理", "http://www.xicidaili.com/nt/","90"},
				{"国外高匿代理", "http://www.xicidaili.com/wn/","139"},
				{"国外普通代理", "http://www.xicidaili.com/wt/","248"},
				{"SOCKS代理", "http://www.xicidaili.com/qq/","424"}};
		List<ProxyHttps> proxs = new ArrayList<ProxyHttps>();
		for(String [] data:dates){
			proxs.addAll(loadChannelProxy(data[1], data[0], Integer.parseInt(data[2])));
		}
		return proxs;
	}
	/**
	 * 获取首页的代理信息将数据写入到classpath 中的 proxy.properties 文件中
	 */
	public static void writeProxy(){
		FileOutputStream out = null;
		try {
			String path = PropertiesUtils.class.getClassLoader().getResource("proxy.properties").getPath().toString();
			out = new FileOutputStream(path);
			long startTime = System.currentTimeMillis();
			System.out.println("-----------------------存放地址:"+path+"-----------------------");
			Properties prop = new Properties();
			//获取我们的Proxy
			List<ProxyHttps> proxs = loadIndexProxy("http://www.xicidaili.com/");
			//首页的和 我们的 后面的是不一样的，所以应该换一个方法
			for(ProxyHttps obj :proxs){
				 //System.out.println(obj.getIp()+":"+obj.getPort());
				prop.setProperty(obj.getIp(), obj.getPort());
			 }
			prop.store(out, "yellowcong Proxy");
			long endTime = System.currentTimeMillis();
			System.out.println("-----------------------Proxy初始化成功----------------------------");
			System.out.println("-------------------获取条数"+prop.size()+"-------------------------");
			System.out.println("-------------------开始时间"+new Date(startTime)+"-------------------------");
			System.out.println("-------------------结束时间"+new Date(endTime)+"-------------------------");
			System.out.println("-------------------更新所用时间"+StringUtil.countTime(startTime, endTime)+"-------------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("代理数据写入失败");
		}finally{
			try {
				if(out!= null){
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void writeProxy(List<ProxyHttps> proxys){
		FileOutputStream out = null;
		try {
			String path = PropertiesUtils.class.getClassLoader().getResource("proxy.properties").getPath().toString();
			out = new FileOutputStream(path);
			long startTime = System.currentTimeMillis();
			System.out.println("-----------------------存放地址:"+path+"-----------------------");
			Properties prop = new Properties();
			//获取我们的Proxy
			//首页的和 我们的 后面的是不一样的，所以应该换一个方法
			for(ProxyHttps obj :proxys){
				 //System.out.println(obj.getIp()+":"+obj.getPort());
				prop.setProperty(obj.getIp(), obj.getPort());
			 }
			prop.store(out, "yellowcong Proxy");
			long endTime = System.currentTimeMillis();
			System.out.println("-----------------------Proxy初始化成功----------------------------");
			System.err.println("-------------------有用的代理条数\t"+prop.size()+"-------------------------");
			System.out.println("-------------------开始时间"+new Date(startTime)+"-------------------------");
			System.out.println("-------------------结束时间"+new Date(endTime)+"-------------------------");
			System.out.println("-------------------更新所用时间"+StringUtil.countTime(startTime, endTime)+"-------------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("代理数据写入失败");
		}finally{
			try {
				if(out!= null){
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取到一个栏目的Proxy,同理也是数据过多，导致程序挂掉了
	 * @param url  访问的地址前缀
	 * @param channel  栏目名称
	 * @param page  栏目的页数  1-10 页可以使用
	 * @return
	 */
	@Deprecated
	public static List<ProxyHttps> loadChannelProxy(String url,String channel,int page){
		List<ProxyHttps> proxys  = null;
		try {
			proxys = new ArrayList<ProxyHttps>();
			for(int i=0;i<page;i++){
				proxys.addAll(loadChannelProxy(url+"/"+page, channel));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("-------------------添加失败-------------------------------");
		}
		return proxys;
	}
	
	/**
	 * 获取首页栏目的数据，这个是不同于我们的栏目获取的ip的
	 * @param url
	 * @param channel
	 * @return
	 */
	public static List<ProxyHttps> loadIndexProxy(String url){
		String content = HttpRequestUtils.sendGet(url);
		List<ProxyHttps> proxys = new ArrayList<ProxyHttps>();
		
		//获取html文档,不是直接new 来实例化对象
		Document document = Jsoup.parse(content);
		
		//获取tr
		Elements eles =document.getElementsByTag("tr");
		//System.out.println("记录条数"+eles.size());
		Iterator<Element> it = eles.iterator();
		while(it.hasNext()){
			String [] date= it.next().text().split(" ");
			String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"; 
			if(date[0].matches(regex)){
				ProxyHttps https = new ProxyHttps(date[0],date[1]);
				proxys.add(https);
			}
		}
		
		return proxys;
	}
	/**
	 * "http://www.xicidaili.com/" 这个地址的proxy获取
	 * @param url
	 * @param channel 栏目
	 * @return
	 * @throws Exception 
	 */
	public static List<ProxyHttps> loadChannelProxy(String url,String channel) throws Exception{
		String content = HttpRequestUtils.sendGet(url);
		List<ProxyHttps> proxys = new ArrayList<ProxyHttps>();
		System.out.println("--------------------------爬Proxy数据-----------------------------");
		
		
		//获取html文档,不是直接new 来实例化对象
		Document document = Jsoup.parse(content);
		
		//获取tr
		Elements eles =document.getElementsByTag("tr");
		//System.out.println("记录条数"+eles.size());
		Iterator<Element> it = eles.iterator();
		
		//便利里面的数据
		while(it.hasNext()){
			Element node = it.next();
			String text = node.text();
			String [] strs = text.split(" ");
			//System.out.println(strs.length+"__"+strs[0]+"__"+strs[1]);

			String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"; 
			Pattern patten = Pattern.compile(regex);
			if( patten.matcher(strs[0]).find()){
				String guojia = StringUtil.getHtmlImage(node.getAllElements().toString());
				
				//两个速度 延迟
				String sudu  = node.select("div[class=bar]").first().attr("title").replace("秒", "");
				//System.out.println(sudu);
				//延迟
				String time = node.select("div[class=bar]").last().attr("title").replace("秒", "");
			//	System.out.println("延迟"+time);
				//1.36.132.221 3128 香港 高匿 HTTP 15-10-16 12:55
				//				15-10-14 02:58
				SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:MM");
				String ip="",port="",localtion="",toumin="",type="",date="";
				Date dateNow = null;
				if(strs.length == 6){
					//System.out.println(text);
					//106.38.194.199 80 高匿 HTTP 15-10-16 20:06
					System.out.println(text);
					ip = strs[0];
					 port = strs[1];
					 localtion = "";
					 toumin = strs[2];
					 type = strs[3];
					 date = strs[4]+" "+strs[5];
					 dateNow = format.parse(date);
					//没有位置
				}else if(strs.length == 7){
					//7个
					 ip = strs[0];
					 port = strs[1];
					 localtion = strs[2];
					 toumin = strs[3];
					 type = strs[4];
					 date = strs[5]+" "+strs[6];
					 dateNow = format.parse(date);
				}
				System.out.println(port+":"+ip);
				ProxyHttps pro = new ProxyHttps(guojia, ip,port,localtion, toumin, type, dateNow,channel, Float.parseFloat(sudu), Float.parseFloat(time));
				//public ProxyHttps(String country, String ip, String port, String localtion,
				/*String name, String type, Date createDate, String channel,
				int quickly, int time) */
				proxys.add(pro);
			}
		}
		
		return proxys;
	}
	/**
	 * 加载proxy.properties文件中的数据
	 * @return
	 */
	public static List<ProxyHttps> loadPropertisProxy(){
		InputStream in = null;
		Properties  prop = null;
		List<ProxyHttps> proxys = null;
		try {
			in = PropertiesUtils.class.getClassLoader().getResourceAsStream("proxy.properties");
			prop  = new Properties();
			prop.load(in);
			
			if(prop.size()>0){
				proxys = new ArrayList<ProxyHttps>();
				for(Map.Entry<Object, Object> entry:prop.entrySet()){
					ProxyHttps http = new ProxyHttps(entry.getKey().toString(), entry.getValue().toString());
					proxys.add(http);
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				if(in!= null){
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return proxys;
	}
	
	/**
	 * 随机获取到我们的代理数据
	 * @return
	 */
	public static ProxyHttps getRandomPropertisProxy(){
		InputStream in = null;
		Properties  prop = null;
		ProxyHttps https = null;
		List<ProxyHttps> proxys = null;
		try {
			in = PropertiesUtils.class.getClassLoader().getResourceAsStream("proxy.properties");
			prop  = new Properties();
			prop.load(in);
			
			if(prop.size()>0){
				proxys = new ArrayList<ProxyHttps>();
				for(Map.Entry<Object, Object> entry:prop.entrySet()){
					ProxyHttps http = new ProxyHttps(entry.getKey().toString(), entry.getValue().toString());
					proxys.add(http);
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				if(in!= null){
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(proxys != null){
			Random random = new Random();
			https = proxys.get(random.nextInt(proxys.size()));
			System.out.println("------------------------------代理 数量 "+proxys.size()+"------------------------------");
		}
		
		return https;
	}
	/**
	 * 判断我们的Proxy是否可以用 
	 * @param url  链接的地址
	 */
	public static void filteProxy(String url){
		/*	String proxyIP = "106.38.194.199";
			//106.38.194.199:80
			int proxyPort = 80;*/
			List<ProxyHttps> userFull = null;
			//String host = "http://www.tuicool.com/articles/UJreAz";
			
			List<ProxyHttps> lists = ProxyUtils.loadPropertisProxy();
			
			for(ProxyHttps obj:lists){
				System.out.println(obj.getIp()+":"+obj.getPort()+"-------测试中");
				int code = testProxy(url, obj.getIp(), Integer.parseInt(obj.getPort()));
				if(code == 200){
					if(userFull  == null){
						userFull = new ArrayList<ProxyHttps>();
					}
					userFull.add(obj);
				}
				System.out.println(obj.getIp()+":"+obj.getPort()+"------------"+code);
			}
			if(userFull != null ){
				ProxyUtils.writeProxy(userFull);
			}
		}
		
	/**
	 * 获取到所有可以用的proxy
	 */
	public static void initProxy(){
		String url = "http://www.xicidaili.com/";
		String host = "http://www.tuicool.com/";
		System.out.println("----------------------加载http://www.xicidaili.com/ 首页数据-----------------------------");
		//获取到首页的代理
		List<ProxyHttps>  lists = loadIndexProxy(url);
		System.out.println("----------------------加载propertis中的数据-----------------------------");
		
		//获取已经存在的
		lists.addAll(loadPropertisProxy());
		//用来存储有用的proxy
		List<ProxyHttps> userFull = null;
		System.out.println("----------------------测试所有的Proxy数据-----------------------------");
		for(ProxyHttps obj:lists){
			System.out.println(obj.getIp()+":"+obj.getPort()+"-------测试中");
			int code = testProxy(host, obj.getIp(), Integer.parseInt(obj.getPort()));
			if(code == 200){
				if(userFull  == null){
					userFull = new ArrayList<ProxyHttps>();
				}
				userFull.add(obj);
			}
			System.out.println(obj.getIp()+":"+obj.getPort()+"------------"+code);
		}
		if(userFull != null ){
			ProxyUtils.writeProxy(userFull);
		}
	}
		/**
		 * 
		 * @param host 要访问的主机
		 * @param proxyIP 代理ip
		 * @param proxyPort 代理端口
		 * @return 
		 * @throws Exception
		 */
		public static int testProxy(String host,String proxyIP,int proxyPort){
			int code = 0;
			try {
				//获取到HttpClient
				HttpClient httpClient =  new HttpClient();
				httpClient.getHostConfiguration().setHost(host);
				//设定超时  5000 毫秒的时间
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
				//设定代理
				httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
				//设定代理 ip 和端口
				httpClient.getHostConfiguration().setProxy(proxyIP, proxyPort);
				//设定代理的用户和密码
				Credentials defaultcreds = new UsernamePasswordCredentials("",  "");
				httpClient.getState().setProxyCredentials(new AuthScope(proxyIP, proxyPort, null), defaultcreds);
				
				//获取GetMethod
				GetMethod method = setGetMethod(host);
				
				if(method != null){
					 code = httpClient.executeMethod(method);
					 //获取请求的数据
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//throw new RuntimeException("-------------"+proxyIP+":"+proxyPort+"\t 无效----------");
				
			}
	        return code;
		}
		
		private static GetMethod setGetMethod(String url) {
			// TODO Auto-generated method stub
			/* 2.生成 GetMethod 对象并设置参数 */
			GetMethod getMethod = null; 
			try{
				//可能会在查询的时候出现异常，我们简单的丢去
				getMethod = new GetMethod(url);
				// 设置 get 请求超时 5s
				getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
				// 设置请求重试处理
				getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
				//Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1
				//Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070309 Firefox/2.0.0.3
				//Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070803 Firefox/1.5.0.12
				//Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; WOW64; Trident/4.0; SLCC1)
				//Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)
				//Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13
				//设置USER_AGENT
				getMethod.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070803 Firefox/1.5.0.12");
			}catch (Exception e){
				throw new RuntimeException("-------------------------请求协议存在问题-----------------------");
			}
			return getMethod;
		}
}
