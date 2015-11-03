/**
 * 
 */
package com.wyp.WEB;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
 */
public class threadHTMLDownloader extends Thread{
	
	private String Hosturl = "http://www.dianping.com";
	private String url = null;
	private String name = null;
	//最大的页码
	private String maxPage = null;
	//次大的页码
	private String secondMaxPage = null;
	private HTMLDownloader HD = null;
	private List<Pair<String, String>> urlList = null;
	
	private String address = null;
	private String tel = null;
	private String temp = null;
	//url 菜系的URL链接
	//name 菜系的名称
	public threadHTMLDownloader(String url, String name){
		this.url = url;
		this.name = name;
		HD  = new HTMLDownloader();
		urlList = new ArrayList<Pair<String, String>>();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		getPage();
		int page = 1;
		try {
			page = Integer.parseInt(secondMaxPage);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//出现错误
			//System.exit(1);
			page = 1;
		}
		
		for(int i = 1; i <= page; i++){
			getURL(HD.downloadFile(url + "p" + i, false));
		}
		
		System.out.println(name + "\t" + urlList.size());
		for(Pair<String, String> pair : urlList){
			getShopPicAndAddress(HD.downloadFile(pair.getFirst(), false));
		}
	}
	
	/**
	 * @param downloadFile
	 * 得到商店的详细信息以及图片
	 */
	private void getShopPicAndAddress(String shopHTML) {
		//这个是用来标志 不要匹配 "公交/驾车" 这些字，我不需要他
		int num = 0;
		
		if(shopHTML == null){
			return;
		}
		
		//得到地址
		 //<dd class="shop-info-content"
		String regex = "<dd[\\s]*class=\"shop-info-content\".*?>(.*?)</dd>";
		Pattern pa = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher ma = pa.matcher(shopHTML);
		
		String regex1 = "<[span|strong]+.*?>(.*?)</[span|strong]+>";
		Pattern pa1 = Pattern.compile(regex1, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		Matcher ma1 = null;
		address = "";
		tel = "";
		while(ma.find()){
			//System.out.println(ma.group(1));
			ma1 = pa1.matcher(ma.group(1));
			num = 0;
			
			while(ma1.find()){
				//System.out.println(ma1.group(1));
				num++;
				
				if(num == 2){
					address = temp;
					temp = ma1.group(1);
					break;
				}
				//System.out.println(ma1.group(1));
				temp = ma1.group(1);
			}
			if(num == 1){
				tel = temp;
			}else{
				address += temp;
			}
		}
		
		
		//寻找图片，并下载下来
		String picPath = "";
		regex = "<div[\\s]*class=\"thumb-wrapper\">.*?<img.*?src=\"(.*?)\".*?</a>";
		pa = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		ma = pa.matcher(shopHTML);
		while(ma.find()){
			//System.out.println(ma.group(1));
			picPath = HD.downloadFile(ma.group(1), false);
		}
		
		System.out.println(address + "\t" + tel + "\t" + picPath);
		
		saveToLocal(address, tel, picPath);
		
	}
	/**
	 * @param address2
	 * @param tel2
	 * @param picPath
	 * 
	 * 保存到本地文件
	 */
	private void saveToLocal(String address, String tel, String picPath) {
		// TODO Auto-generated method stub
		BufferedWriter bw = null;
		try {
			name = name.replace("/", "");
			bw = new BufferedWriter(new FileWriter("temp\\" + name + ".cvs", true));
			bw.write(address + ";" + tel + ";" + picPath);
			bw.write(System.getProperty("line.separator"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				bw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param downloadFile
	 * 得到菜系的店铺URL以及店名
	 */
	private void getURL(String HTML) {
		if(HTML == null){
			return;
		}
		String regex = "<li[\\s]*class[\\s]*=\"shopname\">.*?<a[\\s]*href[\\s]*=[\\s]*\"(.*?)\".*?>(.*?)</a>";
		Pattern pa = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher ma = pa.matcher(HTML);
		String shopURL = "";
		String shopName = "";
		while(ma.find()){
			shopURL = ma.group(1);
			shopName = ma.group(2);
			urlList.add(new Pair<String, String>(Hosturl + shopURL, shopName));
			//System.out.println(Hosturl + shopURL + "\t" + shopName);
		}
	}
	
	//得到页码
	private void getPage(){
		//System.out.println(HD.downloadFile(url));
		String HTML = HD.downloadFile(url, false);
		if(HTML == null){
			return;
		}
		//<a href="/search/category/7/10/g422p50" class="PageLink" title="50" onclick="pageTracker._trackPageview('dp_search_category_more_page_50_shenzhen_food')">50
		String regex = "<div[\\s]*class=\"Pages\">(.*?)</div>";
		Pattern pa = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher ma = pa.matcher(HTML);
		String find = "";
		while(ma.find()){
			find = ma.group(1);
			//System.out.println(name + "\t" + find);
			break;
		}
		
		regex = "<a.*?>(.*?)</a>";
		pa = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		ma = pa.matcher(find);
		
		//因为正则表达式的最后一个匹配到的是 "下一页",而这个前面匹配到的肯定就是最大的页面了 
		while(ma.find()){
			find = ma.group(1);
			secondMaxPage = maxPage;
			maxPage = find;
		}
		//System.out.println(name + "\t" + secondMaxPage);
	}	
}
