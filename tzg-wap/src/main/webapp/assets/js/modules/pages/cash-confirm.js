/*--------------------------*\
	提现 确认
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	var $form = $("#j_cash_confirm_form");

	// 表单提交
	// ----------------------------------------------------------
	require("module/jquery.form");

	var v = $form.ajaxForm({
		successMessage: false,
		validate: {
			rules: {
				phone: {
					required: true,
					mobile: true
				},
				dynamicVerifyCode: {
					required: true,
					digits: true,
					length: 6
				},
				payPassword: {
					required: true
				}
			},
			messages: {
				phone: {
					required: "请输入手机号码",
					mobile: "手机号码不正确"
				},
				dynamicVerifyCode: {
					required: "请输入验证码",
					length: "验证码不正确",
					digits: "验证码不正确"
				},
				payPassword: {
					required: "请输入交易密码"
				}
			},
			errorPlacement: function(error, element) {
				switch( element.attr("name") ) {
					case "dynamicVerifyCode":
						error.insertAfter(element.parents(".captcha"));
						break;

					default:
						error.insertAfter(element);
				}
			},
			submitHandler: function (form) {
				var $form = $(form);
				var config = $form.data("ajaxFormConfig");
				var queryStr = $form.serialize();

				$.ajax($form.attr("action"), {
					dataType: "json",
					type: $form.attr("method"),
					data: queryStr,
					context: form,
					success: config.successHandler,
					failed: config.failedHandler
				});

				$form.find(":submit").attr("disabled", true).html('<i class="u-fntIco--ldg"></i>');
			}
		},
		success: function(data) {
			if (data.redirectURL) {
				self.location.href = basePath + data.redirectURL;
				return false;
			}
		}
	});


	// 验证码
	// ----------------------------------------------------------
	require("module/countdown");

	var sendCaptchaParam = {};
	$.each(["bankCardId", "cardNo"], function(i, v) {
		sendCaptchaParam[v] = $form.find("[name="+ v +"]").val();
	});

	var $sendCaptchaBtn = $("#j_send_captcha");
	$sendCaptchaBtn.countdown({
		eventType: "click",
		countDownOverMsg: "重新获取",
		request: {
			url: basePath + "/user/cash/getcashverificationbyself",
			data: sendCaptchaParam
		}
	});
});

