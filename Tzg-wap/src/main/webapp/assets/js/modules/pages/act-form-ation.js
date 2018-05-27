/*-------------------------------*\
	人人赚 活动落地页
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	require("module/countdown");

	var $actLogin_form = $("#j_actLogin-form"),
		$phone = $actLogin_form.find("[name=phoneNumber]");

	$.validator.addMethod("mobile", function(mobileNumber) {
		return mobileNumber && (/^1[0-9]{10}$/g).test(mobileNumber);
	});

	// 验证码倒计时
	var $sendCaptchaBtn = $("#j_send_captcha");
	$sendCaptchaBtn.countdown({
		eventType: "click",
		countDownOverMsg: "重新获取",
		beforeCountdown: function($element, config) {
			if( !$phone.valid() ) {
				//alert( $("#phoneNumber-error").text() );
				return false;
			}
		},
		request: {
			url: basePath + "/dynamicValidateCode/send",
			data: {phone: function(){return $phone.val();}, bus: 'register'}
		}
	});

	// 新手福利包 - 领取福利包
	$actLogin_form.ajaxForm({
		validate: {
			rules: {
				dynamicVerifyCode: {
					required: true,
					number: true
				},
				password: {
					required: true
				}
			},
			messages: {
				dynamicVerifyCode: {
					required: "请输入验证码",
					number: "转出金额不正确"
				},
				password: {
					required: "请输入登录密码"
				}
			}
		},
		successHandler: function (data) {
			if(data.success){
				self.location.href = basePath + "/app/pyramid/actSuccess";
			}else{
				$actLogin_form.find(":submit").toDefaultStatu();
				alert(data.msg);
			}
		}
	});
});
