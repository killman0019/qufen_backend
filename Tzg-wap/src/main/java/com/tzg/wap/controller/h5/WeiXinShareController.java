package com.tzg.wap.controller.h5;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.StringUtil;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.wap.utils.HttpUtil;

@Controller(value = "WeiXinShareController")
@RequestMapping("/kff/weiXinShare")
public class WeiXinShareController extends BaseController {
	private static Logger logger = Logger.getLogger(WeiXinShareController.class);

	private static String appid = "wx25a68f06e3b90950";
	private static String secret = "25c095a52efa013a249638a1ba151531";
	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "/getJsApiTicket")
	@ResponseBody
	public BaseResponseEntity getJsApiTicket(String link, HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();

		String accessToken = getAccessToken();
		String jsApiTicket = getJsApiTicket(accessToken);
		System.out.println(jsApiTicket);
		Map<String, String> map = makeWXTicket(jsApiTicket, link);
		bre.setData(map);
		return bre;
	}

	@RequestMapping(value = "/getAccessToken")
	@ResponseBody
	public BaseResponseEntity getAccessToken(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String accessToken = getAccessToken();
		map.put("accessToken", accessToken);
		bre.setData(map);
		return bre;
	}

	/**
	 * 获取access_token
	 * 
	 * @return
	 */
	private String getAccessToken() {
		// 从缓存里去获取token
		String weixinAccessToken = null;
		try {
			weixinAccessToken = redisService.get("weixinAccessToken");
			if (StringUtil.isBlank(weixinAccessToken)) {
				String appId = appid;
				String appSecret = secret;
				String url = "https://api.weixin.qq.com/cgi-bin/token";
				String returnData = HttpUtil.sendGet(url, "grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
				JSONObject json = JSONObject.fromObject(returnData);
				if (json.containsKey("access_token")) {
					if (json.get("access_token") != null && !json.get("access_token").equals("")) {
						weixinAccessToken = json.get("access_token").toString();
						redisService.put("weixinAccessToken", weixinAccessToken, 7200);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return weixinAccessToken;
	}

	/**
	 * 获取ticket
	 * 
	 * @return
	 */
	private String getJsApiTicket(String accessToken) {
		// 从缓存里去获取jsApiTicket
		String jsApiTicket = null;
		try {
			jsApiTicket = redisService.get("jsApiTicket");
			if (StringUtil.isBlank(jsApiTicket)) {
				String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
				String result = HttpUtil.sendGet(requestUrl, "access_token=" + accessToken + "&type=jsapi");
				JSONObject json = JSONObject.fromObject(result);
				if (json.containsKey("ticket")) {
					if (json.get("ticket") != null && !json.get("ticket").equals("")) {
						jsApiTicket = json.get("ticket").toString();
						redisService.put("jsApiTicket", jsApiTicket, 7200);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsApiTicket;
	}

	// 生成微信权限验证的参数
	public static Map<String, String> makeWXTicket(String jsApiTicket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonceStr = createNonceStr();
		String timestamp = createTimestamp();
		String string1;
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + nonceStr + "×tamp=" + timestamp + "&url=" + url;
		logger.info("string1" + string1);
		try {

			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());

			// signature = EncryptionUtil.encodeBySHA1(string1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ret.put("url", url);
		ret.put("nonceStr", nonceStr);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appid", appid);
		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	// 生成随机字符串
	private static String createNonceStr() {
		return UUID.randomUUID().toString();
	}

	// 生成时间戳
	private static String createTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
