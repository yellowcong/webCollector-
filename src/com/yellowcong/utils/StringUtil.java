package com.yellowcong.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StringUtil {
	/**
	 * 判断字符串是否是整数
	 * @param number
	 * @return
	 */
	public static boolean isInteger(String number) {
		boolean isNumber = false;
		if (StringUtil.isNotEmpty(number)) {
			isNumber = number.matches("^([1-9]\\d*)|(0)$");
		}
		return isNumber;
	}
	/**
	 * 判断字符串不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		boolean isNotEmpty=false;
		if(str!=null && !str.trim().equals("") && !str.trim().equalsIgnoreCase("NULL")){
			isNotEmpty=true;
		}
		return isNotEmpty;
	}
	/**
	 * 判断字符串为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return !isNotEmpty(str);
	}
	/**
	 * 将数组转成SQL认识的字符串     123,432,2312     id in('123','432','2312')
	 * @param ids
	 * @return
	 */
	public static String arrayToStr(String[] ids) {
		StringBuffer str=new StringBuffer();
		for(int i=0;i<ids.length;i++){
			str.append("'"+ids[i]+"',");
		}
		if(ids.length>0){
			str.deleteCharAt(str.length()-1);
		}
		return str.toString();
	}
	
	/**
	 * 将传递过来Str转化为 数字数组
	 * @param str
	 * @return
	 */
	public static int[] strToArray(String str){
		String [] idStrs = str.split(",");
		int [] ids = new int[idStrs.length];
		for(int i=0;i<idStrs.length;i++){
			ids[i] = Integer.parseInt(idStrs[i]);
		}
		return ids;
	}
	
	/**
	 * 给数据进行md5加密
	 * @param message
	 * @return
	 */
	public static String md5(String message)
	  {
	    try
	    {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      
	      md.update(message.getBytes());
	      return new BigInteger(1, md.digest()).toString(16);
	    }
	    catch (Exception e)
	    {
	      throw new RuntimeException("MD5转码失败");
	    }
	  }
	
	/**
	 * 通过老文件的名称自动生成新的文件
	 * @param oldName 
	 * @return
	 */
	public static String newName(String oldName){
		String [] datas = oldName.split("\\.");
		String type = datas[datas.length-1];
		String newName  = UUID.randomUUID().toString()+"."+type;
		return newName;
	}
	
	/**
	 * 通过老文件的名称自动生成新的文件
	 * @param oldName 
	 * @return
	 */
	public static String getFileType(String fileName){
		String [] datas = fileName.split("\\.");
		String type = datas[datas.length-1];
		return type;
	}
	
	/**
	 * 清除html中的所有的标签
	 * @param htmlStr
	 * @return
	 */
	public static String removeHtmlTags(String htmlStr){
		String textStr = "";
		String scriptReg = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
		String styleReg = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
		String htmltReg = "<[^>]+>";
		/**
		 * 定义html页面中的匹配起
		 */
		Pattern script = Pattern.compile(scriptReg,Pattern.CASE_INSENSITIVE);
		textStr = script.matcher(htmlStr).replaceAll("");
		Pattern style =Pattern.compile(styleReg,Pattern.CASE_INSENSITIVE);
		textStr = style.matcher(textStr).replaceAll("");
		Pattern html =Pattern.compile(htmltReg,Pattern.CASE_INSENSITIVE);;
		textStr = html.matcher(textStr).replaceAll("");
		return textStr;
	}
	
	/**
	 * 移除字符串中的 回车、换行、空格、制表符
	 * @param htmlStr html 的字符串
	 * @return
	 */
	public static String removeBlank(String htmlStr){
		String textStr = "";
		String blankReg = "\\s*|\t\r\n";
		textStr = Pattern.compile(blankReg, Pattern.CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
		return textStr;
	}
	
	/**
	 * 判断我们的数据 中，是否有中文字符
	 * @param str
	 * @return
	 */
	public boolean isChineseChar(String str){
		boolean flag = false;
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
		if(pattern.matcher(str).find()){
			flag = true;
		}
		return flag;
	}
	
	public static String newPath(String filePath){
		if(filePath.contains("%20")){
			filePath = filePath.replace("%20", " ");
		}
		return filePath;
	}
	/**
	 * 给定最大值和 生成的数组个数 就会返回一个数组对象
	 * @param size  生成的数组大小
	 * @param max  最大值
	 * @return
	 */
	public static int[] randomInt(int size,int max){
		int[] intRet = new int[size];
        int intRd = 0; //存放随机数
        int count = 0; //记录生成的随机数个数
        int flag = 0; //是否已经生成过标志
        Random random = new Random();
        while(count<size){
             //产生随机数
             intRd = random.nextInt(max);
             //判断是否相等
             for(int i=0;i<count;i++){
	                 if(intRet[i]==intRd){
	                     flag = 1;
	                     break;
	                 }else{
	                     flag = 0;
	                 }
	             }
	             if(flag==0){
	                 intRet[count] = intRd;
	                 count++;
	             }
	    }
        return intRet;
	}
	
	/**
	 * 获取HTml中的图片数据
	 * @param htmlStr
	 * @return
	 */
	public static List<String> getHtmlImages(String htmlStr){
		List<String> list = new ArrayList<String>();
		String reg = "<\\s*img(.+?)src=[\"'](.+?)[\"']\\s*/?\\s*>";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(htmlStr);
		while(matcher.find()){
			//获取图片地址 url
			list.add(matcher.group(2).split("\"")[0]);
		}
		return list;
	}
	/**
	 * 获取一张图片
	 */
	public static String getHtmlImage(String htmlStr){
		List<String> list = new ArrayList<String>();
		boolean hasImage = false;
		String reg = "<\\s*img(.+?)src=[\"'](.+?)[\"']\\s*/?\\s*>";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(htmlStr);
		while(matcher.find()){
			//获取图片地址 url
			list.add(matcher.group(2).split("\"")[0]);
			
			hasImage = true;
		}
		if(hasImage){
			return list.get(0);
		}else{
			return "";
		}
		
	}
	
	
	/**
	 * 去掉带有Emoji的文字 ，否则数据库会报错
	 * @param str
	 * @return
	 */
	public static String removeNonBmpUnicode(String str) {    
		   if (str == null) {    
		       return null;    
		   }    
		   str = str.replaceAll("[^\\u0000-\\uFFFF]", "");    
		  return str;    
	} 
	
	/**
	 * 判断传递过来的字符串是否是ip
	 * @param str 传递过来的字符串
	 * @return
	 */
	public static boolean isIp(String str){
		boolean flag = false;
		if(str.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 移除 xml文件中的bom 数据问题
	 * utf-8 编码
	 * @param content
	 * @return
	 */
    public static String removeBom(String content){
    	String result = null;
    	try {
			byte [] dates = content.getBytes("UTF-8");
			result = new String(dates,3,dates.length-3,"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
	
	/**
	 * 将毫秒转化消耗时间
	 * @param date
	 * @return
	 */
	public static String countTime(long start,long end){
		StringBuffer buff = new StringBuffer();
		long date = (end - start)/1000;
		//计算天数
		long day = date/(24*60*60);
		buff.append(day+"天");
		
		long hh = date%(24*60*60)/(60*60);
		buff.append(hh+"时");
		long mm = date%(24*60*60)%(60*60)/60;
		buff.append(mm+"分");
		long ss = date%(24*60*60)%(60*60)%60;
		buff.append(ss+"秒");
		String str  = buff.toString();
		if(day == 0){
			str = str.replace("0天", "");
			if(hh ==  0){
				str = str.replace("0时", "");
				if(mm == 0){
					str = str.replace("0分", "");
				}
			}
		}
		
		
		return str;
	}
	
	public static String getHttpImageName(String str){
		String [] data = str.split("/");
		str = data[data.length -1];
		return str;
	}
	
}
