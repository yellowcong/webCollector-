package com.yellowcong.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

public class FileUtils {
	
	private FileUtils(){}
	
	/**
	 * 获取类路径下文件的路径
	 * @param fileName
	 * @return
	 */
	public static String getClassPathFilePath(String fileName){
		String path = FileUtils.class.getClassLoader().getResource(fileName).getPath().toString();
		if(path.contains("%20")){
			path = path.replace("%20", " ");
		}
		return path;
	}
	
	/**
	 * 获取文件对象
	 * @param fileName
	 * @return
	 */
	public static File getClassPathFile(String fileName){
		return new File(FileUtils.getClassPathFilePath(fileName));
	}
	
	/**
	 * 获取类文件的输入流
	 * @param fileName
	 * @return
	 */
	public static InputStream getClassPathFileInputStream(String fileName){
		return FileUtils.class.getResourceAsStream(fileName);
	}
	
	/**
	 * 获取一个文本文件里面的内容
	 * @param filePath
	 * @return
	 */
	public static String getFileContent(String filePath){
		FileReader in  = null;
		BufferedReader reader = null;
		try {
			in  = new FileReader(new File(filePath));
			reader = new BufferedReader(in);
			String line = null;
			StringBuffer str = new StringBuffer();
			while((line = reader.readLine())!= null){
				str.append(line);
				str.append("\r\n");
			}
			return str.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(in != null){
					in.close();
				}
				if(reader != null ){
					reader.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 获取 web目录下的路径，便于上传
	 * @param str
	 * @param request
	 * @return
	 */
	public static String getWebFilePath(String str,HttpServletRequest request){
		return request.getRealPath(str);
	}
	
	/**
	 * 通过 路径来创建文件
	 * @param path
	 */
	public static void createDirectory(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
}
