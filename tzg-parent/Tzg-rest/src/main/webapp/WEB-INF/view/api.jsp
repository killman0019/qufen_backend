<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ctx" value="<%=basePath%>"/>

<html>
<head>
	<title>api doc</title>
</head>

<body>


<pre> 
<center><b>内部API</b></center>
<hr/>
<p style="color:#555">
测试服务器：${ctx}
生产环境均采用https协议, https测试： 端口改为8443

接口公共部分

URL请求地址以 /tzgCredit 开头的需要在head写入secId和token信息，如下：
Head：
	secId： 手机终端唯一标识（String）
	token： 安全认证唯一标识（String）

BaseRsponse:
	code  	Integer 0为成功，其他均为失败(判断是否:0为true, 其他code均为false);
	serverDatetime String 服务器时间
	msg	String	  错误消息
	data	Object 	  返回的数据

统一异常处理机制：
	前端获取BaseRsponse的code为0时，如果需要返回数据从BaseRsponse的data取，反之code不为0，直接在页面显示msg信息。

API列表:

1、注册：
①进入注册页面  (已整理   RegisterController 已废弃   )
	uri:/registerIndex  
	method:post
	params:
	return:
		BaseRsponse json string 
		data	String	注册消息 
②注册  (已整理)
	uri:/register
	method:post
	params:
		phoneNumber	String	手机号码
		password	String 密码
		dynamicVerifyCode	String  手机动态验证码
		agreeProtocol	String 是否同意协议 "true" "false"的字符串
		url	String 注册页面浏览器url 可选
		registerSource	String 注册来源  可选
		inviteCode	String 推荐码  可选
		deviceId	String 设备号 可选
		deviceType	String (ios,android)
	return:
		BaseRsponse json string 

2、登陆   (已整理     LoginController.java  补天)
	uri:/login
	method:post  
   	params:
		loginName	String	登陆账号
		password	String  密码明码
		secId		String 手机终端唯一标识
	return:
		BaseRsponse json string 
		<!-- loginaccount	Object  用户基本信息，参考<a href="http://192.168.1.177:8080/db/index_files/inde37.htm#160">http://192.168.1.177:8080/db/index_files/inde37.htm#160</a>
		investeraccount	Object	如果是投资人返回投资人的账户资金信息，参考<a href="http://192.168.1.177:8080/db/index_files/inde39.htm#170">http://192.168.1.177:8080/db/index_files/inde39.htm#170</a>
		borroweraccount	Object	如果是借款人返回借款人账户的资金信息，参考<a href="http://192.168.1.177:8080/db/index_files/inde36.htm#155">http://192.168.1.177:8080/db/index_files/inde36.htm#155</a>
	     -->
	    	token       	String  加密的安全认证标识   app和 rest 通信用到
	    	loginToken      String   app与 bbs 和人人赚 通信用到 

2.1、获取用户信息  (已整理)
	uri:/tzgCredit/investor/account/home/profile
	method:post  
   	params:
		userName	String 用户名
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse json string 
		loginaccount	Object 用户基本信息，请注意：vcName为真实姓名。参考<a href="http://192.168.1.177:8080/db/index_files/inde37.htm#160">http://192.168.1.177:8080/db/index_files/inde37.htm#160</a>
		investeraccount	Object	如果是投资人返回投资人的账户资金信息，参考<a href="http://192.168.1.177:8080/db/index_files/inde39.htm#170">http://192.168.1.177:8080/db/index_files/inde39.htm#170</a>
		borroweraccount	Object	如果是借款人返回借款人账户的资金信息，参考<a href="http://192.168.1.177:8080/db/index_files/inde36.htm#155">http://192.168.1.177:8080/db/index_files/inde36.htm#155</a>
		bindBankCount Integer 绑定银行卡的张数
	
		
3、手机号是否可以注册  (已整理   RegisterController.java 补天 )
	uri:/register/phoneAvailable
	method:post
	params:
		phone	String	手机号
	return：
		BaseRsponse json string 
		isRegister  1已注册 ，0 未注册
		
		
4、发送手机动态验证码 (已整理     DynamicValidateCodeController。java  补天)
	uri:/dynamicValidateCode/send
	method:post
	params:
		module	String	功能名：
				注册效验码("register"),  
				充值效验码("charge"),
				提现效验码("cash"),
				修改手机效验码("phoneNumberChange"),
				新手机效验码("newPhoneNumber"),
				忘记交易密码效验码("forgetPayPassword"),
				忘记密码效验码("forgetPassword");
				设置快捷支付密码设置效验码("fastPayPassword");
				修改快捷支付密码效验码("upFastPayPassword")
		phone	String	手机号 ,注册效验码,新手机效验码 需要传 真实手机号 ，忘记密码效验码,其他情况 传过来的 都是打星的手机号
	return:
		BaseRsponse json string
	
5、手机动态验证码是否正确 (已整理     DynamicValidateCodeController.java  补天)
	uri:/dynamicValidateCode/verify
	method:post
	params:
		module	String 功能名
		phone	String	手机号
		code	String 验证码
		token   String  
	return:
		BaseRsponse json string
		
6、我的账户首页  (已整理 )HomeController.java  补天
	uri:/tzgCredit/investor/account/home/v2
	method:post
	params:
		uid	Integer	用户ID
		secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse json string
		
		userObject Object 用户对象
		realName	String 姓名
		now	datetime	当前时间
		numRed	Integer 可用红包个数
		numTotalAsset	BigDecimal	总资产
		numOutstanding	BigDecimal	待收本金
		numOutstandingProfit	BigDecimal	待收利息
		numTotalProfit	BigDecimal	总收益
		numEnvelopes	BigDecimal	可用红包金额
		numAvailable	BigDecimal	可用余额
		numFinancialRecord Integer 资金流水笔数
		numTotalInvest	BigDecimal	总的投资金额
		isInvest	String	是否投资过
		totalInterest	BigDecimal	投资总收益
		msgCount	int	未读消息总数
		incomeList	前15日收益列表 map类型，key为序号，value为收益，最后一个为本日收益
		inviteFriendsH5Url 返回邀请好友链接地址	
		numCurrentBaoBalance BigDecimal	铜钱宝总额
		currentBaoAutoInvestIstate 铜钱宝铜钱宝自动设置 1：有效；2无效
		autoTransferProtocolUrl 自动转入转出协议
		bbc 	int	绑定银行卡数量
7、投资人回款记录：(已整理)  补天  InvestRepayController 已弃用
	uri:/tzgCredit/investor/asset/investRepay/list		
	method:post
	params:
		iloginAccountId	Integer	用户ID
		istate	Integer 回款状态： 1、未还；2、已还；
		pageSize	Integer	每页数量
		pageIndex	Integer	当前第几页
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		rows：[]	所有回款记录列表，具体每项含义参考：<a href="http://192.168.1.177:8080/db/">http://192.168.1.177:8080/db/</a>,tbInvestRepayRecord
	throws:
			
			
8、我的投资记录(已整理)  InvestController  补天
	uri:/tzgCredit/investor/asset/invest/list
	method:post
	params:
		// iloginAccountId	Integer	用户ID
		istate	String  逗号分隔的参数例如 istate:1 or 2
						1--处理中(与第三方交互中)；
						2--投资中（第三方处理成功）；
						3--投资失败（第三方处理失败）；
						4、回款中；
						5、回款结束。
		sortField	String 排序方式： dtInvest|投资时间；dtRepay|回款时间；
		pageSize	Integer	每页数量
		pageIndex	Integer	当前第几页
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		investRecord:	投资记录列表，具体每项含义参考：<a href="http://192.168.1.177:8080/db/">http://192.168.1.177:8080/db/</a>，tbInvestRecord
		numRepayCorpus:	Double	待收本金
		numRepayInterest:	Double	待收利息
		 /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * iloginAccountId
     */ 	
	private java.lang.Integer iloginAccountId;
	/**
	 * 投资人
	 */
	private String loginAccountName;
    /**
     * isubjectId
     */ 	
	private java.lang.Integer isubjectId;
	/**
	 * 标的 融资金额
	 */
	private String numTotalFinancing;
	
	/**
	 * 下一还款日
	 */
	private java.lang.String dtNextRepayStr;
	
	/**
	 * 借款期限
	 */
	private String numPeriod;
	
    /**
     * 标的名称
     */ 	
	private java.lang.String vcName;
	
	/**
	 * 项目名称
	 */
	private String projectName;
    /**
     * 获取标的利率：计划年利率+奖励利率+额外利率
     */ 	
	private String totalInterestRate;
    
    /**
     * 投资时间
     */ 	
	private java.lang.String dtInvestStr;
	
    /**
     * 投资金额
     */ 	
	private String numInvest;
    
	/**
     * 收益 
     */ 	
	private String numProfit;
	
	/**
     * 1--处理中(与第三方交互中)；             2--投资中（第三方处理成功）；             3--投资失败（第三方处理失败）；             4、回款中；             5、回款结束。
     */ 	
	private Integer istate;	
	throws:
			
