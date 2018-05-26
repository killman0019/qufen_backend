package com.tzg.common.utils.rest;

import java.io.Serializable;

public interface RestConstants extends Serializable {


	public final static String key_rest = "key_rest_sms_";
	
	/**
	 * 提现状态
	 */
	public final static ConstantProperty[] tbCash_iState={
		new ConstantProperty(1,"处理中"),
		new ConstantProperty(2,"处理成功"),
		new ConstantProperty(3,"处理失败")
		};
	
	/**
	 * 提现账号类型
	 */
	public final static ConstantProperty[] tbCash_iType={
		new ConstantProperty(1,"借款人"),
		new ConstantProperty(2,"投资人"),
		new ConstantProperty(3,"担保机构"),
		new ConstantProperty(4,"平台账户"),
		new ConstantProperty(5,"渠道")
		};
	/**
	 * 提现方式
	 */
	public final static ConstantProperty[] tbCash_vcPayType={
	 new ConstantProperty(1,"认证"),
	 new ConstantProperty(2,"网银")
	};
	
	/**
	 * 交易类型
	 */
	public final static ConstantProperty[] tbFinancialRecord_iTradeType={
		new ConstantProperty(1,"充值"),
		new ConstantProperty(2,"提现"),
		new ConstantProperty(3,"提现手续费"),
		new ConstantProperty(4,"投资"),
		new ConstantProperty(5,"本息回款"),
		new ConstantProperty(6,"本金回款"),
		new ConstantProperty(7,"利息回款"),
		new ConstantProperty(8,"提现失败退款"),
		new ConstantProperty(9,"捐赠"),
		
		new ConstantProperty(10,"充值扣款"),
		new ConstantProperty(11,"投资体验标"),
		new ConstantProperty(12,"体验标利息回款"),
		new ConstantProperty(13,""),
		new ConstantProperty(14,"投资掌薪计划"),
		new ConstantProperty(15,"掌薪计划返还本息"),
		new ConstantProperty(16,""),
		new ConstantProperty(17,""),
		new ConstantProperty(18,""),
		new ConstantProperty(19,"投资铜钱宝"),
		
		new ConstantProperty(20,"充值"),
		new ConstantProperty(21,"提现"),
		new ConstantProperty(22,"提现手续费"),
		new ConstantProperty(23,"借款"),
		new ConstantProperty(24,"服务费"),
		new ConstantProperty(25,"保证金支出"),
		new ConstantProperty(26,"还款"),
		new ConstantProperty(27,"管理费"),
		new ConstantProperty(28,"保证金收入"),
		new ConstantProperty(29,"补款还款"),
		new ConstantProperty(30,"补还管理费"),
		
		new ConstantProperty(31,""),
		new ConstantProperty(32,""),
		new ConstantProperty(33,""),
		new ConstantProperty(34,""),
		new ConstantProperty(35,""),
		new ConstantProperty(36,""),
		new ConstantProperty(37,""),
		new ConstantProperty(38,""),
		new ConstantProperty(39,""),
		
		//---------------平台产生的流水
		//----充值
		new ConstantProperty(40,"投资人充值"),
		new ConstantProperty(41,"充值手续费支出"),
		new ConstantProperty(42,"借款人充值"),
		new ConstantProperty(43,""),
		//----提现
		new ConstantProperty(44,"投资人提现"),
		new ConstantProperty(45,"平台提现手续费收入"),
		new ConstantProperty(46,"投资人提现失败退款"),
		new ConstantProperty(47,"平台提现手续费退款"),
		new ConstantProperty(48,"借款人提现"),
		//----投资
		new ConstantProperty(49,"红包支出"),
		//----募集满时
		new ConstantProperty(50,"募集完投资人投资总额"),
		new ConstantProperty(51,"借款人借款"),
		new ConstantProperty(52,"借款人借款服务费支出"),
		new ConstantProperty(53,"平台服务费收入"),
		new ConstantProperty(54,"借款人保证金支出"),
		new ConstantProperty(55,"平台保证金收入"),
		//----投资人还款（充值之后）
		new ConstantProperty(56,"借款人还款"),
		new ConstantProperty(57,"投资人回款"),
		new ConstantProperty(58,"借款人管理费支出"),
		new ConstantProperty(59,"平台管理费收入"),
		//----备用金垫付
		new ConstantProperty(60,"备用金垫付"),
		//----借款人补还（充值之后）
		new ConstantProperty(61,"借款人补还还款"),
		new ConstantProperty(62,"备用金回款"),
		new ConstantProperty(63,"平台保证金支出"),
		new ConstantProperty(64,"借款人保证金收入"),
		new ConstantProperty(65,"借款人补还管理费"),
		new ConstantProperty(66,""),
		new ConstantProperty(67,""),
		new ConstantProperty(68,""),
		new ConstantProperty(69,""),
		new ConstantProperty(70,""),
		new ConstantProperty(71,""),
		new ConstantProperty(72,""),
		new ConstantProperty(73,""),
		new ConstantProperty(74,""),
		new ConstantProperty(75,""),
		new ConstantProperty(76,""),
		new ConstantProperty(77,""),
		new ConstantProperty(78,""),
		new ConstantProperty(79,""),
		new ConstantProperty(80,""),
		new ConstantProperty(81,""),
		new ConstantProperty(82,""),
		new ConstantProperty(83,""),
		new ConstantProperty(84,""),
		new ConstantProperty(85,""),
		new ConstantProperty(86,""),
		new ConstantProperty(87,""),
		new ConstantProperty(88,""),
		new ConstantProperty(89,""),
		new ConstantProperty(90,""),
		new ConstantProperty(91,""),
		new ConstantProperty(92,""),
		new ConstantProperty(93,""),
		new ConstantProperty(94,""),
		new ConstantProperty(95,""),
		new ConstantProperty(96,""),
		new ConstantProperty(97,""),
		new ConstantProperty(98,""),
		new ConstantProperty(99,""),
		new ConstantProperty(100,""),
		new ConstantProperty(101,""),
		new ConstantProperty(102,""),
		new ConstantProperty(103,""),
		new ConstantProperty(104,""),
		new ConstantProperty(105,""),
		new ConstantProperty(106,""),
		new ConstantProperty(107,""),
		new ConstantProperty(108,""),
		new ConstantProperty(109,""),
		new ConstantProperty(110,""),
		new ConstantProperty(111,""),
		new ConstantProperty(112,""),
		new ConstantProperty(113,""),
		new ConstantProperty(114,""),
		new ConstantProperty(115,""),
		new ConstantProperty(116,""),
		new ConstantProperty(117,""),
		new ConstantProperty(118,""),
		new ConstantProperty(119,""),
		new ConstantProperty(120,""),
		new ConstantProperty(121,""),
		new ConstantProperty(122,""),
		new ConstantProperty(123,""),
		new ConstantProperty(124,""),
		new ConstantProperty(125,""),
		new ConstantProperty(126,""),
		new ConstantProperty(127,""),
		new ConstantProperty(128,""),
		new ConstantProperty(129,""),
		new ConstantProperty(130,""),
		new ConstantProperty(131,""),
		new ConstantProperty(132,""),
		new ConstantProperty(133,""),
		new ConstantProperty(134,""),
		new ConstantProperty(135,""),
		new ConstantProperty(136,""),
		new ConstantProperty(137,""),
		new ConstantProperty(138,""),
		new ConstantProperty(139,""),
		new ConstantProperty(140,""),
		new ConstantProperty(141,""),
		new ConstantProperty(142,""),
		new ConstantProperty(143,""),
		new ConstantProperty(144,""),
		new ConstantProperty(145,""),
		new ConstantProperty(146,""),
		new ConstantProperty(147,""),
		new ConstantProperty(148,""),
		new ConstantProperty(149,""),
		new ConstantProperty(150,""),
		new ConstantProperty(151,""),
		new ConstantProperty(152,""),
		new ConstantProperty(153,""),
		new ConstantProperty(154,""),
		new ConstantProperty(155,""),
		new ConstantProperty(156,""),
		new ConstantProperty(157,""),
		new ConstantProperty(158,""),
		new ConstantProperty(159,""),
		new ConstantProperty(160,""),
		new ConstantProperty(161,""),
		new ConstantProperty(162,""),
		new ConstantProperty(163,""),
		new ConstantProperty(164,""),
		new ConstantProperty(165,""),
		new ConstantProperty(166,""),
		new ConstantProperty(167,""),
		new ConstantProperty(168,""),
		new ConstantProperty(169,""),
		new ConstantProperty(170,""),
		new ConstantProperty(171,""),
		new ConstantProperty(172,""),
		new ConstantProperty(173,""),
		new ConstantProperty(174,""),
		new ConstantProperty(175,""),
		new ConstantProperty(176,""),
		new ConstantProperty(177,""),
		new ConstantProperty(178,""),
		new ConstantProperty(179,""),
		new ConstantProperty(180,""),
		new ConstantProperty(181,""),
		new ConstantProperty(182,""),
		new ConstantProperty(183,""),
		new ConstantProperty(184,""),
		new ConstantProperty(185,""),
		new ConstantProperty(186,""),
		new ConstantProperty(187,""),
		new ConstantProperty(188,""),
		new ConstantProperty(189,""),
		new ConstantProperty(190,""),
		new ConstantProperty(191,""),
		new ConstantProperty(192,""),
		new ConstantProperty(193,""),
		new ConstantProperty(194,""),
		new ConstantProperty(195,""),
		new ConstantProperty(196,""),
		new ConstantProperty(197,""),
		new ConstantProperty(198,""),
		new ConstantProperty(199,""),
		new ConstantProperty(200,"铜钱宝还息"),
		new ConstantProperty(201,"铜钱宝还本"),
		new ConstantProperty(202,"人人赚提现"),
		new ConstantProperty(203,"")
	};
	
