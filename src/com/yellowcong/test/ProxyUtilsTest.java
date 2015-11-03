package com.yellowcong.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;
import org.springframework.test.annotation.ExpectedException;

import com.yellowcong.model.ProxyHttps;
import com.yellowcong.utils.HttpClientUtils;
import com.yellowcong.utils.ProxyUtils;

public class ProxyUtilsTest {

	@Test
	public void testWriteProxy(){
		ProxyUtils.writeProxy();
	}
	
	
	@Test
	public void testFilteProxy(){
		ProxyUtils.filteProxy("http://www.tuicool.com/articles/UJreAz");
	}
	
	@Test
	public void testInitProxy(){
		ProxyUtils.initProxy();
	}
	public static void filteProxy(String url){
		/*	String proxyIP = "106.38.194.199";
			//106.38.194.199:80
			int proxyPort = 80;*/
			List<ProxyHttps> userFull = null;
			String host = "http://www.tuicool.com/articles/UJreAz";
			
			List<ProxyHttps> lists = ProxyUtils.loadPropertisProxy();
			
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
	
		@Test
		public void testLoadrandom() throws Exception{
			while(true){
				ProxyHttps proxy = ProxyUtils.getRandomPropertisProxy();
				System.out.println(proxy.getIp()+":"+proxy.getPort());
				Thread.sleep(500);
			}
		}
}	