9、我的红包（已整理） RedController.java 
	uri: /tzgCredit/investor/asset/red/list/v2
	method:post
	params:
	//	iloginAccountId	Integer	用户ID
		itype	String	金额变化类型	1 -- 增加； 
									2 -- 减少。
									3 -- 过期
									多个可逗号分隔
		pageSize	Integer	每页数量
		pageIndex	Integer	当前第几页
		secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		awardsdisplayList	红包列表，具体每项含义参考：<a href="http://192.168.1.177:8080/db/">http://192.168.1.177:8080/db/</a>，tbawardsdisplay
		availableRed	double	可用红包金额
		sevenExpired	七天内将过期的红包金额
	throws:
					
		
10、我的资金流水记录(已整理 FundController.java 资产页-》资金记录 全部列表 )
	uri:/tzgCredit/investor/asset/fund/list
	method:post
	params:
	//	iloginAccountId	Integer	用户ID
		itradeType	csv String	状态	eg:{"iloginAccountId":"1","pageIndex":1,"pageSize":10,   'itradeType':"19,20,21"}
1、投资人—充值（+）
2、投资人—提现（- ）
3、投资人—提现手续费（-）
4、投资人—投资（-）
5、投资人—本息回款（+）
6、投资人—本金回款（+）
7、投资人—利息回款（+）
8、投资人—提现失败退款（+）
9、投资人—捐赠（-）


20、借款人—充值（+）
21、借款人—提现（-）
22、借款人—提现手续费（-）
23、借款人—借款（+）
24、借款人—服务费（-）
25、借款人—保证金支出（-）
26、借款人—还款（-）
27、借款人—管理费（-）
28、借款人—保证金收入（+）
29、借款人—补还还款（-）
30、借款人—补还管理费（-）

---------------平台产生的流水
----充值
40、平台—投资人充值（+）
41、平台—充值手续费支出（-）
42、平台—借款人充值（+）


----提现
44、平台—投资人提现（-）
45、平台—平台提现手续费收入（+）
46、平台—投资人提现失败退款（+）
47、平台—平台提现手续费退款（-）
48、平台—借款人提现（-）

----投资
49、平台—红包支出（-）

----募集满时
50、平台—募集完投资人投资总额（-）
51、平台—借款人借款（+）
52、平台—借款人借款服务费支出（-）
53、平台—平台服务费收入（+）
54、平台—借款人保证金支出（-）
55、平台—平台保证金收入（+）

----投资人还款（充值之后）
56、平台—借款人还款（-）
57、平台—投资人回款（+）
58、平台—借款人管理费支出（-）
59、平台—平台管理费收入（+）

----备用金垫付
60、平台—备用金垫付（-）

----借款人补还（充值之后）
61、平台—借款人补还还款（-）
62、平台—备用金回款（+）
65、平台—借款人补还管理费（-）


63、平台—平台保证金支出（-）
64、平台—借款人保证金收入（+）
		pageSize	Integer	每页数量
		pageIndex	Integer	当前第几页
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		rows：[]	资金流水列表，具体每项含义参考：<a href="http://192.168.1.177:8080/db/">http://192.168.1.177:8080/db/</a>，tbFinancialRecord	
		
		【借款人额外返回：
		private String projectName;
		private String projectCode;
		private BigDecimal numInterestRate;
		private BigDecimal numManagementRate;
		】		
	throws:
	
11、充值页面 (已整理   ChargeNewController.java  补天) 启用
	未实名认证 account.getIverifyIdcard() 先去实名认证，参见 api 12
	未设置交易密码account.getVcPaymentcipher() 先去设置交易密码，参见  api 13
	实名认证 和 交易密码 完成后跳回充值页面
	uri:/tzgCredit/investor/asset/charge/new/index		
	method:post
	params:
	
		iterminalType Integer	可选。暂时不用，预留  可传值 1:pc,2:wap,3:ios,4:android
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		bindBankCardList 已绑定的银行卡列表
		bankList	银行列表
		vcName	认证姓名，加*号的
		vcCardCode	认证的身份证 ，加*号的
				
12、实名认证(已整理)  补天，已弃用
第一步：是否已经实名认证：isRealNameAuth
	uri:/tzgCredit/investor/account/setup/isRealNameAuth
	method:post
	params:
		iloginAccountId	Integer	用户ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识	
	return:
		BaseRsponse	json string	未认证返回0,已认证返回其他错误码
			
第二步：认证 (已整理)  补天，已弃用
	uri:/tzgCredit/investor/account/setup/realNameAuth
	method:post
	params:
		iloginAccountId	Integer	用户ID	
		vcName	String 用户真实姓名
		vcCardCode	String	身份证
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		
13、设置交易密码 (已整理)  PasswordController.java 补天  已弃用
	uri:/tzgCredit/investor/account/secure/setPayPassword
	method:post
	params:
		iloginAccountId	Integer 用户ID
		password	String 密码
		confirmPassword	String 确认密码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
			
14、 修改交易密码 (已整理) PasswordController.java 补天  已弃用
	uri:/tzgCredit/investor/account/secure/modifyPayPassword
	method:post
	params:
		iloginAccountId	Integer 用户ID
		newPassword	String 密码
		confirmNewPassword	String 确认密码
		oldPassword String 旧密码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		
① 判断原交易密码是否正确 (已整理)  PasswordController.java 补天  
	uri:/tzgCredit/investor/account/secure/verifyOldPayPassword
	method:post
	params:
		iloginAccountId	Integer 用户ID
		oldPassword	String 原密码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
			
15、银行卡 卡bin信息查询，银行 和卡号是否匹配 (已整理)  补天 ChargeNewController.java  已弃用
	uri:/tzgCredit/investor/asset/charge/new/queryBankCard
	method:post
	params:
		bankId	String	银行id
		cardNo	String 银行卡号
		secId	String 手机终端唯一标识
		token	String 安全认证唯一标识	
	return:
		BaseRsponse	json string	
			
16、充值 (已整理   ChargeNewController.java   补天)
如果未绑卡
	如果是连连，直接绑卡，返回支付请求参数
	如果是易宝，发送绑卡请求，客户端会收到一个短信和绑卡请求Id
如果已绑卡
	如果是连连，返回支付请求参数
	如果是易宝，发送绑卡请求，客户端会收到一个短信和orderId
	uri:/tzgCredit/investor/asset/charge/new/sendChargeRequest
	method:post
	params:
		vcName	String	 真实姓名 如果以实名认证是带*号遮挡的，没有认证传完整的便于后台认证。
		vcCardCode	String 身份证号
		amount	String	充值金额
		phone	String phone
		bankCode	String 用户选择的银行的代码
		cardNo	String 未绑定时是用户输入的银行卡号	，后台会做bin信息验证，已绑定是选择的绑定的卡的卡号
		iChannelType	Integer 用户选择的银行卡所属的银行的默认支付渠道 1 连连 2，易宝
		agreeProtocol	String 是否同意协议 true同意false不同意
		secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
		bbid   String 绑卡ID ，在 charge/new/index 中返回给app的 bindbankcardlist中的id
	return:
		BaseRsponse	json string	 code为0，确认信息成功
		iChannelType=1时，走连连渠道，返回连连充值请求参数信息
			chargeReqInfo  同旧版本，充值请求信息 see <a href="http://192.168.1.161:9090/rest/doc/charge" >http://192.168.1.161:9090/rest/doc/charge</a>
		iChannelType=2时，走易宝渠道，返回易宝充值的绑定请求id 和订单id  在最后确定一步需要将下面两个参数传回服务端
			requestid 存在
				requestid	String 绑卡请求返回的id 绑卡时返回
			requestid 不存在
				errorCode
				errorMsg
				
