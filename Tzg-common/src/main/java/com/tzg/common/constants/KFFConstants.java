/**
 *开封府项目相关常量 
 */
package com.tzg.common.constants;

/**
 * @author Administrator
 *
 */
public class KFFConstants {

	public static final String APP_TYPE_IOS_99 = "IOS_99";
	public static final String APP_TYPE_IOS_299 = "IOS_299";
	public static final String APP_TYPE_ANDROID = "ANDROID";
	
	public static final int PLATFORM_IOS_99 = 1;
	public static final int PLATFORM_ANDROID = 2;
	public static final int PLATFORM_IOS_299 = 3;
	
	//长内容长度
	public static final int MAX_BIG_CONTENT_LENGTH = 3000;
	//短内容长度
	public static final int MAX_NORMAL_CONTENT_LENGTH = 300;
	//标题长度
	public static final int MAX_NORMAL_TITLE_LENGTH = 30;

    //未收藏
	public static final int COLLECT_STATUS_NOCOLLECT = 0;
	//已收藏
	public static final int COLLECT_STATUS_COLLECTED = 1;
	//不显示收藏按钮
	public static final int COLLECT_STATUS_NOT_SHOW = 2;
	
	//有效
	public static final int STATUS_ACTIVE = 1;
	//无效
	public static final int STATUS_INACTIVE = 0;

	//关注类型：1-关注项目;2-关注帖子；3-关注用户
	public static final int FOLLOW_TYPE_PROJECT = 1;	
	public static final int FOLLOW_TYPE_POST = 2;
	public static final int FOLLOW_TYPE_USER = 3;

	//帖子类型：1-评测；2-讨论；3-文章
	public static final int POST_TYPE_EVALUATION = 1;	
	public static final int POST_TYPE_DISCUSS = 2;	
	public static final int POST_TYPE_ARTICLE = 3;	

	//1-帖子点赞；2-评论点赞
	public static final int PRAISE_TYPE_POST = 1;	
	public static final int PRAISE_TYPE_COMMENTS = 2;
	
	//点赞状态
	public static final int PRAISE_STATUS_NO = 0;
	public static final int PRAISE_STATUS_YES = 1;
	public static final int PRAISE_STATUS_NOSHOW = 2;	
	
	// 1-简单测评；2-全面系统专业测评;3-部分系统专业评测；4-用户自定义专业测评	
	public static final int EVA_MODEL_TYPE_SIMPLE = 1;
	public static final int EVA_MODEL_TYPE_FULL_PRO = 2;
	public static final int EVA_MODEL_TYPE_PART_PRO = 3;
	public static final int EVA_MODEL_TYPE_CUSTOMIZED = 4;
	
	//1用户头像；2帖子中内容图片3项目图片
	public static final int IMGTYPE_AVATARS = 1;
	public static final int IMGTYPE_POSTS = 2;
	public static final int IMGTYPE_PROJECTS = 3;
	
	//(1；待审核；2-审核通过；3-拒绝)
	public static final int PROJECT_STATE_PENDING = 1;
	public static final int PROJECT_STATE_PASS = 2;
	public static final int PROJECT_STATE_REJECT = 3;
	
	//1-关注；2-点赞；3-评论；4-赞赏；5-评论被回复；6-上榜单；7-奖励token
	public static final int MESSAGE_TYPE_FOLLOW = 1;
	public static final int MESSAGE_TYPE_PRAISE = 2;
	public static final int MESSAGE_TYPE_COMMENT = 3;
	public static final int MESSAGE_TYPE_COMMENDATION = 4;
	public static final int MESSAGE_TYPE_COMMENT_REPLY = 5;
	public static final int MESSAGE_TYPE_RANK = 6;
	public static final int MESSAGE_TYPE_AWARD_TOKEN = 7;
	
	//消息状态 1-未读；2-已读
	public static final int MESSAGE_STATE_UNREAD = 1;	
	public static final int MESSAGE_STATE_READ = 2;	

}
