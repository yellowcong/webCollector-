package com.yellowcong.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.FileDataSource;

import org.apache.commons.beanutils.BeanUtils;


/**
 * 写一个 关于Property的工具类，以前的工具类 丢了
 * 
 * @author 狂飙のyellowcong 2015年7月29日
 *
 *依赖 FileUtils BeanUtils
 */
public class PropertiesUtils {
	private PropertiesUtils() {
	}

	/**
	 * 
	 * 将我们的Bean 转化成property
	 * @param obj
	 * @param propFile
	 */
	public static void bean2Property(Object obj,String propFile) {
		try {
			Field [] fields = obj.getClass().getDeclaredFields();
			Properties prop = new Properties();
			
			for(Field field:fields){
				String key = field.getName();
				String val =BeanUtils.getProperty(obj, key);
				if(val == null || val.trim().equals("")){
					val = "";
				}
				prop.setProperty(key, val);
			}
			
			PropertiesUtils.writeProp(prop, propFile);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 将我们的property 中的一个对象转化为一个了类对象
	 * @param propFile
	 * @param clazz
	 * @return
	 */
	public static <T> T property2Bean(String propFile, Class<T> clazz) {
		try {
			Properties prop = PropertiesUtils.loadProp(propFile);

			Object obj = clazz.newInstance();
			for (Map.Entry<Object, Object> entry : prop.entrySet()) {
				BeanUtils.copyProperty(obj, entry.getKey().toString(),
						entry.getValue());
			}
			return (T) obj;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	

	/**
	 * 
	 * @param list
	 * @param propFile
	 */
	public static <T> void list2Property(List<T> list,String propFile) {
		
		try {
			Properties prop = new Properties();
			for(T obj  :list){
				Field []  fields =obj.getClass().getDeclaredFields();
				for(Field field:fields){
					String key = field.getName();
					String val = BeanUtils.getProperty(obj, key);
					if(val == null || val.trim().equals("")){
						val = "";
					}
					prop.setProperty(key, val);
				}
			}
			
			PropertiesUtils.writeProp(prop, propFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载我们的Property 文件
	 * @param propFile
	 * @return
	 */
	private static Properties loadProp(String propFile){
		Properties prop;
		try {
			InputStream in = PropertiesUtils.class.getClassLoader()
					.getResourceAsStream(propFile);
			prop = new Properties();
			prop.load(in);
			return prop;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 写propties对象到文件中
	 * @param prop
	 * @param propFile
	 */
	public static void writeProp(Properties prop,String propFile){
		try {
			String path = PropertiesUtils.class.getClassLoader().getResource("").getPath().toString();
			String filePath = path+propFile;
			if(filePath.contains("%20")){
				filePath = filePath.replace("%20", " ");
			}
			//判断文件是否存在，若不存在就直接创建一个对象
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
			//存储文件
			prop.store(new FileOutputStream(filePath), "网站更细日期"+new SimpleDateFormat("yyy-MM-dd").format(new Date()));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
