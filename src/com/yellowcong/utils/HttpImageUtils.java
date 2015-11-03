package com.yellowcong.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 网络图片下载工具
 * @author yellowcong
 * @createDate 2015年10月25日
 *
 */
public class HttpImageUtils {
	//存放地址
	private static String path = "E:/BaiduYunDownload/img3/";
	//
	public static boolean downloadImage(String urlPathStr){
		String imageName = "";
		FileOutputStream out   = null;
		InputStream in = null;
		boolean flag = false;
		try {
			imageName = StringUtil.getHttpImageName(urlPathStr);
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
			
			in = conn.getInputStream();
			
			out = new FileOutputStream(path+imageName);
			byte [] buff = new byte[1024];
			int len = 0;
			while((len = in.read(buff))!= -1){
				//写数据
				out.write(buff, 0, len);
			}
			System.err.println("------------------"+imageName+"下载成功------------------------");
			flag = true;
		} catch(FileNotFoundException e1){
			//System.err.println("------图片没有 找到---------");
			flag = false;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
