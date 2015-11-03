# webCollector-
这个写天，研究了一下webcollector这个爬虫框架，其中有一个爬取http://www.tuicool.com/  (推酷)的例子和一个爬取代理ip的例子，其中有数据库和图片的爬取


## 多线程爬取
ExecutorService  pool  =  Executors.newFixedThreadPool(3);
for(int i=0;i<3;i++){
		 
			//执行方法
			pool.execute(new Runnable() {
				
				public synchronized void crawl(){
				  //爬取数据
				}
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(ticket>0){
						crawl();
					}
				}
			});
		}
		
		
	# 通过jsonp来解析html文档
		
		//下面是一段来爬取一个代理ip网站，通过jsoup来解析页面数据，然后存入到数据库中，这个部分主要是解析
		//获取html文档,不是直接new 来实例化对象
		Document document = Jsoup.parse(content);
		
		//System.out.println("记录条数"+eles.size());
		Iterator<Element> it = eles.iterator();
		
		//便利里面的数据
		while(it.hasNext()){
			Element node = it.next();
			String text = node.text();
			String [] strs = text.split(" ");
			//System.out.println(strs.length+"__"+strs[0]+"__"+strs[1]);

			String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"; 
			Pattern patten = Pattern.compile(regex);
			if( patten.matcher(strs[0]).find()){
				String guojia = StringUtil.getHtmlImage(node.getAllElements().toString());
				
				//两个速度 延迟
				String sudu  = node.select("div[class=bar]").first().attr("title").replace("秒", "");
				//System.out.println(sudu);
				//延迟
				String time = node.select("div[class=bar]").last().attr("title").replace("秒", "");
			//	System.out.println("延迟"+time);
				//1.36.132.221 3128 香港 高匿 HTTP 15-10-16 12:55
				//				15-10-14 02:58
				SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:MM");
				String ip="",port="",localtion="",toumin="",type="",date="";
				Date dateNow = null;
				if(strs.length == 6){
					//System.out.println(text);
					//106.38.194.199 80 高匿 HTTP 15-10-16 20:06
					System.out.println(text);
					ip = strs[0];
					 port = strs[1];
					 localtion = "";
					 toumin = strs[2];
					 type = strs[3];
					 date = strs[4]+" "+strs[5];
					 dateNow = format.parse(date);
					//没有位置
				}else if(strs.length == 7){
					//7个
					 ip = strs[0];
					 port = strs[1];
					 localtion = strs[2];
					 toumin = strs[3];
					 type = strs[4];
					 date = strs[5]+" "+strs[6];
					 dateNow = format.parse(date);
				}
				System.out.println(port+":"+ip);
				ProxyHttps pro = new ProxyHttps(guojia, ip,port,localtion, toumin, type, dateNow,channel, Float.parseFloat(sudu), Float.parseFloat(time));
				//public ProxyHttps(String country, String ip, String port, String localtion,
				/*String name, String type, Date createDate, String channel,
				int quickly, int time) */
				proxys.add(pro);
			}
		}
		
		
	##  通过设定ip地址来瞒过一些反爬虫的网站
		public static String sendGet(String urlPath, String param){
		  String result = "";
	      BufferedReader buff = null;
	      
	     try {
	    	 String urlPathStr = null;
	    	if(param  == null  || "".equals(param.trim())){
	    		urlPathStr = urlPath;
	    	}else{
	    		urlPathStr = urlPath + "?" + param;
	    	}
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
           //X-Forward-For: 117.28.255.37
            //Client-Ip: 117.28.255.37
            
            //打开连接
            conn.connect();
            //获取结果
            buff  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //设定结果
           String line  = null;
            while((line = buff.readLine())!= null){
            	result +=line;
            }
            return result;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				//关闭连接
				if(buff != null){
					buff.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
