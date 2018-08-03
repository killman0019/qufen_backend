package com.tzg.common.utils.getui;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
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
public class PushList {
	
	
	public static String pushMsg(List cids,String msg,String title,String content) {
		
		 // 配置返回每个用户返回用户状态，可选
        System.setProperty("gexin_pushList_needDetails", "true");
        // 配置返回每个别名及其对应cid的用户状态，可选
        // System.setProperty("gexin_pushList_needAliasDetails", "true");
        IGtPush push = new IGtPush(sysGlobals.host, sysGlobals.appKey, sysGlobals.masterSecret);
        // 通知透传模板
        NotificationTemplate template = notificationTemplateDemo(title,content,msg);
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
        	if(AppType.ANDROID.getValue().toString().equals(cids.get(i).toString())) {
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
	
    public static NotificationTemplate notificationTemplateDemo(String title,String content,String msg) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(sysGlobals.appId);
        template.setAppkey(sysGlobals.appKey);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(content);
        // 配置通知栏图标
        style.setLogo("");
        // 配置通知栏网络图标
        style.setLogoUrl(sysGlobals.QF_IMG_URL);
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(msg);
        return template;
    }
}