②易宝充值确认 (已整理    ChargeNewController.java   补天)
	uri:/tzgCredit/investor/asset/charge/new/confirm
	method:post
	params:
		//iloginAccountId	Integer	用户id
		requestId	String	绑定请求id，未绑卡时传
		orderId	String	订单id	已绑卡时传
		amount	String	充值金额
		code	String	短信验证码
		bankCode	String 用户选择的银行的代码
		cardNo	String  未绑定时是用户输入的银行卡号	，后台会做bin信息验证，已绑定是选择的绑定的卡的卡号
		phone	String	用户输入的手机号
		channelType	Integer 用户选择的银行卡所属的银行的默认支付渠道 1 连连 2，易宝
		secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
		bbid    String 绑定银行卡ID
	return:
		BaseRsponse	json string	 code为0，确认信息成功
		orderid 存在
			orderid	String	易宝的订单号
			recharge	充值信息对象
		orderid 不存在
			errorCode
			errorMsg			

17、 提现
第一步：
①进入提现页面(已整理)   WithdrawNewController 补天
	uri:/tzgCredit/investor/asset/withdraw/new/index
	method:post
	params:
		iloginAccountId	Integer	用户ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	

		bankList	用户绑定的银行卡列表 see<a href="http://192.168.1.177:8080/db/index_files/inde64.htm#271">http://192.168.1.177:8080/db/index_files/inde64.htm#271</a>
		bindBankCardList	用户已经绑定的卡列表，为空则未绑定任何卡
		vcName	String 姓名	如果未绑卡，则返回实名认证信息，用于绑卡 	新增加卡时，第二步会先绑定再提现
		vcCardCode	String	身份证
		withDrawCard	已绑卡时，提现银行卡 优先显示这张卡
		minCash	BigDecimal	最低提现额
		numAvailable	BigDecimal	可提现余额
		todayCashCount	Integer	当天已提过现提现次数
		
②计算取现手续费 (已整理)  WithdrawNewController 补天
	uri:/tzgCredit/investor/asset/withdraw/new/cashFee
	method:post
	params:
		//iloginAccountId	Integer	用户ID
		amount	String	提现金额
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		numCashFee	Double	手续费

第二步：提现
①发送易宝绑定请求获取验证码(已整理)  WithdrawNewController 补天
	uri:/tzgCredit/investor/asset/withdraw/new/yeepayBindBankRquest
	method:post
	params:
		//iloginAccountId	Integer	用户id
		vcName	String	 真实姓名 如果以实名认证是带*号遮挡的，没有认证传完整的便于后台认证。
		vcCardCode	String 身份证号
		phone	String 易宝用来发验证码的
		bankCode	String 用户选择的银行的代码
		cardNo	String 未绑定时是用户输入的银行卡号	，后台会做bin信息验证，已绑定是选择的绑定的卡的卡号
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
			requestid 存在
				requestid	String 绑卡请求返回的id 绑卡时返回
			requestid 不存在
				errorCode
				errorMsg

②(已整理)  WithdrawNewController 补天
uri:/tzgCredit/investor/asset/withdraw/new/yeepayBindBankConfirm
	method:post
	params:
		//iloginAccountId	Integer	用户id
		phone	String 易宝用来发验证码的
		code	String 短信验证码
		bankCode	String 用户选择的银行的代码
		cardNo	String 未绑定时是用户输入的银行卡号	，后台会做bin信息验证，已绑定是选择的绑定的卡的卡号
		requestId	String 绑定请求id
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
			requestid 存在
				requestid	String 绑卡请求返回的id 绑卡时返回
			requestid 不存在
				errorCode
				errorMsg

③确认提现(已整理)  WithdrawNewController  补天
	uri:/tzgCredit/investor/asset/withdraw/new/cash/confirm
	method:post
	params:
		//iloginAccountId	Integer	用户ID
		//bankCode	String	提现银行卡ID
		//cardNo	String	卡号
		amount	String	提现金额
		password	String	交易密码 sha-356加密字符串
		code	String 手机动态验证码
		channelType	Integer	支付通道
		channelTypeBeforeBind	String 提现时获取的已绑银行卡列表的bindChannelType,没有时传空字符串。
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
		bbid String 绑定银行卡id
	return:
		BaseRsponse	json string	
		realAmount	BigDecimal	实际提现	
		
④查询卡的绑定状态 (已整理)  WithdrawNewController  补天
	uri:/tzgCredit/investor/asset/withdraw/new/cardBindStatus
	method:post
	params:
		//iloginAccountId	Integer	用户ID
		cardNo	String	卡号
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		bindbankcard	绑定卡对象 istate为7时为解绑状态，具体参考tbbindbankcard表
								
19、 忘记登陆密码第一步(已整理)  LoginPasswordController  补天 已弃用
	uri:/account/resetpassword/first
	method:post
	params:
		phone	String 手机号
		code	String	动态验证码
	return:
		BaseRsponse	json string	
		forgetPasswordToken	String	保证安全token
		forgetPasswordId	String	用户id
			
20、 忘记登陆密码第二步(已整理)  LoginPasswordController  补天 已弃用
	uri:/account/resetpassword/second
	method:post
	params:
		phone	String	手机号
		password	String	新密码
		confirmPassword	String 确认新密码
		forgetPasswordToken	String	第一步的token
		forgetPasswordId	Integer 第一步返回的用户id
	return:
		BaseRsponse	json string	
	
21、 修改登陆密码 (已整理)  PasswordController  补天 
	uri:/tzgCredit/investor/account/secure/modifyPassword
	method:post
	params:
		//iloginAccountId	Integer 用户ID
		oldPassword	String 密码
		newPassword	String 密码
		confirmNewPassword	String 确认密码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	

22、① 找回交易密码第一步 (已整理)  PasswordController  补天 已弃用
	uri:/tzgCredit/investor/account/secure/findPayPassword
	method:post
	params:
		uid	Integer	用户id
		phone	String  手机号
		cardNo	String	身份证号
		code	String	手机动态验证码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	code为 0成功，调用第二步修改交易密码接口
		
②	找回交易密码第二步 (已整理)  PasswordController  补天 已弃用
	uri:/tzgCredit/investor/account/secure/findPayPassword2
	method:post
	params:
		iloginAccountId	Integer 用户ID
		newPassword	String 密码
		confirmNewPassword	String 确认密码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		
23、 绑定邮箱(已整理)   SetupController  补天
	uri:/tzgCredit/investor/account/setup/bindEmail
	method:post
	params:
		iloginAccountId	Integer 用户ID
		email	String	邮箱
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		
①查询邮箱是否已经绑定(已整理)  SetupController  补天 已弃用
	uri:/tzgCredit/investor/account/setup/isBindEmail
	method:post
	params:
		iloginAccountId	Integer 用户ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string		

② 修改邮箱(已整理)  SetupController  补天 已弃用
	uri:/tzgCredit/investor/account/setup/modifyEmail
	method:post
	params:
		iloginAccountId	Integer 用户Id
		email	String	邮箱
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string
				
24、 修改绑定手机第一步 ,确认旧手机号码
	
	请求    
	-->4、发送手机动态验证码，module传phoneNumberChange)，给旧手机号发短信验证码
	
		
25、 修改绑定手机第二步，确认新手机号码 (已整理) 确定按钮  (SetupController.java 补天)
	uri:/tzgCredit/investor/account/setup/modifyPhone
	method:post
	params:
		phone	String	新手机号
		code	String	手机动态验证码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		
