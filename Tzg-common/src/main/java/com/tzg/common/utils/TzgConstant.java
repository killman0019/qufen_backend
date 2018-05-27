package com.tzg.common.utils;

import java.math.BigDecimal;

public interface TzgConstant {

    String                      MESSAGE                          = "msg";

    String                      SUCCESS                          = "success";

    /**
     * 铜掌柜域名
     */
    String                      TZG_DOMAIN_NAME                  = "tzg.cn";
    String                      TZG_WEB_DOMAIN_FULL_NAME         = "https://www.tzg.cn";
    String                      TZG_WAP_DOMAIN_FULL_NAME         = "https://m.tzg.cn";
    /**
     * 测试环境地址用于csrf过滤通过
     */
    String                      TZG_PRE_IP_FULL_NAME         = "122.224.156.194";

    /**
     * 苹果商城地址
     */
    String                      IOS_APP_STORE_DOWN_URL           = "https://itunes.apple.com/cn/app/klook/id988621288?l=en&mt=8";

    String                      ANDRIOD_APP_DOWN_URL             = "https://down.tzg.cn/apk/tzg.apk";

    /**
     * 微信接口 key
     */
    String                      WECHAT_KEY                       = "5615b9f32fb3";

    /**
     * 单点登录 cookie key  防止cookie被篡改
     */
    String                      SSO_COOKIE_KEY                   = "tzgsadjuwdsq1123";

    /**
     * 跳转地址
     */
    String                      REDIRECT_URL                     = "redirectURL";

    /**
     * 最大金额
     */
    BigDecimal                  MAXCHARGEAMT                     = new BigDecimal(9999999.99);

    /**
     * 手机动态验证码长度
     */
    Integer                     DYNAMIC_VALIDATE_CODE_LENGTH     = 6;

    /**
     * 手机动态验证码key
     */
    String                      DYNAMIC_VALIDATE_CODE_KEY        = "dynamicValidateCode";

    /**
     * 注册来源 cookie存储key
     */
    String                      SOURCE_STORE_KEY                 = "tzg_register_source_key";

    /**
     * 推荐链接key
     */
    String                      RECOMMEND_LINK_KEY               = "tzg_recommend_link_key";

    /**
     * 最小密码长度
     */
    int                         MIN_PASSWORD_LENGTH              = 8;

    /**
     * 最大密码长度
     */
    int                         MAX_PASSWORD_LENGTH              = 20;

    /**
     * 推荐码长度
     */
    int                         RECOMMEND_CODE_LENGTH            = 6;

    /**
     * 平台上传的文件存放的路径
     */
    String                      UPLOAD_BASE_PATH                 = "/upload";

    /**
     * 记录不存在
     */
    String                      MESSAGE_RECORD_NOT_EXIST         = "该条记录已不存在，请重新刷新之后再操作！";

    /**
     * 记录更新成功
     */
    String                      MESSAGE_RECORD_UPDATE_SUCESS     = "修改成功！";

    /**
     * 记录保存成功
     */
    String                      MESSAGE_RECORD_SAVE_SUCESS       = "保存成功！";

    /**
     * 记录删除成功
     */
    String                      MESSAGE_RECORD_DELETE_SUCESS     = "删除成功！";

    /**
     * 系统异常
     */
    String                      MESSAGE_EXCEPTION                = "系统异常！";

    /**
     * 操作成功
     */
    String                      MESSAGE_RECORD_OPERATE_SUCESS    = "操作成功！";

    /**
     * 操作完成
     */
    String                      MESSAGE_RECORD_OPERATE_FINISH    = "操作完成！";

    /**
     * 活动页充值返回路径
     */
    String                      ACTIVITYURL                      = "activityUrl";

    /**
     * 登陆同步私钥---可以验证redis
     */
    String                      LOGIN_SIGN_KEY                   = "sign_private_key";
    /**
     * 登陆同步验证私钥---验证获取的用户userid
     */
    String                      USERID_SIGN                      = "userid_sign";

    /**铜掌柜三个端代码**/
    String                      TZG_WEB                          = "_WEB";
    String                      TZG_WAP                          = "_WAP";
    String                      TZG_REST                         = "_REST";

    /**渠道-人人赚名称*/
    String                      RENRENZHUAN                      = "renrenzhuan";

    public static final String  CSRF_PARAM_NAME                  = "CSRFToken";

    public static final String  CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CSRF_PARAM_NAME + ".tokenval";
    
    /**
     * 邮箱认证/修改，对称加密密码,后续优化采用统一的密钥管理(处理密钥分类、时效)
     */
    public static final String EMAIL_TOKEN_PWD                  = "999f4789e97e42f79c8f467bac1d3d2c";
    /**
     * 加密时的编码/转码字符集
     */
    public static final String DEFAULT_CHARSET="UTF-8";
    
    /**
     * 系统异常默认提示
     */
    public static final String DEFAULT_ERROR_MSG="网络或服务异常,请稍后重试";
}
