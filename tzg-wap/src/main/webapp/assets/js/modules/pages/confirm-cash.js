/*--------------------------*\
	提现确认
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var $form = $("#j_confirm_cash_form");
	
	// 验证码倒计时
	require("module/countdown");
	var $sendCaptchaBtn = $("#j_send_captcha");
	$sendCaptchaBtn.countdown({
		eventType: clickEventType,
		countDownOverMsg: "重新获取",
		request: {
			url: basePath + "/dynamicValidateCode/sendByLoginAccount",
			data: {bus: 'cash'}
		}
	});

	// 地区联动菜单
	require("module/jquery.region-selectors");
	var $selectors = $form.find("select");
	$selectors.regionSelectors();

	$form.ajaxForm({
		validate: {
			rules: {
				bankProvince: {
					required: true
				},
				cityCode: {
					required: true
				},
				brabankName: {
					required: true
				},
				payPassword: {
					required: true
				},
				dynamicVerifyCode: {
					required: true,
					digits: true,
					length: 6
				}
			},
			messages: {
				bankProvince: {
					required: "请选择所在省份"
				},
				cityCode: {
					required: "请选择所在城市"
				},
				brabankName: {
					required: "请输入开户行名称"
				},
				payPassword: {
					required: "请输入交易密码"
				},
				dynamicVerifyCode: {
					required: "请输入验证码",
					length: "验证码不正确",
					digits: "验证码不正确"
				}
			}
		}
	});
});