26、 资金流水交易详情(已整理   FundController.java 资产页-》资金记录-》资金明细)  补天
	uri:/tzgCredit/investor/asset/fund/financial/detail
	method:post
	params:
		id	Integer	资金流水ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string
		action	String	资金流水类型对应的动作
		operateTime	datetime	操作时间
		operateAmount	Double	涉及金额
		vcCode	String	资金流水号
		target	String	资金流水对应的对象
		notice	String	说明
		
		(
		投资特有属性:
		iType	Integer	标的类型
		numDiscount	Double	贴现额
		actualAmount	Double 实际支出
		)
	throws:
			
27、 我投资标的记录的详情（已整理）  InvestController.java  补天 已弃用
	uri:/tzgCredit/investor/asset/invest/detail
	method: post
	params:
	    type Integer 投资类型1:标的；2：定期宝
		id	Integer	投资记录ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string
		investrecord Object	投资记录详情，详细含义参考：<a href="http://192.168.1.177:8080/db/index_files/inde8.htm#23">http://192.168.1.177:8080/db/index_files/inde8.htm#23</a>
		projectNameCode	String	项目名字+编号

		
28、 我的投资回款记录--- 一次投资记录的回款记录 (已整理)  InvestReayController   补天 
	uri:/tzgCredit/investor/asset/investRepay/oneInvestRepayRecord
	method:post
	params:
	    type Integer 投资类型1:标的；2：定期宝
		//iloginAccountId	Integer 用户Id
		iSubjectID	Integer	标的ID
		iInvestRecordID Integer  投资记录ID
		pageSize	Integer
		pageIndex	Integer
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string
		Investrepayrecord	某一次投资的回款记录 列表，具体每项含义参考：<a href="http://192.168.1.177:8080/db/">http://192.168.1.177:8080/db/</a>,tbInvestRepayRecord

/**		
29、 首页homepage (已整理 IndexController.java "推荐"页   已启用 补天)
	uri:/home/index
	method:post
	params:
		userId	Integer	用户ID  可选参数
		loginStatus	String 用户登录状态  "true" :已登录，"false":未登录
	return:
		BaseRsponse	json string
		homeSubject 标的信息，参考<a href="http://192.168.1.177:8080/db/index_files/inde10.htm#31">http://192.168.1.177:8080/db/index_files/inde10.htm#31</a>
		currentbaoRecommendSlogan String 铜钱宝app推荐页文案
		pyramidRecommendSlogan String 人人赚app推荐页文案
		pyramidURL    String 人人赚活动入口链接
		pyramidInductionURL String 人人赚活动入口链接
		
		investmentIncome	万元收益
	throws:
*/

	
29、 首页homepage (已整理 SubjectController.java "推荐"页     )   补天
	uri:/index/v2
	method:post
	params:
	    plat	String	app平台  “android”，“ios”   在客户的header中
	    channel	String 活动渠道   可选参数     在客户的header中
		token	String	在客户的header中
	return:
		BaseRsponse	json string
		homeSubject 标的信息，参考<a href="http://192.168.1.177:8080/db/index_files/inde10.htm#31">http://192.168.1.177:8080/db/index_files/inde10.htm#31</a>
		currentbaoRecommendSlogan String 铜钱宝app推荐页文案
		pyramidRecommendSlogan String 人人赚app推荐页文案
		pyramidURL    String 人人赚活动入口链接
		pyramidInductionURL String 人人赚活动入口链接
		appAnnouncement   <Message> 客户端公告  
		investmentIncome	万元收益
	throws:
	
	
	
/**			
30、 标的详情 (已整理 IndexController.java “资产”页点一个标的进入 标的详情   补天  已弃用 )
	uri:/home/subject/detail
	method:post
	params:
		subjectId	Integer	标的id
		projectId	Integer	项目id
		uid	Integer	用户id
	return:
		BaseRsponse	json string
		project	Object 项目信息 ，参考<a href="http://192.168.1.177:8080/db/index_files/inde14.htm#47">http://192.168.1.177:8080/db/index_files/inde14.htm#47</a>
		subject Object 标的对象
		imgUrlPrefix	String 图片url前缀
		projectsafeguardList	List	安全保障,参考<a href="http://192.168.1.177:8080/db/index_files/inde13.htm#43">http://192.168.1.177:8080/db/index_files/inde13.htm#43</a>
		investCount	Integer	投资笔数
		firstPlan	List	还款计划第一条记录,参考<a href="http://192.168.1.177:8080/db/index_files/inde21.htm#75">http://192.168.1.177:8080/db/index_files/inde21.htm#75</a>
		guaranteeName	String	担保机构名称
		investmentIncome Double	每万元预估受益
		systemCurrentTime long	服务器当前时间毫秒数
		remainInvestAmount	Double	标的剩余可投
	throws:
*/	

30、 标的详情 (已整理 SubjectController.java “资产”页点一个标的进入 标的详情 )  补天
	uri:/subject/detail/v2
	method:post
	params:
		subjectId	Integer	标的id
		token	String	接口在 登录 时给的客户端的 token  保存在客户端的header中
	return:
		BaseRsponse	json string
		subject Object 标的对象
		guaranteeName	String	担保机构名称
		investmentIncome Double	每万元预估受益
		systemCurrentTime long	服务器当前时间毫秒数
		remainInvestAmount	Double	标的剩余可投
	throws:		
		
		
		
31、 项目详情 (已整理  IndexController.java 项目介绍 -已弃用)
	uri:/home/subject/project/detail
	method:post
	params:
		id	Integer	项目id
	return:
		BaseRsponse	json string
		project	Object	项目详情,参考<a href="http://192.168.1.177:8080/db/index_files/inde14.htm#47">http://192.168.1.177:8080/db/index_files/inde14.htm#47</a>
		imgUrlPrefix	String 图片url前缀
		projectcataloglist	List	风控审核材料,参考<a href="http://192.168.1.177:8080/db/index_files/inde18.htm#63">http://192.168.1.177:8080/db/index_files/inde18.htm#63</a>
		riskpersonal	Object	借款人风控信息,参考<a href="http://192.168.1.177:8080/db/index_files/inde16.htm#55">http://192.168.1.177:8080/db/index_files/inde16.htm#55</a>
		projectattachList	List	风控附件,参考<a href="http://192.168.1.177:8080/db/index_files/inde20.htm#71">http://192.168.1.177:8080/db/index_files/inde20.htm#71</a>
	throws:
		
32、 项目安全保障 (已整理  IndexController.java 安全保障 -已弃用)
	uri:/home/subject/safeguard
	method:post
	params:
		projectId	Integer	项目ID
	return:
		BaseRsponse	json string
		imgUrlPrefix	String 图片url前缀  
		projectsafeguardList	List	保障措施,参考<a href="http://192.168.1.177:8080/db/index_files/inde13.htm#43">http://192.168.1.177:8080/db/index_files/inde13.htm#43</a>
		guarantee	Object	担保机构,参考<a href="http://192.168.1.177:8080/db/">http://192.168.1.177:8080/db/</a>
		projectsuggestions	Object	风控意见,参考<a href="http://192.168.1.177:8080/db/index_files/inde19.htm#67">http://192.168.1.177:8080/db/index_files/inde19.htm#67</a>
	throws:
		
33、 该标的投资记录 （IndexController.java 投资记录 -已弃用）
	uri:/home/subject/investRecord
	method:post
	params:
		subjectId	Integer	标的ID
		pageIndex	Integer	当前第几页	
		pageSize	Integer	每页数量
	return:
		BaseRsponse	json string
		InvestrecordPage 分页对象	该标的投资记录，参考<a href="http://192.168.1.177:8080/db/index_files/inde8.htm#23">http://192.168.1.177:8080/db/index_files/inde8.htm#23</a>
	throws:
		
34、 该标的还款计划  （IndexController.java 还款计划  -已弃用）
	uri:/home/subject/repayPlan
	method:post
	params:
		subjectId	Integer	标的ID
		pageIndex	Integer	当前第几页	
		pageSize	Integer	每页数量
	return:
		BaseRsponse	json string
		subjectplanPage	分页对象	该标的还款计划，参考<a href="http://192.168.1.177:8080/db/index_files/inde21.htm#75">http://192.168.1.177:8080/db/index_files/inde21.htm#75</a> 	
	throws:

