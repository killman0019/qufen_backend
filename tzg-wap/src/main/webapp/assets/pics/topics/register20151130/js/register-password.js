define(function (require) {
	var $ = require("jquery");
	require("./jquery.form");
	
	var $form = $("#j_register_form_step_2");
	var $phone = $form.find('[name="phoneNumber"]');
	var phone = $phone.val();

	// 发送验证码
	(function() {
		require("module/countdown");

		var $sendCaptchaBtn = $("#j_send_captcha");
		$sendCaptchaBtn.prop("disabled", false);
		$sendCaptchaBtn.countdown({
			eventType: "click",
			countDownOverMsg: "重新获取",
			beforeCountdown: function($element, config) {
				if( !phone ) {
					tzg.showMessage("页面超时");
					return false;		
				}
			},
			request: {
				url: basePath + "/dynamicValidateCode/send",
				data: {
					phone: phone,
					bus: 'register'
				},
				successHandler: function (data) {}
			}
		});

		$sendCaptchaBtn.triggerHandler("click");
	})();

	// 表单验证
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
				password: {
					required: true,
					tpassword: true
				}
			},
			messages: {
				dynamicVerifyCode: {
					required: "请输入验证码",
					length: "验证码不正确",
					digits: "验证码不正确",
					remote: "验证码错误，请核对"
				},
				password: {
					required: "请输入密码"
				}
			},
			// errorPlacement: function($error, $formControl) {
			// 	$error.appendTo($formControl.parents(".form-ipt-item"));
			// },
			errorLabelContainer: "#j_error_label_container"
		},
		submitButtonLoading: function ($button) {
			if ($button && $button instanceof $ && $button.length) {
				$button.prop("disabled", true).addClass("-loading");
			}
		},
		submitButtonRecovery: function($button) {
			if ($button && $button instanceof $ && $button.length) {
				$button.prop("disabled", false).removeClass("-loading");
			}
		}
	});

	$("[data-icon] input").on("focus", function() {
		$(this).parents(".form-ipt-item").addClass("-focus");
	}).on("blur", function() {
		$(this).parents(".form-ipt-item").removeClass("-focus");
	});
});
