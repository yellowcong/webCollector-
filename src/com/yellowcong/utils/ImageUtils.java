package com.yellowcong.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.FilenameUtils;

public class ImageUtils {
	
	private ImageUtils(){}
	/**
	 * 获取图片宽度
	 * @param file
	 * @return
	 */
	public static int getImageWidth(File file){
		try {
			BufferedImage img =ImageIO.read(file);
			return img.getWidth();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获取图片宽度
	 * @param file
	 * @return
	 */
	public static int getImageWidth(InputStream in){
		try {
			BufferedImage img =ImageIO.read(in);
			return img.getWidth();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取图片的高度
	 * @param file 传入图片文件对象
	 * @return
	 */
	public static int getImageHeight(File file){
		try {
			BufferedImage img =ImageIO.read(file);
			return img.getHeight();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取图片的高度
	 * @param in 传入图片文件对象
	 * @return
	 */
	public static int getImageHeight(InputStream in ){
		try {
			BufferedImage img =ImageIO.read(in);
			return img.getHeight();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 传入图片的输入流，然后根据给定的图片的宽度和高度来生成新的图片 宽度和高度
	 * @param in
	 * @param width
	 * @param height 
	 * @param path 需要写出的路径 
	 * @return
	 */
	public static boolean getNewImage(InputStream in,String path,int width,int height){
		boolean flag = false;
		BufferedImage img =null;
		BufferedImage buff = null;
		try {
			img = ImageIO.read(in);
			double oldHeight =  img.getHeight();
			double oldWidth = img.getWidth();
			double scaleWidth;
			double scaleHeight;
			//判断图片是否合适
			if(oldWidth>width && oldHeight>height){

				//判断是否 小于宽度的1.2,当高度大于原本的 一点点就不管了,直接将图片写出， 然后再截图
				if(oldHeight <1.2*height && oldWidth <1.2*width){
					buff = Thumbnails.of(img).scale((((double)width)/oldWidth)).asBufferedImage();
					flag = true;
				}
				
				//方案一，按照宽度缩放,宽度肯定是一是
				if(!flag){
					buff = Thumbnails.of(img).scale((((double)width)/oldWidth)*1.2).asBufferedImage();
					
					scaleHeight = buff.getHeight();
					if(scaleHeight < height){
						flag = false;
					}else{
						flag = true;
					}
				}
				
				//方案二， 按照高度来写数据, 判断高度是否 合适
				if(!flag){
					buff = Thumbnails.of(img).scale((((double)height)/oldHeight)*1.2).asBufferedImage();
					scaleWidth = buff.getWidth();
					if(scaleWidth <width){
						flag = false;
					}else{
						flag  = true;
					}
				}
				
				//写出图片 
				if(true){
					buff =  Thumbnails.of(buff).sourceRegion(Positions.CENTER,width,height).scale(1.0D).asBufferedImage();
					ImageIO.write(buff, FilenameUtils.getExtension(path), new File(path));
				}
			}else{
				//图片 大小不合适 
				flag = false;
			}
		} catch (Exception e) {
			throw new RuntimeException("图片生成失败");
		}
		return flag;
	}
	
	
	/**
	 * 传递一个文件对象 来生成一个一定规格的图片 ,根据图片的宽度 ， 修改图片生成 策略
	 * @param file
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean getNewImage(File file,int width,int height){
		boolean flag = false;
		try {
			BufferedImage img =ImageIO.read(new FileInputStream(file));
			BufferedImage buff = null;
			double oldHeight =  img.getHeight();
			double oldWidth = img.getWidth();
			double scaleWidth;
			double scaleHeight;
			//判断图片是否合适
			if(oldWidth>width && oldHeight>height){
				
				//判断是否 小于宽度的1.2,当高度大于原本的 一点点就不管了,直接将图片写出， 然后再截图
				if(oldHeight <1.2*height && oldWidth <1.2*width){
					buff = Thumbnails.of(img).scale((((double)width)/oldWidth)).asBufferedImage();
					flag = true;
				}
				
				//方案一，按照宽度缩放,宽度肯定是一是
				if(!flag){
					buff = Thumbnails.of(img).scale((((double)width)/oldWidth)*1.2).asBufferedImage();
					
					scaleHeight = buff.getHeight();
					if(scaleHeight < height){
						flag = false;
					}else{
						flag = true;
					}
				}
				
				//方案二， 按照高度来写数据, 判断高度是否 合适
				if(!flag){
					buff = Thumbnails.of(img).scale((((double)height)/oldHeight)*1.2).asBufferedImage();
					scaleWidth = buff.getWidth();
					if(scaleWidth <width){
						flag = false;
					}else{
						flag  = true;
					}
				}
				
				//写出图片 
				if(true){
					buff =  Thumbnails.of(buff).sourceRegion(Positions.CENTER,width,height).scale(1.0D).asBufferedImage();
					ImageIO.write(buff, FilenameUtils.getExtension(file.getPath()), new File(file.getPath()));
				}
			}else{
				//图片 大小不合适 
				flag = false;
			}
		} catch (Exception e) {
			throw new RuntimeException("图片生成失败");
		}
		return flag;
	}
	/**
	 * 截图传递过来坐标，宽度和高度  进行图片的截取
	 * @param x  x坐标
	 * @param y  y坐标
	 * @param w  宽度
	 * @param h  高度
	 * @param file  文件对象
	 * @return
	 */
	public static boolean cutImage(int x,int y,int w,int h,File file){
		try {
			BufferedImage img =ImageIO.read(new FileInputStream(file));
			//这个sourceRegion 传递经来的是 坐标 和 宽度 ，高度
			img = Thumbnails.of(img).sourceRegion( x, y,w,h).scale(1.0f).asBufferedImage();
			ImageIO.write(img, FilenameUtils.getExtension(file.getPath()), new File(file.getPath()));
		} catch (Exception e) {
			throw new RuntimeException("截图失败");
		}
		return false;
	}
	
}
