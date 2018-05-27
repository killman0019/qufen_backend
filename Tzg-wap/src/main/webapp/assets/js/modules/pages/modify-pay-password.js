/*-------------------------------*\
	Modify Pay Password Second Step
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var $form = $("#j_modif_pay_password");

	$form.ajaxForm({
		// successMessage: "登录成功",
		validate: {
			rules: {
				oldPassword: {
					required: true,
					rangelength: [8, 20]
				},
				newPassword: {
					required: true,
					tpassword: true
				},
				confirmNewPassword: {
					required: true,
					equalTo: "input[name=newPassword]"
				}
			},
			messages: {
				oldPassword: {
					required: "请输入原交易密码",
					rangelength: "交易密码不正确"
				},
				newPassword: {
					required: "请输入新密码"
				},
				confirmNewPassword: {
					required: "请再次输入新密码",
					equalTo: "两次输入的密码不一致"
				}
			}
		}
	});
});
