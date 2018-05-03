package com.tzg.common.service;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.PushPayload;

public class PushPayLoadService {
	
	//正式账号
	//appKey = "87640accab6caf8fb07b6ad4" ;
	//masterSecret = "03db6d58eb3292e932930b73" ;
	//测试账号
	//appKey = "33df8f9ab3343333625b3f35" ;
	//masterSecret = "f1994a9710e26deef8a3b1c7" ;
	
	private static String appKey = null ;
	private static String masterSecret = null ;
	
	public static void init(String masterSecret, String appKey) {
		PushPayLoadService.masterSecret = masterSecret;
		PushPayLoadService.appKey = appKey;
	}
	
	public static void sendPush(PushPayload ppla) throws Exception {
		JPushClient jpc = new JPushClient(masterSecret, appKey);
		jpc.sendPush(ppla);
	}
}
