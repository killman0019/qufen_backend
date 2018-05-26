package com.tzg.common.utils;

import java.math.BigDecimal;

public interface TzgConstant {
	
	String MESSAGE ="msg";
    
    String SUCCESS = "success";
    
    /**
     * 铜掌柜域名
     */
    String TZG_DOMAIN_NAME = "tzg.cn";
    String TZG_WEB_DOMAIN_FULL_NAME = "https://www.tzg.cn";
    String TZG_WAP_DOMAIN_FULL_NAME = "https://m.tzg.cn";
    
    /**
     * 苹果商城地址
     */
    String IOS_APP_STORE_DOWN_URL = "https://itunes.apple.com/cn/app/klook/id988621288?l=en&mt=8";
    
    String ANDRIOD_APP_DOWN_URL = "https://down.tzg.cn/apk/tzg.apk";
    
    /**
     * 微信接口 key
     */
    String WECHAT_KEY = "5615b9f32fb3";
    
    /**
     * 单点登录 cookie key  防止cookie被篡改
     */
    String SSO_COOKIE_KEY = "tzgsadjuwdsq1123";
    
    /**
     * 跳转地址
     */
    String REDIRECT_URL = "redirectURL";
    
    /**
     * 最大金额
     */
    BigDecimal MAXCHARGEAMT = new BigDecimal(9999999.99);
	
	/**
     * 手机动态验证码长度
     */
    Integer DYNAMIC_VALIDATE_CODE_LENGTH = 6;
    
	/**
     * 手机动态验证码key
     */
    String DYNAMIC_VALIDATE_CODE_KEY = "dynamicValidateCode";
    
	/**
     * 注册来源 cookie存储key
     */
    String SOURCE_STORE_KEY = "tzg_register_source_key";
    
    /**
     * 推荐链接key
     */
    String RECOMMEND_LINK_KEY = "tzg_recommend_link_key";
    
    /**
     * 最小密码长度
     */
    int MIN_PASSWORD_LENGTH = 8;

    /**
     * 最大密码长度
     */
    int MAX_PASSWORD_LENGTH = 20;
    
    /**
     * 推荐码长度
     */
    int  RECOMMEND_CODE_LENGTH = 6;
    
    /**
     * 平台上传的文件存放的路径
     */
    String UPLOAD_BASE_PATH = "/upload";
    
    /**
     * 记录不存在
     */
	String MESSAGE_RECORD_NOT_EXIST = "该条记录已不存在，请重新刷新之后再操作！";
	
	/**
	 * 记录更新成功
	 */
	String MESSAGE_RECORD_UPDATE_SUCESS  = "修改成功！";
	
	/**
	 * 记录保存成功
	 */
	String MESSAGE_RECORD_SAVE_SUCESS  = "保存成功！";
	
	/**
	 * 记录删除成功
	 */
	String MESSAGE_RECORD_DELETE_SUCESS  = "删除成功！";
	
	/**
	 * 系统异常
	 */
	String MESSAGE_EXCEPTION  = "系统异常！";
	
	/**
	 * 操作成功
	 */
	String MESSAGE_RECORD_OPERATE_SUCESS  = "操作成功！";
	
	/**
	 * 操作完成
	 */
	String MESSAGE_RECORD_OPERATE_FINISH  = "操作完成！";
	
	
	/**
	 * 活动页充值返回路径
	 */
	String ACTIVITYURL  = "activityUrl";
	
	/**
	 * 登陆同步私钥
	 */
	String LOGIN_SIGN_KEY  = "sign_private_key";
}
