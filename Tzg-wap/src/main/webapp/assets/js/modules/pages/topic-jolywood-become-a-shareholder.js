/*--------------------------*\
	中来入股专题 Page
	/topics/20150511
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	var $form = $("#j_register_form");
	var $phone = $form.find("[name=phoneNumber]");
	var $captcha = $form.find("[name=dynamicVerifyCode]");
	var $password = $form.find("[name=password]");
	var $dialogCloseButton = $("#j_dialog_close");

	var $dialog = $("#j_dialog");
	var $dialogContent = $("#j_dialog_content");

	$dialog.on(tzg.config.CLICK_EVENT, "[j-action-close]", function() {
		$dialog.hide();
	});

	function _alert(msg) {
		$dialogContent.text(msg || "");
		$dialog.show();
		$dialogCloseButton.focus();
	}

	var validator = {
		check_phoneNumber: function () {
			var msg = "";
			var v = $phone.val();

			if (v == "") {
				msg = "请输入手机号码";
			}
			else if (!(/^1[0-9]{10}$/g).test(v)) {
				msg = "手机号码不正确";
			}

			return msg;
		},

		check_dynamicVerifyCode: function () {
			var msg = "";
			var v = $captcha.val();

			if (v == "") {
				msg = "请输入验证码";
			}

			return msg;
		},

		check_password: function () {
			var msg = "";
			var v = $password.val();

			if (v == "") {
				msg = "请输入密码";
			}
			else if (!(/^\S{8,20}$/g).test(v)) {
				msg = "密码由8-20位数字、字母及非空格常用符号组成";
			}

			return msg;
		}
	};

	$form.submit(function(e) {
		e.preventDefault();

		var msg = "";
		$.each(["phoneNumber", "dynamicVerifyCode", "password"], function(i, name) {
			msg = validator["check_" + name]();
			if (msg) {
				return false;
			}
		});

		if (msg) {
			_alert(msg);
		}
		else {
			// 发送请求
			var queryStr = $form.serialize() + "&agreeProtocol=true&confirmPassword=" + $password.val();

			$.ajax($form.attr("action"), {
				dataType: "json",
				type: $form.attr("method"),
				data: queryStr,
				context: $form.get(0),
				success: function(data) {
					if (data.success) {
						self.location.href = data.redirectURL;
					}
					else {
						_alert(data.msg);
					}
				},
				failed: function() {
					_alert("对不起，请求出错，请重试");
				}
			});
		}
	});

	// 验证码倒计时
	require("module/countdown");
	var $sendCaptchaBtn = $("#j_send_captcha");
	$sendCaptchaBtn.countdown({
		eventType: tzg.config.CLICK_EVENT,
		countDownOverMsg: "重新获取",
		beforeCountdown: function($element, config) {
			var msg = validator.check_phoneNumber();
			if( msg ) {
				_alert(msg);
				return false;
			}
		},
		request: {
			url: basePath + "/dynamicValidateCode/send",
			data: {phone: function(){return $phone.val();}, bus: 'register'}
		}
	});
});
