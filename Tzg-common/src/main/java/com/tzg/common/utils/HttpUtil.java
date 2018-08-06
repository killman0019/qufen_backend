package com.tzg.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.sysGlobals;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;

//import net.sf.json.JSONObject;

/** 
* @ClassName: HttpUtil 
* @Description: TODO<HttpClient工具类> 
* @author linj<作者>
* @date 2018年7月20日 下午1:41:33 
* @version v1.0.0 
*/
public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);

	/*
	public static void main(String[] args) {
		
	//		String sss = "【行情 | BTC在7400美元附近强势整理 人气热度增速放缓】据TokenInsight 数据显示，反映区块链行业整体表现的TI指数北京时间7月20日9时报809.40点，较昨日同期下跌7.18点，跌幅0.88%。通用平台类通证走势依然弱于大盘，其指数TIG报780.05点，较昨日同期下跌24.81点，跌幅3.08%。另据监测显示，BTC人气热度增速连续4日上升后回调至0.11%（昨日0.16%）；全球转账数回调至22.8万（昨日23.9万，上周20.3万），保持近一月的高位运行。BCtrend分析师认为，BTC价格短期高速上升后人气高位盘整，挖矿难度有所下降，严防风险。技术分析方面，独立分析师Tommy认为，BTC在7400美元左右强势整理，短期看7800美元较难突破，上方空间不大。";
	//		String rex="[【】]+";
	//		String[] str=sss.split(rex);
	//		System.out.println(str[1]+"====="+str[2]);
		
		

	//		String url ="http://api.bizhihui.info/v1/coin_news?token=f7e731f65347b6c7309267ba51b46b8e&key=HSR&limit=10";
		
		//币智慧获取币的快讯
		String key = "ETN";
		String url = sysGlobals.BIZHIHUI_NEWS_URL+"key="+key+"&token="+sysGlobals.BZH_TOKEN+"&limit="+100;
		boolean flag = true;
		long bgtt = new Date().getTime();
		System.out.println("------------------------JSON转换开始时间 --->"+key+"------------------------");
		JSONObject jsonObject = new JSONObject();
		String result = HttpUtil.doGet(url);
		jsonObject = JSONObject.parseObject(result); //将字符串{“id”：1}
		if(null!=jsonObject) {
			flag = false;
		}
		if(flag) {return;}
		long edtt = new Date().getTime();
		long edd = (edtt-bgtt)/1000;
		System.out.println("------------------------JSON转换结束时间 ---共>"+edd+"秒"+key+"------------------------");
	//		System.out.println(jsonObject.toString());
		String code = null;
		if(jsonObject.toString().indexOf("data")==-1) {return;}
		Object codec = jsonObject.get("code");
		if(StringUtil.isNotEmpty(codec)&&!codec.equals(null)&&!codec.equals("null")) {
			 code = jsonObject.get("code").toString();
		}
		String ddt = jsonObject.get("data").toString();
		if(null!=code && code.equals("200")&&ddt.length()>10) {
			long beTime = new Date().getTime();
			System.out.println("------------------------批量插入开始 --->"+key+"------------------------");
	//			System.out.println("ddt...............................>:"+key+"="+ddt);
			JSONArray data = JSONArray.fromObject(ddt);
	//			System.out.println(data.toString());
			if(data.isEmpty()) {return;}
			for (int j = 0; j < data.size(); j++) {
				NewsFlashWithBLOBs nwFh = new NewsFlashWithBLOBs();
				Object sttr = data.get(j);
	//				System.out.println("sttr="+sttr+"=sttr");
				if(sttr.equals(null)) {continue;}
				JSONObject data1 = JSONObject.parseObject(sttr.toString());
	//				System.out.println("data1="+data1+"=data2");
	//				String bzjId = null;
	//				if(data1.toString().indexOf("\"_id\":")!=-1) {
	//					Object str1 = data1.get("_id");
	//					if(StringUtil.isNotEmpty(str1)&&!str1.equals(null)&&!str1.equals("null")) {
	//						bzjId = (String)data1.get("_id");
	//						nwFh.setBzjId(bzjId);
	//					}
	//				}
	//				if(null==bzjId) {continue;}
	//				Map<String,Object> seMap = new HashMap<String,Object>();
	//				seMap.put("bzjId", bzjId);
	//				seMap.put("projectCode", key);
	//				List<Integer> ints = newsFlashMapper.findListByAttr(seMap);
	//				if(!ints.isEmpty()) {continue;}
	//				if(data1.toString().indexOf("\"abstract\":")!=-1) {
	//					Object str1 = data1.get("abstract");
	//					//TODO：会报错
	////					System.out.println("str1="+str1+"=str1");
	//					if(StringUtil.isNotEmpty(str1)&&!str1.equals(null)&&!str1.equals("null")) {
	////						System.out.println(str1.toString());
	//						JSONArray abstractt = JSONArray.fromObject(str1.toString());
	//						String abc = abstractt.get(0).toString();
	//						nwFh.setAbstractc(StringUtil.filterEmoji(abc));
	//					}
	//				}
	//				if(data1.toString().indexOf("\"article_events\":")!=-1) {
	//					Object str2 = data1.get("article_events");
	//					if(StringUtil.isNotEmpty(str2)&&!str2.equals(null)&&!str2.equals("null")) {
	//						String articleEvents = JSONArray.fromObject(str2.toString()).toString();
	//						nwFh.setArticleEvents(StringUtil.filterEmoji(articleEvents));
	//					}
	//				}
	//				if(data1.toString().indexOf("\"content\":")!=-1) {
	//					Object str3 = data1.get("content");
	//					if(StringUtil.isNotEmpty(str3)&&!str3.equals(null)&&!str3.equals("null")) {
	//						nwFh.setContent(StringUtil.filterEmoji(str3.toString()));
	//					}
	//				}
	//				if(data1.toString().indexOf("\"host\":")!=-1) {
	//					Object str4 = data1.get("host");
	//					if(StringUtil.isNotEmpty(str4)&&!str4.equals(null)&&!str4.equals("null")) {
	//						nwFh.setHost(str4.toString());
	//					}
	//				}
				if(data1.toString().indexOf("\"html\":")!=-1) {
					Object str5 = data1.get("html");
					if(StringUtil.isNotEmpty(str5)&&!str5.equals(null)&&!str5.equals("null")) {
						String strr = str5.toString();
						if(strr.indexOf("img")!=-1 && strr.indexOf("${")!=-1) {
							System.out.println("strr="+strr+"=strr");
							String notEmoj = StringUtil.filterEmoji(ImgUtils.repairContent(strr, sysGlobals.BZH_IMG_PATH));
							nwFh.setHtml(notEmoj);
						}else {
							nwFh.setHtml(StringUtil.filterEmoji(strr));
						}
					}
				}
	//				if(data1.toString().indexOf("\"publish_time\":")!=-1) {
	//					Object str6 = data1.get("publish_time");
	//					if(StringUtil.isNotEmpty(str6)&&!str6.equals(null)&&!str6.equals("null")) {
	//						Long nnTime = 0L;
	//						Long publish_time = Long.valueOf(str6.toString());
	//						long nowTime = beTime;
	//						if(publish_time>nowTime){
	//							nnTime = nowTime;
	//						}else {
	//							nnTime = publish_time;
	//						}
	//						Date date = new Date(nnTime);
	//						String publishTime = DateUtil.getDate(date, DateUtil.dateTimePattern);
	//						nwFh.setPublishTime(publishTime);
	//					}
	//				}
	//				if(data1.toString().indexOf("\"rank\":")!=-1) {
	//					Object str7 = data1.get("rank");
	//					if(StringUtil.isNotEmpty(str7)&&!str7.equals(null)&&!str7.equals("null")) {
	//						BigDecimal rank = new BigDecimal(str7.toString());
	//						nwFh.setRank(rank);
	//					}
	//				}
	//				if(data1.toString().indexOf("\"sentiment\":")!=-1) {
	//					Object str8 = data1.get("sentiment");
	//					if(StringUtil.isNotEmpty(str8)&&!str8.equals(null)&&!str8.equals("null")) {
	//						JSONObject sentimentc = JSONObject.parseObject(str8.toString());
	//						nwFh.setSentiment(StringUtil.filterEmoji(sentimentc.toString()));
	//					}
	//				}
	//				if(data1.toString().indexOf("\"site\":")!=-1) {
	//					Object str9 = data1.get("site");
	//					if(StringUtil.isNotEmpty(str9)&&!str9.equals(null)&&!str9.equals("null")) {
	//						nwFh.setSite(str9.toString());
	//					}
	//				}
	//				if(data1.toString().indexOf("\"tags\":")!=-1) {
	//					Object str10 = data1.get("tags");
	//					if(StringUtil.isNotEmpty(str10)&&!str10.equals(null)&&!str10.equals("null")) {
	//						JSONArray tagsc = JSONArray.fromObject(str10.toString());
	//						nwFh.setTags(StringUtil.filterEmoji(tagsc.toString()));
	//					}
	//				}
	//				if(data1.toString().indexOf("\"title\":")!=-1) {
	//					Object str11 = data1.get("title");
	//					if(StringUtil.isNotEmpty(str11)&&!str11.equals(null)&&!str11.equals("null")) {
	//						nwFh.setTitle(StringUtil.filterEmoji(str11.toString()));
	//					}
	//				}
	//				if(data1.toString().indexOf("\"url\":")!=-1) {
	//					Object str12 = data1.get("url");
	//					if(StringUtil.isNotEmpty(str12)&&!str12.equals(null)&&!str12.equals("null")) {
	//						nwFh.setUrl(str12.toString());
	//					}
	//				}
	//				if(data1.toString().indexOf("\"relevance\":")!=-1) {
	//					Object str13 = data1.get("relevance");
	//					if(StringUtil.isNotEmpty(str13)&&!str13.equals(null)&&!str13.equals("null")) {
	//						JSONArray relevancec = JSONArray.fromObject(str13.toString());
	//						nwFh.setRelevance(StringUtil.filterEmoji(relevancec.toString()));
	//					}
	//				}
	//				nwFh.setProjectCode(key);
	//				Date date = new Date();
	//				nwFh.setCreatedAt(date);
	//				nwFh.setUpdatedAt(date);
	//				mpList.add(nwFh);
			}
	//			if(mpList.isEmpty()) {continue;}
	//			newsFlashMapper.insertBatchs(mpList);
			long endTime = new Date().getTime();
			long miao = (endTime-beTime)/1000;
			System.out.println("------------------------批量插入结束 --->"+key+"-共花费"+miao+"秒------------------");
		}
			
		
		
		
		
		
		//币智慧获取币的基本信息
	//		String url = sysGlobals.BIZHIHUI_URL+"key=polis&token="+sysGlobals.BZH_TOKEN;
	//		boolean flag = true;
	//		JSONObject jsonObject = new JSONObject();
	//		int i = 0;
	//		while(flag&&i<11) {
	//			 String result = HttpUtil.doGet(url);
	//			 jsonObject = JSONObject.fromObject(result); //将字符串{“id”：1}
	//			if(null!=jsonObject) {
	//				flag = false;
	//			}
	//			i += 1;
	//		}
	//		if(flag) {
	//			throw new RuntimeException("10次 都未获取到币种！");
	//		}
	////		System.out.println(jsonObject.toString());
	//		String code = jsonObject.get("code").toString();
	//		if(code.equals("200")) {
	//			CoinBasic coinBasic = new CoinBasic();
	//			JSONObject data = JSONObject.fromObject(jsonObject.get("data").toString());
	//			System.out.println(data.toString());
	//			
	//			JSONObject descriptions = JSONObject.fromObject(data.get("descriptions").toString());
	//			System.out.println(descriptions.toString());
	//			JSONArray zh = descriptions.getJSONArray("zh");
	//			coinBasic.setDescriptions(zh.getString(0));
	//			
	//			Long publishTime = Long.valueOf(data.get("publish_time").toString());
	//			Date longDate = new Date(publishTime);
	//			String date = DateUtil.getDate(longDate, DateUtil.dateTimePattern);
	//			coinBasic.setPublishTime(date);
	//			String website = data.get("website").toString();
	//			coinBasic.setWebsite(website);
	//			String whitepaper = data.get("whitepaper").toString();
	//			coinBasic.setWhitepaper(whitepaper);
	//			String github = data.get("github").toString();
	//			coinBasic.setGithub(github);
	//			logger.info("------------------------task 完毕--->获取币种信息task执行完毕------------------------");
	//		}
		
		
		
		
		//获取coinmarketcap
	//		Integer start = 0;
	//		Integer limit = 100;
	//		String url = sysGlobals.COINMARKETCAP_URL+"start="+start+"&limit="+limit;
	//		boolean flag = true;
	//		JSONObject jsonObject = new JSONObject();
	//		int i = 0;
	//		while(flag&&i<11) {
	//			 String result = doGet(url);
	//			 jsonObject = JSONObject.fromObject(result); //将字符串{“id”：1}
	//			if(null!=jsonObject) {
	//				flag = false;
	//			}
	//			i += 1;
	//		}
	//		System.out.println(jsonObject.toString());
	//		JSONObject data = JSONObject.fromObject(jsonObject.get("data").toString());
	//		System.out.println(data.toString());
	//		List<String> sList = new ArrayList<String>();
	//		try {
	//		    Iterator iterator = data.keys();
	//		    while(iterator.hasNext()){
	//	    		String key = (String) iterator.next();
	//	            sList.add(key);
	//		    }
	//		   
	//		} catch (JSONException e) {
	//		    e.printStackTrace();
	//		}
	//		List<CoinBasic> listc = new ArrayList<CoinBasic>();
	//		System.out.println(sList.toString());
	//		for (int j = 0; j < sList.size(); j++) {
	//			String str = data.get(sList.get(j)).toString();
	////			System.out.println(str);
	//			JSONObject strData = JSONObject.fromObject(str);
	//			CoinBasic coinBasic = new CoinBasic();
	//			coinBasic.setCmcId(Integer.valueOf(strData.get("id").toString()));
	//			coinBasic.setName(strData.get("name").toString());
	//			coinBasic.setSymbol(strData.get("symbol").toString());
	//			coinBasic.setWebsiteSlug(strData.get("website_slug").toString());
	//			listc.add(coinBasic);
	//		}
	//		
	//		System.out.println(listc.size());
		
		//获取金色财经的信息
	//		String url = sysGlobals.COINDOG_URL;
	//		JSONArray list = jsonObject.getJSONArray("list");
	//		System.out.println(list);
	//		if(list.isEmpty()) {
	//			return;
	//		}
	//		JSONObject listObject = list.getJSONObject(0);
	//		if(null==listObject) {
	//			return;
	//		}
	//		JSONArray lives = listObject.getJSONArray("lives");
	//		System.out.println(lives);
	//		if(lives.isEmpty()) {
	//			return;
	//		}
	//		String rex="[【】]+";
	//		for (int j = 0; j < lives.size(); j++) {
	//			Map map =(Map) lives.get(j);
	//			//我们只保存3分以上的，包含3分
	//			Integer grade = (Integer)map.get("grade");
	//			if(grade<3) {
	//				continue;
	//			}
	//			Integer coinDogId = (Integer)map.get("id");
	//			System.out.println(coinDogId);
	//			//TODO:将coinDogId和数据库比对，要是数据库中没有，则将数据保存到我们数据库中
	//			
	//			SystemNewsFlash newsFlash = new SystemNewsFlash();
	//			Date date = new Date();
	//			newsFlash.setCreatedAt(date);
	//			newsFlash.setUpdatedAt(date);
	//			Object contentc = map.get("content");
	//			if(null!=contentc) {
	//				String content = contentc.toString();
	//				String[] str=content.split(rex);
	//				if(StringUtils.isBlank(str[1])) {
	//					newsFlash.setTitle(sysGlobals.B_HOTS_NEWS);
	//				}else {
	//					newsFlash.setTitle(str[1]);
	//				}
	//				if(StringUtils.isBlank(str[2])) {
	//					newsFlash.setContent(sysGlobals.B_HOTS_NEWS);
	//				}else {
	//					newsFlash.setContent(str[2]);
	//				}
	//			}
	//			newsFlash.setRise((Integer)map.get("up_counts"));
	//			newsFlash.setFall((Integer)map.get("down_counts"));
	//			newsFlash.setState(sysGlobals.ENABLE);
	//			newsFlash.setAuthor(sysGlobals.NEWSAUTHOR);
	//			newsFlash.setIsCheckDetails(1);
	//			//判断是否是重点突出
	//			Object highlightColor = map.get("highlight_color");
	//			if(null!=highlightColor) {
	//				newsFlash.setIsProminent(0);
	//			}else {
	//				newsFlash.setIsProminent(1);
	//			}
	//			newsFlash.setCoinDogId(coinDogId);
	//			
	//			
	//		}
	}
	*/

	/**
	 * get请求
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @throws InterruptedException 
	 */
	public static String doGet(String url) throws KeyManagementException, NoSuchAlgorithmException {

		try {

			SSLContext sslContext = SSLContexts.custom().useTLS().build();

			SSLConnectionSocketFactory f = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1", "TLSv1.2" }, null, null);

			// HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(f).build();

			HttpClient client = HttpClients.custom().setSSLSocketFactory(f).build();

			// HttpClient client = new DefaultHttpClient();
			/*try {
				SSLContext sslContext = SSLContext.getInstance("TLS");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/**请求发送成功，并得到响应**/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/**读取服务器返回过来的json字符串数据**/
				String strResult = EntityUtils.toString(response.getEntity());
				return strResult;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * post请求(用于key-value格式的参数)
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, Map params) {

		BufferedReader in = null;
		try {
			// 定义HttpClient
			HttpClient client = new DefaultHttpClient();
			// 实例化HTTP方法
			HttpPost request = new HttpPost();
			request.setURI(new URI(url));

			// 设置参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String value = String.valueOf(params.get(name));
				nvps.add(new BasicNameValuePair(name, value));

				// System.out.println(name +"-"+value);
			}
			request.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpResponse response = client.execute(request);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) { // 请求成功
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}

				in.close();

				return sb.toString();
			} else { //
				System.out.println("状态码：" + code);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, String params) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;

		try {

			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			} else {
				logger.error("请求返回:" + state + "(" + url + ")");
			}
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
