/*-------------------------------*\
	Forget Password First Step
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var validates = require("module/validate");
	$.validator.addMethod("mobile", validates.checkMobile);

	var $form = $("#j_phone_change_first_form");
	var $phone = $form.find("[name=phoneNumber]");
	//var $oldPhone = $form.find("[name=oldPhoneNumber]");
	
	// 验证码倒计时
	require("module/countdown");
	var $sendCaptchaBtn = $("#j_send_captcha");
	$sendCaptchaBtn.addClass("z-gray").css("cursor", "no-drop");
	$sendCaptchaBtn.countdown({
		eventType: clickEventType,
		countDownOverMsg: "重新获取",
		beforeCountdown: function($element, config) {
			if ($sendCaptchaBtn.hasClass("z-gray")) {
				return false;
			}
			if( !$phone.valid() ) {
				tzg.showMessage( $("#phoneNumber-error").text() );
				return false;		
			}
		},
		request: {
			url: basePath + "/dynamicValidateCode/send",
			data: {phone: function(){return $phone.val();}, bus: 'phoneNumberChange'}
		}
	});

	$form.ajaxForm({
		// successMessage: "登录成功",
		validate: {
			rules: {
				phoneNumber: {
					required: true,
					mobile: true,
					remote: {
					 	url: basePath + "/user/phoneChange/isOldPhone",
					 	checkFunction: function(element, data) {
					 		if (data.success) {
					 			$sendCaptchaBtn.removeClass("z-gray").css("cursor", "pointer");
					 		}
					 		else {
					 			$sendCaptchaBtn.addClass("z-gray").css("cursor", "no-drop");
					 		}
					 		return data.success;
					 	}
					}
				},
				dynamicVerifyCode: {
					required: true,
					digits: true,
					length: 6,
					remote: {
						url: basePath + "/dynamicValidateCode/verifyCodeWithNoLogin",
						data:{bus:'phoneNumberChange'},
						paramName: "code",
						extraFormParams: ["phoneNumber"],
						checkFunction: function(element, data) {
							return data.success;
						}
					}
				}
			},
			messages: {
				phoneNumber: {
					required: "请输入原手机号码",
					mobile: "手机号码不正确",
					remote: "原手机号错误"
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
