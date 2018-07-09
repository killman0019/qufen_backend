package com.tzg.wap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.QiniuUtil;
import com.tzg.common.utils.SubimitHtml;
import com.tzg.common.utils.ToRemoveHtml;
import com.tzg.common.utils.WorkHtmlRegexpUtil;
import com.tzg.common.utils.sendTelephone;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.photo.PhotoIview;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.wap.utils.UploadPic;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import com.vdurmont.emoji.EmojiParser.FitzpatrickAction;

/**
 * test父类
 * 
 * @author fallenLeaves
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-wap.xml", "classpath*:spring/rmi-service-context.xml" })
public class BaseTest {

	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static void main(String[] args) {
		/*String a = "<div style='text-align:center;'> 整治“四风”   清弊除垢<br/><span style='font-size:14px;'> </span><span style='font-size:18px;'>公司召开党的群众路线教育实践活动动员大会</span><br/></div>";
		String result1 = "<p><img src='http://p3.pstatp.com/large/pgc-image/1527673880338d28f7e37ce' alt='特朗普不淡定了，5天连发14条推文，只说了一件事儿'></p><p>这些推文的内容包括了引用福克斯新闻频道对于特朗普的正面报道，还包括了引用顾问罗伯特穆勒（“通俄门事件”调查官）团队提到的指控俄罗斯干预美国大选的文章“13个愤怒的民主党人”，这说明特朗普对于“通俄门”的调查结果以及对总统职位的影响还是心中有数的。</p><p>比起数量，这些推文在内容上的相似程度似乎更让人印象深刻。</p><p>特朗普在周六的一篇推文中写道：</p><p>'This whole Russia Probe is Rigged. Just an excuse as to why the Dems and Crooked Hillary lost the Election and States that haven't been lost in decades. 13 Angry Democrats, and all Dems if you include the people who worked for Obama for 8 years. #SPYGATE &amp; CONFLICTS OF INTEREST!'</p><p>“通俄门的调查缺乏独立性。完全是希拉里和她的民主党在丢掉某些洲的选票，并且输掉整个总统大选之后制造的借口，当然还包括“13个愤怒的民主党人士”以及为奥巴马政府工作了8年的所有民主党人士的借口。</p><p>而周二早晨的一篇推文说：</p><p>'Why aren't the 13 Angry and heavily conflicted Democrats investigating the totally Crooked Campaign of totally Crooked Hillary Clinton. It's a Rigged Witch Hunt, that's why! Ask them if they enjoyed her after election celebration!'</p><p>“这13名愤怒的民主党人为什么不去调查希拉里?克林顿(Hillary Clinton)和民主党的竞选活动，其中涉及众多的犯罪和与俄罗斯之间的大量勾结？这是一次被操纵的调查！”</p><p>尽管推文的内容有微小的改动，但对于其表达的意思则大致相同。“被操纵的调查”“13个愤怒的民主党人”“为什么没有调查希拉里的竞选活动”，还有一如既往的感叹号和表示强调的大写字母！</p><p>这14篇推文，实际只是1篇推文。</p><p>特朗普的支持者称这是发推文的文字规律。特朗普清楚，如果他重复去做某一件事情，积累到一定时间后，自然会有相当数量的人相信他。同时还有另外一部分支持者，始终对特朗普反复提出的“寻找巫婆”（暗示希拉里在竞选中的操纵行为），“骗局”,“13个愤怒的民主党人”等一些列关键词深信不疑，这也会间接让他们对于穆勒团队进行的“通俄门”事件调查结果表示怀疑。</p><p>鉴于此，考虑到特朗普所发的推文都是没有经过任何修改的文字，可以完全还原他的真实想法，我们可以判断在最近几天，此项调查确实牵动着特朗普的心。</p><p>特朗普的推文，看起来更像是他在教训一个曾经对他提出莫名指控，不喜欢他本人，甚至会不惜一切代价阻止他成功的诋毁者。</p><p>在特朗普看来，虽然穆勒本人是被共和党主席亲自任命的联邦调查局局长，同时也是被共和党副检察长任命的特别顾问，却带领一群民主党人士调查特朗普“通俄门”中的污点。包括特朗普的前国家安全顾问和前竞选团队副主席在内的5人已经在穆勒的调查中认罪。</p><p>特朗普是个十分相信阴谋的人。包括曾经被广泛议论的奥巴马并非出生于美国，到曾经谣传的911过后穆斯林在双子座大楼上庆祝，再到Ted Cruz（美国德州联邦参议员，现任NASA委员会主席）的父亲可能参与了肯尼迪总统的暗杀事件。</p><p>特朗普的最新一条推文，明显的表达了他对于调查的困扰：“对不起，我需要迅速把注意力集中在朝核问题，不良贸易，退伍军人安置，经济，军队建设这些大问题上来，而不是继续强调调查组是否应该调查克林顿/俄罗斯/FBI/奥巴马/林奇，或是其他谁。”</p><p>显然，特朗普知道把一部分精力用来处理“通俄门”的调查是十分分心的。但困扰并没能带来理性的解决。特朗普的“通俄门事件”调查仍在继续。</p><p><br>&nbsp;</p><ul><li><a href='https://www.toutiao.com/search/?keyword=%E5%B8%8C%E6%8B%89%E9%87%8C' target='blank'>希拉里</a></li><li><a href='https://www.toutiao.com/search/?keyword=%E5%A5%A5%E5%B7%B4%E9%A9%AC'target='_blank'>奥巴马</a></li><li><a href='https://www.toutiao.com/search/?keyword=%E7%BD%97%E4%BC%AF%E7%89%B9%C2%B7%E7%A9%86%E5%8B%92' target='_blank'>罗伯特·穆勒</a></li><li><a href='https://www.toutiao.com/search/?keyword=%E4%BC%8A%E6%96%AF%E5%85%B0%E5%9B%BD' target='_";
		System.out.println(test(result1));
		System.out.println(WorkHtmlRegexpUtil.delHTMLTag(result1));
		System.out.println();
		System.out.println(WorkHtmlRegexpUtil.delHTMLTag(result1).replace("&nbsp;", " "));
		*/
		/*System.out.println(getMillis());
		System.out.println(getCurrentTimeSS());
		String str1 = "http://app.qufen.top/";
		String str2 = "www.qufem.io";

		String str = "http://app.qufen.top//upload/projects/201806/2018061217495937";
		String str4 = "http://" + "app.qufen.top" + "/";
		str = str.replaceAll(str4, "");
		System.out.println(str);*/

		/*String str5 = "http://t11.baidu.com/it/u=2559796420,1427899438&fm=170&s=42F210D74FE3709C8C85A0630300F073&w=360&h=360&img.JPEG";
		String str6 = "http://p3.pstatp.com/large/pgc-image/15289075482782fed3b5757";// 头条
		String str7 = "https://bihu2001.oss-cn-shanghai.aliyuncs.com/img/bb4e1a30ea53c00acf0770cfb416ded5.jpg?x-oss-process=style/size_md";
		String str8 = "https://pic.36krcnd.com/201806/14021231/7plndf83a4pzlnix.JPG!1200";// 36氪
		String str9 = "https://mmbiz.qpic.cn/mmbiz_jpg/GA0ru2kvgLmlcPVR4kPcticM46Yu9Yw35LL9k5jiaUJ5XjQHzrzREWswWpf4Yxic4b1YM7QfSyQlhJpL4p0LQXMJw/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1";// 微信号
		String str10 = "5b0988e595225.cdn.sohucs.com/images/20180613/264ed5e03b8b495789965c3aae1bfa13.jpeg";// 搜狐
		String str11 = "http://n.sinaimg.cn/news/1_img/upload/2b0c102b/781/w550h231/20180614/3aCf-hcwpcmq6607016.jpg";
		QiniuUtil.changeToLocalUrl(str6, "test6");
		QiniuUtil.changeToLocalUrl(str5, "test5");
		QiniuUtil.changeToLocalUrl(str7, "test7");
		QiniuUtil.changeToLocalUrl(str8, "test8");
		QiniuUtil.changeToLocalUrl(str9, "test9");
		QiniuUtil.changeToLocalUrl(str10, "test10");
		QiniuUtil.changeToLocalUrl(str11, "test11");*/
		/*String str = "D:\\opt\\file\\upload\\poster\\5add9a8.png";
		File file = new File(str);
		boolean delete = file.delete();
		if (delete) {
			System.out.println("删除成功!");
		}*/
		/*for (int i = 0; i < 100; i++) {
			String sendTele = sendTelephone.sendTele();
			System.out.println(sendTele);
		}*/
		/*String str  ="a";
		System.out.println(str.length());	52494646 6C32000057
		/*									52494646 4222000057
		*									52494646 C61A000057
		 * 									52494646 0229000057
		 *  mFileTypes.put("FFD8FF", "jpg");52494646 2404000057 
		mFileTypes.put("89504E47", "png"); 	52494646 6243000057
		mFileTypes.put("47494638", "gif");	52494646 700A000057
		mFileTypes.put("49492A00", "tif"); 	52494646 DCB2000057
		mFileTypes.put("424D", "bmp"); 		52494646 F462000057
		 * */
		/*String str = "http://p9.pstatp.com/large/pgc-image/153076627078120c425acbd";

		String str2 = "https://bihu2001.oss-cn-shanghai.aliyuncs.com/img/f87e96af3f14ea63b7e2c91a1fe295fd.jpg?x-oss-process=style/size_md";
		str2 = "https://oss02.bihu.com/img/c4ea35c7e75b2e5ce372919e2371fbec.jpg?x-oss-process=style/size_md";
		str2 = "https://oss02.bihu.com/img/18e7583409d9b634d372c0518a97663f.jpg?x-oss-process=style/size_md";
		str2 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2469074034,2810444930&fm=27&gp=0.jpg";
		str2 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3487866323,3474022882&fm=27&gp=0.jpg";
		str2 = "https://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/3b292df5e0fe99257f2d97a83fa85edf8db17121.png";
		str2 = "https://mmbiz.qpic.cn/mmbiz_png/HHGMTCukVg9ySf95nsygo3RD5ITOzXmJGwZV2gia3PGbyFjialA2dla57ej3XQTaRFSnvsnBVeBlXslMAqyMeMzQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1";
		//str2 = "https://mmbiz.qpic.cn/mmbiz_jpg/ibvFLR0soPFb7rBvKZibT9wEAHdHcshgwhbAPvo4n9GYjSVCzHMrSRHQElPIzK4oIZQI1zfP91NIEiaOsuZicPmaAA/640?tp=webp&wxfrom=5&wx_lazy=1";
		//str2 = "https://mmbiz.qpic.cn/mmbiz_png/ibvFLR0soPFYT6UDbHno00M2MLYcuY6h2HSTmaHbQePScNyCYuhx9tKicUgovrhYE4rmvgib8485HCr0524W4gRkA/640?tp=webp&wxfrom=5&wx_lazy=1";
		byte[] image2Bytes = getImageFromNetByUrl(str2);
		System.err.println(byte2HexStr(image2Bytes));
		System.out.println(image2Bytes);
		// String str = "Here is a boy: \uD83D\uDC66\uD83C\uDFFF!";
		// System.out.println(EmojiParser.parseToAliases(str, FitzpatrickAction.REMOVE));
		//
		/*	String str = "An 😀awesome 😃string with a few 😉emojis!";
			Collection<Emoji> collection = new ArrayList<Emoji>();
			// collection.add(EmojiManager.getForAlias("wink")); // This is 😉

			System.out.println(EmojiParser.removeAllEmojis(str));// 去除所有表情
			System.out.println(EmojiParser.removeAllEmojisExcept(str, collection));
			System.out.println(EmojiParser.removeEmojis(str, collection));
		*/
		/*if ("" == "") {
			System.err.println("aaaa");
		}*/
		String format = String.format("%010d", 11);
		System.out.println(format);
	}

	class ExcutDemo {

	}

	public static String test(String str) {
		return ToRemoveHtml.removeHtmlTag(str);

	}

	public static long getMillis() {
		Date date = new Date();
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	public static String getCurrentTimeSS() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String now = sdf.format(date);
		return now;
	}

	public static byte[] image2Bytes(String urlPath) {
		byte[] data = null;
		InputStream is = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			// conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(6000);
			is = conn.getInputStream();
			if (conn.getResponseCode() == 200) {
				data = readInputStream(is);
			} else {
				data = null;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			conn.disconnect();
		}
		return data;

	}

	public static byte[] readInputStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		try {
			while ((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();
		try {
			is.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	private static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			System.err.println("获取图片后缀失败!");
			e.printStackTrace();
		}
		return null;
	}

	public static String bytesToHexFun1(byte[] bytes) {
		// 一个byte为8位，可用两个十六进制位标识
		char[] buf = new char[bytes.length * 2];
		int a = 0;
		int index = 0;
		for (byte b : bytes) { // 使用除与取余进行转换
			if (b < 0) {
				a = 256 + b;
			} else {
				a = b;
			}

			buf[index++] = HEX_CHAR[a / 16];
			buf[index++] = HEX_CHAR[a % 16];
		}

		return new String(buf);
	}

	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < 9; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

}
