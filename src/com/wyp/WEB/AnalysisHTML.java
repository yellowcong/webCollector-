/**
 * 
 */
package com.wyp.WEB;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wyp.utils.Pair;
/**
 * @author 过往记忆
 * blog: www.wypblog.com
 * Create Data: 2012-8-9
 * Email: wyphao.2007@163.com
 * 
 * 版权所有，翻版不究，但在修改本程序的时候必须加上这些注释！
 * 仅用于学习交流之用
 * 本类主要是获取菜系，然后有几个菜系就生成几个线程去爬取具体的网站
 */
public class AnalysisHTML {
	//存储菜系的链接
	public List<Pair<String, String>> urlList = null;
	private HTMLDownloader hd = null;
	private String Hosturl = "http://www.dianping.com";
	private String HTMLSrc = "";
	
	public AnalysisHTML(String url){
		hd = new HTMLDownloader();
		urlList = new ArrayList<Pair<String,String>>();
		
		HTMLSrc = hd.downloadFile(url, false);
		if(HTMLSrc == null){
			System.exit(1);
		}
		
		//System.out.println(HTMLSrc);
		fingURL(HTMLSrc);
		analysisCaiXi();
		
	}
	
	
	/**
	 * 分析菜系
	 */
	private void analysisCaiXi() {
		// TODO Auto-generated method stub
		for(Pair<String, String> pair : urlList){
			new threadHTMLDownloader(pair.getFirst(), pair.getSecond()).start();
		}
	}


	/**
	 * @param html
	 * 在源码中寻找URL连接
	 */
	private void fingURL(String html) {
		String regex = "<div[\\s]+class=\"aside aside-left\">(.*?)</ul>";
		Pattern pa = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher ma = pa.matcher(html);
		String find = "";
		while(ma.find()){
			//System.out.println(ma.group(1));
			find = ma.group();
			break;
		}
		
		regex = "<ul>(.*?)</ul>";
		pa = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		ma = pa.matcher(find);
		while(ma.find()){
			find = ma.group(1);
			break;
		}
		
		//寻找菜系
		regex = "<li>[\\s]?<a[\\s]*href[\\s]*=[\\s]*\"(.*?)\".*?>(.*?)<span.*?/li>";
		pa = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		ma = pa.matcher(find);
		while(ma.find()){
			System.out.println(Hosturl + ma.group(1) + "\t" + ma.group(2));
			Pair<String, String> pair = new Pair<String, String>(Hosturl + ma.group(1), ma.group(2).replaceAll("&nbsp;", ""));
			urlList.add(pair);
		}
		
		//System.out.println(urlList.size());
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AnalysisHTML("http://www.dianping.com/search/category/7/10");
		
		//new AnalysisHTML("http://i2.dpfile.com/2009-10-31/3049378_m.jpg");
	}

}
