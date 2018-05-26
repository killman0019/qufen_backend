define(function (require) {
	var $ = require("jquery");
	require("./jquery.form");

	$.validator.addMethod("mobile", function(mobileNumber) {
		return mobileNumber && (/^1[34578][0-9]{9}$/g).test(mobileNumber);
	});

	var $form = $("#j_register_form_step_1");
	var $phone = $form.find('[name="phoneNumber"]');
	var $submitBtn = $form.find('[name="submity"]');

	$form.ajaxForm({
		validate: {
			rules: {
				phoneNumber: {
					required: true,
					mobile: true,
					remote: {
						url: basePath + "/register/invitePhone",
						data: {
							agreeProtocol: "true"
						},
						type: "post",
						checkFunction: function(element, data) {
							return data.success;
						}
					}
				},
				agreeProtocol: {
					required: true
				}
			},
			messages: {
				phoneNumber: {
					required: "请输入手机号码",
					mobile: "请输入正确的手机号码",
					remote: $("#j_register_phone_hint").html()
				},
				agreeProtocol: {
					required: "必须同意相关协议"
				}
			},
			errorPlacement: function($error, $formControl) {
				switch( $formControl.attr("name") ) {
					case "phoneNumber":
						$error.insertAfter($formControl.parents(".form-ipt-items"));
						break;

					case "agreeProtocol":
						$error.insertAfter($formControl.parent());
						break;

					default:
						$error.insertAfter($formControl);
				}
			}
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
		},
		success: function (data) {
			if (!data.success) {
				alert(data.msg || "请求出错");
				return;
			}

			var config = $.data(this, "ajaxFormConfig");
			config.cancelHandleComplete = true;

			var createForm = require("module/create-form");
			var newForm = createForm({
				formAction: basePath + data.redirectURL,
				formMethod: "post",
				formTarget: "",
				data: data.registerRequest
			});
			newForm.submit();
			return false;
			
		}
	});

	$phone.val("");
	$submitBtn.prop("disabled", false);

	$phone.on("focus", function() {
		$phone.parent().addClass("-focus");
	});
	$phone.on("blur", function() {
		$phone.parent().removeClass("-focus");
	});

	// 协议弹入弹出
	var $protocolPopup = $("#j_protocol_popup");
	$protocolPopup.find(".protocol-popup_ft .button").on("click", function (e) {
		e.preventDefault();
		$protocolPopup.removeClass("-opened");
	});

	$("#j_open_protocol_btn").on("click", function (e) {
		e.preventDefault();
		$protocolPopup.addClass("-opened");
	});
});