/*--------------------------*\
	注册第二步（验证码）
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
	require("module/jquery.form");

	$.validator.addMethod("mobile", function(mobileNumber) {
		return mobileNumber && (/^1[0-9]{10}$/g).test(mobileNumber);
	});

	var $form = $("#j_register_2_form");
	var $captcha = $("#j_captcha_ipt");
	var $submitBtn = $form.find('[name="submity"]');
	var phone = $form.find('[name="phoneNumber"]').val();

	$form.ajaxForm({
		validate: {
			rules: {
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
				}
			},
			messages: {
				dynamicVerifyCode: {
					required: "请输入验证码",
					length: "验证码不正确",
					digits: "验证码不正确",
					remote: "验证码错误,请重新获取"
				},
				validcode: {
					required: "请输入验证码",
					length: "验证码不正确"
				}
			}
		},
		successHandler: function (data) {
			var form = this;
			var $form = $(form);
			var config = $form.data("ajaxFormConfig");

			if (config.success && $.isFunction(config.success) && config.success.call(form, data) === false) {
				return;
			}

			if (data.success) {
				if (config.successMessage !== false) {
					tzg.showMessage(config.successMessage);
				}

				if (config.redirect && data[config.returnRedirectName]) {
					setTimeout(function () { self.location.href = basePath + data[config.returnRedirectName];}, 800);
				}
				else {
					$form.find(":submit").toDefaultStatu();
				}
			}
			else {
				$form.find(":submit").toDefaultStatu();
				alert(data.msg);
			}
		}
	});

	$captcha.val("");
	$submitBtn.prop("disabled", true);

	$captcha.on( "oninput" in $captcha.get(0) ? "input" : "propertychange", function(e) {
		if (e.type === "input" || e.type === "propertychange" && e.originalEvent.propertyName === "value") {
			$submitBtn.prop("disabled", !$captcha.val());
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

		var $sendCaptchaBtn = $("#j_send_captcha");
		$sendCaptchaBtn.countdown({
			eventType: "click",
			countDownOverMsg: "重新获取",
			beforeCountdown: function($element, config) {
				if( !phone ) {
					tzg.showMessage("页面超时");
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
					phone: phone,
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

		// 默认发送
		$sendCaptchaBtn.click();
	})();
});
