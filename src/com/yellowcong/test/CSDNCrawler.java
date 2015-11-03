package com.yellowcong.test;


import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;

/**
 * Crawl news from yahoo news
 *
 * @author hu
 */
public class CSDNCrawler extends BreadthCrawler {

    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     * information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     * links which match regex rules from pag
     */
    public CSDNCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*start page*/
        this.addSeed("http://blog.csdn.net/ajaxhu/article/details/39610001?utm_source=tuicool");

        /*fetch url like http://news.yahoo.com/xxxxx*/
        //设定爬去的url
        this.addRegex("http://news.yahoo.com/.*");
        /*do not fetch url like http://news.yahoo.com/xxxx/xxx)*/
        //不爬去除了news以外的数据
        this.addRegex("-http://news.yahoo.com/.+/.*");
        /*do not fetch jpg|png|gif*/
        //不爬去.jpg png gif的文件
        this.addRegex("-.*\\.(jpg|png|gif).*");
        /*do not fetch url contains #*/
        //不爬去包含 #的数据
        this.addRegex("-.*#.*");
    }

    @Override
    public void visit(Page page, Links nextLinks) {
        String url = page.getUrl();
        /*if page is news page*/
       /* if (Pattern.matches("http://news.yahoo.com/.+html", url)) {
            we use jsoup to parse page
            Document doc = page.getDoc();

            extract title and content of news by css selector
            String title = doc.select("h1[class=headline]").first().text();
            String content = doc.select("div[class=body yom-art-content clearfix]").first().text();

            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);

            If you want to add urls to crawl,add them to nextLink
            WebCollector automatically filters links that have been fetched before
            If autoParse is true and the link you add to nextLinks does not match the regex rules,the link will also been filtered.
            // nextLinks.add("http://xxxxxx.com");
        }*/
        Document doc =  page.getDoc();
        //文章标题
        String title =  doc.select("span[class=link_title]").first().text();
        //文章内容
        String content = doc.select("div[class=article_content]").first().text();
        //日期
        //查看数量
        //分类
        
        
        System.out.println(title);
        System.out.println(content);
    }

    public static void main(String[] args) throws Exception {
        CSDNCrawler crawler = new CSDNCrawler("crawl", true);
        //设定线程
        crawler.setThreads(1);
      //  crawler.setTopN(100);
        //crawler.setResumable(true);
            /*start crawl with depth of 4*/
        //设定深度
        crawler.start(1);
    }

}
