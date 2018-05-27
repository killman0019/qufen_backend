/*--------------------------*\
	注册第一步（手机号）
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
	require("module/jquery.form");

	$.validator.addMethod("mobile", function(mobileNumber) {
		return mobileNumber && (/^1[34578][0-9]{9}$/g).test(mobileNumber);
	});

	var $form = $("#j_register_1_form");
	var $phone = $form.find('[name="phoneNumber"]');
	var $submitBtn = $form.find('[name="submity"]');

	$form.ajaxForm({
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
				}
			},
			messages: {
				phoneNumber: {
					required: "请输入手机号",
					mobile: "请输入正确的手机号",
					remote: "该号码已注册，请直接登录"
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

	$phone.val("");
	$submitBtn.prop("disabled", true);

	$phone.on( "oninput" in $phone.get(0) ? "input" : "propertychange", function(e) {
		if (e.type === "input" || e.type === "propertychange" && e.originalEvent.propertyName === "value") {
			$submitBtn.prop("disabled", !$phone.val());
		}
	});
	
	// 链接添加 toURL
	var attachQuery = require("module/attachQuery");
	attachQuery("toURL", $("[j-tourl-link]"));
});
