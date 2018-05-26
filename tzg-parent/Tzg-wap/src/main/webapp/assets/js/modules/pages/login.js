/*--------------------------*\
	Login Page
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var $form = $("#j_login_form");
	$form.attr("action", $form.attr("action") + ".json");

	$form.ajaxForm({
		successMessage: "登录成功",
		validate: {
			rules: {
				loginName: {
					required: true
				},
				password: {
					required: true
				}
			},
			messages: {
				loginName: {
					required: "请输入用户名"
				},
				password: {
					required: "请输入密码"
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
					var redirectURL = data[config.returnRedirectName];
					setTimeout(function () { self.location.replace(redirectURL.indexOf("http") === 0 ?   redirectURL : basePath+redirectURL) }, 800);
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
	
	// 链接添加 toURL
	var attachQuery = require("module/attachQuery");
	attachQuery("toURL", $("[j-tourl-link]"));
});
