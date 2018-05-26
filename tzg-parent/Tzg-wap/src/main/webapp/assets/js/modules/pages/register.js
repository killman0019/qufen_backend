/*--------------------------*\
	Register Page
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var validates = require("module/validate");
	$.validator.addMethod("mobile", validates.checkMobile);

	var $form = $("#j_register_form");
	var $phone = $form.find("[name=phoneNumber]");

	// 协议弹出
	var $popupProtocol = $("#j_protocol_popup");
	$popupProtocol.find("[pppclosebtn]").on(clickEventType, function () {
		$popupProtocol.removeClass("z-opened");
	});
	var $protocolButton = $("#j_protocol_btn");
	$protocolButton.on(clickEventType, function () {
		$popupProtocol.addClass("z-opened");
	});
	$form.ajaxForm({
		//successMessage: "操作成功",
		validate: {
			rules: {
				phoneNumber: {
					required: true,
					mobile: true,
					remote: {
					 	url: basePath + "/register/validPhone",
					 	checkFunction: function(element, data) {
					 		return data.success;
					 	}
					}
				},
				password: {
					required: true,
					tpassword: true
				},
				confirmPassword: {
					required: true,
					equalTo: "input[name=password]"
				},
				dynamicVerifyCode: {
					required: true,
					digits: true,
					length: 6,
					remote: {
						url: basePath + "/dynamicValidateCode/verifyCodeWithNoLogin",
						data:{bus: 'register'},
						paramName: "code",
						extraFormParams: ["phoneNumber"],
						checkFunction: function(element, data) {
							return data.success;
						}
					}
				},
				validcode: {
					required: true,
					length: 4
				},
				agreeProtocol: {
					required: true
				}
			},
			messages: {
				phoneNumber: {
					required: "请输入手机号码",
					mobile: "手机号码不正确",
					remote: "手机号码已经存在"
				},
				password: {
					required: "请输入密码"
				},
				confirmPassword: {
					required: "请再次输入密码",
					equalTo: "两次输入的密码不一致"
				},
				dynamicVerifyCode: {
					required: "请输入验证码",
					length: "验证码不正确",
					digits: "验证码不正确",
					remote: "验证码错误,请重新获取"
				},
				validcode: {
					required: "请输入验证码",
					length: "验证码不正确"
				},
				agreeProtocol: {
					required: "必须同意相关协议"
				}
			}
		}
	});

	// 验证码倒计时
	(function() {
		require("module/countdown");
		var $picCapchaItem = $("#j_pic_captcha");
		$picCapchaItem.hide();
		var $captchaPic = $picCapchaItem.find(".m-picCpt_pic");
		var $picCaptchaInput = $picCapchaItem.find(".m-picCpt_ipt");
		$picCaptchaInput.val("");

		var $captchaInput = $("#j_captcha_ipt");
		var originalURL = $captchaPic.attr("src");
		$captchaPic.on("click", function() {
			$captchaPic.attr("src", originalURL + "?m="+ Math.random());
		});

		// 手机号变化则刷新图片验证码显示
		$phone.on( "oninput" in $phone.get(0) ? "input" : "propertychange", function(e) {
			if (e.type === "input" || e.type === "propertychange" && e.originalEvent.propertyName == "value") {
				$picCapchaItem.hide();
			}
		});


		var $sendCaptchaBtn = $("#j_send_captcha");
		$sendCaptchaBtn.countdown({
			eventType: clickEventType,
			countDownOverMsg: "重新获取",
			beforeCountdown: function($element, config) {
				if( !$phone.valid() ) {
					tzg.showMessage( $("#phoneNumber-error").text() );
					return false;		
				}
				if ($picCapchaItem.css("display") != "none" && !$picCaptchaInput.valid()) {
					tzg.showMessage( $("#validcode-error").text() );
					return false;
				}
			},
			request: {
				url: basePath + "/dynamicValidateCode/sendwithCaptcha",
				data: {
					phone: function(){return $phone.val();},
					bus: 'register',
					token: function(){return $captchaInput.data("captcha-token");},
					validcode: function(){return $picCaptchaInput.val();}
				},
				successHandler: function (data) {
					// 显示/隐藏图片验证码
					$picCapchaItem[data.isShowPicCaptch ? "show" : "hide"]();
					// 刷新token
					$captchaInput.data("captcha-token", data.token);
					// 验证码错误
					if (data.picCaptchaWrong) {
						$captchaPic.attr("src", originalURL + "?m="+ Math.random());
					}
				}
			}
		});
	})();
});