35、 投资交易 	（已整理）   InvestController  补天
	uri:/tzgCredit/investor/asset/invest/exchange/v2
	method:post
	params:
		iloginAccountId	Integer	用户ID
		subjectId	Integer	标的Id
		investAmt	String	投资金额
		redAmt	String  用户勾选的红包金额
		agreeProtocol	String 是否同意协议 true false字符串
		payPassword	String	交易密码
		investDevice	String (ios,android)
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		investrecordId	String	投资记录id
		expectEarnings	Double	预期收益
		
36、 投资可用红包列表（已整理）  InvestController  补天
	uri:/tzgCredit/investor/asset/invest/availableRed/v2
	method:post
	params:
		//iloginAccountId	Integer	用户ID
		subjectId	Integer 标的ID
		numPeriod	Integer	标的借款期限
		investAmt	String	投资金额
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string		
		redAmt 红包金额

37、	理财产品	（已弃用）  IndexController  补天 已弃用
	uri: /home/subject/list
	method:post
	params:
		iloginAccountId	Integer	用户ID
		loginStatus	String	"true or "false"
		pageIndex	Integer	
		pageSize	Integer
	return:
		BaseRsponse	json string	
		Subject []	标的的分页对象，参考<a href="http://192.168.1.177:8080/db/index_files/inde10.htm#31">http://192.168.1.177:8080/db/index_files/inde10.htm#31</a>
	
38、	预期收益	（已整理）   InvestController   补天  已弃用 
	uri: /tzgCredit/investor/asset/invest/expectEarnings
	method:post
	params:
		subjectId	Integer	标的ID
		investAmt	String	投资金额
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		data	Double 预期收益
	
<h4><b>借款人</b></h4>
		
39、	 借款人--账户中心 
	uri: /debtor/account/index
	method:post
	params:
		uid	Integer	用户ID
	return:
		BaseRsponse	json string	
		userObject Object 用户对象
		realName	String 姓名
		now	datetime	当前时间
		borroweraccount	Object	借款人账户资金信息对象，参考<a href="http://192.168.1.177:8080/db/index_files/inde34.htm#119">http://192.168.1.177:8080/db/index_files/inde34.htm#119</a>
		repayrecord	Object 待还款对象,参考<a href="http://192.168.1.177:8080/db/index_files/inde12.htm#39">http://192.168.1.177:8080/db/index_files/inde12.htm#39</a>
		numFinancialRecord Integer 资金流水笔数
	throws:
		1002,"missing args"			
		
		
40、	 借款人--我的借款记录
	uri: /debtor/account/borrowRecord
	method:post
	params:
		uid	Integer	用户ID
		iState	Integer	借款状态 ，可选，1、未还；2、已还；不传为所有记录。
		pageIndex	Integer	
		pageSize	Integer
	return:
		BaseRsponse	json string	
		data 借款记录的分页对象，参考 <a href="http://192.168.1.177:8080/db/index_files/inde4.htm#7">http://192.168.1.177:8080/db/index_files/inde4.htm#7</a>
		
		补充字段：
		private BigDecimal borrowerSubjectAmount;	已/未还款总额
		private String borrowerSubjectOverdue;  该标的借款是否存在逾期
		private Integer borrowerSubjectPeriod;	借款总期次
		private String borrowerSubjectProjectCode;	项目编号
		private String borrowerSubjectName;	标的名称
	throws:
		1002,"missing args"			
				
41、	 借款人--借款详情
	uri: /debtor/account/borrowRecord/detail
	method:post
	params:
		id	Integer	借款记录ID
	return:
		BaseRsponse	json string	
		data	单标的还款计划分页对象，参考<a href="http://192.168.1.177:8080/db/index_files/inde4.htm#7">http://192.168.1.177:8080/db/index_files/inde4.htm#7</a>
	throws:
	
42、	 借款人--借款记录的单标的还款计划 
	uri: /debtor/account/borrowRecord/repayPlan 
	method:post
	params:
		iSubjectID	Integer	标的ID
		pageIndex	Integer	
		pageSize	Integer
	return:
		BaseRsponse	json string	
		data	单标的还款计划分页对象，参考<a href="http://192.168.1.177:8080/db/index_files/inde12.htm#47">http://192.168.1.177:8080/db/index_files/inde12.htm#47</a>
	throws:

43、	 借款人-资金流水交易记录  ，同投资人的资金流水记录接口
44、	 借款人--资金流水详情 ，同投资人的资金流水详情接口

45、	 借款人--我要还款 
	uri: /debtor/repayment/repayPage 
	method:post
	params:
		uid	Integer	用户ID
		borrowRecordId	Integer 借款ID
	return:
		BaseRsponse	json string	
		subject	Object	标的信息，参考http://192.168.1.177:8080/db/ 标的信息
		borrowRecord	Object	借款记录，参考借款记录
		repayrecord	Object	还款记录，参考 还款情况
		borroweraccount	Object	借款人账户，参考 借款人账户
	throws:		

46、	 借款人--还款 确认
	uri: /debtor/repayment/repay 
	method:post
	params:
		uid	Integer	用户ID
		repayrecordId  Integer 还款记录id
		payPassword String 交易密码
	return:
		BaseRsponse	json string	
		
	throws:	
		4005,"pay password can not be null"
		2002,"password error"
		6000,"repay available insufficient"
		
47、	 借款人--还款记录
	uri: /debtor/account/repayRecord 
	method:post
	params:
		uid	Integer	用户ID
		pageIndex	Integer	
		pageSize	Integer	
	return:
		BaseRsponse	json string
		data	还款记录分页对象，参考<a href="http://192.168.1.177:8080/db/index_files/inde12.htm#47">http://192.168.1.177:8080/db/index_files/inde12.htm#47</a>
	throws:		

48、账户管理功能同投资人

49、

帮助中心分类列表    AboutController   补天  已弃用 
	uri: /about/help/topic/typeList   
	method:post
	params:
		pageIndex	Integer	
		pageSize	Integer	
	return:
		BaseRsponse	json string
		data	帮助中心类别列表对象， 参考 http://192.168.1.177:8080/db/ 表格帮助中心类别 tbHelpCategy
	throws:	


帮助中心列表      AboutController   补天  已弃用 
	uri: /about/help/topic/list
	method:post
	params:
		helpCategyId	Integer	类别ID， 目前传数字 1
		pageIndex	Integer	
		pageSize	Integer	
	return:
		BaseRsponse	json string
		data	帮助中心对象，参考<a href="http://192.168.1.177:8080/db/index_files/inde28.htm#121">http://192.168.1.177:8080/db/index_files/inde28.htm#121</a>
	throws:		
		
50、检测升级    AboutController   补天   
	uri: /about/update/checkUpdate
	method:post
	params:
		version	String	版本字符串
		type	Integer 客户端类型,1:android;2:ios;
	return:
		BaseRsponse	json string
		updateable	是否有可更新的版本，true：有；false:无；
		appversion	版本信息对象
	throws:		
	
	
51、是否交易过	（已弃用）
	uri: /investor/asset/invest/isExchange	
	method:post
	params:
		uid	Integer	用户id
	return:
		BaseRsponse	json string
		data true：是，false：否
	throws:		

52、首页焦点图 (已整理 IndexController.java “推荐”页焦点图)   补天 已弃用  ，功能放 在 /index/v2
	uri: /home/focus
	method:post
	params:
	return:
		BaseRsponse	json string
		data	list 焦点图对象列表
		        activitylist 活动图
	throws:	
		
53、获取服务器时间毫秒数（IndexController.java 服务器时间毫秒数)   补天 已弃用   
	uri: /home/systemCurrentTimeMillis
	method:post
	params:
	return:
		BaseRsponse	json string
		data	long  当前服务器时间毫秒数
	throws:		
54、获取标的剩余可投金额 (IndexController.java 标的详情页 中的 剩余可投金额)  补天  已 弃用  功能放在  SubjectController  /subject/detail/v2 中
	uri: /home/subject/remainInvestAmount
	method:post
	params:
		subjectId Integer
	return:
		BaseRsponse	json string
		data	String  标的剩余可投金额
	throws:

	
55、预期收益无需auth验证(IndexController.java 预期收益)   补天  已弃用
	uri: /home/subject/invest/expectEarnings
	method:post
	params:
		subjectId Integer
		investAmt	String 投资金额
	return:
		BaseRsponse	json string
		investAmt	Double	投资金额
		investmentIncome	Double  预期收益  浮点数字符串
	throws:
	