	/**
	 * 标的类型
	 */
	public final static ConstantProperty[] tbSubject_iType = {
		new ConstantProperty(1,"普通标"),
		new ConstantProperty(2,"新手标"),
		new ConstantProperty(3,"普通活动标"),
		new ConstantProperty(4,"新手活动标")
	};
	
	/**
	 * 标的状态
	 */
	public final static ConstantProperty[] tbSubject_iState ={
		new ConstantProperty(1,"新录入"),
		new ConstantProperty(2,"审核中"),
		new ConstantProperty(3,"退回"),
		new ConstantProperty(4,"终止"),
		new ConstantProperty(5,"审核通过"),
		new ConstantProperty(6,"待预告"),
		new ConstantProperty(7,"处理中(与第三方交互中)"),
		new ConstantProperty(8,"处理失败（第三方处理失败）"),
		new ConstantProperty(9,"处理成功"),
		new ConstantProperty(10,"待开标"),
		new ConstantProperty(11,"投标中"),
		new ConstantProperty(12,"还款中"),
		new ConstantProperty(13,"结束"),
		new ConstantProperty(14,"投标结束，等待划款")
	};
	
	/**
	 * 红包类型
	 */
	public final static ConstantProperty[] tbRedRecord_iType = {
		new ConstantProperty(1,"注册红包"),
		new ConstantProperty(2,"邀请好友红包"),
		new ConstantProperty(3,"开通第三方托管"),
		new ConstantProperty(4,"兑换码兑换红包"),
		new ConstantProperty(5,"实名认证红包"),
		new ConstantProperty(6,"邮箱认证红包"),
		new ConstantProperty(7,"活动奖励")
	};
	
