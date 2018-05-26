/*-------------------------------*\
	real Name Authen Second Step
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var $form = $("#j_real_name_authen");

	$form.ajaxForm({
		// successMessage: "登录成功",
		validate: {
			rules: {
				vcName: {
					required: true
				},
				vcCardCode: {
					required: true
				}
			},
			messages: {
				vcName: {
					required: "请输入姓名"
				},
				vcCardCode: {
					required: "请输入身份证号"
				}
			}
		}
	});
});
