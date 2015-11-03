package com.yellowcong.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mchange.v2.async.StrandedTaskReporting;
import com.yellowcong.dto.PassageImageDto;
import com.yellowcong.model.Links;
import com.yellowcong.model.Passage;
import com.yellowcong.model.PassageImages;
import com.yellowcong.model.Summarys;
import com.yellowcong.service.LinksService;
import com.yellowcong.service.SummaryService;
import com.yellowcong.service.TuikuService;
import com.yellowcong.utils.HttpClientUtils;
import com.yellowcong.utils.HttpRequestUtils;
import com.yellowcong.utils.StringUtil;

@Service("tuikuService")
public class TuikuServiceImpl implements TuikuService {
	private SummaryService summaryService;
	private LinksService linksService;
	@Resource(name="summaryService")
	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}
	
	
	@Resource(name="linksService")
	public void setLinksService(LinksService linksService) {
		this.linksService = linksService;
	}

	// http://www.tuicool.com/articles/emMrMrj
	public PassageImageDto loadPsg(String url) {
		Passage psg = null;
		PassageImageDto dto  = new PassageImageDto();
		try {
			String result = HttpRequestUtils.sendGet(url);
			//System.out.println("数据----------------------"+result);
			// http://www.tuicool.com/articles/6FBfqe3
			// String result =
			// HttpRequestUtils.sendGet("http://www.tuicool.com/articles/6FBfqe3");
			/*
			 * FileReader in = new FileReader(
			 * "C:\\Users\\yellowcong\\Desktop\\阿里云上创业来了！会记得阿里软件的免费之败吗？ - 推酷.htm"
			 * ); char [] buff = new char[1024]; StringBuffer buffer = new
			 * StringBuffer(); while((in.read(buff))!= -1){ buffer.append(new
			 * String(buff,0,buff.length)); } String result = buffer.toString();
			 * in.close();
			 */

			// System.out.println(result);
			// result =
			// result.substring(result.indexOf("span8 contant article_detail_bg")-12,
			// result.indexOf("span4 article_right_side")-12);
			// 文章标题
			String title = result.substring(result.indexOf("<h1>") + 4,
					result.indexOf("</h1>"));
			// System.out.println("文章标题:\t"+title);
			// 文章日期
			String date = result.substring(
					result.indexOf("class=\"timestamp\"") + 26,
					result.indexOf("<span class=\"from\"") - 22);
			// 2015-10-15 16:42:29
			// System.out.println("创建日期:\t"+date);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
			Date dateNow = format.parse(date);

			// 获取关键字
			String keywordStr = result.substring(
					result.indexOf("<span>主题") + 20,
					result.indexOf("class=\"article_body\"") - 5);
			// System.out.println(keywordStr);
			String[] keywords = StringUtil.removeHtmlTags(keywordStr)
					.replace(" ", ",").split(",");
			Set<String> keys = new HashSet<String>();
			StringBuffer buffer = new StringBuffer();
			for (String key : keywords) {
				// System.out.println("关键字"+key);
				if (key != null && !"".equals(key.trim())) {
					buffer.append(key + ",");
				}
			}

			keywordStr = buffer.toString().trim();
			keywordStr = keywordStr.substring(0, keywordStr.length() - 1);
			// 获取文章来源
			String source = result.substring(
					result.indexOf("class=\"source\">") + 20,
					result.indexOf("<span>主题"));
			source = StringUtil.removeHtmlTags(source).replace("原文&nbsp;", "")
					.trim();
			// System.out.println("文章来源:\t"+source);

			// 文章专栏
			// csdn tuiku傻的
			String channel = result.substring(
					result.indexOf("class=\"from\"") - 6,
					result.indexOf("class=\"source\"") - 5);
			channel = StringUtil.removeHtmlTags(channel).trim();
			// System.out.println("文章栏目:\t"+channel);

			// 文章内容
			String content = result.substring(
					result.indexOf("class=\"article_body\"") - 6,
					result.indexOf("class=\"article_social\"") - 5);
			// System.out.println("文章内容:\t"+content);

			// 获取content里面的图像
			List<String> imgs;
			List<PassageImages> psgImgs = null;
			String imgUrl = "";
			if (content.contains("<img")) {
				// System.out.println("有图片");
				// String reg = "<\\s*img(.+?)src=[\"'](.+?)[\"']\\s*/?\\s*>";
				/*
				 * Pattern pattern = Pattern.compile(reg); Matcher matcher =
				 * pattern.matcher(content); while(matcher.find()){
				 * System.out.println("图片"+matcher.group(0)); }
				 */
				psgImgs = new  ArrayList<PassageImages>();
				imgs = StringUtil.getHtmlImages(content);
				//System.err.println("--------------------------图片数量"+imgs.size());
				for(String str:imgs){
					PassageImages img = new PassageImages(str,new Date());
					psgImgs.add(img);
				}
				
				/*
				 * for(String str:imgs){ System.out.println("图片地址\t"+str); }
				 * System.out.println( "图片数量:\t"+imgs.size());
				 */
				imgUrl = imgs.get(0);
			}

			/*
			 * String title, Date createDate, String keyWords, String sourceUrl,
			 * String sourceChannel, String indexImage, String content
			 */
			// 创建对象
			psg = new Passage(title, dateNow, keywordStr, source, channel,
					imgUrl, content);
			dto.setPassage(psg);
			dto.setPassageImages(psgImgs);
		} catch(StringIndexOutOfBoundsException e2){
			System.err.println("--------------------------文章转化失败----------------------------------");
			
			if(url != null){
				//添加 link
				Links links = new Links("0", url, new Date());
				links = this.linksService.add(links);
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

	/**
	 * 通过url获取 到 获取推酷目录中文章的条数目中的Summary 数据的条数 一共有20页面
	 * http://www.tuicool.com/ah/0/4?lang=1
	 * 
	 */
	public List<Summarys> loadSummarys(String url, String channelNow) {
		List<Summarys> sumarys = new ArrayList<Summarys>();
		
		try {
			//通过以前的直接访问
			String result = HttpRequestUtils.sendGet(url);
			//通过  代理的http , 加入代理
			//String result = HttpClientUtils.sendGet(url,true);
		    System.out.println("------------------------下一页--------------------");
			String content = result.substring(
					result.indexOf("id=\"list_article\"") - 5,
					result.indexOf("read-later-alert") - 12);
			String[] summarys = content.split("class=\"single_fake\"");
			for (int i = 1; i < summarys.length; i++) {
				// System.out.println(summarys[i]);

				// 获取链接
				String reg = "title=\"(.+?)\"";
				String title = getRegex(reg, summarys[i], 1);
				if (title.length() > 100) {
					title = title.substring(0, 100);
				}
				title = StringUtil.removeNonBmpUnicode(title);
				//System.out.println("文章标题"+title);
				// title = new String(title.getBytes("utf8mb4 "),"UTF-8");

				// System.out.println(title);
				// 获取链接地址
				// href="/articles/IRzq6vn"
				String linkStr = "href=\"(.+?)\"";
				String link = getRegex(linkStr, summarys[i], 1);
				// System.out.println("链接地址:\t"+link);

				// 获取content
			/*	String sum = summarys[i].substring(
						summarys[i].indexOf("class=\"article_cut\"") - 6,
						summarys[i].indexOf("class=\"tip meta-tip\"") - 6);
				sum = StringUtil.removeHtmlTags(sum).trim();*/
				
				// System.out.println("内容:"+sum);

				// 获取图片
				String imgStr = StringUtil.getHtmlImage(summarys[i]);
				// System.out.println("图片地址:\t"+imgStr);

				// 获取栏目
				String channel = summarys[i].substring(
						summarys[i].indexOf("class=\"icon-file icon\"") - 6,
						summarys[i].indexOf("class=\"icon-time icon\"") - 6);
				channel = StringUtil.removeHtmlTags(channel).trim();
				// System.out.println("添加时间:\t"+channel);

				// 获取日期
				String time = summarys[i].substring(summarys[i]
						.indexOf("class=\"icon-time icon\"") - 6, summarys[i]
						.indexOf("class=\"read-later-btn pull-right\"") - 50);
				time = StringUtil.removeHtmlTags(time).trim();
				// System.out.println("添加时间:\t"+time);
				DateFormat format = new SimpleDateFormat("MM-dd hh:MM");
				Date dateNow = format.parse(time);
				// 10-14 16:07

				String contentStr = summarys[i].substring(
						summarys[i].indexOf("class=\"article_cut\"") - 6,
						summarys[i].indexOf("class=\"tip meta-tip\"") - 6);
				contentStr = StringUtil.removeHtmlTags(contentStr);
				
				
				if (contentStr.length() > 255) {
					contentStr = contentStr.substring(0, 255);
				}
				contentStr = StringUtil.removeNonBmpUnicode(contentStr);
				Summarys sums = new Summarys(title, dateNow, contentStr,
						channel, imgStr, link);
				sums.setChannelNow(channelNow);
				sumarys.add(sums);
			}
		}catch(StringIndexOutOfBoundsException e1){
			System.err.println("--------------------------经检测你所在的网络可能存在爬虫------------------------------");
			System.exit(1);
		}catch(ParseException e2){
			System.err.println("--------------------------字符串转换失败------------------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sumarys;

	}

	/**
	 * 获取反悔的匹配的数据
	 * 
	 * @param regs
	 * @param groupIndex
	 * @return
	 */
	private String getRegex(String regs, String content, int groupIndex) {
		String str = null;
		Pattern pattern = Pattern.compile(regs);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			str = matcher.group(groupIndex);
		}
		return str;
	}

	private String subStr(String content, String startRegs, String endRegx) {
		return content.substring(content.indexOf(startRegs),
				content.indexOf(endRegx));
	}

	@Override
	public int updateIndex() {
		// TODO Auto-generated method stub
		// 1-20 页
		// 热门 lang =2 表示英文 lang = 1 表示中文
		// http://www.tuicool.com/ah/1/"+i+"?lang=1

		// 科技
		// http://www.tuicool.com/ah/101000000/"+i+"?lang=2

		// 创投
		// http://www.tuicool.com/ah/101040000/1?lang=1

		// 数码
		// http://www.tuicool.com/ah/101050000/2?lang=1

		// 热门 lang =2 表示英文 lang = 1 表示中文
		// http://www.tuicool.com/ah/1/2?lang=1
		// 科技
		// http://www.tuicool.com/ah/101000000/2?lang=1
		// 创投
		// http://www.tuicool.com/ah/101040000/1?lang=1
		// 数码
		// http://www.tuicool.com/ah/101050000/"+i+"?lang=1"
		// 技术
		// http://www.tuicool.com/ah/20/1?lang=1
		// 技术便利 有点问题 ，文字切了一半
		// 设计
		// http://www.tuicool.com/ah/108000000/1?lang=1
		// 营销
		// http://www.tuicool.com/ah/114000000/1?lang=1
		int start = this.summaryService.conunt();
		long startTime = System.currentTimeMillis();
		
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("0", "热门");
		map.put("101000000", "科技");
		map.put("101040000", "创投");
		map.put("101050000", "数码");
		map.put("20", "技术");
		map.put("108000000", "设计");
		map.put("114000000", "营销");
		for(Map.Entry<String, String> obj :map.entrySet()){
			//List<Summarys> sums = tuikuService.loadSummarys("http://www.tuicool.com/ah/20/"+i+"?lang=1","技术");
			//获取中文的数据
			for(int i=0;i<=20;i++){
				List<Summarys> sums = this.loadSummarys("http://www.tuicool.com/ah/"+obj.getKey()+"/"+i+"?lang=1", obj.getValue());
				this.summaryService.adds(sums);
			}
			
			//添加英文的数据
			for(int i=0;i<=20;i++){
				List<Summarys> sums = this.loadSummarys("http://www.tuicool.com/ah/"+obj.getKey()+"/"+i+"?lang=2", obj.getValue());
				this.summaryService.adds(sums);
			}
		}
		
		int end = this.summaryService.conunt();
		long endTime = System.currentTimeMillis();
		System.out.println("------------------开始时间"+new Date(startTime)+"----------------------");
		System.out.println("------------------结束时间"+new Date(endTime)+"----------------------");
		System.out.println("------------------使用时间"+StringUtil.countTime(startTime, endTime)+"----------------------");
		System.out.println("------------------更新的条数"+(end-start)+"----------------------");
	
		return end-start;
	}

	
}