	public final static ConstantProperty[] tbSubject_iValueDateType ={
		new ConstantProperty(1,"T（成交日）+0"),
		new ConstantProperty(2,"T（成交日）+1"),
		new ConstantProperty(3,"T（成交日）+2"),
		new ConstantProperty(4,"T（成交日）+3"),
		new ConstantProperty(5,"结束时计息"),
		new ConstantProperty(6,"满标计息")
	};
	
	public final static ConstantProperty[] tbSubject_iRepayMode ={
		new ConstantProperty(1,"到期还本付息"),
		new ConstantProperty(2,"每日付息到期还本"),
		new ConstantProperty(3,"每月付息到期还本"),
		new ConstantProperty(4,"等额本息"),
		new ConstantProperty(5,"按季付息到期还本")
	};

	public final static ConstantProperty[] tbExpSubject_iRepayMode ={
		new ConstantProperty(1,"到期返还收益"),
		new ConstantProperty(2,"每日付息到期还本"),
		new ConstantProperty(3,"每月付息到期还本"),
		new ConstantProperty(4,"等额本息"),
		new ConstantProperty(5,"到期返还收益")
	};
	
	public final static ConstantProperty[] tbRedRecord_iState ={
		new ConstantProperty(1,"未领用"),
		new ConstantProperty(2,"已领用"),
		new ConstantProperty(3,"已使用"),
		new ConstantProperty(4,"已过期")
	};

