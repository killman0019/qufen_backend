/*-------------------------------*\
	Email Authen Second Step
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var $form = $("#j_email_authen");

	$form.ajaxForm({
		successMessage: "邮箱认证信息已发送，请尽快登录邮箱确认。",
		validate: {
			rules: {
				vcEmail: {
					required: true
				}
			},
			messages: {
				vcEmail: {
					required: "请输入邮箱"
				}
			}
		}
	});
});
