/*-------------------------------*\
	Forget Password First Step
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var validates = require("module/validate");
	$.validator.addMethod("mobile", validates.checkMobile);

	var $form = $("#j_forget_pay_password_form");
	var $phone = $form.find("[name=phoneNumber]");

	// 验证码倒计时
	require("module/countdown");
	var $sendCaptchaBtn = $("#j_send_captcha");
	$sendCaptchaBtn.countdown({
		eventType: clickEventType,
		countDownOverMsg: "重新获取",
		beforeCountdown: function($element, config) {
			if( !$phone.valid() ) {
				tzg.showMessage( $("#phoneNumber-error").text() );
				return false;		
			}
		},
		request: {
			url: basePath + "/dynamicValidateCode/send",
			data: {phone: function(){return $phone.val();}, bus: 'forgetPayPassword'}
		}
	});

	$form.ajaxForm({
		// successMessage: "登录成功",
		validate: {
			rules: {
				vcCardCode: {
					required: true,

					remote: {
						url: basePath + "/user/payPassword/verifyCardCode",
						checkFunction: function(element, data) {
							return data.success;
						}
					}
				},
				phoneNumber: {
					required: true,
					mobile: true
				},
				dynamicVerifyCode: {
					required: true,
					digits: true,
					length: 6,
					remote: {
						url: basePath + "/dynamicValidateCode/verifyCodeWithNoLogin",
						data:{bus:'forgetPayPassword'},
						paramName: "code",
						extraFormParams: ["phoneNumber"],
						checkFunction: function(element, data) {
							return data.success;
						}
					}
				}
			},
			messages: {
				vcCardCode: {
					required: "请输入身份证号",
					remote: "身份证号不正确"
				},
				phoneNumber: {
					required: "请输入手机号码",
					mobile: "手机号码不正确"
				},
				dynamicVerifyCode: {
					required: "请输入验证码",
					length: "验证码不正确",
					digits: "验证码不正确",
					remote: "验证码错误,请重新获取"
				}
			}
		}
	});
});