	public final static ConstantProperty[] tbRepayRecord_iState ={
		new ConstantProperty(1,"未还"),
		new ConstantProperty(2,"已还"),//与第三方交互，处理成功则为已还。
		new ConstantProperty(3,"处理中"),
		new ConstantProperty(4,"处理失败")
	};
	/**
	 * 回款类型
	 */
	public final static ConstantProperty[] tbInvestRepayRecord_iType ={
		new ConstantProperty(1,"本息"),
		new ConstantProperty(2,"本金"),
		new ConstantProperty(3,"利息")
	};
	
	public final static ConstantProperty[] tbInvestRepayRecord_iState ={
		new ConstantProperty(1,"未还"),
		new ConstantProperty(2,"已还")
	};
	
	
	public final static ConstantProperty[] tbLoginAccount_iVerify = {
		new ConstantProperty(1,"已认证"),
		new ConstantProperty(2,"未认证")
	};
	
	public final static ConstantProperty[] tbLoginAccount_iType = {
		new ConstantProperty(1,"借款人"),
		new ConstantProperty(2,"投资人"),
		new ConstantProperty(3,"担保机构"),
		new ConstantProperty(4,"平台账户"),
		new ConstantProperty(5,"未渠道")
	};
	
	
	public final static ConstantProperty[] tbRepayRecord_iFundsResource = {
		new ConstantProperty(1,"借款人"),
		new ConstantProperty(2,"机构"),
		new ConstantProperty(3,"平台备用金"),
		new ConstantProperty(4,"借款人+机构"),
		new ConstantProperty(5,"借款人+平台备用金"),
		new ConstantProperty(6,"机构+平台备用金"),
		new ConstantProperty(7,"借款人+机构+平台备用金")
	};
	
	public final static ConstantProperty[] tbRepayRecord_iOverdue = {
		new ConstantProperty(1,"正常"),
		new ConstantProperty(2,"逾期")
	};
	
	public final static ConstantProperty[] tbHelpDetail_iState ={
		new ConstantProperty(1,"有效"),
		new ConstantProperty(2,"无效")
	};
	
	public final static ConstantProperty[] tbAppVersion_iType ={
		new ConstantProperty(1,"Android"),
		new ConstantProperty(2,"iOS")
	};
	
	public final static ConstantProperty[] tbMessage_iState ={
		new ConstantProperty(1,"未读"),
		new ConstantProperty(2,"已读")
	};
	
}
