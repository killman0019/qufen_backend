package com.tzg.common.utils;

/**
 * 
 *全局的常量 写在这里
 *
 */
public final class sysGlobals {
	/**状态启用-0*/
	public static final Integer ENABLE = 0;
	/**状态禁用-1*/
	public static final Integer DISABLE = 1;
	/**判断正式环境才发送短信*/
	public static final String DEV_ENVIRONMENT = "FORMAL";
	/**
	 * 文章的评论点赞奖励
	 */
	public static final String ARTICLE_COMMENT_AWARD = "ARTICLE_COMMENT_AWARD";
	/**
	 * 讨论的评论点赞奖励
	 */
	public static final String DISCUSS_COMMENT_AWARD = "DISCUSS_COMMENT_AWARD";
	/**
	 * 评测的评论点赞奖励
	 */
	public static final String EVA_COMMENT_AWARD = "EVA_COMMENT_AWARD";
	/**
	 * 单项评测的评论点赞奖励
	 */
	public static final String SINFLE_EVA_COMMENT_AWARD = "SINFLE_EVA_COMMENT_AWARD";
	/**
	 * 限制发布时间计算收益
	 */
	public static final String PUBLISH_DAY_COUNT = "PUBLISH_DAY_COUNT";
	/**
	 * 首次评论点赞奖励的token数量
	 */
	public static final String COMMENT_FIRST_AWARD_TOKEN = "COMMENT_FIRST_AWARD_TOKEN";
	/**
	 * 非首次评论点赞奖励的token数量
	 */
	public static final String COMMENT_AWARD_TOKEN = "COMMENT_AWARD_TOKEN";
	/**
	 * 普通评测点赞奖励
	 */
	public static final String GENERAL_EVA_PRAISE_AWARD_TOKEN = "GENERAL_EVA_PRAISE_AWARD_TOKEN";
	/**
	 * 专业评测点赞奖励
	 */
	public static final String PROFE_EVA_PRAISE_AWARD_TOKEN = "PROFE_EVA_PRAISE_AWARD_TOKEN";
	/**
	 * 单项评测点赞奖励
	 */
	public static final String SINGLE_EVA_PRAISE_AWARD_TOKEN = "SINGLE_EVA_PRAISE_AWARD_TOKEN";
	/**
	 * 讨论点赞奖励
	 */
	public static final String DISCUSS_PRAISE_AWARD_TOKEN = "DISCUSS_PRAISE_AWARD_TOKEN";
	/**
	 * 文章点赞奖励
	 */
	public static final String ARTICLE_PRAISE_AWARD_TOKEN = "ARTICLE_PRAISE_AWARD_TOKEN";
	/**
	 * 有效赞点赞给点赞人的奖励
	 */
	public static final String YX_PRAISE_TOKEN_TO_PRAYSER = "YX_PRAISE_TOKEN_TO_PRAYSER";
	/**
	 * 评测的url
	 */
	public static final String EVA_URL = "EVA_URL";
	/**
	 * 投诉次数达到多少次帖子隐藏
	 */
	public static final String REPORT_DEGREE = "REPORT_DEGREE";
	/**
	 * 文章的url
	 */
	public static final String ARTICLE_URL = "ARTICLE_URL";
	/**
	 * 讨论的url
	 */
	public static final String DIS_URL = "DIS_URL";

	/**推送给全部用户默认一次取多少消息*/
	public static Integer DEFAULT_MSG_COUNT = 1000;
	/**启动的线程数*/
	public static Integer START_THREAD_COUNT = 10;
	/**每次往个推那边推送的个数*/
	public static Integer SEND_GETUI_COUNT = 100;
	/**个推appId*/
	public static String appId = "bbhc5OhXzz7HtrqZ2coeN1";
	/**个推appKey*/
	public static String appKey = "FTfDlpO5ea5WaRk2LldqW5";
	/**个推masterSecret*/
	public static String masterSecret = "Aaio28TYk9ADMo54UOQtS5";
	/**个推请求地址*/
	public static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	/**区分的网络图标*/
	public static String QF_IMG_URL = "https://pic.qufen.top/projects1531359616526360779?imageView2/0/format/png";
	/**个推定时Code*/
	public static Integer GETUI_SET_TIME = 1;
	/**个推通知标题*/
	public static String GETUI_NOTIFY = "区分";
	/**区分官方的币名称*/
	public static String QDKL_CION = "FIND";
	/**Tokenrecords的setFunctionDesc*/
	public static String SET_FUNCTION_DESC = "点评挖矿收益";
	/**Tokenrecords的setMeno*/
	public static String SET_MENO = "完成挖矿活动奖励！";
	/**Tokenrecords的setFunctionType*/
	public static Integer SET_FUNCTION_TYPE = 80;
	/**tokenaward的setTokenAwardFunctionDesc*/
	public static String SET_TOKENAWARD_FUNCTIONDESC = "完成挖矿活动奖励";
	/**tokenaward的setTokenAwardFunctionType*/
	public static Integer SET_TOKENAWARD_FUNCTIONTYPE = 80;
	/**tokenaward的setIssuer*/
	public static String SET_ISSUER = "系统程序";
	/**tokenaward的setRemark*/
	public static String SET_REMARK = "完成挖矿活动奖励为FIND币，系统程序直接发放";

	/**发现-活跃用户数量（粉丝）大于30*/
	public static String HOT_USER = "HOT_USER";
	/**发现-热门项目(关注超过500人)*/
	public static String HOT_PROJECT = "HOT_PROJECT";
	/**爆料的点赞量超过10的内容自动加入当日的精选池*/
	public static String DISSCS_POINT_OF_PRAISE = "DISSCS_POINT_OF_PRAISE";
	/**文章，评测的点赞量超过10的内容自动加入当日的内容池*/
	public static String POST_POINT_OF_PRAISE = "POST_POINT_OF_PRAISE";
	/**用户的文章或评测被推至首页的时候，发送个推消息*/
	public static String CONTENT_GETUI_MSG_BEGIN = "恭喜您发布的内容《";
	/**用户的文章或评测被推至首页的时候，发送个推消息*/
	public static String CONTENT_GETUI_MSG_END = "》被推荐至首页。";
	/**推荐，爆料每页取出多少条让程序随机取出条数*/
	public static String POST_EVERY_PAGE = "POST_EVERY_PAGE";
	/**关注列表取帖子的点赞量大于5的*/
	public static String PRISE_TO_FOLLOW_POST = "PRISE_TO_FOLLOW_POST";
	/**封号信息提示的content*/
	public static String DISABLE_ACCOUNT_MSG = "尊敬的用户您好，您的账号被举报存在违规操作，已被禁用。若有疑问，请联系区分客服。";
	/**内容违规隐藏个推提示的标题*/
	public static String DISABLE_FOR_CONTENT_TITLE = "内容违规";
	/**内容违规隐藏个推提示的内容体*/
	public static String DISABLE_FOR_CONTENT = "尊敬的用户您好，您发布的内容被举报涉嫌违规，已被隐藏，请注意勿违反社区规则。";
	/**区分官方账号id*/
	public static Integer QUFEN_ACCOUNT_ID = 2;

	/**举报个推发送信息的title*/
	public static String REPORT_GETUITITLE = "举报结果";

}