56 ios审核敏感内容开关 (已整理)  AboutController  补天 已弃用
	uri:/tzgCredit/about/iosAuditContentSwitch
	method:post
	params:
		secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json string
		data	String  1:显示，0： 不显示
	
57 注册协议地址 (已整理)   RegisterController.java  补天
	uri:/register/protocol
	method:post
	params:
	return:
		BaseRsponse	json string
		data	String 注册协议url地址
	
58 红包兑换码； (已修改)   RedController.java  补天
	uri: /tzgCredit/investor/asset/red/useCode
	method:post
	params:
		uid	Integer 用户id
		code	String	红包兑换码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string
		result	String	"0"：成功 ;其他数字：失败	
		numAwardsAmt	double	兑换的金额
	throws:
	
59 红包改版，版本兼容，审核期间是否启用红包功能，以保审核顺利通过  （已过时，弃用，不修改）  AboutController。java  补天  已弃用
	uri:/about/iosAuditRedSwitch
	method:post
	params:
	return:
		BaseRsponse	json string
		data	String  1:启用，0： 不启用
	throws:
/** 	
60 标的详情底部状态 （ IndexController.java 标的详情 中的 底部状态提示  已废弃  补天）
	uri:/home/subject/subjectBottomStatus
	method:post
	params:
		subjectId Integer
		uid	Integer
	return:
		BaseRsponse	json string
		status: true 亮色,false 灰色
		subjectStatus: 
					1:立即投资或者1元秒杀
					2:请登录后进行投资
					3:余额不足,请充值
					4:请先完成投资前准备
					10:待开标
					12:还款中
					13:已结束
					14:该标仅针对首次投资客户
					15:您已投过秒杀标
					20:借款人登录
		msg:显示消息
		defaultInvestAmt:默认投资额
	throws:
*/	
60 标的详情底部状态 （ SubjectController.java 标的详情 中的 底部状态提示）
	uri:/subject/subjectBottomStatus
	method:post
	params:
		subjectId Integer
		token	String
	return:
		BaseRsponse	json string
		status: true 亮色,false 灰色
		subjectStatus: 
					1:立即投资或者1元秒杀
					2:请登录后进行投资
					3:余额不足,请充值
					4:请先完成投资前准备
					10:待开标
					12:还款中
					13:已结束
					14:该标仅针对首次投资客户
					15:您已投过秒杀标
					20:借款人登录
		msg:显示消息
		defaultInvestAmt:默认投资额
	throws:	
	
/**	
		
61、	APP 理财产品列表 2.0	(已修改 IndexController.java “投资”页   补天  已弃用)
	uri: /home/subject/list/v2
	method:post
	params:
		iloginAccountId	Integer	用户ID
		loginStatus	String	"true or "false"
	return:
		BaseRsponse	json string	
		experience	投资中的体验标Experiencesubject对象列表，目前产品设计貌似只有一个
		activity	Subject对象列表
		hot		Subject对象列表
		experienceWating	待开体验标Experiencesubject对象列表，目前产品设计貌似只有一个
		experienceRepaying  还款中的，你懂的
		waiting	Subject对象列表
		repay	Subject对象列表
		repaying	Subject对象列表
		fixedbao	project对象列表
		currentbao	project对象列表
		repayTotalRevenue	已还款总额（含本金和利息）
	throws:
*/
	
61、	APP 理财产品列表 3.0	(已修改 SubjectController.java ) 补天
	uri: /subject/list/v3
	method:post
	params:
		iloginAccountId	Integer	用户ID
		loginStatus	String	"true or "false"
	return:
		BaseRsponse	json string	
		experience	投资中的体验标Experiencesubject对象列表，目前产品设计貌似只有一个
		hot		Subject对象列表
		waiting	Subject对象列表
		repay	Subject对象列表    已还款
		repaying	Subject对象列表   还款中
		fixedbao	project对象列表
		currentbao	project对象列表
		repayTotalRevenue	已还款总额（含本金和利息）
		currentbaoIntetrestRate  BigDecimal 活期宝利率
		currentbaoMinAmount BigDecimal  活期宝金额
	throws:
		
	
		
/** 		
62、	APP 理财产品列表 2.0	更多列表(已修改 IndexController.java "投资"页中的>按钮    废弃 补天)
	uri: /home/subject/list/more
	method:post
	params:
		iloginAccountId	Integer	用户ID
		loginStatus	String	"true or "false"
		itype	标的类型  eg: "2,3,4,5"
		istate	标的状态  eg:"11,12"
		productType	Integer	1非体验标 2体验标,此参数还是预留吧， 暂时改为只传1
		pageIndex	Integer	
		pageSize	Integer
	return:
		BaseRsponse	json string	
		productType==1，moreList，	正常标 Subject对象列表
		istate包含有10||istate包含有11，额外返回 体验标 experienceList，Experiencesubject对象列表
	throws:
		1002,"missing args"	
*/

62、	APP 理财产品列表 2.0	更多列表(已修改 SubjectController.java   ) 补天
	uri: /subject/list/more
	method:post
	params:
	    token String 
		loginStatus	String	"true or "false"
		itype	标的类型  eg: "2,3,4,5"
		istate	标的状态  eg:"11,12"
		productType	Integer	1非体验标 2体验标,此参数还是预留吧， 暂时改为只传1
		pageIndex	Integer	
		pageSize	Integer
	return:
		BaseRsponse	json string	
		productType==1，moreList，	正常标 Subject对象列表
		istate包含有10||istate包含有11，额外返回 体验标 experienceList，Experiencesubject对象列表
	throws:
		1002,"missing args"	
		

63、 消息列表 （新增） (MessageController。java 补天)
	url: /tzgCredit/messagelist
	method:	post
	params:
		pageIndex	int 页码
		pageSize	int 每页记录数
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		
64、 消息详情 （新增 MessageController。java 补天）
	url: /tzgCredit/messagedetail
	method:	post
	params:
		messageId	int 消息ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		
65、邀请二维码 (新增)   PasswordController   补天  已弃用
	url：/tzgCredit/investor/account/secure/showQRcode
	method:	post
	params:
		uid		int 登陆用户id
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识

65、获取邀请码和邀请地址 (新增)  PasswordController   补天  待确认
	url：/tzgCredit/investor/account/secure/invitationCode
	method:	post
	params:
		uid		int 登陆用户id
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return：
		BaseRsponse json string 
		invitationCode String 邀请码
		invitationUrl String 邀请地址
		amount String 邀请好友红包金额

 66、添加消息通知 (新增)   MessageController    补天 
	url: /tzgCredit/addMessageNotify
	method:	post
	params:
		//loginAccount	int 登陆用户id
		type	int 通知类别
		bitType	int 标的类型 (1普通标 2体验标)
		subjectId	int 标的id
		platform	int 设备类型 (ios设备固定 1 android设备固定2)
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return：
		BaseRsponse json string
		
67、删除用户所有消息(新增)   MessageController    补天 
	url: /tzgCredit/deleteMessageByUserId
	method:	post
	params:
		//loginAccount	int 登陆用户id
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return：
		BaseRsponse json string

68、 找回密码 (新增)  LoginPasswordController   补天
	uri:/account/resetpassword/forgetPassword
	method:post
	params:
		phone	String	手机号
		code	String	手机动态验证码
		password	String	新密码
		confirmPassword	String 确认新密码
	return:
		BaseRsponse	json string	
		
69、 根据支付密码设置快捷支付密码 (新增)  PasswordController  补天 
	uri:/tzgCredit/investor/account/secure/setFastPayPasswordForPayPwd
	method:post
	params:
		//iloginAccountId	Integer	登录用户ID
		confirmPassword	String	交易密码
		fastPayPassword	String	快捷支付密码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		fastPayPassword String 快捷支付密码
		
70、 根据手机动态验证码设置快捷支付密码 (新增)  PasswordController  补天 
	uri:/tzgCredit/investor/account/secure/setFastPayPasswordForVerifyCode
	method:post
	params:
		iloginAccountId	Integer	登录用户ID
		phone		String	手机号码
		verifyCode String	动态验证码
		fastPayPassword	String	快捷支付密码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		fastPayPassword String 快捷支付密码
		
