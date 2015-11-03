package com.yellowcong.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 * 通过这个工具类，来发送数据请求信息
 * @author 狂飙のyellowcong
 * 2015年8月15日
 *
 */
public class HttpRequestUtils {
	private HttpRequestUtils(){}
	
	
	/**
	 * 给定义URL 然后就访问数据了
	 * @param urlPath
	 * @return
	 */
	public static String sendGet(String urlPath){
		return HttpRequestUtils.sendGet(urlPath,null);
	}
	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
	public static String sendGet(String urlPath, String param){
		  String result = "";
	      BufferedReader buff = null;
	      
	     try {
	    	 String urlPathStr = null;
	    	if(param  == null  || "".equals(param.trim())){
	    		urlPathStr = urlPath;
	    	}else{
	    		urlPathStr = urlPath + "?" + param;
	    	}
			//获取URL
			URL url = new URL(urlPathStr);
			
	        //获取连接
			URLConnection conn  = url.openConnection();
			//设定连接的属性
			conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("conn", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //百度
            //Baiduspider+(+http://www.baidu.com/search/spider.htm)
            //火狐
            //Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)
            //设定 ip 设定成百度的
            conn.setRequestProperty("X-Forwarded-For", "117.28.255.37");
            conn.setRequestProperty("Client-Ip", "117.28.255.37");
           //X-Forward-For: 117.28.255.37
            //Client-Ip: 117.28.255.37
            
            //打开连接
            conn.connect();
            //获取结果
            buff  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //设定结果
           String line  = null;
            while((line = buff.readLine())!= null){
            	result +=line;
            }
            return result;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				//关闭连接
				if(buff != null){
					buff.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
	public static String sendPost(String urlPath, String param){
		  String result = "";
	      BufferedReader buff = null;
	      //向别的服务器些数据
	      PrintWriter writer = null;
	      try {
			URL url = new URL(urlPath);
			
			 URLConnection conn =url.openConnection();
				//设定连接的属性
				conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("conn", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	           
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            
	            //通过 PrintWriter写数据到 服务器中
	            writer = new PrintWriter( conn.getOutputStream());
	            //添加参数
	            writer.print(param);
	            writer.flush();
	            
	            //获取结果
	            //获取结果
	            buff  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            //设定结果
	           String line  = null;
	            while((line = buff.readLine())!= null){
	            	result +=line;
	            }
	            return result;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				if(writer != null){
					writer.close();
				}
				
				if(buff != null){
					buff.close();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	/**
	 * 给定一个URL 进行post访问
	 * @param urlPath
	 * @return
	 */
	public static String sendPost(String urlPath){
		return HttpRequestUtils.sendPost(urlPath,null);
	}
	
	/**
	 * 需要通过post方法 推送数据到服务器中
	 * urlPath 百度的推送路径 http://data.zz.baidu.com/urls?site=hkjhjgc.duapp.com&token=vLebVefzx38Zin5D
	 * urls 推送的路径地址
	 * @return
	 */
	public static String post2Bae(String urlPath,String[] urls){
		String result = "";
	      BufferedReader buff = null;
	      //向别的服务器些数据
	      PrintWriter writer = null;
	      try {
				URL url = new URL(urlPath);
				 URLConnection conn =url.openConnection();
				//设定连接的属性
	            conn.setRequestProperty("Host","data.zz.baidu.com");  
	            conn.setRequestProperty("User-Agent", "curl/7.12.1");  
	            conn.setRequestProperty("Content-Length", "83");  
	            conn.setRequestProperty("Content-Type", "text/plain");  
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            
	            //通过 PrintWriter写数据到 服务器中
	            writer = new PrintWriter( conn.getOutputStream());
	            String param = "";  
	            for(String s : urls){  
	                param += s+"\n";  
	            } 
	            writer.print(param.trim());
	            writer.flush();
	            
	            //打印标签头的数据
	            /*Map<String, List<String>>   fields =  conn.getHeaderFields();
	            for(Map.Entry<String, List<String>> entry:fields.entrySet()){
	            	System.out.println(entry.getKey()+":"+entry.getValue());
	            }*/
	            //获取结果
	            //获取结果
	            buff  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            //设定结果
	           String line  = null;
	            while((line = buff.readLine())!= null){
	            	result +=line;
	            }
	            return result;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				if(writer != null){
					writer.close();
				}
				
				if(buff != null){
					buff.close();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	//http://hkjhjgc.duapp.com/passage/show/58
	/**
	 * 推送数据到百度中,我们需要 文章id 来添加数据 ， 自动生成文章的url访问
	 * @param pid
	 * @return
	 */
	public static String postPassage2Bae(int pid){
		String purl ="http://hkjhjgc.duapp.com/passage/show/"+pid;
		String [] urls = new String [] {purl};
		//其中访问的url地址要弄清楚了，不要在搞飞机的搞错了
		return HttpRequestUtils.post2Bae("http://data.zz.baidu.com/urls?site=hkjhjgc.duapp.com&token=vLebVefzx38Zin5D",urls);
	}
}
