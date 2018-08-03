package com.tzg.common.utils.getui;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.tzg.common.enums.AppType;
import com.tzg.common.utils.sysGlobals;

/** 
* @ClassName: PushList 
* @Description: TODO<往批量用户手机推送消息> 
* @author linj<作者>
* @date 2018年7月11日 下午3:03:53 
* @version v1.0.0 
*/
public class iOSPushList {
	
	
	public static String iOSPushMsg(List cids,String msg,String title,String content) {
		
		 // 配置返回每个用户返回用户状态，可选
        System.setProperty("gexin_pushList_needDetails", "true");
        // 配置返回每个别名及其对应cid的用户状态，可选
        // System.setProperty("gexin_pushList_needAliasDetails", "true");
        IGtPush push = new IGtPush(sysGlobals.host, sysGlobals.appKey, sysGlobals.masterSecret);
        // 通知透传模板
//        NotificationTemplate template = notificationTemplateDemo(title,content,msg);
        TransmissionTemplate template =getTemplate(title,content,msg);
        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
        List targets = new ArrayList();
        for (int i = 0; i < cids.size(); i++) {
        	Target target = new Target();
        	target.setAppId(sysGlobals.appId);
        	if(AppType.IOS.getValue().toString().equals(cids.get(i).toString())) {
        		continue;
        	}
        	target.setClientId(cids.get(i).toString());
        	targets.add(target);
		}
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        IPushResult ret = push.pushMessageToList(taskId, targets);
        System.out.println(ret.getResponse().toString());
		return null;
	}
	
	public static TransmissionTemplate getTemplate(String title,String content,String msg) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(sysGlobals.appId);
        template.setAppkey(sysGlobals.appKey);
	    template.setTransmissionContent("透传内容");
	    template.setTransmissionType(2);
	    APNPayload payload = new APNPayload();
	    //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
	    payload.setAutoBadge("+1");
	    payload.setContentAvailable(1);
	    payload.setSound("default");
	    payload.setCategory(content);
	    //简单模式APNPayload.SimpleMsg
//	    payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
	    //字典模式使用APNPayload.DictionaryAlertMsg
	    payload.setAlertMsg(getDictionaryAlertMsg(title,msg));
	    template.setAPNInfo(payload);
	    return template;
	}
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title,String msg){
	    APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
	    alertMsg.setBody(msg);
	    alertMsg.setActionLocKey("ActionLockey");
	    alertMsg.setLocKey("LocKey");
	    alertMsg.addLocArg("loc-args");
	    alertMsg.setLaunchImage("launch-image");
	    // iOS8.2以上版本支持
	    alertMsg.setTitle(title);
	    alertMsg.setTitleLocKey("TitleLocKey");
	    alertMsg.addTitleLocArg("TitleLocArg");
	    return alertMsg;
	}
}
