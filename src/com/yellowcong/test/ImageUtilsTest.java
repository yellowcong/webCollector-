package com.yellowcong.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

import com.yellowcong.utils.HttpImageUtils;
import com.yellowcong.utils.StringUtil;

public class ImageUtilsTest {

	
	@Test
	public void testDownLoadImage(){
		
		System.out.println("-----------图片下载------------");
		String imageName = "";
		//存放地址
		String path = "D:/code/java/img/";
		FileOutputStream out   = null;
		InputStream in = null;
		try {
			String urlPathStr = "http://img0.tuicool.com/iUVfqa.png";
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
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getImage(){
		String str = "http://img0.tuicool.com/iUVfqa.png";
		String [] data = str.split("/");
		str = data[data.length -1];
		System.out.println(str);
	}
	
	@Test
	public void testDownload(){
		HttpImageUtils.downloadImage("http://img0.tuicool.com/Jb2InaJ.jpg");
	}
}
