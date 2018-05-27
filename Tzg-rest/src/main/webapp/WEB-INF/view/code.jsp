<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>api doc</title>
</head>

<body>
<pre>
<a href="http://192.168.1.161:9090/rest/doc/" >返回到api</a>
错误码：
	//global
	
   /** ------------------ 全局异常信息 ----------------- */
	
	SYS_SUCCESS(0, "操作成功"),
	
	SYS_ERROR(9999, "系统异常"),
	
    MISSING_ARGS(9998, "缺失参数"),
	
	/** ------------------ 登录账号相关错误码以11开头 ----------------- */
    
    NO_AUTH(11001, "未授权，请重新登录"),
    
    PASSWORD_NULL(11002, "密码不能为空"),
    
    SEC_ID_NULL(11003, "唯一识别号不能为空"),
    
    LOGIN_NAME_OR_PASSWORD_INCORRECT(11004, "登录帐号或密码错误"),
    
    LOGIN_NAME_NULL(11005, "登录帐号不能为空"),
    
    ACCOUNT_LOCKED(11006, "账户被冻结，请联系客服"),

    NEED_PERMISSION(11007, "需要权限"),
    
    PHONE_NULL(11008, "手机号不能为空"),
    
    PHONE_FORMAT_ERROR(11009, "手机号码格式错误"),
    
    PHONE_ALREADY_EXIST(11010, "该手机号已被注册"),
    
    PASSWORD_FORMAT_ERROR(11011, "密码不符合要求，必须为8-20位，且数字和字母的组合"),
    
    DYNAMIC_VERIFY_CODE_ERROR(11012, "手机动态验证码错误"),
    
    HAVE_NOT_AGREE_PROTOCOL(11013, "未同意注册协议"),
    
    USER_NOT_EXIST(11014, "用户不存在"),
    
    MOBILE_NOT_EXIST(11015, "该手机号未注册"),
    
    DYNAMIC_VERIFY_CODE_EXPIRED(11016, "验证码已失效 请重新点击获取验证码"),
    
    REAL_NAME_ALREADY_AUTH(11017, "已实名认证"),
    
    DYNAMIC_VERIFY_CODE_INVALID(11018, "手机动态验证码已失效"),
    
    PASSWORD_IS_NUMBER(11019, "密码必须为全部数字"),
    
    PASSWORD_SIX_NUMBER(11020, "请输入6位数字密码"),
    
    HAVE_NOT_AGREE_PROTOCOL2(11021, "请阅读并同意《铜掌柜投资咨询与管理服务协议》"),
    
    PASSWORD_IS_SIMPLE(11020, "设置失败, 密码过于简单"),
    
    USER_NOT_EXIST_LOGIN(11021, "用户不存在, 请重新登录"),
    
    /** ------------------ 用户信息相关错误码以12开头 ----------------- */
    
    VCNAME_NULL(12001, "姓名不能为空"),
    
    VCCARD_NULL(12002, "身份证不能为空"),
    
    VCNAME_INCORRECT(12003, "姓名不合法"),
    
    VCCARD_INCORRECT(12004, "身份证号码错误"),
    
    CONFIRM_PASSWORD_NULL(12005, "确认密码不能为空"),

    TWO_PASSWORDS_NOT_MATCH(12006, "两次输入密码不一致"),

    SEND_MAIL_FAIL(12007, "邮件发送失败或者邮箱未开通"),

    OLD_PASSWORD_NULL(12010, "原密码不能为空"),
    
    NEW_PASSWORD_NULL(12011, "新密码不能为空"),
 
    CONFIRM_NEW_PASSWORD_NULL(12012, "确认新密码不能为空"),
    
    OLD_PASSWORD_INCORRECT(12013, "原密码错误"),

    OPERATE_ILLEGAL(12014, "操作非法"),

    EMAIL_NULL(12015, "邮箱不能为空"),
   
    EMAIL_INCORRECT(12016, "请输入正确的邮箱"),
   
    EMAIL_ALREADY_AUTH(12017, "客官，您的邮箱已认证过了"),
    
    EMAIL_ALREADY_EXIST(12018, "邮箱已经存在"),

    ID_CARD_HAVE_NOT_BIND_ANY_USER(12019, "该身份证号未绑定任何用户"),

    HAVE_NOT_BIND_PHONE(12020, "未绑定手机"),

    PHONE_INCORRECT(12021, "电话号码不正确"),
    
    /** ------------------ 支付相关错误码以13开头 ----------------- */
    
    BANK_CARD_QUERY_FAILURE(13001, "查询银行卡失败"),
    
    BANK_AND_CARD_NO_NOT_MATCH(13002, "所选银行和银行卡不匹配"),
    
    DID_NOT_REAL_NAME_AUTH(13003, "请先进行实名认证"),
    
    DID_NOT_SET_PAY_PASSWORD(13004, "请先设置交易密码"),
    
    ID_CARD_ALREADY_EXIST(13005, "身份证已存在"),
    
    BANK_ALREADY_BIND(13006, "银行卡已经绑定"),
    
    PAY_FAILURE(13007, "支付失败"),
    
    DYNAMIC_VERIFY_CODE_NULL(13008, "手机动态验证码不能为空"),
   
    YIBAO_RECHARGE_ERROR(13009, "易宝充值失败"),
    
    CARD_NO_NULL(13010, "银行卡号不能为空"),
    
    DID_NOT_CHOOSE_THE_BANK(13011, "请选择银行"),
    
    RECHARGE_AMOUNT_NOT_LESS_THAN_ONE(13012, "充值金额不得小于1元"),
    
    RECHARGE_AMOUNT_TOO_LARGE(13013, "充值金额过大"),
    
    AMOUNT_FORMAT_INCORRECT(13014, "请输入正确的金额,仅支持2位小数"),
    
    MONEY_NULL(13015, "金额不能为空"),
    
    PAY_PASSWORD_NULL(13016, "交易密码不能为空"),
    
    PASSWORD_ERROR_5(13017, "交易密码输入不正确超过5次交易密码已被冻结,请联系客服或重置交易密码"),
    
    PASSWORD_ERROR(13018, "交易密码错误"),
 
    BANK_NAME_NULL(13019, "银行名不能为空"),
 
    BANK_NOT_EXIST(13020, "银行不存在"),

    RECHARGE_ALREADY_EXPIRE(13021, "充值信息已过期"),

    SERIALNUMBER_NOT_BLANK(13022, "资金流水号不能为空"),
 
    RECHARGETIME_NOT_BLANK(13023, "充值时间不能为空"),

    LIANLIAN_RECHARGE_ERROR(13024, "连连充值失败"),

    REQUESTID_NOT_BLANK(13025, "易宝绑卡返回requestid不能为空"),

    ORDERID_NOT_NULL(13026, "易宝支付返回orderid不能为空"),
    
    OPENING_BANK_NULL(13027, "开户行不能为空"),
   
    AMOUNT_TOO_LOW(13028, "提现金额太小"),

    CASH_FEE_NULL(13029, "手续费不能为空"),

    WITHDRAW_AVAILABLE_INSUFFICIENT(13030, "余额不足"),

    EXCEEDS_THE_MAX_CASHING_OF_CARD(13031, "超出该卡最高提现额度"),

    SYS_INVESTOR_MIN_CASH_NUM(13032, "最低提现金额限制"),

    USER_NOT_BING_BANK(13033, "用户未绑定该银行"),
    
    COUNTER_FEE_BING_BANK(13034, "手续费大于提现金额，请明日提取"),
    
    FAST_PAY_PASSWORD_NULL(13035, "手机支付密码不能为空"),
    
    FAST_PASSWORD_ERROR(13036, "手机支付密码错误"),
    
    FAST_PASSWORD_ERROR_5(13037, "手机支付密码输错已达5次，请修改后再投资"),
    
    RED_ENVELOPE_ERROR(13038, "红包兑换失败，请重试"),
    
    RED_ENVELOPE_code_ERROR(13039, "兑换码错误，请重试"),
    DID_NOT_SET_FAST_PAY_PASSWORD(13039, "请先设置手机支付密码"),
    
    /** ------------------ 标相关错误码以15开头 ----------------- */
  
    SUBJECT_NOT_EXIST(15001, "标的不存在"),
    
    SUBJECT_INVALID(15002, "标的无效"),
  
    INVEST_AVAILABLE_INSUFFICIENT(15003, "余额不足，请充值"),

    EXCEEDS_THE_MAXIMUM_AMOUNT_OF_INVESTMENT(15004, "本次投资金额超出最大可投资金额"),

    EXCEEDS_THE_AMOUNT_OF_INVESTMENT(15005, "投资金额超出可投资金额"),

    LESS_MIN(15006, "投资金额小于起投金额"),

    NOT_MEET_INCREASE_AMOUNT(15007, "投资金额不满足递投金额"),

    NO_REMAINING_THE_AMOUNT_OF_INVESTMENT(15008, "该标在您支付成功前已被其他投资者抢完"),

    SUBJECT_ONLY_NEWBIE(15009, "该标仅针对首次投资客户"),

    ACCUMULATIVE_TOTAL_EXCEEDS_THE_MAXIMUM_AMOUNT_OF_INVESTMENT(15010, "累计投资金额超出最大可投资金额"),

    SUBJECT_LOCKED(15011, "抢标人数太多啦，订单创建失败，请重试!"),

    SECKILL_SUBJECT_INVESTED(15012, "您已投过秒杀标，给别人个机会吧~"),

    REPAY_AVAILABLE_INSUFFICIENT(15013, "还款余额不足"),
    
    SUBJECT_INVALID2(150014, "该标在您支付成功前已被其他投资者抢完"),
    
    /** ------------------ 定期宝项目相关错误码以16开头 ----------------- */
    FIXEDBAOPROJECT_NOT_EXIST(160001, "项目不存在"),
  
    FIXEDBAOPROJECT_INVALID(160002, "该项目在您支付成功前已募满"),
    
    FIX_NO_REMAINING_THE_AMOUNT_OF_INVESTMENT(160003, "该项目在您支付成功前已募满"),
    
    /** ------------------ 活期宝项目相关错误码以17开头 ----------------- */
    CURRENTBAOPROJECT_NOT_EXIST(170001, "项目不存在"),
  
    CURRENTBAOPROJECT_INVALID(170002, "该项目已暂停"),
    
    CURRENT_NO_REMAINING_THE_AMOUNT_OF_INVESTMENT(170003, "该项目在您支付成功前已募满"),
    
    /** ------------------ 	其他相关错误码以19开头 ----------------- */

    URI_NOT_FOUND(19001, "资源不存在"),

    CACHE_EXCEPTION(19002, "缓存异常"),

    VERSION_EXCEPTION(19003, "请等待版本更新"),
    

    // ************************************************************************/
    // *****************************experience subject*************************/
    // ************************************************************************/

    PASSED(7001, "符合要求"),

    DISQUALIFICATION_OLD_NOTSUPPORT(7002, "不支持老客"),

    DISQUALIFICATION_NEW_NOTSUPPORT(7003, "不支持新客"),

    DISQUALIFICATION_DUPLICATE(7004, "重复投资同一体验标"),

    EXCEEDS_THE_QUOTA_OF_INVESTMENT(7005, "名额已用完"),

    DISQUALIFICATION_NEW_DUPLICATE(7006, "新客重复投资体验标");
	//...
	
	
	<a href="http://192.168.1.161:9090/rest/doc/" >返回到api</a>
	<br/><br/><br/>
	
	
	
</pre>	
</body>
</html>