71、 根据手机动态验证码修改快捷支付密码 (新增)   PasswordController.java  补天
	uri:/tzgCredit/investor/account/secure/upFastPayPasswordForVerifyCode
	method:post
	params:
		//iloginAccountId	Integer	登录用户ID
		phone		String	手机号码
		verifyCode String	动态验证码
		fastPayPassword	String	快捷支付密码
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		fastPayPassword String 快捷支付密码
		
72、 获取红包金额 (新增 IndexController.java 新客红包 金额)   补天 已废弃
	uri:/home/redamount
	method:post
	params:
	return:
		BaseRsponse	json string	
		
73、 体验标投资记录(修改)  ExperienceSubjectController  补天
	uri:/tzgCredit/experienceSubject/investlist
	method:post
	params:
		//iLoginAccountId Integer	登录用户ID
		pageSize	Integer	每页数量
		pageIndex	Integer	当前第几页
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	

74、 体验标详情(修改)   ExperienceSubjectController.java 补天已废弃
	uri:/experienceSubject/subjectdetail
	method:post
	params:
		id Integer	体验标ID
		uid		Integer 登录用户ID
	return:
		BaseRsponse	json string			
		subjectrecord
		
74、 体验标详情(修改)   ExperienceSubjectController.java 补天已废弃
	uri:/experienceSubject/subjectdetail/v2
	method:post
	params:
		id Integer	体验标ID
		//uid		Integer 登录用户ID
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string			
		subjectrecord		
	
		
		
75、 后端登录（兼容新旧用户）  已弃用
	uri:/backLogin
	method:post
	params:
		secId		String 手机终端唯一标识
		secret		String 旧的安全识别码(格式类似：Basic MTU4MjM2OTUyMzY6NzI1MmUwNTUxMjUxMjRiMjQyNmE5OGYwOGRiMWJjNjM=)
	return:
		BaseRsponse	json string			
		token		String 安全认证唯一标识
		
76、	APP 定期宝列表 (FixedBaoController.java   补天)
	uri: /fixedBao/project/list
	method:post
	params:
		token	String	用户ID
		loginStatus	String	"true or "false"
	return:
		BaseRsponse	json string	
		fixedBaoProjectList	Project对象列表
77、	APP 项目详情    FixedBaoController.java   补天
	uri: /fixedBao/project/detail
	method:post
	params:
		projectId	Integer	项目ID
	return:
		BaseRsponse	json string	
		fixedbaoproject 项目对象
		joinTime 购买加入
		lockTime 进入锁定期
		outLockTime 退出锁定期
		formatOutLockTime 退出锁定期 yyyy-MM-dd
		investCount 今日投资笔数
		investmentIncome 万元 预期收益
	throws:
		9998,"缺失参数"	
		160001,"项目不存在"	
		
		
78 定期宝项目详情底部状态  （ FixedBaoController.java  补天）
	uri:/fixedBao/projectBottomStatus
	method:post
	params:
		projectId Integer
		token	String
	return:
		BaseRsponse	json string
		status: true 亮色,false 灰色
		projectStatus: 
		            0:请登录后操作
					1:清算中
					2:立即投资
					5:余额不足,请充值
					3:已募满
					4:请设置交易密码
					20:借款人登录
		msg:显示消息: 请先设置手机支付密码,请设置交易密码,
		defaultInvestAmt:默认投资额
79、 投资交易  FixedBaoInvestController。java   补天
	uri:/tzgCredit/investor/asset/fixedbaoinvest/exchange
	method:post
	params:
		//iloginAccountId	Integer	用户ID
		projectId	Integer	项目Id
		investAmt	String	投资金额
		agreeProtocol	String 是否同意协议 true false字符串
		payPassword	String	交易密码
		investDevice	String (ios,android)
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		investrecordId	String	投资记录id
		expectEarnings	Double	预期收益
	throws:
80、今日项目投资记录：  FixedBaoController.java   补天  已弃用
	uri:/fixedBao/project/todayinvestRecord
	method:post
	params:
		projectId	Integer 项目ID
		pageSize	Integer	每页数量
		pageIndex	Integer	当前第几页
	return:
		BaseRsponse	json string	
		rows：[]	
	throws:
81、 我投资项目记录的详情   FixedBaoInvestController  补天  已弃用
	uri:/tzgCredit/investor/asset/fixedbaoinvest/detail/{id}
	method:get
	params:
		id	Integer	投资记录ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string
		fixedbaoinvestrecord Object	投资记录详情
	throws:
82、 投资验证   InvestController.java   补天
	uri:/tzgCredit/investor/asset/invest/exchange/checkInvest
	method:post
	params:
	//	iloginAccountId	Integer	用户ID
		subjectId	Integer	标的Id
		investAmt	String	投资金额
		redAmt	String  用户勾选的红包金额
		agreeProtocol	String 是否同意协议 true false字符串
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		code	为0表示验证通过,否则会有错误信息提示
83、资格审查   ExperienceSubjectController.java   补天   已弃用
	uri:/tzgCredit/experienceSubject/check
	method:post
	params:
		iLoginAccountId	Integer	用户ID
		iExperienceSubjectId	Integer	标的Id
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		code	为0表示验证通过,否则会有错误信息提示
83、资格审查   ExperienceSubjectController.java   补天   
	uri:/tzgCredit/experienceSubject/check/v2
	method:post
	params:
	//	iLoginAccountId	Integer	用户ID
		iExperienceSubjectId	Integer	标的Id
	//	secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		code	为0表示验证通过,否则会有错误信息提示		
		
		
84、投资回款记录   ExperienceSubjectController  补天
	uri:/tzgCredit/experienceSubject/repaylist
	method:post
	params:
		//iLoginAccountId	Integer	用户ID
		iState	Integer	状态
		pageIndex Integer	页码
		pageSize  Integer	总页数
		//secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		code	为0表示验证通过,否则会有错误信息提示
85、一次投资的回款记录   ExperienceSubjectController  补天
	uri:/tzgCredit/experienceSubject/oneInvestRepayRecord
	method:post
	params:
		//iLoginAccountId	Integer	用户ID
		iSubjectID	Integer	标的ID
		iInvestRecordID Integer	投资记录ID
		pageIndex Integer	页码
		pageSize  Integer	总页数
		//secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		code	为0表示验证通过,否则会有错误信息提示	
86：设置活期宝自动转入   SetupController。java  补天
	uri:/tzgCredit/investor/account/setup/setCurrentBaoAutoInvest
	method:post
	params:
	//	uid	Integer	用户ID
		iState  Integer 1 -- 有效；2 -- 无效。
	//	secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json boolean	false设置失败，true设置成功
	throws:
		9998,"缺失参数"	
		170001, "项目不存在"
        170002, "该项目已暂停"
	    170003, "清算中"
	    170004, "已额满"
	    170005, "余额不足，请充值"
        170006, "请设置手机支付密码"
87：活期宝转入详情   CurrentbaoController.java  补天
	uri:/tzgCredit/currentBao/project/detail
	method:post
	params:
		//uid	Integer	用户ID
		secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json currentbaoproject 表tbcurrentbaoproject  活期宝项目实体
		                 investeraccount 表tbinvesteraccount  投资人账户实体
		                 investmentIncome 万份收益
		                 daysFor7IncomeRate list[numY,dateX] 7日年化收益率  maxNumY 最大利率
		                 daysFor7Income list[numY,dateX] 7日收益  maxNumY 最大收益
		                 btnIntoDisplay 转入按钮是否可用，0不可用，1可用
		throws:
		9998,"缺失参数"	
		170001,"项目不存在"	
88：活期宝项目详情信息    补天 已弃用
	uri:/tzgCredit/currentBao/project/detailInfo
	method:post
	params:
		uid	Integer	用户ID
		secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json currentbaoproject 表tbcurrentbaoproject  活期宝项目实体
		throws:
		9998,"缺失参数"	
		170001,"项目不存在"
