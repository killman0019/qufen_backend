/*--------------------------*\
	注册第三步（密码）
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
	require("module/jquery.form");

	$.validator.addMethod("invitecode", function(v) {
		return !v || v && (/^[0-9]{6}$/g).test(v);
	});

	var $form = $("#j_register_3_form");
	var $submitBtn = $form.find('[name="submity"]');

	$form.ajaxForm({
		validate: {
			rules: {
				password: {
					required: true,
					tpassword: true
				},
				inviteCode: {
					invitecode: true
				},
				agreeProtocol: {
					required: true
				}
			},
			messages: {
				password: {
					required: "请输入密码"
				},
				inviteCode: {
					invitecode: "邀请码应为6位数字，请核对"
				},
				agreeProtocol: {
					required: "必须同意相关协议"
				}
			},
			errorPlacement: function(error, element) {
				switch( element.attr("name") ) {
					case "agreeProtocol":
						error.insertAfter(element.parents("div:first"));
						break;

					default:
						error.insertAfter(element);
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
					setTimeout(function () { self.location.replace(redirectURL.indexOf("http") === 0 ? redirectURL : basePath+redirectURL); }, 800);
				}
				else {
					$form.find(":submit").toDefaultStatu();
				}
			}
			else {
				$form.find(":submit").toDefaultStatu();
				tzg.showMessage(data.msg);
				if (config.redirect && data[config.returnRedirectName]) {
					var redirectURL = data[config.returnRedirectName];
					setTimeout(function () { self.location.replace(redirectURL.indexOf("http") === 0 ? redirectURL : basePath+redirectURL); }, 1200);
				}
			}
		}
	});

	$submitBtn.prop("disabled", true);

	var $password = $form.find("[name=password]");
	var $agreeProtocol = $form.find("[name=agreeProtocol]");
	var eventType = "oninput" in $password.get(0) ? "input" : "propertychange";
	var eventHandler = function(e) {
		if (e.type === "input" || e.type === "click" || e.type === "propertychange" && e.originalEvent.propertyName === "value") {
			$submitBtn.prop("disabled", !($password.val() && $agreeProtocol.prop("checked")));
		}
	};

	$password.val("").on(eventType, eventHandler);
	$agreeProtocol.on("click", eventHandler);

	// 协议弹出
	var $popupProtocol = $("#j_protocol_popup");
	$popupProtocol.find("[pppclosebtn]").on("click", function () {
		$popupProtocol.removeClass("z-opened");
	});
	var $protocolButton = $("#j_protocol_btn");
	$protocolButton.on("click", function () {
		$popupProtocol.addClass("z-opened");
	});
});
