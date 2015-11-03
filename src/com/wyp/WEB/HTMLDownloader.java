/**
 * 
 */
package com.wyp.WEB;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.wyp.utils.proxyServer;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @author 过往记忆
 * blog: www.wypblog.com
 * Create Data: 2012-8-9
 * Email: wyphao.2007@163.com
 * 
 * 版权所有，翻版不究，但在修改本程序的时候必须加上这些注释！
 * 仅用于学习交流之用
 */
public class HTMLDownloader {
	
	//用来链接服务器的类
	HttpClient httpClient = null;
	
	//用来处理读取到的网页
	private String line = null;
	//用来储存网页源码的
	String html = "";
	
	//代理服务器
	String proxyIP = proxyServer.proxyIP[1];
	//代理服务器端口
	int proxyPort = proxyServer.proxyPort[1];
	
	private String Hosturl = "www.dianping.com";
	int hostPort = 80;
	String chatset = "utf-8";
	public HTMLDownloader(){
		httpClient = new HttpClient();
		// 设置 Http 连接超时 5s
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
	}
	
	//isForbidden是标志是否被人家限制了IP
	public String downloadFile(String url, boolean isForbidden){
		String filePath = null;
		
		url = (url.startsWith("http://") || url.startsWith("https://")) ? url : ("http://" + url).intern();
		
		//被限制访问了，我们就设置代理
		if(isForbidden){
			setProxy(proxyIP, proxyPort);
		}
		
		GetMethod getMethod = setGetMethod(url);
		if(getMethod == null){
			System.out.println("请求协议设置都搞错了，所以我无法完成您的请求");
			System.exit(1);
		}
		
		/* 3.执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			
			// 判断访问的3XX状态码
			if (statusCode == HttpStatus.SC_OK) { //2XX状态码
				// 根据网页 url 生成保存时的文件名
				Header header = getMethod.getResponseHeader("Content-Type");
				if(header != null){
					String applicationType = header.getValue();
					if(applicationType != null){
						if(applicationType.indexOf("html") != -1){//网页格式
							/* 4.处理 HTTP 响应内容 */
							InputStream input = getMethod.getResponseBodyAsStream();
							//处理收到的内容以及乱码
						    BufferedReader reader = null;
						    
						    reader = new BufferedReader(new InputStreamReader(input, chatset));
						    
						    StringBuffer sb = new StringBuffer();
					        while ((line = reader.readLine()) != null) {
					            sb.append(line).append(System.getProperty("line.separator"));
					        }
					        
					        html = sb.toString();
					       
						}else{
							/* 4.处理 HTTP 响应内容 */
							filePath = getFileNameByUrl(url, applicationType);
							byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
							saveOtherToLocal(responseBody, filePath);
							html = filePath;
						}
					}
				}
			}else if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
					|| (statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (statusCode == HttpStatus.SC_SEE_OTHER)
					|| (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
				return null;
			}else if(statusCode == HttpStatus.SC_FORBIDDEN){	//访问被人家限制了就设置代理
				//代理服务器的个数
				int proxySize = proxyServer.proxyIP.length;
				int i = 0;
				for(; i < proxySize; i++){
					//我们一个一个测试代理服务器的有用性
					System.out.println("正在测试代理：" + proxyServer.proxyIP[i] + ":" + proxyServer.proxyPort[i]);
					int status = testProxyServer(url, proxyServer.proxyIP[i], proxyServer.proxyPort[i]);
					if(status == HttpStatus.SC_OK){//代理服务器找到了
						break;
					}else{ //其他情况你就继续去代理吧
						continue;
					}
				}
				
				if(i >= proxySize){
					System.out.println("唉，我把你设置的代理服务器都测试了，好像没有发现有效的代理，我只有退出了！");
					return null;
				}
				
				System.out.println("代理：" + proxyServer.proxyIP[i] + ":" + proxyServer.proxyPort[i] + "目前可用");
				proxyIP = proxyServer.proxyIP[i];
				proxyPort = proxyServer.proxyPort[i];
				return downloadFile(url, true);
			} else{	//其他状态码，简单丢去
				//System.err.println("Method failed: " + getMethod.getStatusLine());
				return null;
			}			
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			//e.printStackTrace();
			return null;
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
			return null;
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		System.out.println(html);
		return html;
	}
	
	
	/**
	 * 
	 */
	private GetMethod setGetMethod(String url) {
		// TODO Auto-generated method stub
		/* 2.生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = null; 
		try{
			//可能会在查询的时候出现异常，我们简单的丢去
			getMethod = new GetMethod(url);
		}catch (IllegalArgumentException e){
			return null;
		}
		
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
		return getMethod;
	}

	/**
	 * 设置代理
	 */
	private void setProxy(String proxyIP, int hostPort) {
		System.out.println("正在设置代理：" + proxyIP + ":" + hostPort);
		// TODO Auto-generated method stub
		httpClient.getHostConfiguration().setHost(Hosturl, hostPort, "http");
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getHostConfiguration().setProxy(proxyIP, proxyPort);
        Credentials defaultcreds = new UsernamePasswordCredentials("",  "");
        httpClient.getState().setProxyCredentials(new AuthScope(proxyIP, proxyPort, null), defaultcreds);
	}

	
	/**
	 * @param string
	 * @param i
	 * 
	 * 测试代理服务器的可用性
	 * 
	 * 只有返回HttpStatus.SC_OK才说明代理服务器有效
	 * 其他的都是不行的
	 */
	private int testProxyServer(String url, String proxyIp, int proxyPort) {
		// TODO Auto-generated method stub
		setProxy(proxyIp, proxyPort);
		GetMethod getMethod = setGetMethod(url);
		if(getMethod == null){
			System.out.println("请求协议设置都搞错了，所以我无法完成您的请求");
			System.exit(1);
		}
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) { //2XX状态码
				return HttpStatus.SC_OK;
			}else if(statusCode == HttpStatus.SC_FORBIDDEN){ //代理还是不行
				return HttpStatus.SC_FORBIDDEN;
			}else{	//	其他的错误
				return 0;
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			System.exit(1);
		} catch (IOException e) {
			// 发生网络异常
			System.exit(1);
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return 0;
	}

	/**
	 * 保存图片 去除掉 url 中非文件名字符
	 */
	public String getFileNameByUrl(String url, String contentType) {
		url = url.substring(7);// remove http://
		String dirs = "temp" + File.separator;
		int i = 0;
		
		/**
		 * 可能在URL里面有一些特殊的符号，因为我们保存文件是以URL链接为路径，
		 * windows不支持一些特殊的字符为文件或者目录名，这样可能就会导致我们
		 * 不能保存这些文件，所以我们需要去掉这些特殊字符。
		 */
		url = url.replaceAll("[\\?:*|<>\"]", "_");
		String tempDir [] = url.split("/");

		//有可能一开始就是访问到主机的URL，这时我们就用这个主机URL作为保存目录
		if(tempDir.length == 1){
			dirs += tempDir[i] + File.separator;
		}
		
		//我们不希望以文件名作为目录名，所以要把tempDir最后一个元素另外处理
		for(i = 0; i < tempDir.length - 1; i++){
			dirs += tempDir[i] + File.separator;
		}
		
		//for循环之后得到了路径，下面在我们电脑里面建立这个路径
		File dirFile = new File(dirs);
		//这个路径不存在则创建
		if(!dirFile.exists()){
			if(!dirFile.mkdirs()){
				System.err.println("创建目录失败" + dirs);
				return null;
			}
		}	
			dirs += tempDir[i];
			if(tempDir[i].indexOf('.') == -1){
				dirs += "." + contentType.substring(contentType.lastIndexOf("/") + 1);
			}
			return dirs;
	}
	
	/**
	 * 保存图片等数组到本地文件 filePath 为要保存的文件的相对地址，需要一个一个字节的保存，
	 * 否则保存的文件将不能读取
	 */
	private void saveOtherToLocal(byte[] data, String filePath) {
		// TODO Auto-generated method stub
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(new File(filePath)));
			for (int i = 0; i < data.length; i++)
				out.write(data[i]);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				out.flush();
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	// 测试的 main 方法
	public static void main(String[] args) {
		HTMLDownloader downLoader = new HTMLDownloader();
		String str = downLoader.downloadFile("http://www.tuicool.com/articles/UJreAz", true);
		System.out.println(str);
		//http://www.dianping.com/search/category/7/10
	}
}