89：活期宝转入页面    CurrentbaoController.java  补天
	uri:/tzgCredit/currentBao/changeinto/page
	method:post
	params:
		//uid	Integer	用户ID
		secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json currentbaoproject 表tbcurrentbaoproject  活期宝项目实体
		                 numAvailable 账户余额
		                 numchangeintoAmt 当前可转入金额
		                 incomeDate  预计收益发放日
		                 autoTransferProtocol String 活期宝自动转入协议
		                 bottomState 按钮状态
		                             projectStatus: 
										            0:请登录后操作
													1:清算中
													2:立即投资
													5:余额不足,请充值
													3:已募满
													4:请设置交易密码
													20:借款人登录
		throws:
		9998,"缺失参数"	
		170001, "项目不存在"
        170002, "该项目已暂停"
	    170003, "清算中"
	    170004, "已额满"
	    170005, "余额不足，请充值"
        170006, "请设置手机支付密码"
90：活期宝资金明细   CurrentbaoInvestController.java  补天  已弃用
	uri:/tzgCredit/investor/asset/currentbaoinvest/funddetails/list
	method:post
	params:
		//iloginAccountId	Integer	用户ID
		itype String 列表类型 1：转入列表；2：转出列表；3收益列表
		pageIndex Integer
		pageSize Integer
		//secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json itype=1 list Currentbaoinvestrecord
		                 itype=2 list Currentbaorepayrecord
		                 itype=3 list Currentbaoincome
		throws:
91：活期宝金额转入   CurrentbaoInvestController.java  补天  
	uri:/tzgCredit/investor/asset/currentbaoinvest/changeinto
	method:post
	params:
		//iloginAccountId	Integer	用户ID
		investAmt String 转入金额
		payPassword String 快捷支付密码
		investDevice String 设备
		//secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json investrecordId Integer
		throws:
		       9998, "缺失参数"
		       13015, "金额不能为空"
		       13035, "手机支付密码不能为空"
		       13014, "请输入正确的金额,仅支持2位小数
		       13003, "请先进行实名认证"
		       13039, "请先设置手机支付密码
		       170001, "项目不存在"
		       11006, "账户被冻结，请联系客服"
		       15003, "余额不足，请充值"
		       13036, "手机支付密码错误"
		       170002, "该项目已暂停"
		       15006, "投资金额小于起投金额"
		       170003, "该项目在您支付成功前已募满"
		       15005, "投资金额超出可投资金额"
92：验证转入金额  CurrentbaoInvestController.java  补天  
	uri:/tzgCredit/investor/asset/currentbaoinvest/checkAmt
	method:post
	params:
		//iloginAccountId	Integer	用户ID
		investAmt String 转入金额
		//secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json investrecordId Integer
		throws:
		       9998, "缺失参数"
		       13015, "金额不能为空"
		       13035, "手机支付密码不能为空"
		       13014, "请输入正确的金额,仅支持2位小数
		       13003, "请先进行实名认证"
		       13039, "请先设置手机支付密码
		       170001, "项目不存在"
		       11006, "账户被冻结，请联系客服"
		       15003, "余额不足，请充值"
		       170002, "该项目已暂停"
		       15006, "投资金额小于起投金额"
		       170003, "该项目在您支付成功前已募满"
		       15005, "投资金额超出可投资金额"
93：活期宝金额转出
	uri:/tzgCredit/investor/asset/currentbaoinvest/transfer
	method:post
	params:
		//iloginAccountId	Integer	用户ID
		investAmt String 转入金额
		payPassword String 快捷支付密码
		transferDevice String 设备
		//secId	String 手机终端唯一标识
		token	String 安全认证唯一标识
	return:
		BaseRsponse	json investrecordId Integer
		                 arrivalDateTime 预计到账时间
		throws:
		       9998, "缺失参数"
		       13015, "金额不能为空"
		       13035, "手机支付密码不能为空"
		       13014, "请输入正确的金额,仅支持2位小数
		       13003, "请先进行实名认证"
		       13039, "请先设置手机支付密码
		       13036, "手机支付密码错误"
		       170001, "项目不存在"
		       11006, "账户被冻结，请联系客服"
		       15003, "余额不足，请充值"
		       170002, "该项目已暂停"
		       
94、资产-》查找已设置的自动投标记录    未启用

uri:/tzgCredit/investor/asset/autoinvest/find
	method:post
	params:
		iLoginAccountId	Integer	用户ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string	
		isSetupAutoinvest 	是否已设置自动投标，true：已设置；false:未设置；
		data  autoinvest	Object 自动投标记录对象，参考<a href="http://192.168.1.177:8080/db/index_files/inde64.htm#285">http://192.168.1.177:8080/db/index_files/inde64.htm#285</a>  
		
95、资产-》更新自动投标记录 （插入或者更新）   未启用
uri:/tzgCredit/investor/asset/autoinvest/save
	method:post
	params:
		iLoginAccountId	Integer	用户ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
		agreeProtocol	String 是否同意协议 true 已同意 false未同意
		numAccountLowAmt BigDecimal 账户保留余额
        numAmtMin BigDecimal 投资金额下限
        numAmtMax BigDecimal 投资金额上限
		numDayMin Integer 投资时间下限
		numDayMax Integer 投资时间上限
		numRateMin Integer 投资收益下限
		numRateMax  Integer  投资收益上限
		dtValidStr  String 设置生效结束时间  format:yyyy-MM-dd example:2015-12-25
		istate Integer 是否启用 1 -- 有效--启用；             2 -- 无效--停用

	return:
		BaseRsponse	json string	
        code	为0表示保存成功,否则会有错误信息提示
		
96、资产-》设置自动投标-》查看协议  未启用

	uri:/tzgCredit/investor/asset/autoinvest/protocol
	method:post
	params:
		iLoginAccountId	Integer	用户ID
		secId		String 手机终端唯一标识
		token		String 安全认证唯一标识
	return:
		BaseRsponse	json string
		data	String 注册协议url地址				

97、 发现页 首页		DiscoveryController.java   补天
	uri:/discovery/index
	method:post
	params:
		plat	String	app平台  “android”，“ios”
		vcChannel	String 活动渠道   可选参数
	return:
		BaseRsponse	json string
		bbsNewPostNumJob  String bbs今天新帖数量 
		bbsNewtPostTitle  String bbs最新一条帖子的标题，
		bbsURL String BBS入口链接
		pyramidURL    String 人人赚活动入口链接
		pyramidNews String 人人赚活动最新一条优惠信息
		activeListURL  String 活动页 链接
		activeNewsTitle  String 最新一条活动标题
		
	throws:		
	
	
98、发现页 ->活动页  DiscoveryController.java 补天
	uri: /discovery/activelist
	method:post
	params:
	    plat	String	app平台  “android”，“ios”
		vcChannel	String 活动渠道   可选参数
		pageSize	Integer	每页数量  
		pageIndex	Integer	当前第几页
	return:
		BaseRsponse	json string
		data	  
		        acitveList list 焦点图对象列表(进行中iState=1,已结束iState=2) 焦点图对象“<Focus>” 见<a href="http://192.168.1.177:8080/db/index_files/inde51.htm#213"> http://192.168.1.177:8080/db/index_files/inde51.htm#213</a>
		        imgUrlPrefix String 静态焦点图前缀地址
	throws:	

99 首页（推荐页） 弹出 客户端公告  （IndexController.java） 补天  已弃用  整合在 SubjectController.java /index/v2  

		uri: /home/appAnnouncement
		method:post
		params:
		return:
		BaseRsponse	json string
		data   appAnnouncement {id,vcTitle,vcContent,dtPublish,dtPublishStr }


		       
htm5页面
	下面URL前加wap的访问地址

1、关于我们（公司背景、团队背景、联系我们）
	url：/app/article/aboutUs
	

2、帮助中心
	url：/app/article/helpCenter
	

3、项目信息（项目介绍、安全保障、投资记录、还款计划）
	url：/app/subject/projectInfo/page?isubjectId=（项目ID）
	
4、安全保障
	url：/app/article/safety
	
</p>		
error code see: <a href="${ctx}/doc/error" >${ctx}/doc/error</a>

</pre>
<br/><br/><br/>	
</body>
</html>
