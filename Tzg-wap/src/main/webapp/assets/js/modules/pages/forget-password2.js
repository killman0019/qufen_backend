/*-------------------------------*\
	Forget Password Second Step
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var $form = $("#j_forget_password_2_form");

	$form.ajaxForm({
		// successMessage: "登录成功",
		validate: {
			rules: {
				password: {
					required: true,
					tpassword: true
				},
				confirmPassword: {
					required: true,
					equalTo: "input[name=password]"
				}
			},
			messages: {
				password: {
					required: "请输入密码"
				},
				confirmPassword: {
					required: "请再次输入密码",
					equalTo: "两次输入的密码不一致"
				}
			}
		}
	});
});
